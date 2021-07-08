package com.psycoolg.ui.components.activities.register;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.psycoolg.R;
import com.psycoolg.databinding.ActivityRegisterBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.RegisterData;
import com.psycoolg.ui.base.BaseBindingActivity;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.ui.components.activities.login.LoginActivity;
import com.psycoolg.ui.components.activities.staticPage.StaticPageActivity;
import com.psycoolg.utils.EmojiExcludeFilter;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.SessionManager;
import com.psycoolg.utils.Utility;

import java.util.HashMap;

public class RegisterActivity extends BaseBindingActivity {
    private static final String TAG = RegisterActivity.class.getName();
    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel = null;
    private SessionManager sessionManager;


    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
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
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<RegisterData>>() {
            @Override
            public void onChanged(ApiResponse<RegisterData> it) {
                handleResult(it);
            }
        });
        binding.etFirstName.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        binding.etLastName.setFilters(new InputFilter[]{new EmojiExcludeFilter()});

    }

    @Override
    protected void setListeners() {
        binding.tvSignIn.setOnClickListener(this);
        binding.tvPrivacyPolicy.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Utility.hideKeyboard(mActivity);
        switch (view.getId()) {
            case R.id.tvSignIn:
                LoginActivity.startActivity(mActivity, null, false);
                finish();
                break;
            case R.id.tvPrivacyPolicy:
                StaticPageActivity.startActivity(mActivity, null, false);
                break;
            case R.id.btnSignUp:
                if (Utility.isDeviceOnline(mActivity)) {
                    registerAPI();
                } else {
                    Utility.showSnackBarMsgError(mActivity, getString(R.string.no_internet));
                }
                break;
        }
    }

    private void registerAPI() {
        String fName = binding.etFirstName.getText().toString().trim();
        String lName = binding.etLastName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String mobile = binding.etMobile.getText().toString().trim();
        String pass = binding.etPassword.getText().toString().trim();
        String confirmPass = binding.etConfirmPassword.getText().toString().trim();

        if (viewModel.isValidFormData(mActivity, fName, lName, email, mobile, pass, confirmPass)) {

            HashMap<String, String> reqData = new HashMap<>();
            reqData.put("firstname", fName);
            reqData.put("lastname", lName);
            reqData.put("email", email);
            reqData.put("mobile", mobile);
            reqData.put("password", pass);
            reqData.put("password_confirm", confirmPass);

            Log.e(TAG, "Api parameters - " + reqData.toString());
            viewModel.registerApi(reqData);
        }
    }

    private void handleResult(ApiResponse<RegisterData> result) {
        switch (result.getStatus()) {
            case ERROR:
                ProgressDialog.hideProgressDialog();
                Log.e(TAG, "error - " + result.getError().getMessage());
                Utility.showSnackBarMsgError(mActivity, result.getError().getMessage());
                break;
            case LOADING:
                ProgressDialog.showProgressDialog(this);
                break;
            case SUCCESS:
                ProgressDialog.hideProgressDialog();
                Log.e(TAG, "Response - " + new Gson().toJson(result));
                if (result.getData().isStatus()) {

                    sessionManager.setUSERID(result.getData().getData().getId());
                    sessionManager.setFIRST_NAME(result.getData().getData().getFirstname());
                    sessionManager.setLAST_NAME(result.getData().getData().getLastname());
                    sessionManager.setEMAIL(result.getData().getData().getEmail());
                    sessionManager.setMOBILE(result.getData().getData().getPhone());
                    sessionManager.setPROFILE_IMG(result.getData().getData().getProfileImage());
                    sessionManager.setREFER_CODE(result.getData().getData().getReferCode());
                    sessionManager.setLogin();

                    HomeActivity.startActivity(mActivity, null, true);
                    finish();

                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                }
                break;
        }
    }

    public static void startActivity(Activity activity, Bundle bundle, boolean isClear) {
        Intent intent = new Intent(activity, RegisterActivity.class);
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
