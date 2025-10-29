package com.mentorHub.mentorHub.auth.controller;
import com.mentorHub.mentorHub.auth.controller.model.FaydaTokenResponse;
import com.mentorHub.mentorHub.auth.controller.service.FaydaAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final FaydaAuthService faydaAuthService;

    @Value("${fayda.client-id}") private String clientId;
    @Value("${fayda.redirect-uri}") private String redirectUri;
    @Value("${fayda.authorization-endpoint}") private String authorizationEndpoint;

    public AuthController(FaydaAuthService faydaAuthService) {
        this.faydaAuthService = faydaAuthService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        String state = UUID.randomUUID().toString();
        String codeChallenge = Base64.getEncoder().encodeToString("sample-code-challenge".getBytes());

        String url = authorizationEndpoint +
                "?client_id=" + clientId +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri +
                "&scope=openid profile email" +
                "&state=" + state +
                "&code_challenge=" + codeChallenge +
                "&code_challenge_method=S256";

        return ResponseEntity.ok(url);
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam String code, @RequestParam String state) throws Exception {
        FaydaTokenResponse tokens = faydaAuthService.exchangeCodeForTokens(code);
        Map<String, Object> userInfo = faydaAuthService.fetchUserInfo(tokens.getAccessToken());
        return ResponseEntity.ok(userInfo);
    }
}
