package com.viplogistics.service;

public interface IEmailService {

    void sendPasswordResetMail(String toEmail, String resetLink);
}
