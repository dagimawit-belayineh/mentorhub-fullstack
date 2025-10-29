package com.mentorHub.mentorHub.auth.controller.util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.*;
import java.util.*;

import java.security.PrivateKey;

public class JwtUtil {
    public static String generateClientAssertion(String clientId, String tokenEndpoint, PrivateKey privateKey){
      long now = System.currentTimeMillis();
      return Jwts.builder()
              .setIssuer(clientId)
              .setSubject(clientId)
              .setAudience(tokenEndpoint)
              .setExpiration(new Date(now + 300000)) // 5 minutes
              .setIssuedAt(new Date(now))
              .signWith(privateKey, SignatureAlgorithm.RS256)
              .compact();
    }
}
