package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.entity.User;

public interface ForgotPasswordService {
    void updateResetPasswordToken(String token, String email);
    void updatePassword(User user, String newPassword);
    User getByResetPasswordToken(String token);
}
