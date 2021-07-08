package com.psycoolg.ui.components.activities.updatePassword;

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

import java.util.HashMap;

import com.psycoolg.R;
import com.psycoolg.databinding.ActivityUpdatePassBinding;
import com.psycoolg.databinding.DialogSuccessBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.UpdatePassData;
import com.psycoolg.ui.base.BaseBindingActivity;
import com.psycoolg.ui.components.activities.login.LoginActivity;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.SessionManager;
import com.psycoolg.utils.Utility;

public class UpdatePassActivity extends BaseBindingActivity {
    private static final String TAG = UpdatePassActivity.class.getName();
    private ActivityUpdatePassBinding binding;
    private UpdatePassViewModel viewModel = null;
    private Bundle mBundle;
    private String userId = "";
    private SessionManager sessionManager;


    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_pass);
        viewModel = new ViewModelProvider(this).get(UpdatePassViewModel.class);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void createActivityObject(@Nullable Bundle savedInstanceState) {
        mActivity = this;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initializeObject() {
        getIntentData();
        sessionManager = new SessionManager();
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<UpdatePassData>>() {
            @Override
            public void onChanged(ApiResponse<UpdatePassData> it) {
                handleResult(it);
            }
        });

    }

    @Override
    protected void setListeners() {
        binding.btnUpdatePass.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Utility.hideKeyboard(mActivity);
        switch (view.getId()) {
            case R.id.btnUpdatePass:
                if (Utility.isDeviceOnline(mActivity)) {
                    updatePassAPI();
                } else {
                    Utility.showSnackBarMsgError(mActivity, getString(R.string.no_internet));
                }
                break;
        }
    }

    private void updatePassAPI() {
        String pass = binding.etNewPass.getText().toString().trim();
        String confirmPass = binding.etConfirmPass.getText().toString().trim();

        if (viewModel.isValidFormData(mActivity, pass, confirmPass)) {

            HashMap<String, String> reqData = new HashMap<>();
            reqData.put("user_id", userId);
            reqData.put("password", pass);
            reqData.put("password_confirm", confirmPass);

            Log.e(TAG, "Api parameters - " + reqData.toString());
            viewModel.updatePassApi(reqData);
        }
    }

    private void handleResult(ApiResponse<UpdatePassData> result) {
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

                    showSuccessDialog();

                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                }
                break;
        }
    }

    private void showSuccessDialog() {
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

        dialogBinding.tvTitle.setText("Successfully");
        dialogBinding.tvMessage.setText("Your password successfully reset.");

        dialogBinding.llOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                LoginActivity.startActivity(mActivity, null, true);
                finish();
            }
        });

        dialog.show();
    }

    private void getIntentData() {
        mBundle = getIntent().getBundleExtra("bundle");
        if (mBundle != null) {
            userId = mBundle.getString("userId");
        }
    }

    public static void startActivity(Activity activity, Bundle bundle, boolean isClear) {
        Intent intent = new Intent(activity, UpdatePassActivity.class);
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
