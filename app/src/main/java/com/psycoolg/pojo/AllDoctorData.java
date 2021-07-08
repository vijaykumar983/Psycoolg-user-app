package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllDoctorData {

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("massage")
    private String massage;

    @SerializedName("status")
    private boolean status;

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public List<DataItem> getData() {
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

    public static class DataItem {

        @SerializedName("profession")
        private String profession;

        @SerializedName("firstname")
        private String firstname;

        @SerializedName("online_status")
        private String onlineStatus;

        @SerializedName("mobile")
        private String mobile;

        @SerializedName("profile_pic")
        private String profilePic;

        @SerializedName("rating")
        private float rating;

        @SerializedName("location")
        private String location;

        @SerializedName("id")
        private String id;

        @SerializedName("email")
        private String email;

        @SerializedName("lastname")
        private String lastname;

        @SerializedName("username")
        private String username;

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getProfession() {
            return profession;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setOnlineStatus(String onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public String getOnlineStatus() {
            return onlineStatus;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile() {
            return mobile;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public float getRating() {
            return rating;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLocation() {
            return location;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }

}