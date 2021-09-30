package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.dto.RegisterDTO;
import com.example.facebookdemo.entity.User;

public interface UserService {

    User register(RegisterDTO registerDTO, PostDTO postDTO);
}
