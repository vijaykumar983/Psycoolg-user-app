package com.psycoolg.ui.components.activities.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.HashMap;

import com.psycoolg.R;
import com.psycoolg.databinding.ActivityLoginBinding;
import com.psycoolg.databinding.DialogSuccessBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.LoginData;
import com.psycoolg.pojo.SendOtpData;
import com.psycoolg.ui.base.BaseBindingActivity;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.ui.components.activities.register.RegisterActivity;
import com.psycoolg.ui.components.activities.verifyOtp.VerifyOtpActivity;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.SessionManager;
import com.psycoolg.utils.Utility;

public class LoginActivity extends BaseBindingActivity implements TextWatcher {
    private static final String TAG = LoginActivity.class.getName();
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel = null;
    private SessionManager sessionManager;
    private String check_api = "";


    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
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
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<LoginData>>() {
            @Override
            public void onChanged(ApiResponse<LoginData> it) {
                handleResult(it);
            }
        });
        viewModel.responseLiveLoginOtpData.observe(this, new Observer<ApiResponse<LoginData>>() {
            @Override
            public void onChanged(ApiResponse<LoginData> it) {
                handleResult(it);
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
        binding.tvSignUp.setOnClickListener(this);
        binding.btnGetOTP.setOnClickListener(this);
        binding.btnLogIn.setOnClickListener(this);
        binding.tvForgetPassword.setOnClickListener(this);
        binding.etOTP.addTextChangedListener(this);
        binding.etPassword.addTextChangedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Utility.hideKeyboard(mActivity);
        switch (view.getId()) {
            case R.id.tvSignUp:
                RegisterActivity.startActivity(mActivity, null, false);
                finish();
                break;
            case R.id.btnGetOTP:
                if (Utility.isDeviceOnline(mActivity)) {
                    if (binding.etPassword.getText().toString().length() == 0) {
                        sendOtpAPI();
                    }
                } else {
                    Utility.showSnackBarMsgError(mActivity, getString(R.string.no_internet));
                }
                break;
            case R.id.btnLogIn:
                if (Utility.isDeviceOnline(mActivity)) {
                    if (check_api.equals("loginOtp")) {
                        loginOtpAPI();
                    } else {
                        loginAPI();
                    }

                } else {
                    Utility.showSnackBarMsgError(mActivity, getString(R.string.no_internet));
                }
                break;
            case R.id.tvForgetPassword:
                VerifyOtpActivity.startActivity(mActivity, null, false);
                break;
        }
    }

    private void loginAPI() {
        String mobile = binding.etMobile.getText().toString().trim();
        String pass = binding.etPassword.getText().toString().trim();

        if (viewModel.isValidFormData(mActivity, mobile, pass)) {

            HashMap<String, String> reqData = new HashMap<>();
            reqData.put("mobile", mobile);
            reqData.put("password", pass);

            Log.e(TAG, "Api parameters - " + reqData.toString());
            viewModel.loginApi(reqData);
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

    private void handleResult(ApiResponse<LoginData> result) {
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

                    sessionManager.setUSERID(result.getData().getData().getUserId());
                    sessionManager.setFIRST_NAME(result.getData().getData().getFirstname());
                    sessionManager.setLAST_NAME(result.getData().getData().getLastname());
                    sessionManager.setEMAIL(result.getData().getData().getEmail());
                    sessionManager.setMOBILE(result.getData().getData().getMobile());
                    sessionManager.setPROFILE_IMG(result.getData().getData().getImage());
                    sessionManager.setAuthToken(result.getData().getData().getToken());
                    sessionManager.setLogin();

                    HomeActivity.startActivity(mActivity, null, true);
                    finish();

                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                }
                break;
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

                    binding.etOTP.setText(result.getData().getData().getOtp());
                    check_api = "loginOtp";
                    showSendOtpDialog();
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
        Intent intent = new Intent(activity, LoginActivity.class);
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (binding.etOTP.getText().hashCode() == charSequence.hashCode()) {
            if (charSequence.toString().length() == 0) {
                binding.etPassword.setEnabled(true);
                binding.etOTP.setEnabled(true);
                check_api = "";
            } else if (charSequence.toString().length() > 0) {
                binding.etOTP.setEnabled(true);
                binding.etPassword.setEnabled(false);
                check_api = "loginOtp";
            }
        } else if (binding.etPassword.getText().hashCode() == charSequence.hashCode()) {
            if (charSequence.toString().length() == 0) {
                binding.etPassword.setEnabled(true);
                binding.etOTP.setEnabled(true);
                check_api = "";
            } else if (charSequence.toString().length() > 0) {
                binding.etPassword.setEnabled(true);
                binding.etOTP.setEnabled(false);
                check_api = "login";
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
