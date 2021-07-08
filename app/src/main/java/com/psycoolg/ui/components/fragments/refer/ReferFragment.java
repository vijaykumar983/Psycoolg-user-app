package com.psycoolg.ui.components.fragments.refer;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.psycoolg.R;
import com.psycoolg.databinding.FragmentReferBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.ReferData;
import com.psycoolg.ui.base.BaseFragment;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.ui.components.adapters.ReferFriendAdapter;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.SessionManager;
import com.psycoolg.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;


public class ReferFragment extends BaseFragment {
    private static final String TAG = ReferFragment.class.getName();
    private FragmentReferBinding binding;
    private ReferViewModel viewModel;
    private SessionManager sessionManager;
    private View.OnClickListener onClickListener = null;
    private HomeActivity homeActivity;
    private ArrayList<ReferData.Data.ReferItem> referItem;
    private ReferFriendAdapter referFriendAdapter;

    @Override
    protected ViewDataBinding setBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_refer, container, false);
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
        binding.appBar.tvTitle.setText("Refer a friend");
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
        referItem = new ArrayList<>();
        myreferApi();
    }


    @Override
    protected void initializeOnCreateObject() {
        homeActivity = (HomeActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(ReferViewModel.class);
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<ReferData>>() {
            @Override
            public void onChanged(ApiResponse<ReferData> it) {
                handleResult(it);
            }
        });
    }


    @Override
    protected void setListeners() {
        binding.appBar.ivBack.setOnClickListener(this);
        binding.btnCopyCode.setOnClickListener(this);
        binding.ivShare.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                homeActivity.onBackPressed();
                break;
            case R.id.btnCopyCode:
                copyClipboard();
                break;
            case R.id.ivShare:
                Utility.shareApp(mActivity);
                break;
        }
    }

    private void myreferApi() {
        if (Utility.isDeviceOnline(mActivity)) {

            HashMap<String, String> reqData = new HashMap<>();
            reqData.put("user_id", sessionManager.getUSERID());

            Log.e(TAG, "Api parameters - " + reqData.toString());
            viewModel.referApi(reqData);

        } else {
            Utility.showToastMessageError(mActivity, getString(R.string.no_internet));
        }
    }

    @SuppressLint("SetTextI18n")
    private void handleResult(ApiResponse<ReferData> result) {
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
                    binding.rvReferFriend.setVisibility(View.VISIBLE);
                    binding.layoutError.rlerror.setVisibility(View.GONE);

                    binding.tvReferCode.setText(result.getData().getData().getReferCode());
                    binding.tvCurrentBonusAmt.setText("Rs. " + result.getData().getData().getCurrentBalance());
                    binding.tvAmtEarned.setText("Rs. " + result.getData().getData().getTotalCradit());

                    if (result.getData().getData().getRefer() != null && !result.getData().getData().getRefer().isEmpty()) {
                        binding.rvReferFriend.setVisibility(View.VISIBLE);
                        binding.layoutError.rlerror.setVisibility(View.GONE);

                        referItem.clear();
                        referItem.addAll(result.getData().getData().getRefer());
                        if (referItem.size() != 0) {
                            referFriendAdapter = new ReferFriendAdapter(mActivity, onClickListener, referItem);
                            binding.rvReferFriend.setAdapter(referFriendAdapter);
                        }
                    } else {
                        binding.rvReferFriend.setVisibility(View.GONE);
                        binding.layoutError.rlerror.setVisibility(View.VISIBLE);
                    }
                } else {
                    //Utility.showToastMessageError(mActivity, result.getData().getMassage());
                    binding.rvReferFriend.setVisibility(View.GONE);
                    binding.layoutError.rlerror.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void copyClipboard() {
        String tvReferCode = binding.tvReferCode.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied", tvReferCode);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(mActivity, "Copied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        viewModel.disposeSubscriber();
        super.onDestroy();
    }
}