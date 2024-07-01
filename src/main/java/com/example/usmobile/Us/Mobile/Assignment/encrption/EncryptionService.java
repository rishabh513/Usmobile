//package com.example.usmobile.Us.Mobile.Assignment.encrption;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EncryptionService {
//    private final PasswordEncoder passwordEncoder;
//
//    public EncryptionService() {
//        this.passwordEncoder = new BCryptPasswordEncoder();
//    }
//
//    public String encodePassword(String password) {
//        return passwordEncoder.encode(password);
//    }
//
//    public boolean matches(String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }
//
//}
