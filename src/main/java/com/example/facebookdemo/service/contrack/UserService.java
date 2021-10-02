package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.dto.RegisterDTO;
import com.example.facebookdemo.entity.User;

public interface UserService {

    User register(RegisterDTO registerDTO, PostDTO postDTO);
    void updateResetPasswordToken(String token, String email);
    User getByResetPasswordToken(String token);
    void updatePassword(User user, String newPassword);
}
