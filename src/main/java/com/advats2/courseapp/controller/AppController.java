package com.advats2.courseapp.controller;

import com.advats2.courseapp.model.Educator;
import com.advats2.courseapp.model.User;
import com.advats2.courseapp.config.MyUserDetailsService;
import com.advats2.courseapp.model.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {
    private final UserRepository userRepository;
    private final MyUserDetailsService myUserDetailsService;

    public AppController(UserRepository userRepository, MyUserDetailsService myUserDetailsService) {
        this.userRepository = userRepository;
        this.myUserDetailsService = myUserDetailsService;
    }

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "you are student";
    }

    @GetMapping("/educators")
    public String allEducators(Model model) {
        List<Educator> educators = userRepository.getAllEducators();
        model.addAttribute("educators", educators);
        return "educators";
    }
}
