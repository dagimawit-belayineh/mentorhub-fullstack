package com.mentorHub.mentorHub.payment.controller;

import com.mentorHub.mentorHub.payment.dto.PaymentRequestDto;
import com.mentorHub.mentorHub.payment.service.PaymentService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

 import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

     @PostMapping("/initialize")
    public ResponseEntity<?> initializePayment(@RequestBody PaymentRequestDto request) {
        try {
            String checkoutUrl = paymentService.initializePayment(request);
            return ResponseEntity.ok(Map.of("checkout_url", checkoutUrl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

     @GetMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestParam String tx_ref) {
        boolean isVerified = paymentService.verifyPayment(tx_ref);
        if (isVerified) {
            return ResponseEntity.ok(Map.of("message", "Payment verified successfully", "tx_ref", tx_ref));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Payment verification failed"));
        }
    }
}
