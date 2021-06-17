package com.GDMS.models.Userlogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetails {

    @SerializedName("USERID")
    @Expose
    private String userid;
    @SerializedName("FIRSTNAME")
    @Expose
    private String firstname;
    @SerializedName("LASTNAME")
    @Expose
    private String lastname;
    @SerializedName("DEPARTMENT")
    @Expose
    private String department;
    @SerializedName("PASSWORD")
    @Expose
    private String password;
    @SerializedName("INACTIVE")
    @Expose
    private Integer inactive;
    @SerializedName("GROUPID")
    @Expose
    private Integer groupid;
    @SerializedName("CHANGEPASSWORDFLAG")
    @Expose
    private String changepasswordflag;
    @SerializedName("USEREMAIL")
    @Expose
    private Object useremail;
    @SerializedName("DMSFLAG")
    @Expose
    private String dmsflag;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getInactive() {
        return inactive;
    }

    public void setInactive(Integer inactive) {
        this.inactive = inactive;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getChangepasswordflag() {
        return changepasswordflag;
    }

    public void setChangepasswordflag(String changepasswordflag) {
        this.changepasswordflag = changepasswordflag;
    }

    public Object getUseremail() {
        return useremail;
    }

    public void setUseremail(Object useremail) {
        this.useremail = useremail;
    }

    public String getDmsflag() {
        return dmsflag;
    }

    public void setDmsflag(String dmsflag) {
        this.dmsflag = dmsflag;
    }

}