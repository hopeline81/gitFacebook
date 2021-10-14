package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.ImageDTO;
import com.example.facebookdemo.dto.RegisterDTO;
import com.example.facebookdemo.entity.Profile;

public interface ProfileService {
    Profile createProfile(RegisterDTO registerDTO);
    Profile getProfile(String email);
}
