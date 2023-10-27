package com.advats2.courseapp.controller;

import com.advats2.courseapp.model.Admin;
import com.advats2.courseapp.model.Course;
import com.advats2.courseapp.model.Educator;
import com.advats2.courseapp.model.repository.CourseRepository;
import com.advats2.courseapp.model.repository.UserRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public AdminController(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String adminHome(Principal principal, Model model) {
        List<Educator> educators = userRepository.getPendingEducators(principal.getName());
        Admin admin = userRepository.findAdmin(principal.getName()).get();
        List<Pair<String,String>> courses = courseRepository.getPendingCourses();
        model.addAttribute("admin", admin);
        model.addAttribute("educators", educators);
        model.addAttribute("courses", courses);
        return "profile_admin";
    }

    @GetMapping("/approveeducator")
    public String educatorApprove(@RequestParam("educator") String educator) {
        System.out.println(educator);
        userRepository.approveEducator(educator);
        return "redirect:/admin";
    }

    @GetMapping("/approvecourse")
    public String courseApprove(@RequestParam("course") String course) {
        courseRepository.approveCourse(course);
        return "redirect:/admin";
    }
}
