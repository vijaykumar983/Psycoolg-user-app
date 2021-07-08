package com.psycoolg.ui.components.activities.verifyOtp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.psycoolg.R;
import com.psycoolg.databinding.ActivityVerifyOtpBinding;
import com.psycoolg.databinding.DialogSuccessBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.LoginData;
import com.psycoolg.pojo.SendOtpData;
import com.psycoolg.ui.base.BaseBindingActivity;
import com.psycoolg.ui.components.activities.updatePassword.UpdatePassActivity;
import com.psycoolg.utils.Constants;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.SessionManager;
import com.psycoolg.utils.Utility;

import java.util.HashMap;

public class VerifyOtpActivity extends BaseBindingActivity {
    private static final String TAG = VerifyOtpActivity.class.getName();
    private ActivityVerifyOtpBinding binding;
    private VerifyOtpViewModel viewModel = null;
    private SessionManager sessionManager;


    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_otp);
        viewModel = new ViewModelProvider(this).get(VerifyOtpViewModel.class);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void createActivityObject(@Nullable Bundle savedInstanceState) {
        mActivity = this;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initializeObject() {
        sessionManager = new SessionManager();
        viewModel.responseLiveLoginOtpData.observe(this, new Observer<ApiResponse<LoginData>>() {
            @Override
            public void onChanged(ApiResponse<LoginData> it) {
                handleLoginOtpResult(it);
            }
        });
        viewModel.responseLiveSendOtpData.observe(this, new Observer<ApiResponse<SendOtpData>>() {
            @Override
            public void onChanged(ApiResponse<SendOtpData> it) {
                handleSendOtpResult(it);
            }
        });
    }

    @Override
    protected void setListeners() {
        binding.btnGetOtp.setOnClickListener(this);
        binding.btnVerify.setOnClickListener(this);
        binding.tvResendOtp.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Utility.hideKeyboard(mActivity);
        switch (view.getId()) {
            case R.id.btnGetOtp:
                if (Utility.isDeviceOnline(mActivity)) {
                    sendOtpAPI();
                } else {
                    Utility.showSnackBarMsgError(mActivity, getString(R.string.no_internet));
                }
                break;
            case R.id.btnVerify:
                if (Utility.isDeviceOnline(mActivity)) {
                    loginOtpAPI();
                } else {
                    Utility.showSnackBarMsgError(mActivity, getString(R.string.no_internet));
                }
                break;
            case R.id.tvResendOtp:
                if (Utility.isDeviceOnline(mActivity)) {
                    sendOtpAPI();
                } else {
                    Utility.showSnackBarMsgError(mActivity, getString(R.string.no_internet));
                }
        }
    }

    private void sendOtpAPI() {
        String mobile = binding.etMobile.getText().toString().trim();

        if (viewModel.isValidSendOtpFormData(mActivity, mobile)) {

            HashMap<String, String> reqData = new HashMap<>();
            reqData.put("mobile", mobile);

            Log.e(TAG, "Api parameters - " + reqData.toString());
            viewModel.sendOtpApi(reqData);
        }
    }

    private void handleSendOtpResult(ApiResponse<SendOtpData> result) {
        switch (result.getStatus()) {
            case ERROR:
                ProgressDialog.hideProgressDialog();
                Utility.showSnackBarMsgError(mActivity, result.getError().getMessage());
                Log.e(TAG, "error - " + result.getError().getMessage());
                break;
            case LOADING:
                ProgressDialog.showProgressDialog(this);
                break;
            case SUCCESS:
                ProgressDialog.hideProgressDialog();
                Log.e(TAG, "Response - " + new Gson().toJson(result));
                if (result.getData().isStatus()) {

                    binding.rlGetOtp.setVisibility(View.GONE);
                    binding.rlVerifyOtp.setVisibility(View.VISIBLE);

                    binding.etOTP.setText(result.getData().getData().getOtp());
                    showSendOtpDialog();
                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                }
                break;
        }
    }

    private void loginOtpAPI() {
        String mobile = binding.etMobile.getText().toString().trim();
        String otp = binding.etOTP.getText().toString().trim();

        if (viewModel.isValidloginOtpFormData(mActivity, mobile, otp)) {

            HashMap<String, String> reqData = new HashMap<>();
            reqData.put("mobile", mobile);
            reqData.put("otp", otp);

            Log.e(TAG, "Api parameters - " + reqData.toString());
            viewModel.loginOtpApi(reqData);
        }
    }

    private void handleLoginOtpResult(ApiResponse<LoginData> result) {
        switch (result.getStatus()) {
            case ERROR:
                ProgressDialog.hideProgressDialog();
                Utility.showSnackBarMsgError(mActivity, result.getError().getMessage());
                Log.e(TAG, "error - " + result.getError().getMessage());
                break;
            case LOADING:
                ProgressDialog.showProgressDialog(this);
                break;
            case SUCCESS:
                ProgressDialog.hideProgressDialog();
                Log.e(TAG, "Response - " + new Gson().toJson(result));
                if (result.getData().isStatus()) {

                    binding.rlGetOtp.setVisibility(View.VISIBLE);
                    binding.rlVerifyOtp.setVisibility(View.GONE);
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", result.getData().getData().getUserId());
                    UpdatePassActivity.startActivity(mActivity, bundle, false);

                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                }
                break;
        }
    }

    private void showSendOtpDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        DialogSuccessBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.dialog_success, null, false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(dialogBinding.getRoot());

        dialogBinding.tvTitle.setText("Send OTP");
        dialogBinding.tvMessage.setText("Your One Time Pin(OTP) will be SMS to you");
        dialogBinding.llOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void startActivity(Activity activity, Bundle bundle, boolean isClear) {
        Intent intent = new Intent(activity, VerifyOtpActivity.class);
        if (bundle != null) intent.putExtra("bundle", bundle);
        if (isClear)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        viewModel.disposeSubscriber();
        super.onDestroy();
    }
}
