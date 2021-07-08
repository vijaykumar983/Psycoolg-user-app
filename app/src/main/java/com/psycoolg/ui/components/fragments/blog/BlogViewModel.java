package com.psycoolg.ui.components.fragments.blog;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psycoolg.network.ApiResponse;
import com.psycoolg.network.RestApi;
import com.psycoolg.network.RestApiFactory;
import com.psycoolg.pojo.BlogCategoryData;
import com.psycoolg.pojo.BlogData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BlogViewModel extends ViewModel {
   /* MutableLiveData<ApiResponse<BlogData>> responseLiveData = new MutableLiveData<>();
    ApiResponse<BlogData> apiResponse = null;

    MutableLiveData<ApiResponse<BlogCategoryData>> responseBlogCatLiveData = new MutableLiveData<>();
    ApiResponse<BlogCategoryData> apiBlogCatResponse = null;


    private RestApi restApi = null;
    private Disposable subscription = null;

    {
        restApi = RestApiFactory.create();
        apiResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
        apiBlogCatResponse = new ApiResponse<>(ApiResponse.Status.LOADING, null, null);
    }

    public final void blogsApi() {
        subscription = restApi.blogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseLiveData.postValue(apiResponse.loading());
                    }
                })
                .subscribe(new Consumer<BlogData>() {
                    @Override
                    public void accept(BlogData blogData) throws Exception {
                        responseLiveData.postValue(apiResponse.success(blogData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseLiveData.postValue(apiResponse.error(throwable));
                    }
                });

    }

    public final void blogCategoryApi() {
        subscription = restApi.blogCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        responseBlogCatLiveData.postValue(apiBlogCatResponse.loading());
                    }
                })
                .subscribe(new Consumer<BlogCategoryData>() {
                    @Override
                    public void accept(BlogCategoryData blogCategoryData) throws Exception {
                        responseBlogCatLiveData.postValue(apiBlogCatResponse.success(blogCategoryData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseBlogCatLiveData.postValue(apiBlogCatResponse.error(throwable));
                    }
                });

    }

    public void disposeSubscriber() {
        if (subscription != null)
            subscription.dispose();
    }*/
}