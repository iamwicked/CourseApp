package com.advats2.courseapp.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Course {
    private String Name;
    private String Description;
    private BigDecimal Rating;
    private BigDecimal Price;
    private String Category;
    private List<Educator> teachers;
    private List<Video> videos;
    private List<Blog> blogs;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public BigDecimal getRating() {
        return Rating;
    }

    public void setRating(BigDecimal rating) {
        Rating = rating;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public List<Educator> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Educator> teachers) {
        this.teachers = teachers;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(getClass() != obj.getClass()) {
            return false;
        }
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        return Objects.equals(this.getName(), ((Course) obj).getName());
    }
}
