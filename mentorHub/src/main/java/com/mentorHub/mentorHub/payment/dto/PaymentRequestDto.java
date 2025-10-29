package com.mentorHub.mentorHub.payment.dto;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private String email;
    private String amount;
    private String firstName;
    private String lastName;
    private String txRef;
}
