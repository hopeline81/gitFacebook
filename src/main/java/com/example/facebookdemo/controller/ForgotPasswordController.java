package com.example.facebookdemo.controller;

import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.ForgotPasswordService;
import com.example.facebookdemo.service.implementation.UtilityGetURL;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController extends BaseController {

    private JavaMailSender mailSender;
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    public ForgotPasswordController(JavaMailSender mailSender, ForgotPasswordService forgotPasswordService) {
        this.mailSender = mailSender;
        this.forgotPasswordService = forgotPasswordService;
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
        return "forgot-password-form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            forgotPasswordService.updateResetPasswordToken(token, email);
            String resetPasswordLink = UtilityGetURL.getSiteURL(request) + "/reset_password?token=" + token;
            forgotPasswordService.sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
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
            return redirect("message") ;
        } else {
            forgotPasswordService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password.");
        }
        return redirect("profile");
    }
}
