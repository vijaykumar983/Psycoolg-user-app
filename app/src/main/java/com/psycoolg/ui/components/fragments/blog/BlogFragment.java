package com.psycoolg.ui.components.fragments.blog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.psycoolg.R;
import com.psycoolg.databinding.FragmentBlogBinding;
import com.psycoolg.network.Retrofit;
import com.psycoolg.pojo.BlogCategoryData;
import com.psycoolg.pojo.BlogData;
import com.psycoolg.ui.base.BaseFragment;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.ui.components.adapters.BlogAdapter;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.Utility;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BlogFragment extends BaseFragment {
    private static final String TAG = BlogFragment.class.getName();
    private FragmentBlogBinding binding;
    private BlogViewModel viewModel;
    private View.OnClickListener onClickListener = null;
    private HomeActivity homeActivity;
    private BlogAdapter blogAdapter;
    private ArrayList<BlogData.DataItem> blogData;
    private ArrayList<BlogCategoryData.DataItem> blogCatData;

    @Override
    protected ViewDataBinding setBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blog, container, false);
        onClickListener = this;
        binding.setLifecycleOwner(this);
        return binding;
    }

    @Override
    protected void createActivityObject() {
        mActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        RelativeLayout rlHomeMain = mActivity.findViewById(R.id.rlHomeMain);
        rlHomeMain.setVisibility(View.GONE);
        binding.appBar.tvTitle.setText("Blog");
        BottomNavigationView bottomNavigationView = mActivity.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        DrawerLayout drawerLayout = mActivity.findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.statusBarAll));
        }
    }

    @Override
    protected void initializeObject() {
        blogData = new ArrayList<>();
        blogCatData = new ArrayList<>();

        if (Utility.isDeviceOnline(mActivity)) {
            blogsAPI();
            blogCategoryAPI();
        } else {
            Utility.showToastMessageError(mActivity, getString(R.string.no_internet));
        }
    }


    @Override
    protected void initializeOnCreateObject() {
        homeActivity = (HomeActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(BlogViewModel.class);
    }


    @Override
    protected void setListeners() {
        binding.appBar.ivBack.setOnClickListener(this);
        binding.tvBlogCategory.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                homeActivity.onBackPressed();
                break;
            case R.id.tvBlogCategory:
                showBlogCategoryDialog();
                break;
            case R.id.rowBlogItem:
                int position = (int) view.getTag();
                blogAdapter.selectedPos = position;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(blogData.get(position).getPermalink())));
                break;
        }
    }

    private void showBlogCategoryDialog() {
        if (blogCatData.size() != 0) {
            ArrayList<String> a1 = new ArrayList<>();

            for (int i = 0; i < blogCatData.size(); i++) {
                a1.add(blogCatData.get(i).getCatName());
            }
            String[] stockArr = a1.toArray(new String[a1.size()]);

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("Blog Category");
            builder.setItems(stockArr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(mActivity, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    binding.tvBlogCategory.setText(stockArr[which]);
                    binding.tvBlogCategory.setSelected(true);
                    Log.e(TAG, "category Id - " + blogCatData.get(which).getCatID());
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Utility.showToastMessageError(mActivity, "No Data available!");
        }
    }

    private void blogCategoryAPI() {
        ProgressDialog.showProgressDialog(mActivity);

        Call<BlogCategoryData> call = Retrofit.getAPIService().blogCategory();
        call.enqueue(new Callback<BlogCategoryData>() {
            @Override
            public void onResponse(Call<BlogCategoryData> call, Response<BlogCategoryData> response) {
                ProgressDialog.hideProgressDialog();
                if (Objects.requireNonNull(response.body()).isStatus()) {
                    Log.e(TAG, "Response blog - " + new Gson().toJson(response.body()));

                    if (response.body().getData() != null && !response.body().getData().isEmpty()) {
                        blogCatData.clear();
                        blogCatData.addAll(response.body().getData());

                    } else {
                        Utility.showToastMessageError(mActivity, response.body().getMassage());
                    }
                } else {
                    Utility.showToastMessageError(mActivity, response.body().getMassage());
                }
            }

            @Override
            public void onFailure(Call<BlogCategoryData> call, Throwable t) {
                ProgressDialog.hideProgressDialog();
                Log.e(TAG, "error - " + t.getMessage());
            }
        });
    }

    private void blogsAPI() {
        ProgressDialog.showProgressDialog(mActivity);

        Call<BlogData> call = Retrofit.getAPIService().blogs();
        call.enqueue(new Callback<BlogData>() {
            @Override
            public void onResponse(Call<BlogData> call, Response<BlogData> response) {
                ProgressDialog.hideProgressDialog();
                if (Objects.requireNonNull(response.body()).isStatus()) {
                    Log.e(TAG, "Response blog category - " + new Gson().toJson(response.body()));
                    binding.rvBlog.setVisibility(View.VISIBLE);
                    binding.layoutError.rlerror.setVisibility(View.GONE);

                    if (response.body().getData() != null && !response.body().getData().isEmpty()) {
                        binding.rvBlog.setVisibility(View.VISIBLE);
                        binding.layoutError.rlerror.setVisibility(View.GONE);
                        blogData.clear();
                        blogData.addAll(response.body().getData());
                        if (blogData.size() != 0) {
                            blogAdapter = new BlogAdapter(mActivity, onClickListener, blogData);
                            binding.rvBlog.setAdapter(blogAdapter);
                        }
                    } else {
                        binding.rvBlog.setVisibility(View.GONE);
                        binding.layoutError.rlerror.setVisibility(View.VISIBLE);
                    }
                } else {
                    //Utility.showToastMessageError(mActivity, result.getData().getMassage());
                    binding.rvBlog.setVisibility(View.GONE);
                    binding.layoutError.rlerror.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<BlogData> call, Throwable t) {
                ProgressDialog.hideProgressDialog();
                Log.e(TAG, "error - " + t.getMessage());
            }
        });
    }

}