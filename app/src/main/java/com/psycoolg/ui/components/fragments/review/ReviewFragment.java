package com.psycoolg.ui.components.fragments.review;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.HashMap;

import com.psycoolg.R;
import com.psycoolg.databinding.DialogSuccessBinding;
import com.psycoolg.databinding.FragmentReviewBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.ReviewData;
import com.psycoolg.ui.base.BaseFragment;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.SessionManager;
import com.psycoolg.utils.Utility;


public class ReviewFragment extends BaseFragment {
    private static final String TAG = ReviewFragment.class.getName();
    private FragmentReviewBinding binding;
    private ReviewViewModel viewModel;
    private View.OnClickListener onClickListener = null;
    private HomeActivity homeActivity;
    private SessionManager sessionManager;
    private Bundle bundle;
    private String doctorId="";

    @Override
    protected ViewDataBinding setBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false);
        onClickListener = this;
        binding.setLifecycleOwner(this);
        return binding;
    }

    @Override
    protected void createActivityObject() {
        mActivity = (AppCompatActivity) getActivity();
    }

    @Override
    protected void initializeObject() {
        sessionManager = new SessionManager();
        bundle = this.getArguments();
        if (bundle != null) {
            doctorId = bundle.getString("doctorId");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        RelativeLayout rlHomeMain = mActivity.findViewById(R.id.rlHomeMain);
        rlHomeMain.setVisibility(View.GONE);
        binding.appBar.tvTitle.setText("Review");
        DrawerLayout drawerLayout = mActivity.findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        BottomNavigationView bottomNavigationView = mActivity.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.statusBarAll));
        }
    }


    @Override
    protected void initializeOnCreateObject() {
        homeActivity = (HomeActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<ReviewData>>() {
            @Override
            public void onChanged(ApiResponse<ReviewData> it) {
                handleResult(it);
            }
        });
    }


    @Override
    protected void setListeners() {
        binding.appBar.ivBack.setOnClickListener(this);
        binding.btnSubmitReview.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Utility.hideKeyboard(mActivity);
        switch (view.getId()) {
            case R.id.ivBack:
                homeActivity.onBackPressed();
                break;
            case R.id.btnSubmitReview:
                if (Utility.isDeviceOnline(mActivity)) {
                    reviewAPI();
                } else {
                    Utility.showToastMessageError(mActivity, getString(R.string.no_internet));
                }
                break;
        }
    }

    private void reviewAPI() {
        String msg = binding.edtMsg.getText().toString().trim();

        if (viewModel.isValidFormData(mActivity, msg)) {

            HashMap<String, String> reqData = new HashMap<>();
            reqData.put("rating", String.valueOf(binding.myRatingBar.getRating()));
            reqData.put("current_user", sessionManager.getUSERID());
            reqData.put("doctor_id", doctorId);
            reqData.put("massage", msg);


            Log.e(TAG, "Api parameters - " + reqData.toString());
            viewModel.reviewApi(reqData);
        }
    }

    private void handleResult(ApiResponse<ReviewData> result) {
        switch (result.getStatus()) {
            case ERROR:
                ProgressDialog.hideProgressDialog();
                Utility.showSnackBarMsgError(mActivity, result.getError().getMessage());
                Log.e(TAG, "error - " + result.getError().getMessage());
                break;
            case LOADING:
                ProgressDialog.showProgressDialog(mActivity);
                break;
            case SUCCESS:
                ProgressDialog.hideProgressDialog();
                Log.e(TAG, "Response - " + new Gson().toJson(result));
                if (result.getData().isStatus()) {

                    showSendOtpDialog();

                } else {
                    Utility.showSnackBarMsgError(mActivity, result.getData().getMassage());
                }
                break;
        }
    }

    private void showSendOtpDialog() {
        final Dialog dialog = new Dialog(mActivity, R.style.Theme_Dialog);
        DialogSuccessBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.dialog_success, null, false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(dialogBinding.getRoot());

        dialogBinding.tvTitle.setText("Successfully");
        dialogBinding.tvMessage.setText("Your review has been successfully submitted.");
        dialogBinding.llOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mActivity.onBackPressed();
            }
        });

        dialog.show();
    }

    @Override
    public void onDestroy() {
        viewModel.disposeSubscriber();
        super.onDestroy();
    }
}