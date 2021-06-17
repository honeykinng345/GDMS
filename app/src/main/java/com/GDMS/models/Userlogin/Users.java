package com.GDMS.models.Userlogin;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Users {

    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("User_Details")
    @Expose
    private UserDetails userDetails;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

}