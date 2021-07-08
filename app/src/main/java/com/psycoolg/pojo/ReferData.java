package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferData {

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

        @SerializedName("refer")
        private List<ReferItem> refer;

        @SerializedName("total_cradit")
        private int totalCradit;

        @SerializedName("current_balance")
        private int currentBalance;

        @SerializedName("refer_code")
        private String referCode;

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        public void setRefer(List<ReferItem> refer) {
            this.refer = refer;
        }

        public List<ReferItem> getRefer() {
            return refer;
        }

        public void setTotalCradit(int totalCradit) {
            this.totalCradit = totalCradit;
        }

        public int getTotalCradit() {
            return totalCradit;
        }

        public void setCurrentBalance(int currentBalance) {
            this.currentBalance = currentBalance;
        }

        public int getCurrentBalance() {
            return currentBalance;
        }

        public void setReferCode(String referCode) {
            this.referCode = referCode;
        }

        public String getReferCode() {
            return referCode;
        }

        public static class ReferItem {

            @SerializedName("amount")
            private String amount;

            @SerializedName("name")
            private String name;

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getAmount() {
                return amount;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }
    }

}