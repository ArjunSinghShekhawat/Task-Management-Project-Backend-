package com.taskManagement.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.*;

public class JwtProvider {

    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public static String generateToken(Authentication authentication){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = populateAuthorities(authorities);
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+86400000))
                .claim("email",authentication.getName())
                .claim("authorities",role)
                .signWith(key)
                .compact();
    }
    public static String populateAuthorities( Collection<? extends GrantedAuthority>authorities){
        Set<String> auths = new HashSet<>();
        for(GrantedAuthority authority:authorities){
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }
    public static String getEmail(String jwt){
        if(jwt!=null){
            jwt=jwt.substring(7);
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
            String email = String.valueOf(claims.get("email"));

            if(email!=null){
                return email;
            }
            throw new BadCredentialsException("email is null !");
        }
        throw new BadCredentialsException("jwt token is null !");
    }
}
