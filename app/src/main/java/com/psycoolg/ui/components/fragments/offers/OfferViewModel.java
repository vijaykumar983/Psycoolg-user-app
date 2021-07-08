package com.psycoolg.ui.components.fragments.offers;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psycoolg.network.ApiResponse;
import com.psycoolg.network.RestApi;
import com.psycoolg.network.RestApiFactory;
import com.psycoolg.pojo.OfferData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OfferViewModel extends ViewModel {
    MutableLiveData<ApiResponse<OfferData>> responseLiveData = new MutableLiveData<>();
    ApiResponse<OfferData> apiResponse = null;


    private RestApi restApi = null;
    private Disposable subscription = null;

    {
        restApi = RestApiFactory.create();
        apiResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
    }

    public final void offerApi() {
        subscription = restApi.offer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLiveData.postValue(apiResponse.loading());
                    }
                })
                .subscribe(new Consumer<OfferData>() {
                    @Override
                    public void accept(OfferData offerData) throws Exception {
                        responseLiveData.postValue(apiResponse.success(offerData));
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
}