package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.service.contrack.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService  {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile createProfile(UserDTO userDTO) {
        Profile profile = new Profile();
        profile.setFullName(userDTO.getUsername());
        profile.setEmail(userDTO.getEmail());
        profile.setAddress(userDTO.getAddress());
        profile.setAge(String.valueOf(userDTO.getAge()));
     //   profile.setPhoto(imageUploadService.uploadImage());
        profileRepository.save(profile);
        return profile;
    }

    @Override
    public Profile getProfile(String email) {
        return profileRepository.findFirstByEmail(email);
    }

}
