package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuoteData {

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

        @SerializedName("author")
        private String author;

        @SerializedName("icon")
        private String icon;

        @SerializedName("updated_by")
        private String updatedBy;

        @SerializedName("delete_at")
        private String deleteAt;

        @SerializedName("id")
        private String id;

        @SerializedName("title")
        private String title;

        @SerializedName("created_by")
        private String createdBy;

        @SerializedName("content")
        private String content;

        @SerializedName("status")
        private String status;

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor() {
            return author;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIcon() {
            return icon;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setDeleteAt(String deleteAt) {
            this.deleteAt = deleteAt;
        }

        public String getDeleteAt() {
            return deleteAt;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

}