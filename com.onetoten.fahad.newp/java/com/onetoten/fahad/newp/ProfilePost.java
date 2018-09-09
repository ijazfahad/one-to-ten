package com.onetoten.fahad.newp;

import java.util.Date;

public class ProfilePost {
    private String FirstName;
    private String LastName;
    private String Name;
    private String dp;
    private String pAverageRating;
    private Date pDate;
    private String pObjectId;
    private String pPicture;
    private String pcaption;
    private String picObjectId;
    private String posts;
    private String rating;

    public void setPicObjectId(String picObjectId) {
        this.picObjectId = picObjectId;
    }

    public String getPicObjectId() {
        return this.picObjectId;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getDp() {
        return this.dp;
    }

    public void setpAverageRating(String pAverageRating) {
        this.pAverageRating = pAverageRating;
    }

    public String getpAverageRating() {
        return this.pAverageRating;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }

    public Date getpDate() {
        return this.pDate;
    }

    public void setpObjectId(String pObjectId) {
        this.pObjectId = pObjectId;
    }

    public String getpObjectId() {
        return this.pObjectId;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return this.Name;
    }

    public void setpPicture(String pPicture) {
        this.pPicture = pPicture;
    }

    public String getpPicture() {
        return this.pPicture;
    }

    public void setPcaption(String pcaption) {
        this.pcaption = pcaption;
    }

    public String getPcaption() {
        return this.pcaption;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getFirstName() {
        return this.FirstName;
    }

    public String getLastName() {
        return this.LastName;
    }

    public String getRating() {
        return this.rating;
    }

    public String getPosts() {
        return this.posts;
    }
}
