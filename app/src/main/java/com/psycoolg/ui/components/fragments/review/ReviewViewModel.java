package com.psycoolg.ui.components.fragments.review;

import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import com.psycoolg.R;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.network.RestApi;
import com.psycoolg.network.RestApiFactory;
import com.psycoolg.pojo.ReviewData;
import com.psycoolg.utils.Utility;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ReviewViewModel extends ViewModel {
    MutableLiveData<ApiResponse<ReviewData>> responseLiveData = new MutableLiveData<>();
    ApiResponse<ReviewData> apiResponse = null;


    private RestApi restApi = null;
    private Disposable subscription = null;

    {
        restApi = RestApiFactory.create();
        apiResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
    }

    public final void reviewApi(HashMap<String, String> reqData) {
        subscription = restApi.review(reqData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLiveData.postValue(apiResponse.loading());
                    }
                })
                .subscribe(new Consumer<ReviewData>() {
                    @Override
                    public void accept(ReviewData reviewData) throws Exception {
                        responseLiveData.postValue(apiResponse.success(reviewData));
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
    public boolean isValidFormData(AppCompatActivity mActivity, String msg) {

        if (TextUtils.isEmpty(msg)) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.enter_msg));
            return false;
        }

        if (msg.length() < 3) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.minimum_3_char_long_review));
            return false;
        }

        return true;
    }
}