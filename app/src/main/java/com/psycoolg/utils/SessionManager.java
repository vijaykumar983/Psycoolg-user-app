package com.psycoolg.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.databinding.BaseObservable;

import static android.content.Context.MODE_PRIVATE;

public class SessionManager extends BaseObservable {

    private final String IS_LOGIN = "isLoggedIn";
    private final String AUTH_TOKEN = "auth_token";
    private final String API_TOKEN = "api_token";

    private final String USERID = "user_id";
    private final String FIRST_NAME = "first_name";
    private final String LAST_NAME = "last_name";
    private final String EMAIL = "email";
    private final String MOBILE = "mobile";
    private final String PROFILE_IMG = "profile_img";
    private final String REFER_CODE = "refer_code";
    private final String ABOUT = "about";


    private static SharedPreferences shared;
    private static SharedPreferences.Editor editor;
    private static SessionManager session;

    public static SessionManager getInstance(Context context) {
        if (session == null) {
            session = new SessionManager();
        }
        if (shared == null) {
            shared = context.getSharedPreferences("PsyCooLGApp", MODE_PRIVATE);
            editor = shared.edit();
        }
        return session;
    }


    public boolean isLogin() {
        return shared.getBoolean(IS_LOGIN, false);
    }

    public void setLogin() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public String getAuthToken() {
        return shared.getString(AUTH_TOKEN, "");
    }

    public void setAuthToken(String authToken) {
        editor.putString(AUTH_TOKEN, authToken);
        editor.commit();
    }

    public String getLogin() {
        return shared.getString(IS_LOGIN, "0");
    }

    public void setLogin(String isLogin) {
        editor.putString(IS_LOGIN, isLogin);
        editor.commit();
    }

    public String getUSERID() {
        return shared.getString(USERID, "");
    }

    public void setUSERID(String userid) {
        editor.putString(USERID, userid);
        editor.commit();
    }

    public String getFIRST_NAME() {
        return shared.getString(FIRST_NAME, "");
    }

    public void setFIRST_NAME(String firstName) {
        editor.putString(FIRST_NAME, firstName);
        editor.commit();
    }

    public String getLAST_NAME() {
        return shared.getString(LAST_NAME, "");
    }

    public void setLAST_NAME(String lastName) {
        editor.putString(LAST_NAME, lastName);
        editor.commit();
    }

    public String getEMAIL() {
        return shared.getString(EMAIL, "");
    }

    public void setEMAIL(String email) {
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public String getMOBILE() {
        return shared.getString(MOBILE, "");
    }

    public void setMOBILE(String mobile) {
        editor.putString(MOBILE, mobile);
        editor.commit();
    }

    public String getREFER_CODE() {
        return shared.getString(REFER_CODE, "");
    }

    public void setREFER_CODE(String referCode) {
        editor.putString(REFER_CODE, referCode);
        editor.commit();
    }

    public String getPROFILE_IMG() {
        return shared.getString(PROFILE_IMG, "");
    }

    public void setPROFILE_IMG(String profile_img) {
        editor.putString(PROFILE_IMG, profile_img);
        editor.commit();
    }

    public String getABOUT() {
        return shared.getString(ABOUT, "");
    }

    public void setABOUT(String about) {
        editor.putString(ABOUT, about);
        editor.commit();
    }



   /* @Bindable("data")
    public UserData getUserData() {

        UserData userData = new UserData();
        userData.setId(shared.getInt(USER_ID, -1));
        userData.setEmail(shared.getString(EMAIL, ""));
        userData.setFirst_name(shared.getString(FIRST_NAME, ""));
        userData.setLast_name(shared.getString(LAST_NAME, ""));
        userData.setName(shared.getString(USERNAME, ""));
        userData.setStd_code(shared.getString(STD_CODE, ""));
        userData.setPhone(shared.getString(PHONE, ""));
        userData.setLanguage(shared.getString(LANGUAGE, ""));
        userData.set_verified(shared.getInt(IS_VERIFIED, -1));
        userData.setFb_id(shared.getString(FB_ID, ""));
        userData.setGoogle_id(shared.getString(GOOGLE_ID, ""));
        userData.setTwitter_id(shared.getString(TWITTER_ID, ""));
        return userData;
    }

    @Bindable("data")
    public void setUserData(UserData userData) {

        editor.putString(USER_ID, String.valueOf(userData.getId()));
        editor.putString(EMAIL, userData.getEmail());
        editor.putString(FIRST_NAME, userData.getFirst_name());
        editor.putString(LAST_NAME, userData.getLast_name());
        editor.putString(USERNAME, userData.getName());
        editor.putString(STD_CODE, userData.getStd_code());
        editor.putString(PHONE, userData.getPhone());
        editor.putString(LANGUAGE, userData.getLanguage());
        editor.putString(IS_VERIFIED, String.valueOf(userData.is_verified()));
        // editor.putString(TOKEN, userData.getToken());
        editor.putString(FB_ID, userData.getFb_id());
        editor.putString(GOOGLE_ID, userData.getGoogle_id());
        editor.putString(TWITTER_ID, userData.getTwitter_id());
        editor.commit();
    }*/


    public void logout() {
        editor.putString(USERID, "");
        editor.putString(FIRST_NAME, "");
        editor.putString(LAST_NAME, "");
        editor.putString(EMAIL, "");
        editor.putString(MOBILE, "");
        editor.putString(ABOUT, "");
        editor.putString(REFER_CODE, "");
        editor.putString(PROFILE_IMG, "");
        editor.putString(API_TOKEN, "");
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();
    }
}