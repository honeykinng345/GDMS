package com.GDMS.models.CheckLocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationDetails {

    @SerializedName("WH_ID")
    @Expose
    private String whId;
    @SerializedName("WH_DESC")
    @Expose
    private String whDesc;
    @SerializedName("WH_Lat")
    @Expose
    private String wHLat;
    @SerializedName("WH_Long")
    @Expose
    private String wHLong;

    public String getWhId() {
        return whId;
    }

    public void setWhId(String whId) {
        this.whId = whId;
    }

    public String getWhDesc() {
        return whDesc;
    }

    public void setWhDesc(String whDesc) {
        this.whDesc = whDesc;
    }

    public String getWHLat() {
        return wHLat;
    }

    public void setWHLat(String wHLat) {
        this.wHLat = wHLat;
    }

    public String getWHLong() {
        return wHLong;
    }

    public void setWHLong(String wHLong) {
        this.wHLong = wHLong;
    }

}