package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.ChangeProfileService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChangeProfileServiceImpl implements ChangeProfileService {

    private final UserService userService;
    private final ProfileRepository profileRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ChangeProfileServiceImpl(UserService userService, ProfileRepository profileRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.profileRepository = profileRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Profile updateProfileDetails(User user, Profile profile, UserDTO userDTO) {
        Profile newProfile = user.getProfile();
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            newProfile.setFullName(user.getUsername());
        }else {
            newProfile.setFullName(userDTO.getUsername());
        }

        if (userDTO.getAddress() == null || userDTO.getAddress().isEmpty()) {
            newProfile.setAddress(profile.getAddress());
        }else if(userDTO.getAddress() != null || !userDTO.getAddress().isEmpty()){
            newProfile.setAddress(userDTO.getAddress());
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            newProfile.setEmail(user.getEmail());
        }else if (userDTO.getEmail() != null || !userDTO.getEmail().isEmpty()) {
            if(!userDTO.getEmail().equals(userDTO.getEmailRepeat())) {
                throw new IllegalArgumentException("Email didn't match");
            }
            newProfile.setEmail(userDTO.getEmail());
        }

        if (userDTO.getAge() == null || userDTO.getEmail().isEmpty()) {
            newProfile.setAge(String.valueOf(user.getAge()));
        }else if(userDTO.getAge() != null || !userDTO.getEmail().isEmpty()){
            newProfile.setAge(String.valueOf(userDTO.getAge()));
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            user.setPassword(user.getPassword());
        }else if(userDTO.getPassword() != null || !userDTO.getPassword().isEmpty()) {
            if (!userDTO.getPassword().equals(userDTO.getPasswordRepeat())) {
                throw new IllegalArgumentException("Password didn't match");
            }
            userService.updatePassword(user, userDTO.getPassword());
        }

        profileRepository.save(newProfile);
        return newProfile;
    }
}
