package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

public class DoctorDetailData {

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

        @SerializedName("education")
        private String education;

        @SerializedName("gender")
        private String gender;

        @SerializedName("city")
        private String city;

        @SerializedName("onlineStatus")
        private String onlineStatus;

        @SerializedName("fee")
        private String fee;

        @SerializedName("about")
        private String about;

        @SerializedName("professions")
        private String professions;

        @SerializedName("rating")
        private float rating;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("experience")
        private String experience;

        @SerializedName("profile_image")
        private String profileImage;

        @SerializedName("password")
        private String password;

        @SerializedName("city_name")
        private String cityName;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("delete_at")
        private String deleteAt;

        @SerializedName("consult")
        private String consult;

        @SerializedName("id")
        private String id;

        @SerializedName("first_name")
        private String firstName;

        @SerializedName("email")
        private String email;

        @SerializedName("healthfeed")
        private String healthfeed;

        @SerializedName("address")
        private String address;

        @SerializedName("short_bio")
        private String shortBio;

        @SerializedName("timing")
        private String timing;

        @SerializedName("profile")
        private String profile;

        @SerializedName("last_name")
        private String lastName;

        @SerializedName("phone")
        private String phone;

        @SerializedName("dob")
        private String dob;

        @SerializedName("clinic")
        private String clinic;

        @SerializedName("category")
        private String category;

        @SerializedName("username")
        private String username;

        @SerializedName("status")
        private String status;

        public void setEducation(String education) {
            this.education = education;
        }

        public String getEducation() {
            return education;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getGender() {
            return gender;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }

        public void setOnlineStatus(String onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public String getOnlineStatus() {
            return onlineStatus;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getFee() {
            return fee;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getAbout() {
            return about;
        }

        public void setProfessions(String professions) {
            this.professions = professions;
        }

        public String getProfessions() {
            return professions;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public float getRating() {
            return rating;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getExperience() {
            return experience;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setDeleteAt(String deleteAt) {
            this.deleteAt = deleteAt;
        }

        public String getDeleteAt() {
            return deleteAt;
        }

        public void setConsult(String consult) {
            this.consult = consult;
        }

        public String getConsult() {
            return consult;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setHealthfeed(String healthfeed) {
            this.healthfeed = healthfeed;
        }

        public String getHealthfeed() {
            return healthfeed;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setShortBio(String shortBio) {
            this.shortBio = shortBio;
        }

        public String getShortBio() {
            return shortBio;
        }

        public void setTiming(String timing) {
            this.timing = timing;
        }

        public String getTiming() {
            return timing;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getProfile() {
            return profile;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getDob() {
            return dob;
        }

        public void setClinic(String clinic) {
            this.clinic = clinic;
        }

        public String getClinic() {
            return clinic;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategory() {
            return category;
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
    }

}