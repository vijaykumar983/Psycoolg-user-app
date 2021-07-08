package com.psycoolg.ui.components.fragments.search;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.psycoolg.databinding.FragmentSearchBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.AllDoctorData;
import com.psycoolg.pojo.CategoryData;
import com.psycoolg.pojo.LocationData;
import com.psycoolg.ui.base.BaseFragment;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.ui.components.adapters.AllDoctorAdapter;
import com.psycoolg.ui.components.fragments.psychologyProfile.PsychologyProfileFragment;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.Utility;

import java.util.ArrayList;

public class SearchFragment extends BaseFragment {
    private static final String TAG = SearchFragment.class.getName();
    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;
    private View.OnClickListener onClickListener = null;
    private HomeActivity homeActivity;
    private ArrayList<AllDoctorData.DataItem> allDoctorData;
    private AllDoctorAdapter allDoctorAdapter;
    private ArrayList<CategoryData.DataItem> categoryData;
    private ArrayList<LocationData.DataItem> locationData;
    //private CategoryAdapter categoryAdapter;
    //private Dialog dialogTalents;

    @Override
    protected ViewDataBinding setBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
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
        allDoctorData = new ArrayList<>();
        categoryData = new ArrayList<>();
        locationData = new ArrayList<>();
        if (Utility.isDeviceOnline(mActivity)) {
            viewModel.allDoctorApi();
            viewModel.categoryApi();
            viewModel.locationApi();
        } else {
            Utility.showToastMessageError(mActivity, getString(R.string.no_internet));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        RelativeLayout rlHomeMain = mActivity.findViewById(R.id.rlHomeMain);
        rlHomeMain.setVisibility(View.GONE);
        binding.appBar.tvTitle.setText("Search");
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
    protected void initializeOnCreateObject() {
        homeActivity = (HomeActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<AllDoctorData>>() {
            @Override
            public void onChanged(ApiResponse<AllDoctorData> it) {
                handleResult(it);
            }
        });
        viewModel.responseCategoryLiveData.observe(this, new Observer<ApiResponse<CategoryData>>() {
            @Override
            public void onChanged(ApiResponse<CategoryData> it) {
                handleCategoryResult(it);
            }
        });
        viewModel.responseLocaitonLiveData.observe(this, new Observer<ApiResponse<LocationData>>() {
            @Override
            public void onChanged(ApiResponse<LocationData> it) {
                handleLocationResult(it);
            }
        });
    }


    @Override
    protected void setListeners() {
        binding.appBar.ivBack.setOnClickListener(this);
        binding.llCategory.setOnClickListener(this);
        binding.tvCategory.setOnClickListener(this);
        binding.tvLocation.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConsultNow:
                int position = (int) view.getTag();
                allDoctorAdapter.selectedPos = position;
                Bundle bundle = new Bundle();
                bundle.putString("doctorId", allDoctorData.get(position).getId());
                homeActivity.changeFragment(new PsychologyProfileFragment(), true, bundle);
                break;
            case R.id.ivBack:
                homeActivity.onBackPressed();
                break;
            /*case R.id.llCategory:
                if (categoryData.isEmpty()) {
                    getCategory();
                } else {
                    showCategoryDialog();
                }
                break;
            case R.id.rowCategoryItem:
                int pos = (int) view.getTag();
                categoryAdapter.selectedPos = pos;

                binding.tvCategory.setText(categoryData.get(pos).getName());
                profileTalent = addTalentDataList.get(pos);
                dialogTalents.dismiss();

                break;*/
            case R.id.tvCategory:
                showCategoryDialog();
                break;
            case R.id.tvLocation:
                showLocationDialog();
                break;


        }
    }

    private void showCategoryDialog() {
        if (categoryData.size() != 0) {
            ArrayList<String> a1 = new ArrayList<>();

            for (int i = 0; i < categoryData.size(); i++) {
                a1.add(categoryData.get(i).getName());
            }
            String[] stockArr = a1.toArray(new String[a1.size()]);

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("Select category");
            builder.setItems(stockArr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(mActivity, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    binding.tvCategory.setText(stockArr[which]);
                    binding.tvCategory.setSelected(true);
                    Log.e(TAG, "category Id - " + categoryData.get(which).getId());
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Utility.showToastMessageError(mActivity, "No Data available!");
        }
    }

    private void showLocationDialog() {
        if (locationData.size() != 0) {
            ArrayList<String> a1 = new ArrayList<>();

            for (int i = 0; i < locationData.size(); i++) {
                a1.add(locationData.get(i).getName());
            }
            String[] stockArr = a1.toArray(new String[a1.size()]);

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("Select location");
            builder.setItems(stockArr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(mActivity, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    binding.tvLocation.setText(stockArr[which]);
                    binding.tvLocation.setSelected(true);
                    Log.e(TAG, "location Id - " + locationData.get(which).getId());
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Utility.showToastMessageError(mActivity, "No Data available!");
        }

    }


    private void handleResult(ApiResponse<AllDoctorData> result) {
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
                    binding.rvAllDoctor.setVisibility(View.VISIBLE);
                    binding.layoutError.rlerror.setVisibility(View.GONE);

                    if (result.getData().getData() != null && !result.getData().getData().isEmpty()) {
                        binding.rvAllDoctor.setVisibility(View.VISIBLE);
                        binding.layoutError.rlerror.setVisibility(View.GONE);
                        allDoctorData.clear();
                        allDoctorData.addAll(result.getData().getData());
                        if (allDoctorData.size() != 0) {
                            allDoctorAdapter = new AllDoctorAdapter(mActivity, onClickListener, allDoctorData);
                            binding.rvAllDoctor.setAdapter(allDoctorAdapter);
                        }
                    } else {
                        binding.rvAllDoctor.setVisibility(View.GONE);
                        binding.layoutError.rlerror.setVisibility(View.VISIBLE);
                    }
                } else {
                    //Utility.showToastMessageError(mActivity, result.getData().getMassage());
                    binding.rvAllDoctor.setVisibility(View.GONE);
                    binding.layoutError.rlerror.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void handleCategoryResult(ApiResponse<CategoryData> result) {
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
                    Log.e(TAG, "Response Category- " + new Gson().toJson(result));

                    if (result.getData().getData() != null && !result.getData().getData().isEmpty()) {
                        categoryData.clear();
                        categoryData.addAll(result.getData().getData());
                        if (categoryData.size() != 0) {
                            //categoryAdapter = new CategoryAdapter(mActivity, categoryData);
                            //binding.spinnerCategory.setAdapter(categoryAdapter);
                        }
                    } else {
                        Utility.showToastMessageError(mActivity, result.getData().getMassage());
                    }
                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                }
                break;
        }
    }

    private void handleLocationResult(ApiResponse<LocationData> result) {
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
                    Log.e(TAG, "Response Location - " + new Gson().toJson(result));

                    if (result.getData().getData() != null && !result.getData().getData().isEmpty()) {
                        locationData.clear();
                        locationData.addAll(result.getData().getData());
                    } else {
                        Utility.showToastMessageError(mActivity, result.getData().getMassage());
                    }
                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                }
                break;
        }
    }

    /*private void showCategoryDialog() {
        dialogTalents = new Dialog(mActivity);
        dialogTalents.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTalents.setCancelable(true);
        dialogTalents.setContentView(R.layout.dialog_category_list);

        EditText editText_price_search = dialogTalents.findViewById(R.id.editText_price_search);
        editText_price_search.setHint("Category");
        RecyclerView rvCurrency = dialogTalents.findViewById(R.id.rvCurrency);
        categoryData.clear();
        categoryData.addAll(categoryData1);

        rvCurrency.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCurrency.getContext(), linearLayoutManager.getOrientation());
        rvCurrency.addItemDecoration(dividerItemDecoration);
        rvCurrency.setLayoutManager(linearLayoutManager);

        categoryAdapter = new CategoryAdapter(mActivity, onClickListener,categoryData);

        rvCurrency.setAdapter(categoryAdapter);

        editText_price_search.addTextChangedListener(this);
        dialogTalents.setCanceledOnTouchOutside(true);
        dialogTalents.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTalents.show();
    }*/

    @Override
    public void onDestroy() {
        viewModel.disposeSubscriber();
        super.onDestroy();
    }

}