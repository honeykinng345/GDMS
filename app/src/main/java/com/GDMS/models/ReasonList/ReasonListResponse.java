package com.GDMS.models.ReasonList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ReasonListResponse {

    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("RReason_List")
    @Expose
    private List<RReason> rReasonList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RReason> getRReasonList() {
        return rReasonList;
    }

    public void setRReasonList(List<RReason> rReasonList) {
        this.rReasonList = rReasonList;
    }

}