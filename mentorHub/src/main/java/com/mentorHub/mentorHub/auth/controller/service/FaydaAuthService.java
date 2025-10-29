package com.mentorHub.mentorHub.auth.controller.service;
import com.mentorHub.mentorHub.auth.controller.model.FaydaTokenResponse;
import com.mentorHub.mentorHub.auth.controller.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
public class FaydaAuthService {
    @Value("${fayda.client-id}") private String clientId;
    @Value("${fayda.redirect-uri}") private String redirectUri;
    @Value("${fayda.token-endpoint}") private String tokenEndpoint;
    @Value("${fayda.userinfo-endpoint}") private String userInfoEndpoint;
    @Value("${fayda.private-key-base64}") private String privateKeyBase64;
    private final RestTemplate restTemplate = new RestTemplate();

    public FaydaTokenResponse exchangeCodeForTokens(String code) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyBase64);
        PrivateKey privateKey = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));

        String clientAssertion = JwtUtil.generateClientAssertion(clientId, tokenEndpoint, privateKey);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri);
        body.add("client_id", clientId);
        body.add("client_assertion", clientAssertion);
        body.add("client_assertion_type",
                "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<FaydaTokenResponse> response =
                restTemplate.postForEntity(tokenEndpoint, request, FaydaTokenResponse.class);

        return response.getBody();
    }

    public Map<String, Object> fetchUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                userInfoEndpoint, HttpMethod.GET, entity, Map.class);
        return response.getBody();
    }

}
