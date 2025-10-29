package com.mentorHub.mentorHub.auth.controller.model;

 import lombok.Data;
@Data
 public class FaydaTokenResponse {
 private String accessToken;
 private String tokenType;
 private String idToken;
 private String expireIn;

}
