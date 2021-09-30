package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.RegisterDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.repository.PostRepository;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.service.contrack.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile createProfile(RegisterDTO registerDTO) {
        Profile profile = new Profile();
        profile.setFullName(registerDTO.getUsername());
        profile.setEmail(registerDTO.getEmail());
        profile.setAddress(registerDTO.getAddress());
        profile.setAge(String.valueOf(registerDTO.getAge()));
//        profile.setUserImage(imageUploadService.uploadImage());
        profileRepository.save(profile);
        return profile;
    }

    @Override
    public Profile getProfile(String email) {
        return profileRepository.findFirstByEmail(email);
    }
}
