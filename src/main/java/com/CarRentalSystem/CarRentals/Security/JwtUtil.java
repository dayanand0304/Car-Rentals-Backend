package com.CarRentalSystem.CarRentals.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secretKey="mysecretkeymysecretkeymysecretkey";

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    //GENERATE TOKEN
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //EXTRACT EMAIL
    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }

    //TOKEN EXPIRATION CHECK
    private boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    //VALIDATE TOKEN
    public boolean isTokenValid(String token,String email){
        return email.equals(extractEmail(token)) && !isTokenExpired(token);
    }
}
