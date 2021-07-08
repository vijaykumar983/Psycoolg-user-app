package com.psycoolg.ui.components.fragments.support;

import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psycoolg.R;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.network.RestApi;
import com.psycoolg.network.RestApiFactory;
import com.psycoolg.pojo.AddTopicData;
import com.psycoolg.pojo.ReferData;
import com.psycoolg.pojo.TopicData;
import com.psycoolg.utils.Utility;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SupportViewModel extends ViewModel {
    MutableLiveData<ApiResponse<TopicData>> responseLiveData = new MutableLiveData<>();
    ApiResponse<TopicData> apiResponse = null;

    /*----Add topic----*/
    MutableLiveData<ApiResponse<AddTopicData>> responseAddTopicLiveData = new MutableLiveData<>();
    ApiResponse<AddTopicData> apiAddTopicResponse = null;


    private RestApi restApi = null;
    private Disposable subscription = null;

    {
        restApi = RestApiFactory.create();
        apiResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
        apiAddTopicResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
    }

    public final void topicApi() {
        subscription = restApi.topic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLiveData.postValue(apiResponse.loading());
                    }
                })
                .subscribe(new Consumer<TopicData>() {
                    @Override
                    public void accept(TopicData topicData) throws Exception {
                        responseLiveData.postValue(apiResponse.success(topicData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseLiveData.postValue(apiResponse.error(throwable));
                    }
                });

    }

    public final void addTopicApi(HashMap<String, String> reqData) {
        subscription = restApi.addTopic(reqData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseAddTopicLiveData.postValue(apiAddTopicResponse.loading());
                    }
                })
                .subscribe(new Consumer<AddTopicData>() {
                    @Override
                    public void accept(AddTopicData addTopicData) throws Exception {
                        responseAddTopicLiveData.postValue(apiAddTopicResponse.success(addTopicData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseAddTopicLiveData.postValue(apiAddTopicResponse.error(throwable));
                    }
                });

    }

    public void disposeSubscriber() {
        if (subscription != null)
            subscription.dispose();
    }

    /*----Validation----*/
    public boolean isValidFormData(AppCompatActivity mActivity, String msg) {

        if (TextUtils.isEmpty(msg)) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.enter_your_msg));
            return false;
        }
        if (msg.length() < 3) {
            Utility.showToastMessageError(mActivity, mActivity.getString(R.string.minimum_3_char_long_msg));
            return false;
        }

        return true;
    }
}