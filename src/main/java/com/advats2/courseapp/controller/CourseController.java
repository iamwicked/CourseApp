package com.advats2.courseapp.controller;

import com.advats2.courseapp.model.Course;
import com.advats2.courseapp.model.Educator;
import com.advats2.courseapp.model.User;
import com.advats2.courseapp.model.repository.CourseRepository;
import com.advats2.courseapp.model.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public CourseController(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("")
    public String allCourses(Model model, Principal principal) {
        List<Course> courses = courseRepository.getAll();
        String username = "ANONYMOUS";
        String role = "ANONYMOUS";
        if(principal != null) {
            username = principal.getName();
            User user = userRepository.findUser(principal.getName()).get();
            role = user.getRole();
        }
        List<Boolean> bools = new ArrayList<>();
        switch (role) {
            case "ANONYMOUS":
                for(int i = 0; i < courses.size(); i++) {
                    bools.add(false);
                }
                break;
            case "ADMIN":
                System.out.println(courses.size());
                for(int i = 0; i < courses.size(); i++) {
                    bools.add(true);
                }
                break;
            case "EDUCATOR":
                for(int i = 0; i < courses.size(); i++) {
                    bools.add(courses.get(i).getTeachers().contains(userRepository.findEducator(username).get()));
                }
                break;
            case "STUDENT":
                for(int i = 0; i < courses.size(); i++) {
                    bools.add(courseRepository.isEnrolled(courses.get(i).getName(),username));
                }
                break;
        }
        model.addAttribute("bools", bools);
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/course")
    public String courseHome(@RequestParam(value = "index", defaultValue = "0") Integer index, Model model, Principal principal, @RequestParam(value = "action", defaultValue = "0") Integer action, @RequestParam(value = "ename", required = false) String ename) {
        if(principal == null) {
            return "redirect:/login";
        }
        List<Course> courses = courseRepository.getAll();
        index = min(index, courses.size()-1);
        index = max(index, 0);
        User user = userRepository.findUser(principal.getName()).get();
        if(Objects.equals(user.getRole(), "EDUCATOR") && !(courses.get(index).getTeachers().contains(userRepository.findEducator(user.getUsername()).get()))) {
            return "redirect:/login";
        }
        if(Objects.equals(user.getRole(), "STUDENT") && !(courseRepository.isEnrolled(courses.get(index).getName(),user.getUsername()))) {
            return "redirect:/login";
        }
        if(courses.size() == 0) {
            return "redirect:/courses";
        }
        if(action == 1) {
            if(Objects.equals(user.getRole(), "STUDENT")) {
                return "redirect:/login";
            }
            List<Educator> educators = userRepository.getAllEducators();
            model.addAttribute("index", index);
            model.addAttribute("action", action);
            model.addAttribute("educators", educators);
            return "educators";
        }
        if(action == 2) {
            if(!Objects.equals(user.getRole(), "ADMIN")) {
                return "redirect:/login";
            }
            courseRepository.removeCourse(courses.get(index).getName());
            return "redirect:/courses";
        }
        if(ename != null) {
            if(Objects.equals(user.getRole(), "STUDENT")) {
                return "redirect:/login";
            }
            courseRepository.addTeacher(courses.get(index).getName(), ename);
            return "redirect:/courses/course?index="+index;
        }
        model.addAttribute("index", index);
        model.addAttribute("role", user.getRole());
        model.addAttribute("course", courses.get(index));
        return "course_details";
    }

    @GetMapping("/buy")
    public String buyCourse(@RequestParam("index") int index, Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        List<Course> courses = courseRepository.getAll();
        index = min(index, courses.size()-1);
        index = max(index, 0);
        User user = userRepository.findUser(principal.getName()).get();
        if(user.getRole() == "EDUCATOR" && (courses.get(index).getTeachers().contains(userRepository.findEducator(user.getUsername()).get()))) {
            return "redirect:/courses/course?index="+index;
        }
        if(user.getRole() == "STUDENT" && (courseRepository.isEnrolled(courses.get(index).getName(),user.getUsername()))) {
            return "redirect:/courses/course?index="+index;
        }
        model.addAttribute("course", courses.get(index));
        return "course_buy";
    }

    @PostMapping("/category")
    public String categoryCourses(@ModelAttribute("category") String category, Model model, Principal principal) {
        List<Course> catcourses = new ArrayList<>();
        List<Course> courses = courseRepository.getAll();
        for(int i = 0; i < courses.size(); i++) {
            if(Objects.equals(courses.get(i).getCategory(), category)) {
                catcourses.add(courses.get(i));
            }
        }
        String username = "ANONYMOUS";
        String role = "ANONYMOUS";
        if(principal != null) {
            username = principal.getName();
            User user = userRepository.findUser(principal.getName()).get();
            role = user.getRole();
        }
        List<Boolean> bools = new ArrayList<>();
        switch (role) {
            case "ANONYMOUS":
                for(int i = 0; i < catcourses.size(); i++) {
                    bools.add(false);
                }
                break;
            case "ADMIN":
                for(int i = 0; i < catcourses.size(); i++) {
                    bools.add(true);
                }
                break;
            case "EDUCATOR":
                for(int i = 0; i < catcourses.size(); i++) {
                    bools.add(catcourses.get(i).getTeachers().contains(userRepository.findEducator(username).get()));
                }
                break;
            case "STUDENT":
                for(int i = 0; i < catcourses.size(); i++) {
                    bools.add(courseRepository.isEnrolled(catcourses.get(i).getName(),username));
                }
                break;
        }
        model.addAttribute("category", category);
        model.addAttribute("bools", bools);
        model.addAttribute("courses", catcourses);
        return "courses";
    }

    @PostMapping("/search")
    public String nameCourses(@RequestParam("search") String search, Model model, Principal principal) {
        List<Course> catcourses = new ArrayList<>();
        List<Course> courses = courseRepository.getAll();
        for(int i = 0; i < courses.size(); i++) {
            if(courses.get(i).getName().contains(search)) {
                catcourses.add(courses.get(i));
            }
        }
        String username = "ANONYMOUS";
        String role = "ANONYMOUS";
        if(principal != null) {
            username = principal.getName();
            User user = userRepository.findUser(principal.getName()).get();
            role = user.getRole();
        }
        List<Boolean> bools = new ArrayList<>();
        switch (role) {
            case "ANONYMOUS":
                for(int i = 0; i < catcourses.size(); i++) {
                    bools.add(false);
                }
                break;
            case "ADMIN":
                for(int i = 0; i < catcourses.size(); i++) {
                    bools.add(true);
                }
                break;
            case "EDUCATOR":
                for(int i = 0; i < catcourses.size(); i++) {
                    bools.add(catcourses.get(i).getTeachers().contains(userRepository.findEducator(username).get()));
                }
                break;
            case "STUDENT":
                for(int i = 0; i < catcourses.size(); i++) {
                    bools.add(courseRepository.isEnrolled(catcourses.get(i).getName(),username));
                }
                break;
        }
        model.addAttribute("search", search);
        model.addAttribute("bools", bools);
        model.addAttribute("courses", catcourses);
        return "courses";
    }

    @GetMapping("/new")
    public String newCourse(Principal principal, Model model) {
        if(principal == null) {
            return "redirect:/login";
        }
        if(!userRepository.findUser(principal.getName()).get().getRole().equals("EDUCATOR")) {
            return "redirect:/login";
        }
        Optional<Educator> educatorOptional = userRepository.findEducator(principal.getName());
        if(!educatorOptional.isPresent()) {
            return "educator_pending";
        }
        model.addAttribute("educator", educatorOptional.get());
        model.addAttribute("course", new Course());
        return "new_course";
    }

    @PostMapping("/new")
    public String newCoursePost(Principal principal, @ModelAttribute Course course) {
        if(principal == null) {
            return "redirect:/login";
        }
        if(!userRepository.findUser(principal.getName()).get().getRole().equals("EDUCATOR")) {
            return "redirect:/login";
        }
        Optional<Educator> educatorOptional = userRepository.findEducator(principal.getName());
        if(!educatorOptional.isPresent()) {
            return "educator_pending";
        }
        courseRepository.addPendingCourses(course, principal.getName());
        return "redirect:/educator";
    }

    @GetMapping("/video")
    public String videoPlayer(@RequestParam("cname") String cname, @RequestParam("id") Integer id, Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        User user = userRepository.findUser(principal.getName()).get();
        switch (user.getRole()) {
            case "STUDENT":
                if(!courseRepository.isEnrolled(cname, user.getUsername())) {
                    return "redirect:/login";
                }
                break;
            case "EDUCATOR":
                Educator educator = new Educator(user);
                if(!courseRepository.getTeachers(cname).contains(educator)) {
                    return "redirect:/login";
                }
                break;
        }
        model.addAttribute("cname", cname);
        model.addAttribute("id", id);
        return "video";
    }

    @GetMapping("/blog")
    public String blogReader(@RequestParam("cname") String cname, @RequestParam("id") Integer id, Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        User user = userRepository.findUser(principal.getName()).get();
        switch (user.getRole()) {
            case "STUDENT":
                if(!courseRepository.isEnrolled(cname, user.getUsername())) {
                    return "redirect:/login";
                }
                break;
            case "EDUCATOR":
                Educator educator = new Educator(user);
                if(!courseRepository.getTeachers(cname).contains(educator)) {
                    return "redirect:/login";
                }
                break;
        }
        model.addAttribute("cname", cname);
        model.addAttribute("id", id);
        return "blog";
    }
}
