package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.exception.InvalidEmailException;
import com.example.facebookdemo.exception.InvalidPasswordException;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.security.auth.login.LoginException;

@Controller
public class UserController extends BaseController implements WebMvcConfigurer {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/login")
    public ModelAndView login() {
        return send("login.html");
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/login")
    public ModelAndView login(@Validated @ModelAttribute("user") UserDTO userDTO
            , BindingResult result) throws LoginException {
        if (result.hasErrors()) {
            throw new LoginException();
        }
        return redirect("profile");
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/register")
    public ModelAndView register(@Validated @ModelAttribute("user") UserDTO userDTO
            , BindingResult result
            , Model model) throws InvalidPasswordException, InvalidEmailException {
        if (result.hasErrors()) {
            model.addAttribute("message", "Please try again." );
            return send("message");
        }
        userService.register(userDTO);
        return redirect("login");
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/register")
    public ModelAndView register() {
        return send("register");
    }
}