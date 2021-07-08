package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

public class RegisterData {

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

        @SerializedName("firstname")
        private String firstname;

        @SerializedName("city")
        private String city;

        @SerializedName("about")
        private String about;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("devicetoken")
        private String devicetoken;

        @SerializedName("deviceId")
        private String deviceId;

        @SerializedName("adminapporved")
        private String adminapporved;

        @SerializedName("password")
        private String password;

        @SerializedName("profile_image")
        private String profileImage;

        @SerializedName("social_type")
        private String socialType;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("role_id")
        private String roleId;

        @SerializedName("refer_by")
        private String referBy;

        @SerializedName("oauth_provider")
        private String oauthProvider;

        @SerializedName("id")
        private String id;

        @SerializedName("email")
        private String email;

        @SerializedName("deviceType")
        private String deviceType;

        @SerializedName("pincode")
        private String pincode;

        @SerializedName("address")
        private String address;

        @SerializedName("last_login")
        private String lastLogin;

        @SerializedName("oauth_uid")
        private String oauthUid;

        @SerializedName("otp")
        private String otp;

        @SerializedName("lastname")
        private String lastname;

        @SerializedName("social_id")
        private String socialId;

        @SerializedName("phone")
        private String phone;

        @SerializedName("name")
        private String name;

        @SerializedName("location")
        private String location;

        @SerializedName("login_token")
        private String loginToken;

        @SerializedName("uniqueId")
        private String uniqueId;

        @SerializedName("username")
        private String username;

        @SerializedName("status")
        private String status;

        @SerializedName("refer_code")
        private String referCode;

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getAbout() {
            return about;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setDevicetoken(String devicetoken) {
            this.devicetoken = devicetoken;
        }

        public String getDevicetoken() {
            return devicetoken;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setAdminapporved(String adminapporved) {
            this.adminapporved = adminapporved;
        }

        public String getAdminapporved() {
            return adminapporved;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setSocialType(String socialType) {
            this.socialType = socialType;
        }

        public String getSocialType() {
            return socialType;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setReferBy(String referBy) {
            this.referBy = referBy;
        }

        public String getReferBy() {
            return referBy;
        }

        public void setOauthProvider(String oauthProvider) {
            this.oauthProvider = oauthProvider;
        }

        public String getOauthProvider() {
            return oauthProvider;
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

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getPincode() {
            return pincode;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setLastLogin(String lastLogin) {
            this.lastLogin = lastLogin;
        }

        public String getLastLogin() {
            return lastLogin;
        }

        public void setOauthUid(String oauthUid) {
            this.oauthUid = oauthUid;
        }

        public String getOauthUid() {
            return oauthUid;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getOtp() {
            return otp;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setSocialId(String socialId) {
            this.socialId = socialId;
        }

        public String getSocialId() {
            return socialId;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLocation() {
            return location;
        }

        public void setLoginToken(String loginToken) {
            this.loginToken = loginToken;
        }

        public String getLoginToken() {
            return loginToken;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setReferCode(String referCode) {
            this.referCode = referCode;
        }

        public String getReferCode() {
            return referCode;
        }
    }
}