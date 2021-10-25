package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface ChangeProfileService {
    Profile updateProfileDetails(User user, Profile profile, UserDTO userDTO, String code) throws MessagingException, UnsupportedEncodingException;
}
