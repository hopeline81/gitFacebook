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

import java.io.IOException;
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
        profile.setFullName(userDTO.getUsername());
        profile.setEmail(userDTO.getEmail());
        profile.setAddress(userDTO.getAddress());
        profile.setAge(String.valueOf(userDTO.getAge()));
        profileRepository.save(profile);
        return profile;
    }

    public Profile updateAvatar(Long profileId, String avatarUrl){
        Optional<Profile> profile = profileRepository.findById(profileId);
        Profile updatedProfile;
        if(profile.isPresent()){
            profile.get().setPhoto(avatarUrl);
//            TODO fix
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
