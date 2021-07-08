package com.psycoolg.ui.components.activities.updatePassword;

import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import com.psycoolg.R;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.network.RestApi;
import com.psycoolg.network.RestApiFactory;
import com.psycoolg.pojo.UpdatePassData;
import com.psycoolg.utils.Utility;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UpdatePassViewModel extends ViewModel {
    MutableLiveData<ApiResponse<UpdatePassData>> responseLiveData = new MutableLiveData<>();
    ApiResponse<UpdatePassData> apiResponse = null;


    private RestApi restApi = null;
    private Disposable subscription = null;


    {
        restApi = RestApiFactory.create();
        apiResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
    }

    public final void updatePassApi(HashMap<String, String> reqData) {
        subscription = restApi.updatePass(reqData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLiveData.postValue(apiResponse.loading());
                    }
                })
                .subscribe(new Consumer<UpdatePassData>() {
                    @Override
                    public void accept(UpdatePassData updatePassData) throws Exception {
                        responseLiveData.postValue(apiResponse.success(updatePassData));
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
    public boolean isValidFormData(AppCompatActivity mActivity, String pass, String confirmPass) {

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
