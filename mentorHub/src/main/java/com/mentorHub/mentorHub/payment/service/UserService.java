package com.mentorHub.mentorHub.payment.service;

import com.mentorHub.mentorHub.auth.controller.service.FaydaAuthService;
import com.mentorHub.mentorHub.payment.dto.UserDto;
import com.mentorHub.mentorHub.payment.model.User;
import com.mentorHub.mentorHub.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private FaydaAuthService authService;
    private BCryptPasswordEncoder passwordEncode;
 public UserService(UserRepository userRepository, FaydaAuthService authService) {
     this.userRepository = userRepository;
     this.authService = authService;
 }
 public User registerUser(UserDto userDto){
     if (userRepository.findByEmail(userDto.getEmail()).isPresent()){
         throw new RuntimeException("Email already exists");

     }
     User user = new User();
     user.setFirstName(userDto.getFirstName());
     user.setLastName(userDto.getLastName());
     user.setEmail(userDto.getEmail());
     user.setFaydaNumber(userDto.getFaydaNumber());
     user.setRole(userDto.getRole() != null ? userDto.getRole() : "USER");
     return userRepository.save(user);
 }
 }

