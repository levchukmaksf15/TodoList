package com.softserve.itacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "home";
    }

}