package com.example.api_gateway_service.service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET = "MRzI9jKHW3aK0Gz1a4x+KQcU+/EEZOuoHg4ucgAU72Fn+BzTNO5KdnpkzZbzvkW7";

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
