package com.example.fahad.newp;

import java.util.Date;

public class SingleItemRating {
    private String CUobjectId;
    private Date CreatedAt;
    private String Name;
    private String School;
    private String UserName;
    private String pPicture;
    private String rating;
    private String ratingId;

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getRatingId() {
        return this.ratingId;
    }

    public void setCUobjectId(String CUobjectId) {
        this.CUobjectId = CUobjectId;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public void setSchool(String school) {
        this.School = school;
    }

    public String getCUobjectId() {
        return this.CUobjectId;
    }

    public String getSchool() {
        return this.School;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setCreatedAt(Date createdAt) {
        this.CreatedAt = createdAt;
    }

    public void setpPicture(String pPicture) {
        this.pPicture = pPicture;
    }

    public void setName(String firstName) {
        this.Name = firstName;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getCreatedAt() {
        return this.CreatedAt;
    }

    public String getName() {
        return this.Name;
    }

    public String getRating() {
        return this.rating;
    }

    public String getpPicture() {
        return this.pPicture;
    }
}
