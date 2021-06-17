package com.GDMS.models.ReasonList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RReason {

    @SerializedName("RREASONID")
    @Expose
    private Integer rreasonid;
    @SerializedName("RREASONDESC")
    @Expose
    private String rreasondesc;

    public Integer getRreasonid() {
        return rreasonid;
    }

    public void setRreasonid(Integer rreasonid) {
        this.rreasonid = rreasonid;
    }

    public String getRreasondesc() {
        return rreasondesc;
    }

    public void setRreasondesc(String rreasondesc) {
        this.rreasondesc = rreasondesc;
    }

}