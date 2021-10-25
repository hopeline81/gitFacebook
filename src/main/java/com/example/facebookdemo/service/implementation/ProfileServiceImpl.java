package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.ProfileService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService  {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
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

    @Override
    public ProfileDTO createNewProfileDTO(String email) {
        User user1 = userRepository.findUserByEmail(email);
        Profile profile = user1.getProfile();

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(user1.getEmail());
        profileDTO.setFullName(profile.getFullName());
        profileDTO.setAge(Integer.parseInt(profile.getAge()));
        profileDTO.setAddress(profile.getAddress());

        return profileDTO;
    }

}
