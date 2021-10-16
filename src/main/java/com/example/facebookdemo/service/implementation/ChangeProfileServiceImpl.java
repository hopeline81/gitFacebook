package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.ChangeProfileService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChangeProfileServiceImpl implements ChangeProfileService {

    private final UserService userService;
    private final ProfileRepository profileRepository;

    public ChangeProfileServiceImpl(UserService userService, ProfileRepository profileRepository) {
        this.userService = userService;
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile updateProfileDetails(User user, Profile profile, UserDTO userDTO) {
        Profile newProfile = user.getProfile();
        if(userDTO.getUsername() != null || !userDTO.getUsername().isEmpty()) {
            newProfile.setFullName(userDTO.getUsername());
        }
        if(userDTO.getAddress() != null || !userDTO.getAddress().isEmpty()) {
            newProfile.setAddress(userDTO.getAddress());
        }
        if(userDTO.getEmail() != null || !userDTO.getEmail().isEmpty()) {
            if(userDTO.getEmail().equals(userDTO.getEmailRepeat()))
            newProfile.setEmail(userDTO.getEmail());
        }
        if(userDTO.getAge() != null || userDTO.getEmail().isEmpty()) {
            newProfile.setAge(String.valueOf(userDTO.getAge()));
        }
        if(userDTO.getPassword() != null || !userDTO.getPassword().isEmpty()){
            if(userDTO.getPassword().equals(userDTO.getPasswordRepeat())){
                userService.updatePassword(user, userDTO.getPassword());
            }
        }
        profileRepository.save(newProfile);
        return newProfile;
    }
}
