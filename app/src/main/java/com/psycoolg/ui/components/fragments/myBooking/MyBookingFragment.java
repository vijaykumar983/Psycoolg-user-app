package com.psycoolg.ui.components.fragments.myBooking;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
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
import com.psycoolg.databinding.FragmentMyBookingBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.MyBookingData;
import com.psycoolg.ui.base.BaseFragment;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.ui.components.adapters.MyBookingAdapter;
import com.psycoolg.ui.components.fragments.review.ReviewFragment;
import com.psycoolg.ui.components.fragments.text_chat.TextChatFragment;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.SessionManager;
import com.psycoolg.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;


public class MyBookingFragment extends BaseFragment {
    private static final String TAG = MyBookingFragment.class.getName();
    private FragmentMyBookingBinding binding;
    private MyBookingViewModel viewModel;
    private View.OnClickListener onClickListener = null;
    private HomeActivity homeActivity;
    private SessionManager sessionManager;
    private ArrayList<MyBookingData.DataItem> myBookingData;
    private MyBookingAdapter myBookingAdapter;

    @Override
    protected ViewDataBinding setBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_booking, container, false);
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
        BottomNavigationView bottomNavigationView = mActivity.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
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
        sessionManager = new SessionManager();
        myBookingData = new ArrayList<>();

        if (Utility.isDeviceOnline(mActivity)) {
            HashMap<String, String> reqData = new HashMap<>();
            reqData.put("user_id", sessionManager.getUSERID());

            Log.e(TAG, "Api parameters - " + reqData.toString());
            viewModel.myBookingApi(reqData);
        } else {
            Utility.showToastMessageError(mActivity, getString(R.string.no_internet));
        }
    }


    @Override
    protected void initializeOnCreateObject() {
        homeActivity = (HomeActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(MyBookingViewModel.class);
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<MyBookingData>>() {
            @Override
            public void onChanged(ApiResponse<MyBookingData> it) {
                handleResult(it);
            }
        });
    }


    @Override
    protected void setListeners() {
        binding.appBar.ivBack.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linearGiveRating:
                int position = (int) view.getTag();
                myBookingAdapter.selectedPos = position;
                Bundle bundle = new Bundle();
                bundle.putString("doctorId", myBookingData.get(position).getId());
                homeActivity.changeFragment(new ReviewFragment(), true, bundle);
                break;
            case R.id.linearChat:
                homeActivity.changeFragment(new TextChatFragment(), true);
                break;
            case R.id.linearCall:
                Utility.audioCall(mActivity, "7722348969");
                break;
            case R.id.ivBack:
                homeActivity.onBackPressed();
                break;
        }
    }

    private void handleResult(ApiResponse<MyBookingData> result) {
        switch (result.getStatus()) {
            case ERROR:
                ProgressDialog.hideProgressDialog();
                Utility.showToastMessageError(mActivity, result.getError().getMessage());
                Log.e(TAG, "error - " + result.getError().getMessage());
                break;
            case LOADING:
                ProgressDialog.showProgressDialog(mActivity);
                break;
            case SUCCESS:
                ProgressDialog.hideProgressDialog();
                if (result.getData().isStatus()) {
                    Log.e(TAG, "Response - " + new Gson().toJson(result));
                    binding.rvMyBooking.setVisibility(View.VISIBLE);
                    binding.layoutError.rlerror.setVisibility(View.GONE);

                    if (result.getData().getData() != null && !result.getData().getData().isEmpty()) {
                        binding.rvMyBooking.setVisibility(View.VISIBLE);
                        binding.layoutError.rlerror.setVisibility(View.GONE);
                        myBookingData.clear();
                        myBookingData.addAll(result.getData().getData());
                        if (myBookingData.size() != 0) {
                            myBookingAdapter = new MyBookingAdapter(mActivity, onClickListener, myBookingData);
                            binding.rvMyBooking.setAdapter(myBookingAdapter);
                        }
                    } else {
                        binding.rvMyBooking.setVisibility(View.GONE);
                        binding.layoutError.rlerror.setVisibility(View.VISIBLE);
                    }
                } else {
                    //Utility.showToastMessageError(mActivity, result.getData().getMassage());
                    binding.rvMyBooking.setVisibility(View.GONE);
                    binding.layoutError.rlerror.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        viewModel.disposeSubscriber();
        super.onDestroy();
    }

}