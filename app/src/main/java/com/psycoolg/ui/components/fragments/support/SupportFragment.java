package com.psycoolg.ui.components.fragments.support;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.psycoolg.R;
import com.psycoolg.databinding.DialogSuccessBinding;
import com.psycoolg.databinding.FragmentSupportBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.AddTopicData;
import com.psycoolg.pojo.TopicData;
import com.psycoolg.ui.base.BaseFragment;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.SessionManager;
import com.psycoolg.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;

public class SupportFragment extends BaseFragment {
    private static final String TAG = SupportFragment.class.getName();
    private FragmentSupportBinding binding;
    private SupportViewModel viewModel;
    private View.OnClickListener onClickListener = null;
    private HomeActivity homeActivity;
    private SessionManager sessionManager;
    private ArrayList<TopicData.DataItem> topicData;
    private String topicId = "";


    @Override
    protected ViewDataBinding setBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_support, container, false);
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
        binding.appBar.tvTitle.setText("Support");
        BottomNavigationView bottomNavigationView = mActivity.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(4).setChecked(true);
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
        topicData = new ArrayList<>();
        if (Utility.isDeviceOnline(mActivity)) {
            viewModel.topicApi();
        } else {
            Utility.showToastMessageError(mActivity, getString(R.string.no_internet));
        }
    }


    @Override
    protected void initializeOnCreateObject() {
        homeActivity = (HomeActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(SupportViewModel.class);
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<TopicData>>() {
            @Override
            public void onChanged(ApiResponse<TopicData> it) {
                handleTopicResult(it);
            }
        });
        viewModel.responseAddTopicLiveData.observe(this, new Observer<ApiResponse<AddTopicData>>() {
            @Override
            public void onChanged(ApiResponse<AddTopicData> it) {
                handleAddTopicResult(it);
            }
        });
    }


    @Override
    protected void setListeners() {
        binding.appBar.ivBack.setOnClickListener(this);
        binding.tvChooseTopic.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        binding.linearContact.setOnClickListener(this);
        binding.tvEmailId.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Utility.hideKeyboard(mActivity);
        switch (view.getId()) {
            case R.id.ivBack:
                homeActivity.onBackPressed();
                break;
            case R.id.tvChooseTopic:
                showTopicDialog();
                break;
            case R.id.btnSubmit:
                if (Utility.isDeviceOnline(mActivity)) {
                    if (binding.tvChooseTopic.getText().toString().equals("Choose Topic"))
                        Utility.showToastMessageError(mActivity, "Please Choose Topic");
                    else
                        addTopicAPI();
                } else {
                    Utility.showToastMessageError(mActivity, getString(R.string.no_internet));
                }
                break;
            case R.id.linearContact:
                Utility.audioCall(mActivity,"+91-999999999");
                break;
            case R.id.tvEmailId:
                Utility.sendEmail(mActivity,"support@psycoolg.com");
                break;
        }
    }

    private void showTopicDialog() {
        if (topicData.size() != 0) {
            ArrayList<String> a1 = new ArrayList<>();

            for (int i = 0; i < topicData.size(); i++) {
                a1.add(topicData.get(i).getName());
            }
            String[] stockArr = a1.toArray(new String[a1.size()]);

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("Choose Topic");
            builder.setItems(stockArr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(mActivity, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    binding.tvChooseTopic.setText(stockArr[which]);
                    binding.tvChooseTopic.setSelected(true);
                    Log.e(TAG, "topic Id - " + topicData.get(which).getId());
                    topicId = topicData.get(which).getId();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Utility.showToastMessageError(mActivity, "No Data available!");
        }
    }

    private void handleTopicResult(ApiResponse<TopicData> result) {
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

                    if (result.getData().getData() != null && !result.getData().getData().isEmpty()) {
                        topicData.clear();
                        topicData.addAll(result.getData().getData());

                    } else {
                        Utility.showToastMessageError(mActivity, result.getData().getMassage());
                    }

                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                }
                break;
        }
    }

    private void addTopicAPI() {
        String msg = binding.edtMsg.getText().toString().trim();

        if (viewModel.isValidFormData(mActivity, msg)) {

            HashMap<String, String> reqData = new HashMap<>();
            reqData.put("topic_id", topicId);
            reqData.put("user_id", sessionManager.getUSERID());
            reqData.put("message", msg);

            Log.e(TAG, "Add Topic Api parameters - " + reqData.toString());
            viewModel.addTopicApi(reqData);
        }
    }

    private void handleAddTopicResult(ApiResponse<AddTopicData> result) {
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

                    showSuccessDialog(result.getData().getMassage());

                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                }
                break;
        }
    }

    private void showSuccessDialog(String message) {
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
        dialogBinding.tvMessage.setText(message);
        dialogBinding.llOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                homeActivity.onBackPressed();
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