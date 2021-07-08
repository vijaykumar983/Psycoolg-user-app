package com.psycoolg.ui.components.activities.verifyOtp;

import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import com.psycoolg.R;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.network.RestApi;
import com.psycoolg.network.RestApiFactory;
import com.psycoolg.pojo.LoginData;
import com.psycoolg.pojo.SendOtpData;
import com.psycoolg.utils.Utility;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VerifyOtpViewModel extends ViewModel {
    MutableLiveData<ApiResponse<LoginData>> responseLiveLoginOtpData = new MutableLiveData<>();
    ApiResponse<LoginData> apiResponseLoginOtp = null;

    MutableLiveData<ApiResponse<SendOtpData>> responseLiveSendOtpData = new MutableLiveData<>();
    ApiResponse<SendOtpData> apiResponseSendOtp = null;


    private RestApi restApi = null;
    private Disposable subscription = null;


    {
        restApi = RestApiFactory.create();
        apiResponseSendOtp = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
        apiResponseLoginOtp = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
    }

    public final void sendOtpApi(HashMap<String, String> reqData) {
        subscription = restApi.sendOtp(reqData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLiveSendOtpData.postValue(apiResponseSendOtp.loading());
                    }
                })
                .subscribe(new Consumer<SendOtpData>() {
                    @Override
                    public void accept(SendOtpData sendOtpData) throws Exception {
                        responseLiveSendOtpData.postValue(apiResponseSendOtp.success(sendOtpData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseLiveSendOtpData.postValue(apiResponseSendOtp.error(throwable));
                    }
                });
    }

    public final void loginOtpApi(HashMap<String, String> reqData) {
        subscription = restApi.loginOtp(reqData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLiveLoginOtpData.postValue(apiResponseLoginOtp.loading());
                    }
                })
                .subscribe(new Consumer<LoginData>() {
                    @Override
                    public void accept(LoginData loginData) throws Exception {
                        responseLiveLoginOtpData.postValue(apiResponseLoginOtp.success(loginData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseLiveLoginOtpData.postValue(apiResponseLoginOtp.error(throwable));
                    }
                });

    }


    public void disposeSubscriber() {
        if (subscription != null)
            subscription.dispose();
    }

    /*Validations*/
    public boolean isValidSendOtpFormData(AppCompatActivity mActivity, String mobile) {

        if (TextUtils.isEmpty(mobile)) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_valid_mobile_number));
            return false;
        }
        if (mobile.length() < 9) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_valid_mobile_number));
            return false;
        }
        return true;
    }

    public boolean isValidloginOtpFormData(AppCompatActivity mActivity, String mobile, String otp) {

        if (TextUtils.isEmpty(mobile)) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_valid_mobile_number));
            return false;
        }
        if (mobile.length() < 9) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_valid_mobile_number));
            return false;
        }

        if (TextUtils.isEmpty(otp)) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_otp));
            return false;
        }
        if (otp.length() < 4) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_valid_otp));
            return false;
        }
        return true;
    }
}
