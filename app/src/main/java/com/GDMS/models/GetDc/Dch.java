package com.GDMS.models.GetDc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dch {

    @SerializedName("DOCNUMBER")
    @Expose
    private String docnumber;
    @SerializedName("DOCSERIAL")
    @Expose
    private Integer docserial;
    @SerializedName("VEHICLENUM")
    @Expose
    private String vehiclenum;
    @SerializedName("DELIVERYBY")
    @Expose
    private String deliveryby;
    @SerializedName("STATUS")
    @Expose
    private Integer status;
    @SerializedName("SAGEUPDATE")
    @Expose
    private Integer sageupdate;
    @SerializedName("REMARKS")
    @Expose
    private String remarks;
    @SerializedName("RREASONID")
    @Expose
    private int rreasonid;
    @SerializedName("DELIVERYDATETIME")
    @Expose
    private String deliverydatetime;
    @SerializedName("DELIVERYLAT")
    @Expose
    private String deliverylat;
    @SerializedName("DELIVERYLONG")
    @Expose
    private String deliverylong;
    @SerializedName("DELIVERYLOCATION")
    @Expose
    private String deliverylocation;
    @SerializedName("SYSDATETIME")
    @Expose
    private String sysdatetime;

    public String getDocnumber() {
        return docnumber;
    }

    public void setDocnumber(String docnumber) {
        this.docnumber = docnumber;
    }

    public Integer getDocserial() {
        return docserial;
    }

    public void setDocserial(Integer docserial) {
        this.docserial = docserial;
    }

    public String getVehiclenum() {
        return vehiclenum;
    }

    public void setVehiclenum(String vehiclenum) {
        this.vehiclenum = vehiclenum;
    }

    public String getDeliveryby() {
        return deliveryby;
    }

    public void setDeliveryby(String deliveryby) {
        this.deliveryby = deliveryby;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSageupdate() {
        return sageupdate;
    }

    public void setSageupdate(Integer sageupdate) {
        this.sageupdate = sageupdate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getRreasonid() {
        return rreasonid;
    }

    public void setRreasonid(int rreasonid) {
        this.rreasonid = rreasonid;
    }

    public String getDeliverydatetime() {
        return deliverydatetime;
    }

    public void setDeliverydatetime(String deliverydatetime) {
        this.deliverydatetime = deliverydatetime;
    }

    public String getDeliverylat() {
        return deliverylat;
    }

    public void setDeliverylat(String deliverylat) {
        this.deliverylat = deliverylat;
    }

    public String getDeliverylong() {
        return deliverylong;
    }

    public void setDeliverylong(String deliverylong) {
        this.deliverylong = deliverylong;
    }

    public String getDeliverylocation() {
        return deliverylocation;
    }

    public void setDeliverylocation(String deliverylocation) {
        this.deliverylocation = deliverylocation;
    }

    public String getSysdatetime() {
        return sysdatetime;
    }

    public void setSysdatetime(String sysdatetime) {
        this.sysdatetime = sysdatetime;
    }

}