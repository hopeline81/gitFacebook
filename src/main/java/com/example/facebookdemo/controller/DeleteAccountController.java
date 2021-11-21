package com.example.facebookdemo.controller;

import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class DeleteAccountController extends BaseController {

    private final UserService userService;

    @Autowired
    public DeleteAccountController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete_account")
    public ModelAndView deleteAccount(@AuthenticationPrincipal User user, HttpServletRequest request) throws ServletException {

        User user1 = userService.loadUserByUsername(user.getEmail());
        userService.deleteUser(user1);
        request.logout();
        return redirect("");
    }
}
