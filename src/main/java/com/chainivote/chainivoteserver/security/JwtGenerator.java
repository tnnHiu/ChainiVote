package com.chainivote.chainivoteserver.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtGenerator {


    // Khóa bí mật được dùng để ký và xác minh JWT.
    // Có khóa này là có thể tạo hoặc xác minh token.
    private static final SecretKey key = Keys.hmacShaKeyFor(
            SecurityConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8)
    );

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + SecurityConstants.JWT_EXPIRATION);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                // Ký token với khóa bí mật bằng thuật toán SHA-512.
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        try {
            Jwt<?, Claims> jwt = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return jwt.getPayload().getSubject();
        } catch (JwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token", ex);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect", ex);
        }
    }
}
