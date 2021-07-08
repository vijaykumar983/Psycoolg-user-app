package com.psycoolg.ui.components.activities.intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.psycoolg.R;
import com.psycoolg.databinding.ActivityIntroBinding;
import com.psycoolg.ui.base.BaseBindingActivity;
import com.psycoolg.ui.components.activities.login.LoginActivity;
import com.psycoolg.ui.components.adapters.CustomPagerAdapter;

public class IntroActivity extends BaseBindingActivity {
    private ActivityIntroBinding binding;
    private int[] imagesArray = {R.drawable.ic_sp1, R.drawable.ic_sp2, R.drawable.ic_sp3};
    private String[] titleArray, subtitleArray;


    @Override
    protected void setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro);

    }

    @Override
    protected void createActivityObject(Bundle savedInstanceState) {
        mActivity = this;
    }

    @Override
    protected void initializeObject() {
        titleArray = getResources().getStringArray(R.array.titleArray);
        subtitleArray = getResources().getStringArray(R.array.subtitleArray);

        binding.viewPager.setAdapter(new CustomPagerAdapter(mActivity, imagesArray, titleArray, subtitleArray));
        binding.tabLayout.setupWithViewPager(binding.viewPager, true);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2)
                    binding.btnNext.setText("Get Started");
                else
                    binding.btnNext.setText("Next");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    @Override
    protected void setListeners() {
        binding.btnNext.setOnClickListener(this);
    }

    public static void startActivity(Activity activity, Bundle bundle, boolean isClear) {
        Intent intent = new Intent(activity, IntroActivity.class);
        if (bundle != null) intent.putExtra("bundle", bundle);
        if (isClear)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                if (binding.viewPager.getCurrentItem() == 2) {
                    LoginActivity.startActivity(mActivity, null, false);
                } else {
                    int currentPage = binding.viewPager.getCurrentItem();
                    int a = currentPage + 1;
                    binding.viewPager.setCurrentItem(a);
                }
                break;
        }
    }
}
