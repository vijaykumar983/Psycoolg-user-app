package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

public class AddTopicData {

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

        @SerializedName("user_id")
        private String userId;

        @SerializedName("topic_id")
        private String topicId;

        @SerializedName("message")
        private String message;

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}