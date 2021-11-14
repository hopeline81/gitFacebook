package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.entity.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface ForgotPasswordService {
    void updateResetPasswordToken(String token, String email);
    void updatePassword(User user, String newPassword);
    User getByResetPasswordToken(String token);
    void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;
    String hashPassword(String password);
}
