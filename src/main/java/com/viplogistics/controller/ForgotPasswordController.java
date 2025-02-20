package com.viplogistics.controller;

import com.viplogistics.jwt.JwtUtils;
import com.viplogistics.service.IEmailService;
import com.viplogistics.service.IMyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin("*")
public class ForgotPasswordController {

    private final JwtUtils jwtUtil;

    private final IEmailService emailService;

    private final IMyUserService userService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        if (!userService.getMyUserByUserName(email)) {
            return ResponseEntity.badRequest().body("Email not found");
        }
        String resetToken = jwtUtil.generatePasswordResetToken(email);
        String resetLink = "https://viplogistics.org/email-verify?token=" + resetToken;

        emailService.sendPasswordResetMail(email, resetLink);

        return ResponseEntity.ok("Password reset link has been sent to your email.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        String email = jwtUtil.extractUsername(token);
        if (email == null || jwtUtil.isTokenExpired(token)) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        boolean success = userService.updatePassword(email, newPassword);
        if (success) {
            return ResponseEntity.ok("Password has been successfully reset.");
        } else {
            return ResponseEntity.badRequest().body("Password reset failed.");
        }
    }
}