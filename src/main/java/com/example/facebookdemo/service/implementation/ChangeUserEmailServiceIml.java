package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.ChangeUserEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service
@Transactional
public class ChangeUserEmailServiceIml implements ChangeUserEmailService {

    private UserRepository userRepository;
    private JavaMailSender mailSender;
    public static final String LINK = "http://localhost:8080";


    @Autowired
    public ChangeUserEmailServiceIml(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void updateEmail(String email, String code) throws UsernameNotFoundException,
            MessagingException,
            UnsupportedEncodingException {
       sendVerificationEmail(email, code);
    }

    @Override
    public User getByVerificationCode(String code) {

        return userRepository.findByVerificationCode(code);
    }

    @Override
    public void sendVerificationEmail(String email, String code) throws MessagingException, UnsupportedEncodingException {
        String subject = "Here's the link to verify your email";
        String verifyLink = LINK + "/verify?code=" + code + "&email=" + email;
        String content = "<p>Hello,</p>"
                + "<p>Click the link below to verify your email:</p>"
                + "<p><a href=\"" + verifyLink + "\">Verify my email</a></p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("hopelinesss@gmail.com", "Nadezhda");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public UserDTO createNewUserDTO(User user){
        User user1 = userRepository.findUserByEmail(user.getEmail());
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setUsername(user1.getFirstName(), user1.getLastName());
        userDTO1.setEmail(user1.getEmail());
        userDTO1.setAddress(user1.getProfile().getAddress());
        userDTO1.setAge(user1.getAge());
        return userDTO1;
    }
}
