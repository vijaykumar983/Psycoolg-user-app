package com.psycoolg.ui.components.fragments.psychologyProfile;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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
import com.psycoolg.R;
import com.psycoolg.databinding.FragmentPsychologyProfileBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.DoctorDetailData;
import com.psycoolg.ui.base.BaseFragment;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.ui.components.fragments.text_chat.TextChatFragment;
import com.psycoolg.utils.Constants;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.SessionManager;
import com.psycoolg.utils.Utility;

import java.util.HashMap;


public class PsychologyProfileFragment extends BaseFragment {
    private static final String TAG = PsychologyProfileFragment.class.getName();
    private FragmentPsychologyProfileBinding binding;
    private PsychologyProfileViewModel viewModel;
    private View.OnClickListener onClickListener = null;
    private HomeActivity homeActivity;
    private SessionManager sessionManager;
    private String doctorId = "";
    private Bundle bundle;

    @Override
    protected ViewDataBinding setBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_psychology_profile, container, false);
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
        binding.appBar.tvTitle.setText("Psychologist Profile");
        BottomNavigationView bottomNavigationView = mActivity.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
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
        getBundle();
        sessionManager = new SessionManager();
        String sourceString = "<b>" + "Description : " + "</b> " + getResources().getString(R.string.text_description);
        binding.tvDescription.setText(Html.fromHtml(sourceString));

        if (Utility.isDeviceOnline(mActivity)) {
            doctorDetailAPI();
        } else {
            Utility.showToastMessageError(mActivity, getString(R.string.no_internet));
        }
    }

    private void getBundle() {
        bundle = this.getArguments();
        if (bundle != null) {
            doctorId = bundle.getString("doctorId");

            Log.e(TAG, "doctor id - " + doctorId);
        }
    }


    @Override
    protected void initializeOnCreateObject() {
        homeActivity = (HomeActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(PsychologyProfileViewModel.class);
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<DoctorDetailData>>() {
            @Override
            public void onChanged(ApiResponse<DoctorDetailData> it) {
                handleResult(it);
            }
        });
    }


    @Override
    protected void setListeners() {
        binding.appBar.ivBack.setOnClickListener(this);
        binding.linearChat.setOnClickListener(this);
        binding.linearCall.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                homeActivity.onBackPressed();
                break;
            case R.id.linearChat:
                homeActivity.changeFragment(new TextChatFragment(), true);
                break;
            case R.id.linearCall:
                Utility.audioCall(mActivity, "7722348969");
                break;
        }
    }

    private void doctorDetailAPI() {
        HashMap<String, String> reqData = new HashMap<>();
        reqData.put("id", doctorId);

        Log.e(TAG, "Api parameters - " + reqData.toString());
        viewModel.doctorDetailApi(reqData);
    }

    private void handleResult(ApiResponse<DoctorDetailData> result) {
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

                    setDoctorProfile(result.getData().getData());

                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                }
                break;
        }
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void setDoctorProfile(DoctorDetailData.Data data) {
        binding.tvDoctorName.setText(data.getFirstName() + " " + data.getLastName());
        Utility.loadImage(binding.ivDoctorProfile, Constants.IMG_URL + data.getProfileImage());
        //binding.tvOnlineStatus.setText(Utility.toTitleCase( data.getOnlineStatus()));
        binding.rbDoctorRating.setRating(data.getRating());
        binding.tvDoctorRating.setText(data.getRating() + "/5");
        binding.tvCategoryPsy.setText(data.getProfessions());
        String sourceString = "<b>" + "Description: " + "</b> ";
        binding.tvDescription.setText(Html.fromHtml(sourceString) + data.getAbout());

        if (data.getOnlineStatus().equals("1")) {
            binding.linearStatus.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_corner_green));
            binding.viewStatus.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_ovel_rounded_green));
        } else {
            binding.linearStatus.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_corner_gray));
            binding.viewStatus.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_ovel_rounded_gray));
        }
    }
}