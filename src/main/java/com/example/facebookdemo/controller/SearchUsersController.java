package com.example.facebookdemo.controller;

import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class SearchUsersController extends BaseController {

    private final UserService userService;

    @Autowired
    public SearchUsersController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public ModelAndView search() {
        return send("search");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/search")
    public ModelAndView searchUsers(@AuthenticationPrincipal User user,
                                    @RequestParam("name") String name, Model model) {
        User user1 = userService.loadUserByUsername(user.getEmail());
        List<User> searchResult = userService.searchByNameAndSort(name, Sort.by(Sort.Direction.ASC, "firstName"));
        List<User> resultWithoutCurrentUser = searchResult.stream()
                .filter(u -> !Objects.equals(u.getEmail(), user1.getEmail()))
                .collect(Collectors.toList());

        model.addAttribute("name", name);
        model.addAttribute("searchResult", searchResult);
        return send("search_result", "searchResult", resultWithoutCurrentUser);
    }
}
