package com.psycoolg.ui.components.fragments.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psycoolg.network.ApiResponse;
import com.psycoolg.network.RestApi;
import com.psycoolg.network.RestApiFactory;
import com.psycoolg.pojo.AllDoctorData;
import com.psycoolg.pojo.CategoryData;
import com.psycoolg.pojo.LocationData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {
    MutableLiveData<ApiResponse<AllDoctorData>> responseLiveData = new MutableLiveData<>();
    ApiResponse<AllDoctorData> apiResponse = null;

    MutableLiveData<ApiResponse<CategoryData>> responseCategoryLiveData = new MutableLiveData<>();
    ApiResponse<CategoryData> apiCategoryResponse = null;

    MutableLiveData<ApiResponse<LocationData>> responseLocaitonLiveData = new MutableLiveData<>();
    ApiResponse<LocationData> apiLocationResponse = null;


    private RestApi restApi = null;
    private Disposable subscription = null;

    {
        restApi = RestApiFactory.create();
        apiResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
        apiCategoryResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
        apiLocationResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
    }

    public final void allDoctorApi() {
        subscription = restApi.allDoctor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLiveData.postValue(apiResponse.loading());
                    }
                })
                .subscribe(new Consumer<AllDoctorData>() {
                    @Override
                    public void accept(AllDoctorData allDoctorData) throws Exception {
                        responseLiveData.postValue(apiResponse.success(allDoctorData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseLiveData.postValue(apiResponse.error(throwable));
                    }
                });

    }

    public final void categoryApi() {
        subscription = restApi.category()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseCategoryLiveData.postValue(apiCategoryResponse.loading());
                    }
                })
                .subscribe(new Consumer<CategoryData>() {
                    @Override
                    public void accept(CategoryData categoryData) throws Exception {
                        responseCategoryLiveData.postValue(apiCategoryResponse.success(categoryData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseCategoryLiveData.postValue(apiCategoryResponse.error(throwable));
                    }
                });

    }

    public final void locationApi() {
        subscription = restApi.location()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLocaitonLiveData.postValue(apiLocationResponse.loading());
                    }
                })
                .subscribe(new Consumer<LocationData>() {
                    @Override
                    public void accept(LocationData locationData) throws Exception {
                        responseLocaitonLiveData.postValue(apiLocationResponse.success(locationData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseLocaitonLiveData.postValue(apiLocationResponse.error(throwable));
                    }
                });

    }

    public void disposeSubscriber() {
        if (subscription != null)
            subscription.dispose();
    }
}