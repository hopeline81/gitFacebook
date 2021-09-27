package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.RegisterDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.ProfileService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private ProfileRepository profileRepository;
    private UserRepository userRepository;
    //  private ImageUploadService imageUploadService;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
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
    public void getProfile(Long id) {
    }
//
//    @Override
//    public List<Profile> allProfiles() {
//        List<Profile> all = profileRepository.findAll();
//        return all;
//    }
}
