package com.advats2.courseapp.config;

import com.advats2.courseapp.model.Admin;
import com.advats2.courseapp.model.Educator;
import com.advats2.courseapp.model.Student;
import com.advats2.courseapp.model.User;
import com.advats2.courseapp.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = this.userRepository.findUser(username);
        System.out.println(userOptional.get().toString());
        return userOptional.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/register/"+user.getRole()+"?username="+user.getUsername();
    }

    public String addAdminDetails(String username) {
        Optional<User> userOptional = this.userRepository.findUser(username);
        Admin admin = new Admin(userOptional.get());
        userRepository.addAdmin(admin);
        return "redirect:/admin";
    }

    public String addEducatorDetails(String username, Educator educator) {
        Optional<User> userOptional = this.userRepository.findUser(username);
        Educator educator1 = new Educator(userOptional.get());
        educator1.setAbout(educator.getAbout());
        educator1.setDegree(educator.getDegree());
        educator1.setGender(educator.getGender());
        educator1.setYear(educator.getYear());
        educator1.setUniversity(educator.getUniversity());
        educator1.setAdminUserName(educator.getAdminUserName());
        userRepository.addPendingEducator(educator1);
        return "redirect:/login";
    }

    public String addStudentDetails(String username, Student student) {
        System.out.println("up");
        Optional<User> userOptional = this.userRepository.findUser(username);
        if(!userOptional.isPresent()) {
            System.out.println("here");
        }
        else {
            System.out.println("there");
        }
        Student student1 = new Student(userOptional.get());
        student1.setDegree(student.getDegree());
        student1.setAge(student.getAge());
        student1.setCity(student.getCity());
        student1.setCollege(student.getCity());
        student1.setPincode(student.getPincode());
        student1.setSpecialisation(student.getSpecialisation());
        student1.setState(student.getState());
        student1.setDOB(student.getDOB());
        userRepository.addStudent(student1);
        return "redirect:/login";
    }
}
