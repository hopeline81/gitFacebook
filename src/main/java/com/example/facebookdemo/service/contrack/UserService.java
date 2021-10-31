package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.User;

import java.util.List;

public interface UserService {

    User register(UserDTO userDTO);
    UserDTO createNewUserDTO(User user);
    void updatePassword(User user, String newPassword);
    List<User> searchByFirstName(String firstName);
}
