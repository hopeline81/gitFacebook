package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        profile.setFullName(userDTO.getFirstName(), userDTO.getLastName());
        profile.setEmail(userDTO.getEmail());
        profile.setAddress(userDTO.getAddress());
        profile.setAge(String.valueOf(userDTO.getAge()));
        profileRepository.save(profile);

        return profile;
    }

    public Profile updateAvatar(Long profileId, String avatarImageUrl){
        Optional<Profile> profile = profileRepository.findById(profileId);
        Profile updatedProfile;
        if(profile.isPresent()){
            profile.get().setAvatarImageUrl(avatarImageUrl);
            updatedProfile = profileRepository.save(profile.get());
        }else {
            throw new IllegalArgumentException();
        }
        return updatedProfile;
    }

    @Override
    public ProfileDTO createNewProfileDTO(String email) {
        User user1 = userRepository.findUserByEmail(email);
        Profile profile = user1.getProfile();

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(user1.getEmail());
        profileDTO.setFullName(user1.getFirstName(), user1.getLastName());
        profileDTO.setAge(user1.getAge());
        profileDTO.setAddress(profile.getAddress());
        profileDTO.setImageUrl(profile.getAvatarImageUrl());

        return profileDTO;
    }
}
