package com.example.facebookdemo.service.contrack;
import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface ChangeUserEmailService {
    void updateEmail(String email, String code) throws MessagingException, UnsupportedEncodingException;
    void sendVerificationEmail(String email, String code) throws MessagingException, UnsupportedEncodingException;
    User getByVerificationCode(String code);
    UserDTO createNewUserDTO(User user);
}
