package com.example.authentication_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Date;
import java.security.Key;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET = "MRzI9jKHW3aK0Gz1a4x+KQcU+/EEZOuoHg4ucgAU72Fn+BzTNO5KdnpkzZbzvkW7";

    public String generateTokenForUser(String username, Collection<? extends GrantedAuthority> authorities) {

        Map<String, Object> claims = new HashMap<>();

        List<String> roles = authorities.stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .collect(Collectors.toList());

        claims.put("authorities",roles);

        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()*1000*60*30))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    public String extractUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<String> extractAuthoritiesFromToken(String token) {
        return extractClaim(token, claims -> claims.get("authorities", List.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }
}
