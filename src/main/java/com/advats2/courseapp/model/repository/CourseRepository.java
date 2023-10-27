package com.advats2.courseapp.model.repository;

import com.advats2.courseapp.model.Blog;
import com.advats2.courseapp.model.Course;
import com.advats2.courseapp.model.Educator;
import com.advats2.courseapp.model.Video;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseRepository {
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;

    public CourseRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public List<Course> getAll() {
        List<Course> courses = jdbcTemplate.query("SELECT * FROM Course", new BeanPropertyRowMapper<>(Course.class));
        for(int i = 0; i < courses.size(); i++) {
            courses.get(i).setTeachers(getTeachers(courses.get(i).getName()));
            courses.get(i).setVideos(getVideos(courses.get(i).getName()));
            courses.get(i).setBlogs(getBlogs(courses.get(i).getName()));
        }
        return courses;
    }

    public List<Educator> getTeachers(String cname) {
        List<String> names = jdbcTemplate.queryForList("SELECT username FROM Teacher WHERE CName = ?", new Object[]{cname}, String.class);
        List<Educator> teachers = new ArrayList<>();
        for(int i = 0; i < names.size(); i++) {
            teachers.add(userRepository.findEducator(names.get(i)).get());
        }
        System.out.println(teachers);
        return teachers;
    }

    public List<Video> getVideos(String cname) {
        return jdbcTemplate.query("SELECT * FROM Video WHERE CName = ?", new Object[]{cname}, new BeanPropertyRowMapper<>(Video.class));
    }

    public List<Blog> getBlogs(String cname) {
        return jdbcTemplate.query("SELECT * FROM Blog WHERE CName = ?", new Object[]{cname}, new BeanPropertyRowMapper<>(Blog.class));
    }

    public Boolean isEnrolled(String cname, String sname) {
        List<String> rows = jdbcTemplate.queryForList("SELECT CName FROM Enrollment WHERE CName = ? and username = ?", new Object[]{cname,sname}, String.class);
        return !rows.isEmpty();
    }

    public void addPendingCourses(Course course, String username) {
        jdbcTemplate.update("INSERT INTO PendingCourses VALUES (?,?,0,?,?)",
                course.getName(), course.getDescription(),
                course.getPrice(), course.getCategory());
        jdbcTemplate.update("INSERT INTO PendingTeachers VALUES (?,?)", username, course.getName());
    }

    public List<Pair<String,String>> getPendingCourses() {
        List<Pair<String,String>> pairs = jdbcTemplate.query("SELECT * FROM PendingTeachers", new RowMapper<Pair<String,String>>() {
            @Override
            public Pair<String,String> mapRow(ResultSet rs, int rowNum) throws SQLException {
                return Pair.of(rs.getString("username"),rs.getString("CName"));
            }
        });
        return pairs;
    }

    public void approveCourse(String cname) {
        List<Course> courses = jdbcTemplate.query("SELECT * FROM PendingCourses WHERE Name = ?", new Object[]{cname}, new BeanPropertyRowMapper<>(Course.class));
        Course course = courses.get(0);
        jdbcTemplate.update("INSERT INTO Course VALUES (?,?,?,?,?)",
                course.getName(), course.getDescription(), course.getRating(),
                course.getPrice(), course.getCategory());
        List<String> names = jdbcTemplate.queryForList("SELECT username FROM PendingTeachers WHERE CName = ?", new Object[]{cname}, String.class);
        for(int i = 0; i < names.size(); i++) {
            jdbcTemplate.update("INSERT INTO Teacher VALUES (?,?)", names.get(0), cname);
        }
        jdbcTemplate.update("DELETE FROM PendingTeachers WHERE CName = ?", cname);
        jdbcTemplate.update("DELETE FROM PendingCourses WHERE Name = ?", cname);
    }

    public void removeCourse(String cname) {
        jdbcTemplate.update("DELETE FROM Teacher WHERE CName = ?", cname);
        /* TODO: write query to delete comments here */
        jdbcTemplate.update("DELETE FROM Video WHERE CName = ?", cname);
        jdbcTemplate.update("DELETE FROM Blog WHERE CName = ?", cname);
        jdbcTemplate.update("DELETE FROM Teacher WHERE CName = ?", cname);
        jdbcTemplate.update("DELETE FROM Course WHERE Name = ?", cname);
    }

    public void addTeacher(String cname, String ename) {
        jdbcTemplate.update("INSERT INTO Teacher VALUES (?,?)", ename, cname);
    }
}
