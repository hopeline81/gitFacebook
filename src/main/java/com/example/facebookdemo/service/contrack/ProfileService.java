package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.RegisterDTO;
import com.example.facebookdemo.entity.Profile;

import java.util.List;

public interface ProfileService {
    Profile createProfile(RegisterDTO registerDTO);
    void getProfile(Long id);

//    List<Profile> allProfiles();
}
