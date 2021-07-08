package com.psycoolg.ui.components.activities.register;

import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import com.psycoolg.R;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.network.RestApi;
import com.psycoolg.network.RestApiFactory;
import com.psycoolg.pojo.RegisterData;
import com.psycoolg.utils.Utility;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends ViewModel {
    MutableLiveData<ApiResponse<RegisterData>> responseLiveData = new MutableLiveData<>();
    ApiResponse<RegisterData> apiResponse = null;


    private RestApi restApi = null;
    private Disposable subscription = null;


    {
        restApi = RestApiFactory.create();
        apiResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
    }

    public final void registerApi(HashMap<String, String> reqData) {
        subscription = restApi.register(reqData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLiveData.postValue(apiResponse.loading());
                    }
                })
                .subscribe(new Consumer<RegisterData>() {
                    @Override
                    public void accept(RegisterData registerData) throws Exception {
                        responseLiveData.postValue(apiResponse.success(registerData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseLiveData.postValue(apiResponse.error(throwable));
                    }
                });

    }


    public void disposeSubscriber() {
        if (subscription != null)
            subscription.dispose();
    }


    /*Validations*/
    public boolean isValidFormData(AppCompatActivity mActivity, String fName, String lName, String email, String mobile, String pass, String confirmPass) {

        if (TextUtils.isEmpty(fName)) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_fname));
            return false;
        }

        if (fName.length() < 3) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.minimum_3_char_long_fname));
            return false;
        }

        if (TextUtils.isEmpty(lName)) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_lname));
            return false;
        }

        if (lName.length() < 3) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.minimum_3_char_long_lname));
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_email));
            return false;
        }

        if (!Utility.isValidEmail(email)) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_valid_email_id));
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_valid_mobile_number));
            return false;
        }
        if (mobile.length() < 9) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_valid_mobile_number));
            return false;
        }

        if (TextUtils.isEmpty(pass)) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_password));
            return false;
        }
        if (pass.length() < 8) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.minimum_8_characters));
            return false;
        }

        if (TextUtils.isEmpty(confirmPass)) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.enter_cpassword));
            return false;
        }
        if (!pass.equals(confirmPass)) {
            Utility.showSnackBarMsgError(mActivity, mActivity.getString(R.string.password_not_match));
            return false;
        }

        return true;
    }
}
