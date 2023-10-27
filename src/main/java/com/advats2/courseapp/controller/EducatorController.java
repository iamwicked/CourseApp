package com.advats2.courseapp.controller;

import com.advats2.courseapp.model.Course;
import com.advats2.courseapp.model.Educator;
import com.advats2.courseapp.model.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/educator")
public class EducatorController {
    private UserRepository userRepository;

    public EducatorController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String educatorHome(Principal principal, Model model) {
        Optional<Educator> educatorOptional = userRepository.findEducator(principal.getName());
        if(!educatorOptional.isPresent()) {
            return "educator_pending";
        }
        Educator educator = educatorOptional.get();
        List<Course> courses = userRepository.getEducatorCourses(educator.getUsername());
        model.addAttribute("educator", educator);
        model.addAttribute("courses", courses);
        return "profile_educator";
    }
}
