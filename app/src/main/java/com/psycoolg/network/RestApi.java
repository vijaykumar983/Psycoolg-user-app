package com.psycoolg.network;

import com.psycoolg.pojo.AddTopicData;
import com.psycoolg.pojo.AllDoctorData;
import com.psycoolg.pojo.BlogCategoryData;
import com.psycoolg.pojo.BlogData;
import com.psycoolg.pojo.CategoryData;
import com.psycoolg.pojo.DoctorDetailData;
import com.psycoolg.pojo.UpdateProfileData;
import com.psycoolg.pojo.LocationData;
import com.psycoolg.pojo.LoginData;
import com.psycoolg.pojo.MyBookingData;
import com.psycoolg.pojo.MyHistoryData;
import com.psycoolg.pojo.OfferData;
import com.psycoolg.pojo.ProfileData;
import com.psycoolg.pojo.QuoteData;
import com.psycoolg.pojo.ReferData;
import com.psycoolg.pojo.RegisterData;
import com.psycoolg.pojo.ReviewData;
import com.psycoolg.pojo.SendOtpData;
import com.psycoolg.pojo.TopicData;
import com.psycoolg.pojo.UpdatePassData;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApi {
    @FormUrlEncoded
    @POST("psychology/public/v1/api/register")
    Observable<RegisterData> register(@FieldMap HashMap<String, String> reqData);

    @FormUrlEncoded
    @POST("psychology/public/v1/api/login")
    Observable<LoginData> login(@FieldMap HashMap<String, String> reqData);

    @FormUrlEncoded
    @POST("psychology/public/v1/api/get/otp")
    Observable<SendOtpData> sendOtp(@FieldMap HashMap<String, String> reqData);

    @FormUrlEncoded
    @POST("psychology/public/v1/api/otp/with/mobile/login")
    Observable<LoginData> loginOtp(@FieldMap HashMap<String, String> reqData);

    @FormUrlEncoded
    @POST("psychology/public/v1/api/update/password")
    Observable<UpdatePassData> updatePass(@FieldMap HashMap<String, String> reqData);

    @POST("psychology/public/v1/api/doctors")
    Observable<AllDoctorData> allDoctor();

    @POST("psychology/public/v1/api/dailyquotes")
    Observable<QuoteData> quote();

    @POST("psychology/public/v1/api/offers")
    Observable<OfferData> offer();

    @FormUrlEncoded
    @POST("psychology/public/v1/api/review")
    Observable<ReviewData> review(@FieldMap HashMap<String, String> reqData);

    @POST("psychology/public/v1/api/categores")
    Observable<CategoryData> category();

    @POST("psychology/public/v1/api/locatons")
    Observable<LocationData> location();

    @FormUrlEncoded
    @POST("psychology/public/v1/api/doctors/details")
    Observable<DoctorDetailData> doctorDetail(@FieldMap HashMap<String, String> reqData);

    @GET("Psycoolg/blog/wp-json/api/v1/post")
    Call<BlogData> blogs();

    @GET("Psycoolg/blog/wp-json/api/v1/category")
    Call<BlogCategoryData> blogCategory();


    @FormUrlEncoded
    @POST("psychology/public/v1/api/history")
    Observable<MyHistoryData> myHistory(@FieldMap HashMap<String, String> reqData);

    @FormUrlEncoded
    @POST("psychology/public/v1/api/refer")
    Observable<ReferData> refer(@FieldMap HashMap<String, String> reqData);

    @FormUrlEncoded
    @POST("psychology/public/v1/api/my-booking")
    Observable<MyBookingData> myBooking(@FieldMap HashMap<String, String> reqData);

    @FormUrlEncoded
    @POST("psychology/public/v1/api/get/profile")
    Observable<ProfileData> getProfile(@FieldMap HashMap<String, String> reqData);

    @POST("psychology/public/v1/api/get/topic")
    Observable<TopicData> topic();

    @FormUrlEncoded
    @POST("psychology/public/v1/api/add/topic")
    Observable<AddTopicData> addTopic(@FieldMap HashMap<String, String> reqData);

    @Multipart
    @POST("psychology/public/v1/api/edit/profile")
    Observable<UpdateProfileData> updateProfile(@Part MultipartBody.Part user_id,
                                              @Part MultipartBody.Part image,
                                              @Part MultipartBody.Part name,
                                              @Part MultipartBody.Part mobile,
                                              @Part MultipartBody.Part email,
                                              @Part MultipartBody.Part about);
}
