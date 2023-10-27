package com.advats2.courseapp.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Video {
    private int VideoID;
    private String CName;
    private String ThumbnailLink;
    private BigDecimal Rating;
    private String Link;
    private int Views;
    private String Description;
    private int Duration;
    private String Title;
    private Date UploadDate;

    public int getVideoID() {
        return VideoID;
    }

    public void setVideoID(int videoID) {
        VideoID = videoID;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getThumbnailLink() {
        return ThumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        ThumbnailLink = thumbnailLink;
    }

    public BigDecimal getRating() {
        return Rating;
    }

    public void setRating(BigDecimal rating) {
        Rating = rating;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Date getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        UploadDate = uploadDate;
    }

    @Override
    public int hashCode() {
        return getVideoID();
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
        return this.getVideoID() == ((Video) obj).getVideoID();
    }
}
