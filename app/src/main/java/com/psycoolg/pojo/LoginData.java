package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

public class LoginData {

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

        @SerializedName("image")
        private String image;

        @SerializedName("firstname")
        private String firstname;

        @SerializedName("user_id")
        private String userId;

        @SerializedName("role_id")
        private String roleId;

        @SerializedName("mobile")
        private String mobile;

        @SerializedName("about ")
        private String about;

        @SerializedName("email")
        private String email;

        @SerializedName("lastname")
        private String lastname;

        @SerializedName("username")
        private String username;

        @SerializedName("token")
        private String token;

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage() {
            return image;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile() {
            return mobile;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getAbout() {
            return about;
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

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}