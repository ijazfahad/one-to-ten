package com.onetoten.fahad.newp;

public class notificationPost {
    private String CUobjectId;
    private String Createdby;
    private String CreatedbyDp;
    private String Createdfor;
    private String Name;
    private String ObjectId;
    private String School;
    private String UserName;
    private String nRating;
    private String whichPicture;

    public void setObjectId(String objectId) {
        this.ObjectId = objectId;
    }

    public String getObjectId() {
        return this.ObjectId;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setCUobjectId(String CUobjectId) {
        this.CUobjectId = CUobjectId;
    }

    public void setSchool(String school) {
        this.School = school;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getName() {
        return this.Name;
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

    public void setnRating(String nRating) {
        this.nRating = nRating;
    }

    public String getnRating() {
        return this.nRating;
    }

    public void setCreatedby(String createdby) {
        this.Createdby = createdby;
    }

    public void setCreatedfor(String createdfor) {
        this.Createdfor = createdfor;
    }

    public void setWhichPicture(String whichPicture) {
        this.whichPicture = whichPicture;
    }

    public void setCreatedbyDp(String createdbyDp) {
        this.CreatedbyDp = createdbyDp;
    }

    public String getCreatedby() {
        return this.Createdby;
    }

    public String getCreatedfor() {
        return this.Createdfor;
    }

    public String getWhichPicture() {
        return this.whichPicture;
    }

    public String getCreatedbyDp() {
        return this.CreatedbyDp;
    }
}
