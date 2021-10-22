package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.ChangeProfileService;
import com.example.facebookdemo.service.contrack.ChangeUserEmailService;
import com.example.facebookdemo.service.contrack.ProfileService;
import com.example.facebookdemo.service.contrack.UserService;
import com.example.facebookdemo.service.implementation.UtilityGetURL;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Controller
public class ChangeProfileController extends BaseController {

    private ChangeProfileService changeProfileService;
    private UserRepository userRepository;
    private ChangeUserEmailService userEmailService;


    public ChangeProfileController(ChangeProfileService changeProfileService,
                                   UserRepository userRepository,
                                   ChangeUserEmailService userEmailService) {
        this.changeProfileService = changeProfileService;
        this.userRepository = userRepository;
        this.userEmailService = userEmailService;
    }

    @GetMapping("/profile-update")
    public ModelAndView viewDetails(@AuthenticationPrincipal User user) {


        UserDTO userDTO = userEmailService.createNewUserDTO(user);

        return send("profile-update", "user", userDTO);
    }

    @PostMapping("/profile-update")
    public String saveDetails(@AuthenticationPrincipal User user,
                                    @ModelAttribute("user") UserDTO userDTO,
                                    Model model) throws IOException, MessagingException {

        model.addAttribute("user", userDTO);
        user.setProfile(changeProfileService.updateProfileDetails(user, user.getProfile(), userDTO, user.getVerificationCode()));
        return "change-email-success";
    }

    @GetMapping("/verify")
    public ModelAndView processChangeEmail(@RequestParam("email") String email, Model model,
                                       HttpServletRequest request,
                                       @RequestParam("code") String code) throws ServletException {

        User user1 = userEmailService.getByVerificatonCode(code);
        if (user1 != null) {
            user1.setEmail(email);
            user1 = userRepository.save(user1);
        }
        UserDTO userDTO = userEmailService.createNewUserDTO(user1);
        model.addAttribute("user", userDTO);
        request.logout();
        return redirect("");
    }
}
