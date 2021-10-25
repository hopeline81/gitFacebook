package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.User;

public interface UserService {

    User register(UserDTO userDTO);

    void updatePassword(User user, String newPassword);
}
