package com.advats2.courseapp.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Blog {
    private int BlogID;
    private String CName;
    private BigDecimal Rating;
    private Date CreatedDate;
    private int Views;
    private String Content;
    private int ReadTime;
    private String Title;

    public int getBlogID() {
        return BlogID;
    }

    public void setBlogID(int blogID) {
        BlogID = blogID;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public BigDecimal getRating() {
        return Rating;
    }

    public void setRating(BigDecimal rating) {
        Rating = rating;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getReadTime() {
        return ReadTime;
    }

    public void setReadTime(int readTime) {
        ReadTime = readTime;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public int hashCode() {
        return getBlogID();
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
        return this.getBlogID() == ((Blog) obj).getBlogID();
    }
}
