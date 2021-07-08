package com.psycoolg.ui.components.fragments.home;

import android.annotation.SuppressLint;
import android.os.Build;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import com.psycoolg.R;
import com.psycoolg.databinding.FragmentHomeBinding;
import com.psycoolg.pojo.HomeData;
import com.psycoolg.ui.base.BaseFragment;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.ui.components.adapters.HomeAdapter;
import com.psycoolg.ui.components.fragments.history.HistoryFragment;
import com.psycoolg.ui.components.fragments.myBooking.MyBookingFragment;
import com.psycoolg.ui.components.fragments.offers.OfferFragment;
import com.psycoolg.ui.components.fragments.quotes.QuotesFragment;
import com.psycoolg.ui.components.fragments.search.SearchFragment;


public class HomeFragment extends BaseFragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private View.OnClickListener onClickListener = null;
    private HomeActivity homeActivity;
    private ArrayList<HomeData> list = null;
    private HomeAdapter adapter ;
    private int[] arrayImg = {R.drawable.ic_my_booking,R.drawable.ic_offers,R.drawable.ic_quotes,R.drawable.ic_history};

    @Override
    protected ViewDataBinding setBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        //viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        onClickListener = this;
        binding.setLifecycleOwner(this);
        return binding;
    }

    @Override
    protected void createActivityObject() {
        mActivity = (AppCompatActivity) getActivity();
    }

    @Override
    protected void initializeObject() {
        setAdapter();
    }

    private void setAdapter() {
        list = new ArrayList<>();
        String[] mTestArray = getResources().getStringArray(R.array.homeItems);

        for (int i = 0; i < mTestArray.length; i++) {
            list.add(new HomeData(i, mTestArray[i], arrayImg[i]));
        }
        adapter = new HomeAdapter(mActivity,onClickListener,list);
        binding.rvHomeItem.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        RelativeLayout rlHomeMain = mActivity.findViewById(R.id.rlHomeMain);
        rlHomeMain.setVisibility(View.VISIBLE);
        BottomNavigationView bottomNavigationView = mActivity.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.statusBarDashboard));
        }
    }


    @Override
    protected void initializeOnCreateObject() {
        homeActivity = (HomeActivity) getActivity();
    }


    @Override
    protected void setListeners() {
        binding.linearSearch.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rowHomeItem:
                int position = (int) view.getTag();
                adapter.selectedPos = position;

                switch (position)
                {
                    case 0:
                        homeActivity.changeFragment(new MyBookingFragment(), true);
                        return;
                    case 1:
                        homeActivity.changeFragment(new OfferFragment(), true);
                        return;
                    case 2:
                        homeActivity.changeFragment(new QuotesFragment(), true);
                        return;
                    case 3:
                        homeActivity.changeFragment(new HistoryFragment(), true);
                        return;
                }


                break;
            case R.id.linearSearch:
                homeActivity.changeFragment(new SearchFragment(), true);
                break;
        }
    }
}