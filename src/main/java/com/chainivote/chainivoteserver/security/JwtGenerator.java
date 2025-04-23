package com.chainivote.chainivoteserver.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtGenerator {
    // Khóa bí mật được dùng để ký và xác minh JWT.
    // Có khóa này là có thể tạo hoặc xác minh token.
    private static final SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        String roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));


        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + SecurityConstants.JWT_EXPIRATION);

        return Jwts.builder().subject(username).claim("roles", roles).issuedAt(now).expiration(expiryDate)
                // Ký token với khóa bí mật bằng thuật toán SHA-512.
                .signWith(key, Jwts.SIG.HS512).compact();
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getUsernameFromJWT(String token) {
        try {
            Jwt<?, Claims> jwt = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return jwt.getPayload().getSubject();
        } catch (JwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token", ex);
        }
    }

    public String getRolesFromJWT(String token) {
        try {
            Jwt<?, Claims> jwt = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return jwt.getPayload().get("roles", String.class);
        } catch (JwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token", ex);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect", ex);
        }
    }
}
