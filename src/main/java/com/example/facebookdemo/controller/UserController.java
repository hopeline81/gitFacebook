package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.RegisterDTO;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
//    @PreAuthorize("!isAuthenticated()")
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public ModelAndView register(@ModelAttribute("user") RegisterDTO registerDTO){
//        userService.register(registerDTO);
//        return redirect("login");
//    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/login")
    public ModelAndView login() {
        return send("login.html");
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/login")
    public ModelAndView login(@Validated @ModelAttribute("user") RegisterDTO registerDTO
            , BindingResult result
            , RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", registerDTO);
            return redirect("register");
        }
        userService.register(registerDTO);
        return redirect("login");
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/register")
    public ModelAndView register(@Validated @ModelAttribute("user") RegisterDTO registerDTO
            , BindingResult result
            , RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", registerDTO);
            return redirect("register");
        }
        userService.register(registerDTO);
        return redirect("profile");
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/register")
    public ModelAndView register() {
        return send("register");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ModelAndView profile(Principal principal) {
        return send("profile", "username", principal.getName());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/my-page")
    public ModelAndView myPage() {
        return send("profile");
    }
}