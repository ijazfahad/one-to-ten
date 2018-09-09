package com.onetoten.fahad.newp;

import java.util.Date;

public class otherProfilePost {
    private String FirstName;
    private String LastName;
    private String Name;
    private String Odp;
    private String gender;
    private String opAverageRating;
    private Date opDate;
    private String opObjectId;
    private String opPicture;
    private String opcaption;
    private String posts;
    private String rating;
    private String relationship;

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRelationship() {
        return this.relationship;
    }

    public String getGender() {
        return this.gender;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public void setOdp(String odp) {
        this.Odp = odp;
    }

    public String getOdp() {
        return this.Odp;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public void setOpcaption(String opcaption) {
        this.opcaption = opcaption;
    }

    public void setOpPicture(String opPicture) {
        this.opPicture = opPicture;
    }

    public void setOpObjectId(String opObjectId) {
        this.opObjectId = opObjectId;
    }

    public void setOpAverageRating(String opAverageRating) {
        this.opAverageRating = opAverageRating;
    }

    public String getName() {
        return this.Name;
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

    public String getOpcaption() {
        return this.opcaption;
    }

    public String getOpPicture() {
        return this.opPicture;
    }

    public String getOpObjectId() {
        return this.opObjectId;
    }

    public Date getOpDate() {
        return this.opDate;
    }

    public String getOpAverageRating() {
        return this.opAverageRating;
    }
}
