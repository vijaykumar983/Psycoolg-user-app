package com.psycoolg.ui.components.fragments.profile;

import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psycoolg.R;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.network.RestApi;
import com.psycoolg.network.RestApiFactory;
import com.psycoolg.pojo.UpdateProfileData;
import com.psycoolg.pojo.ProfileData;
import com.psycoolg.utils.Utility;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.http.Part;

public class ProfileViewModel extends ViewModel {
    MutableLiveData<ApiResponse<ProfileData>> responseLiveData = new MutableLiveData<>();
    ApiResponse<ProfileData> apiResponse = null;

    /*-----update profile-----*/
    MutableLiveData<ApiResponse<UpdateProfileData>> responseUpdateProfileLiveData = new MutableLiveData<>();
    ApiResponse<UpdateProfileData> apiUpdateProfileResponse = null;



    private RestApi restApi = null;
    private Disposable subscription = null;

    {
        restApi = RestApiFactory.create();
        apiResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
        apiUpdateProfileResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
    }

    public final void profileApi(HashMap<String, String> reqData) {
        subscription = restApi.getProfile(reqData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLiveData.postValue(apiResponse.loading());
                    }
                })
                .subscribe(new Consumer<ProfileData>() {
                    @Override
                    public void accept(ProfileData profileData) throws Exception {
                        responseLiveData.postValue(apiResponse.success(profileData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseLiveData.postValue(apiResponse.error(throwable));
                    }
                });

    }

    public final void updateProfile(@Part MultipartBody.Part user_id,
                                    @Part MultipartBody.Part image,
                                    @Part MultipartBody.Part name,
                                    @Part MultipartBody.Part mobile,
                                    @Part MultipartBody.Part email,
                                    @Part MultipartBody.Part about) {
        subscription = restApi.updateProfile(user_id, image, name, mobile, email, about)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseUpdateProfileLiveData.postValue(apiUpdateProfileResponse.loading());
                    }
                })
                .subscribe(new Consumer<UpdateProfileData>() {
                    @Override
                    public void accept(UpdateProfileData updateProfileData) throws Exception {
                        responseUpdateProfileLiveData.postValue(apiUpdateProfileResponse.success(updateProfileData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseUpdateProfileLiveData.postValue(apiUpdateProfileResponse.error(throwable));
                    }
                });

    }

    public void disposeSubscriber() {
        if (subscription != null)
            subscription.dispose();
    }

    /*Validations */
    public boolean isValidFormData(AppCompatActivity mActivity, String image, String name, String phone, String email, String bio) {
        if (TextUtils.isEmpty(image)) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.please_select_image));
            return false;
        }
        if (TextUtils.isEmpty(name)) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.enter_name));
            return false;
        }

        if (name.length() < 3) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.minimum_3_char_long_name));
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.enter_mobile));
            return false;
        }
        if (phone.length() < 9) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.enter_valid_mobile_number));
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.enter_email));
            return false;
        }

        if (!Utility.isValidEmail(email)) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.enter_valid_email_id));
            return false;
        }


        if (TextUtils.isEmpty(bio)) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.enter_your_bio));
            return false;
        }

        if (bio.length() < 3) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.minimum_3_char_long_bio));
            return false;
        }

        return true;
    }

}