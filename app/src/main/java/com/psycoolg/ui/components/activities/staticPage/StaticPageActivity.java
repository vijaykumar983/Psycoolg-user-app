package com.psycoolg.ui.components.activities.staticPage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.psycoolg.R;
import com.psycoolg.databinding.ActivityStaticPageBinding;
import com.psycoolg.ui.base.BaseBindingActivity;

public class StaticPageActivity extends BaseBindingActivity {
    private ActivityStaticPageBinding binding;


    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_static_page);
    }

    @Override
    protected void createActivityObject(@Nullable Bundle savedInstanceState) {
        mActivity = this;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initializeObject() {
        binding.appBar.tvTitle.setText("Privacy and Policy");
        binding.appBar.ivNotification.setVisibility(View.GONE);
    }

    @Override
    protected void setListeners() {
        binding.appBar.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }

    public static void startActivity(Activity activity, Bundle bundle, boolean isClear) {
        Intent intent = new Intent(activity, StaticPageActivity.class);
        if (bundle != null) intent.putExtra("bundle", bundle);
        if (isClear)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }
}
