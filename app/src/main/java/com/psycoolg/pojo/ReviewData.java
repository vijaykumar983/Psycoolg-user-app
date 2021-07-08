package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

public class ReviewData {

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

        @SerializedName("doctor_id")
        private String doctorId;

        @SerializedName("rating")
        private String rating;

        @SerializedName("massage")
        private String massage;

        @SerializedName("current_user")
        private String currentUser;

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getRating() {
            return rating;
        }

        public void setMassage(String massage) {
            this.massage = massage;
        }

        public String getMassage() {
            return massage;
        }

        public void setCurrentUser(String currentUser) {
            this.currentUser = currentUser;
        }

        public String getCurrentUser() {
            return currentUser;
        }
    }
}