package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView searchUsers(@Param("firstName") String firstName , Model model) {
        List<User> searchResult = userService.searchByFirstName(firstName);
        model.addAttribute("firstName", firstName);
        model.addAttribute("searchResult", searchResult);
        return send ("search_result", "searchResult", searchResult);
    }

    @GetMapping("/search_result")
    public ModelAndView searchResult(@Param("firstName") String firstName) {
        List<User> searchResult = userService.searchByFirstName(firstName);
        return send ("search_result", "searchResult", searchResult);
    }
}
