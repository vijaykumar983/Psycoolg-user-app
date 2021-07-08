package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

public class SendOtpData {

    @SerializedName("data")
    private Data data;

    @SerializedName("massage")
    private String massage;

    @SerializedName("status")
    private boolean status;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
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

    public static class Data {

        @SerializedName("otp")
        private String otp;

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getOtp() {
            return otp;
        }
    }

}