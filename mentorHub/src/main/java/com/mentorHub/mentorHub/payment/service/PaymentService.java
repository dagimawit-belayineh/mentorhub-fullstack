package com.mentorHub.mentorHub.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentorHub.mentorHub.payment.dto.PaymentRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
@Service
public class PaymentService {
    @Value("${chapa.secret-key}")
    private String secretKey;

    @Value("${chapa.callback-url}")
    private String callbackUrl;

    private static final String CHAPA_URL = "https://api.chapa.co/v1/transaction/initialize";
    private final RestTemplate restTemplate = new RestTemplate();

    public String initializePayment(PaymentRequestDto request) throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("amount", request.getAmount());
        body.put("email", request.getEmail());
        body.put("first_name", request.getFirstName());
        body.put("last_name", request.getLastName());
        body.put("tx_ref", request.getTxRef());
        body.put("callback_url", callbackUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + secretKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(CHAPA_URL, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.getBody());
            return json.path("data").path("checkout_url").asText(); // Chapa redirect link
        }

        throw new Exception("Payment initialization failed: " + response.getBody());
    }

    public boolean verifyPayment(String txRef) {
        String verifyUrl = "https://api.chapa.co/v1/transaction/verify/" + txRef;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(verifyUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(response.getBody());
                return "success".equalsIgnoreCase(json.path("data").path("status").asText());
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
