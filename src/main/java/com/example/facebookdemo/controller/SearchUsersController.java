package com.example.facebookdemo.controller;

import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchUsersController extends BaseController {

    private final UserService userService;

    @Autowired
    public SearchUsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ModelAndView search() {
        return send ("search");
    }

    @PostMapping("/search")
    public ModelAndView searchUsers(@RequestParam("name") String name , Model model) {
        List<User> searchResult = userService.searchByNameAndSort(name, Sort.by(Sort.Direction.ASC, "firstName"));

        model.addAttribute("name", name);
        model.addAttribute("searchResult", searchResult);
        return send ("search_result", "searchResult", searchResult);
    }
}
