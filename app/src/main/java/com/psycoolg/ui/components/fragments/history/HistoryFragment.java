package com.psycoolg.ui.components.fragments.history;

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
import com.psycoolg.databinding.FragmentHistoryBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.MyHistoryData;
import com.psycoolg.ui.base.BaseFragment;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.ui.components.adapters.HistoryAdapter;
import com.psycoolg.ui.components.fragments.review.ReviewFragment;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.SessionManager;
import com.psycoolg.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryFragment extends BaseFragment {
    private static final String TAG = HistoryFragment.class.getName();
    private FragmentHistoryBinding binding;
    private HistoryViewModel viewModel;
    private View.OnClickListener onClickListener = null;
    private HomeActivity homeActivity;
    private SessionManager sessionManager;
    private ArrayList<MyHistoryData.DataItem> myHistoryData;
    private HistoryAdapter historyAdapter;


    @Override
    protected ViewDataBinding setBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
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
        binding.appBar.tvTitle.setText("History");
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
    protected void initializeObject() {
        sessionManager = new SessionManager();
        myHistoryData = new ArrayList<>();
        myHistoryApi();
    }

    private void myHistoryApi() {
        if (Utility.isDeviceOnline(mActivity)) {

            HashMap<String, String> reqData = new HashMap<>();
            reqData.put("user_id", sessionManager.getUSERID());

            Log.e(TAG, "Api parameters - " + reqData.toString());
            viewModel.myHistoryApi(reqData);

        } else {
            Utility.showToastMessageError(mActivity, getString(R.string.no_internet));
        }
    }


    @Override
    protected void initializeOnCreateObject() {
        homeActivity = (HomeActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<MyHistoryData>>() {
            @Override
            public void onChanged(ApiResponse<MyHistoryData> it) {
                handleResult(it);
            }
        });
    }

    private void handleResult(ApiResponse<MyHistoryData> result) {
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
                    binding.rvHistory.setVisibility(View.VISIBLE);
                    binding.layoutError.rlerror.setVisibility(View.GONE);

                    if (result.getData().getData() != null && !result.getData().getData().isEmpty()) {
                        binding.rvHistory.setVisibility(View.VISIBLE);
                        binding.layoutError.rlerror.setVisibility(View.GONE);
                        myHistoryData.clear();
                        myHistoryData.addAll(result.getData().getData());
                        if (myHistoryData.size() != 0) {
                            historyAdapter = new HistoryAdapter(mActivity, onClickListener, myHistoryData);
                            binding.rvHistory.setAdapter(historyAdapter);
                        }
                    } else {
                        binding.rvHistory.setVisibility(View.GONE);
                        binding.layoutError.rlerror.setVisibility(View.VISIBLE);
                    }
                } else {
                    //Utility.showToastMessageError(mActivity, result.getData().getMassage());
                    binding.rvHistory.setVisibility(View.GONE);
                    binding.layoutError.rlerror.setVisibility(View.VISIBLE);
                }
                break;
        }
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
                historyAdapter.selectedPos = position;
                Bundle bundle = new Bundle();
                bundle.putString("doctorId", myHistoryData.get(position).getId());
                homeActivity.changeFragment(new ReviewFragment(), true, bundle);
                break;
            case R.id.ivBack:
                homeActivity.onBackPressed();
                break;
        }
    }

    @Override
    public void onDestroy() {
        viewModel.disposeSubscriber();
        super.onDestroy();
    }
}