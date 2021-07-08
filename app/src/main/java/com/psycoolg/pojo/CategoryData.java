package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryData {

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

        @SerializedName("current_date")
        private String currentDate;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("name")
        private String name;

        @SerializedName("id")
        private String id;

        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }

        public String getCurrentDate() {
            return currentDate;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}