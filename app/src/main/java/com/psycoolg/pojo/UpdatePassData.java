package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

public class UpdatePassData {

    @SerializedName("data")
    private String data;

    @SerializedName("massage")
    private String massage;

    @SerializedName("status")
    private boolean status;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}