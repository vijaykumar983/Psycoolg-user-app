package com.psycoolg.ui.components.fragments.offers;

import android.os.Build;
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

import java.util.ArrayList;

import com.psycoolg.R;
import com.psycoolg.databinding.FragmentOfferBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.OfferData;
import com.psycoolg.ui.base.BaseFragment;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.ui.components.adapters.OfferAdapter;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.Utility;


public class OfferFragment extends BaseFragment {
    private static final String TAG = OfferFragment.class.getName();
    private FragmentOfferBinding binding;
    private OfferViewModel viewModel;
    private View.OnClickListener onClickListener = null;
    private HomeActivity homeActivity;
    private ArrayList<OfferData.DataItem> offerData;
    private OfferAdapter offerAdapter;

    @Override
    protected ViewDataBinding setBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offer, container, false);
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
        binding.appBar.tvTitle.setText("Offers");
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
        offerData = new ArrayList<>();
        if (Utility.isDeviceOnline(mActivity)) {
            viewModel.offerApi();
        } else {
            Utility.showToastMessageError(mActivity, getString(R.string.no_internet));
        }
    }


    @Override
    protected void initializeOnCreateObject() {
        homeActivity = (HomeActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(OfferViewModel.class);
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<OfferData>>() {
            @Override
            public void onChanged(ApiResponse<OfferData> it) {
                handleResult(it);
            }
        });
    }


    @Override
    protected void setListeners() {
        binding.appBar.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                homeActivity.onBackPressed();
                break;
        }
    }

    private void handleResult(ApiResponse<OfferData> result) {
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
                    binding.rvOffer.setVisibility(View.VISIBLE);
                    binding.layoutError.rlerror.setVisibility(View.GONE);

                    if (result.getData().getData() != null && !result.getData().getData().isEmpty()) {
                        binding.rvOffer.setVisibility(View.VISIBLE);
                        binding.layoutError.rlerror.setVisibility(View.GONE);
                        offerData.clear();
                        offerData.addAll(result.getData().getData());
                        if (offerData.size() != 0) {
                            offerAdapter = new OfferAdapter(mActivity, onClickListener, offerData);
                            binding.rvOffer.setAdapter(offerAdapter);
                        }
                    } else {
                        binding.rvOffer.setVisibility(View.GONE);
                        binding.layoutError.rlerror.setVisibility(View.VISIBLE);
                    }
                } else {
                    //Utility.showToastMessageError(mActivity, result.getData().getMassage());
                    binding.rvOffer.setVisibility(View.GONE);
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