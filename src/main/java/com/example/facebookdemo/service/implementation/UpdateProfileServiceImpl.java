package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.service.contrack.UpdateProfileService;
import com.example.facebookdemo.service.contrack.ChangeUserEmailService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service
@Transactional
public class UpdateProfileServiceImpl implements UpdateProfileService {

    private final UserService userService;
    private final ProfileRepository profileRepository;
    private final ChangeUserEmailService changeUserEmailService;

    public UpdateProfileServiceImpl(UserService userService, ProfileRepository profileRepository, ChangeUserEmailService changeUserEmailService) {
        this.userService = userService;
        this.profileRepository = profileRepository;
        this.changeUserEmailService = changeUserEmailService;
    }

    @Override
    public Profile updateProfileDetails(User user, Profile profile, UserDTO userDTO, String code) throws MessagingException, UnsupportedEncodingException {
        Profile newProfile = user.getProfile();

        changeAddress(profile, userDTO, newProfile);
        if(!userDTO.getEmail().equals(user.getEmail())) {
            changeEmail(user, user.getProfile(), userDTO, code);
        }
        changePassword(user, userDTO);
        profileRepository.save(newProfile);

        return newProfile;
    }

    @Override
    public void changeEmail(User user, Profile profile, UserDTO userDTO, String verificationCode) throws MessagingException, UnsupportedEncodingException {
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            user.setEmail(user.getEmail());
        }else if (userDTO.getEmail() != null || !userDTO.getEmail().isEmpty()) {
            if(!user.getEmail().equals(userDTO.getEmail())) {
                changeUserEmailService.updateEmail(userDTO.getEmail(), user.getVerificationCode());
            }
            user.setEmail(user.getEmail());
        }
    }

    private void changePassword(User user, UserDTO userDTO) {
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            user.setPassword(user.getPassword());
        }else if(userDTO.getPassword() != null || !userDTO.getPassword().isEmpty()) {
            if (!userDTO.getPassword().equals(userDTO.getPasswordRepeat())) {
                throw new IllegalArgumentException("Password didn't match");
            }
            userService.updatePassword(user, userDTO.getPassword());
        }
    }

    private void changeAddress(Profile profile, UserDTO userDTO, Profile newProfile) {
        if (userDTO.getAddress() == null || userDTO.getAddress().isEmpty()) {
            newProfile.setAddress(profile.getAddress());
        }else if(userDTO.getAddress() != null || !userDTO.getAddress().isEmpty()){
            newProfile.setAddress(userDTO.getAddress());
        }
    }
}
