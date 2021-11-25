package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.ForgotPasswordService;
import com.example.facebookdemo.service.contrack.UserService;
import com.example.facebookdemo.util.GetURLUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController extends BaseController {

    private ForgotPasswordService forgotPasswordService;
    private UserService userService;

    @Autowired
    public ForgotPasswordController(ForgotPasswordService forgotPasswordService, UserService userService) {
        this.forgotPasswordService = forgotPasswordService;
        this.userService = userService;
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
        return "forgot-password-form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        if(!forgotPasswordService.isEmailExist(email)) {
            model.addAttribute("message", "This email didn't exist. First need register");
            return "message";
        }
        String token = RandomString.make(30);

        try {
            forgotPasswordService.updateResetPasswordToken(token, email);
            String resetPasswordLink = GetURLUtil.getSiteURL(request) + "/reset_password?token=" + token;
            forgotPasswordService.sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("message", "Error while sending email");
            return "message";
        }
        return "forgot-password-form";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = forgotPasswordService.getByResetPasswordToken(token);

        if (user == null) {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reset your password");
        return "reset-password-form";
    }

    @PostMapping("/reset_password")
    public ModelAndView processResetPassword(HttpServletRequest request, Model model) throws ServletException {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        User user = forgotPasswordService.getByResetPasswordToken(token);

        if (user == null) {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid Token");
            return redirect("message");
        }
        String encodedPassword = forgotPasswordService.hashPassword(password);
        forgotPasswordService.updatePassword(user, encodedPassword);
        model.addAttribute("message", "You have successfully changed your password.");
        request.login(user.getEmail(), password);
        UserDTO userDTO = userService.createNewUserDTO(user);

        return redirect("profile", "user", userDTO);
    }
}
