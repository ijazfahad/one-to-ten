package com.example.fahad.newp;

import java.util.Date;

public class HomePost {
    private String AvgRating;
    private String CUobjectID;
    private String Caption;
    private Date CreatedAt;
    private String Fname;
    private String Lname;
    private String Name;
    private String ObjectId;
    private String Ppicture;
    private String School;
    private String Username;
    private String picture;

    public void setSchool(String school) {
        this.School = school;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public void setCUobjectID(String CUobjectID) {
        this.CUobjectID = CUobjectID;
    }

    public void setAvgRating(String avgRating) {
        this.AvgRating = avgRating;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setObjectId(String ObjectId) {
        this.ObjectId = ObjectId;
    }

    public void setPpicture(String ppicture) {
        this.Ppicture = ppicture;
    }

    public void setCreatedAt(Date createdAt) {
        this.CreatedAt = createdAt;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setCaption(String caption) {
        this.Caption = caption;
    }

    public void setLname(String lname) {
        this.Lname = lname;
    }

    public void setFname(String fname) {
        this.Fname = fname;
    }

    public String getSchool() {
        return this.School;
    }

    public String getUsername() {
        return this.Username;
    }

    public String getCUobjectID() {
        return this.CUobjectID;
    }

    public String getAvgRating() {
        return this.AvgRating;
    }

    public String getName() {
        return this.Name;
    }

    public String getObjectId() {
        return this.ObjectId;
    }

    public String getPpicture() {
        return this.Ppicture;
    }

    public String getFname() {
        return this.Fname;
    }

    public String getLname() {
        return this.Lname;
    }

    public String getCaption() {
        return this.Caption;
    }

    public String getPicture() {
        return this.picture;
    }

    public Date getCreatedAt() {
        return this.CreatedAt;
    }
}
