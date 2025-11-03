package com.mentorHub.mentorHub.payment.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull(message= "first name can not be null")
    @Size(min = 5 , max = 50)
    private String firstName;
    @NotNull(message= "last name can not be null")
    @Size(min = 5 , max = 50)
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @NotNull(message= "fayda number must not be null")
    private String faydaNumber;
    private boolean verified  = false;
    private String role;

}
