package com.example.ecommerceApp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ecommerceApp.model.LocalUser;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

@Value("${jwt.algorithm.key}")
private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

@Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

private Algorithm algorithm;

@PostConstruct
    public void postConstruct() {
    algorithm = Algorithm.HMAC256(algorithmKey);

}

private static final String USERNAME_KEY = "USERNAME";

public String generateJwt(LocalUser localUser){
    return JWT.create()
            .withClaim(USERNAME_KEY, localUser.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * expiryInSeconds))
            .withIssuer(issuer)
            .sign(algorithm);
}

    public String getUserName(String token) {
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }



};

