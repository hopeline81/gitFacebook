package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.service.contrack.ChangeProfileService;
import com.example.facebookdemo.service.contrack.ChangeUserEmailService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service
@Transactional
public class ChangeProfileServiceImpl implements ChangeProfileService {

    private final UserService userService;
    private final ProfileRepository profileRepository;
    private final ChangeUserEmailService changeUserEmailService;

    public ChangeProfileServiceImpl(UserService userService, ProfileRepository profileRepository, ChangeUserEmailService changeUserEmailService) {
        this.userService = userService;
        this.profileRepository = profileRepository;
        this.changeUserEmailService = changeUserEmailService;
    }

    @Override
    public Profile updateProfileDetails(User user, Profile profile, UserDTO userDTO, String code) throws MessagingException, UnsupportedEncodingException {
        Profile newProfile = user.getProfile();
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            user.setUsername(user.getUsername());
        }else {
            user.setUsername(userDTO.getUsername());
        }

        if (userDTO.getAddress() == null || userDTO.getAddress().isEmpty()) {
            newProfile.setAddress(profile.getAddress());
        }else if(userDTO.getAddress() != null || !userDTO.getAddress().isEmpty()){
            newProfile.setAddress(userDTO.getAddress());
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            user.setEmail(user.getEmail());
        }else if (userDTO.getEmail() != null || !userDTO.getEmail().isEmpty()) {
            if(!user.getEmail().equals(userDTO.getEmail())) {
                changeUserEmailService.updateEmail(userDTO.getEmail(), user.getVerificationCode());
            }else if(user.getEmail().equals(userDTO.getEmail())) {
                user.setEmail(user.getEmail());
            }
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
