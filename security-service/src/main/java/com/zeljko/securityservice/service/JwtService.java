package com.zeljko.securityservice.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }


    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                // Set the content of the token, which consists of claims (information)
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // The token will be valid for 30 minutes from the issuance time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                // Sign the token using the key and signature algorithm
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String getSecret() {
        // Retrieve the secret from the environment variable or configuration management system
        return System.getenv("SECRET");
    }

}
