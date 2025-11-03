package com.mentorHub.mentorHub.payment.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
@Entity
public class User {
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
