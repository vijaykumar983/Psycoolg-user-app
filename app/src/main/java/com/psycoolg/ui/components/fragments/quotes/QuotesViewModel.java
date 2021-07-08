package com.psycoolg.ui.components.fragments.quotes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psycoolg.network.ApiResponse;
import com.psycoolg.network.RestApi;
import com.psycoolg.network.RestApiFactory;
import com.psycoolg.pojo.QuoteData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class QuotesViewModel extends ViewModel {
    MutableLiveData<ApiResponse<QuoteData>> responseLiveData = new MutableLiveData<>();
    ApiResponse<QuoteData> apiResponse = null;


    private RestApi restApi = null;
    private Disposable subscription = null;

    {
        restApi = RestApiFactory.create();
        apiResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
    }

    public final void quoteApi() {
        subscription = restApi.quote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLiveData.postValue(apiResponse.loading());
                    }
                })
                .subscribe(new Consumer<QuoteData>() {
                    @Override
                    public void accept(QuoteData quoteData) throws Exception {
                        responseLiveData.postValue(apiResponse.success(quoteData));
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