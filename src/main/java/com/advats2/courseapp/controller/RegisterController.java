package com.advats2.courseapp.controller;

import com.advats2.courseapp.config.MyUserDetails;
import com.advats2.courseapp.config.MyUserDetailsService;
import com.advats2.courseapp.model.Educator;
import com.advats2.courseapp.model.Student;
import com.advats2.courseapp.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final MyUserDetailsService myUserDetailsService;

    public RegisterController(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @GetMapping("")
    public String registerGet(Model model) {
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("")
    public String registerPost(@ModelAttribute("user") User user) {
        System.out.println(user.toString());
        return myUserDetailsService.addUser(user);
    }

    @GetMapping("/ADMIN")
    public String adminRegister(@RequestParam String username) {
        return myUserDetailsService.addAdminDetails(username);
    }

    @GetMapping("/EDUCATOR")
    public String educatorRegister(@RequestParam String username, Model model) {
        Educator educator = new Educator();
        model.addAttribute("username",username);
        model.addAttribute("educator",educator);
        return "register_Educator";
    }

    @PostMapping("/EDUCATOR")
    public String educatorPostRegister(@RequestParam String username, @ModelAttribute Educator educator) {
        return myUserDetailsService.addEducatorDetails(username,educator);
    }

    @GetMapping("/STUDENT")
    public String studentRegister(@RequestParam String username, Model model) {
        Student student = new Student();
        model.addAttribute("student",student);
        model.addAttribute("username", username);
        return "register_Student";
    }

    @PostMapping("/STUDENT")
    public String studentPostRegister(@RequestParam String username, @ModelAttribute Student student) {
        System.out.println("nice");
        return myUserDetailsService.addStudentDetails(username,student);
    }
}
