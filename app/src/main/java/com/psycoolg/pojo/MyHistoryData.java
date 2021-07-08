package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyHistoryData{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("massage")
	private String massage;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setMassage(String massage){
		this.massage = massage;
	}

	public String getMassage(){
		return massage;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public static class DataItem{

		@SerializedName("profile_image")
		private String profileImage;

		@SerializedName("amount")
		private String amount;

		@SerializedName("category_name")
		private String categoryName;

		@SerializedName("phone")
		private String phone;

		@SerializedName("booking_date")
		private String bookingDate;

		@SerializedName("onlineStatus")
		private String onlineStatus;

		@SerializedName("last_name")
		private String lastName;

		@SerializedName("id")
		private String id;

		@SerializedName("first_name")
		private String firstName;

		@SerializedName("email")
		private String email;

		@SerializedName("username")
		private String username;

		public void setProfileImage(String profileImage){
			this.profileImage = profileImage;
		}

		public String getProfileImage(){
			return profileImage;
		}

		public void setAmount(String amount){
			this.amount = amount;
		}

		public String getAmount(){
			return amount;
		}

		public void setCategoryName(String categoryName){
			this.categoryName = categoryName;
		}

		public String getCategoryName(){
			return categoryName;
		}

		public void setPhone(String phone){
			this.phone = phone;
		}

		public String getPhone(){
			return phone;
		}

		public void setBookingDate(String bookingDate){
			this.bookingDate = bookingDate;
		}

		public String getBookingDate(){
			return bookingDate;
		}

		public void setOnlineStatus(String onlineStatus){
			this.onlineStatus = onlineStatus;
		}

		public String getOnlineStatus(){
			return onlineStatus;
		}

		public void setLastName(String lastName){
			this.lastName = lastName;
		}

		public String getLastName(){
			return lastName;
		}

		public void setId(String id){
			this.id = id;
		}

		public String getId(){
			return id;
		}

		public void setFirstName(String firstName){
			this.firstName = firstName;
		}

		public String getFirstName(){
			return firstName;
		}

		public void setEmail(String email){
			this.email = email;
		}

		public String getEmail(){
			return email;
		}

		public void setUsername(String username){
			this.username = username;
		}

		public String getUsername(){
			return username;
		}
	}

}