package com.example.facebookdemo.service.contrack;
import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Profile;

public interface ProfileService {
    Profile createProfile(UserDTO userDTO);
    ProfileDTO createNewProfileDTO(String email);
}
