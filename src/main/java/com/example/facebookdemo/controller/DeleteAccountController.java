package com.example.facebookdemo.controller;

import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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


    @GetMapping("/delete_acount")
    public ModelAndView deleteAcount(@AuthenticationPrincipal User user, HttpServletRequest request) throws ServletException {

        userService.deleteUser(user);
        request.logout();
        return redirect("");
    }
}
