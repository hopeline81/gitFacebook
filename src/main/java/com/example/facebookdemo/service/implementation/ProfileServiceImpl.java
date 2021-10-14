package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.ImageDTO;
import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.dto.RegisterDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.service.contrack.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;


    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile createProfile(RegisterDTO registerDTO){
        Profile profile = new Profile();
        profile.setFullName(registerDTO.getUsername());
        profile.setEmail(registerDTO.getEmail());
        profile.setAddress(registerDTO.getAddress());
        profile.setAge(String.valueOf(registerDTO.getAge()));
        profileRepository.save(profile);
        return profile;
    }

    public Profile updateAvatar(Long profileId, Image avatarUrl){
        Optional<Profile> profile = profileRepository.findById(profileId);
        Profile updatedProfile;
        if(profile.isPresent()){
            profile.get().setImage(avatarUrl);
            updatedProfile = profileRepository.save(profile.get());
        }else {
            throw new IllegalArgumentException();
        }
        return updatedProfile;
    }

    @Override
    public Profile getProfile(String email) {
        return profileRepository.findFirstByEmail(email);
    }
}
