package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.RegisterDTO;
import com.example.facebookdemo.entity.User;

public interface UserService {
    //    String login(String username,String password) throws InvalidUsernameException, InvalidPasswordException;
    User register(RegisterDTO registerDTO);
}
