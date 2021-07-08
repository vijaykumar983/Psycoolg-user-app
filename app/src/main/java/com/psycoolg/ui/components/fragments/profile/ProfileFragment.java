package com.psycoolg.ui.components.fragments.profile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.psycoolg.R;
import com.psycoolg.databinding.DialogSuccessBinding;
import com.psycoolg.databinding.FragmentProfileBinding;
import com.psycoolg.network.ApiResponse;
import com.psycoolg.pojo.ProfileData;
import com.psycoolg.pojo.UpdateProfileData;
import com.psycoolg.ui.base.BaseFragment;
import com.psycoolg.ui.components.activities.home.HomeActivity;
import com.psycoolg.utils.ProgressDialog;
import com.psycoolg.utils.SessionManager;
import com.psycoolg.utils.Utility;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends BaseFragment implements BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener,
        BSImagePicker.ImageLoaderDelegate {
    private static final String TAG = ProfileFragment.class.getName();
    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private View.OnClickListener onClickListener = null;
    private HomeActivity homeActivity;
    private SessionManager sessionManager;
    private TextView txtuserName;
    private ImageView imvUserProfile;

    public static String selectedImagePath = "";
    private static final int REQ_CODE_GALLERY_PICKER3 = 30;
    private File mFileTemp;
    public static final String TEMP_PHOTO_FILE_NAME = "GoTo.png";

    @Override
    protected ViewDataBinding setBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
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
        binding.appBar.tvTitle.setText("Profile");
        BottomNavigationView bottomNavigationView = mActivity.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        DrawerLayout drawerLayout = mActivity.findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.statusBarAll));
        }

        if (sessionManager.getFIRST_NAME().isEmpty() || sessionManager.getLAST_NAME().isEmpty() || sessionManager.getMOBILE().isEmpty()
                || sessionManager.getEMAIL().isEmpty() || sessionManager.getABOUT().isEmpty() || sessionManager.getPROFILE_IMG().isEmpty()) {
            getProfileAPI();
        } else {
            setProfileData();
        }
    }

    @Override
    protected void initializeObject() {
        sessionManager = new SessionManager();
        mFileTemp = Utility.createAppDir(mActivity, mFileTemp, TEMP_PHOTO_FILE_NAME);
    }


    @Override
    protected void initializeOnCreateObject() {
        homeActivity = (HomeActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.responseLiveData.observe(this, new Observer<ApiResponse<ProfileData>>() {
            @Override
            public void onChanged(ApiResponse<ProfileData> it) {
                handleResult(it);
            }
        });
        viewModel.responseUpdateProfileLiveData.observe(this, new Observer<ApiResponse<UpdateProfileData>>() {
            @Override
            public void onChanged(ApiResponse<UpdateProfileData> it) {
                handleUpdateProfileResult(it);
            }
        });
    }


    @Override
    protected void setListeners() {
        binding.appBar.ivBack.setOnClickListener(this);
        binding.rlProfile.setOnClickListener(this);
        binding.btnEditProfile.setOnClickListener(this);
        txtuserName = mActivity.findViewById(R.id.txtuserName);
        imvUserProfile = mActivity.findViewById(R.id.imvUserProfile);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                homeActivity.onBackPressed();
                break;
            case R.id.rlProfile:
                BSImagePicker pickerDialog = new BSImagePicker.Builder("com.psycoolg")
                        .build();
                pickerDialog.show(getChildFragmentManager(), "picker");
                break;
            case R.id.btnEditProfile:
                if (Utility.isDeviceOnline(mActivity)) {
                    updateProfileAPI();
                } else {
                    Utility.showSnackBarMsgError(mActivity, getString(R.string.no_internet));
                }
                break;
        }
    }

    private void setProfileData() {
        binding.etName.setText(sessionManager.getFIRST_NAME() + " " + sessionManager.getLAST_NAME());
        binding.etMobile.setText(sessionManager.getMOBILE());
        binding.etEmail.setText(sessionManager.getEMAIL());
        binding.etBio.setText(sessionManager.getABOUT());
        if (selectedImagePath.isEmpty()) {
            if (sessionManager.getPROFILE_IMG() != null && !sessionManager.getPROFILE_IMG().isEmpty())
                Utility.loadImage(binding.imvUserProfile, sessionManager.getPROFILE_IMG());
        }
    }

    private void getProfileAPI() {
        if (Utility.isDeviceOnline(mActivity)) {

            HashMap<String, String> reqData = new HashMap<>();
            reqData.put("user_id", sessionManager.getUSERID());

            Log.e(TAG, "Api parameters - " + reqData.toString());
            viewModel.profileApi(reqData);

        } else {
            Utility.showToastMessageError(mActivity, getString(R.string.no_internet));
        }
    }

    @SuppressLint("SetTextI18n")
    private void handleResult(ApiResponse<ProfileData> result) {
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

                    sessionManager.setUSERID(result.getData().getData().getId());
                    sessionManager.setFIRST_NAME(result.getData().getData().getFirstname());
                    sessionManager.setLAST_NAME(result.getData().getData().getLastname());
                    sessionManager.setEMAIL(result.getData().getData().getEmail());
                    sessionManager.setMOBILE(result.getData().getData().getPhone());
                    sessionManager.setPROFILE_IMG(result.getData().getData().getProfileImage());
                    sessionManager.setABOUT(result.getData().getData().getAbout());

                    setProfileData();
                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                }
                break;
        }
    }

    private void updateProfileAPI() {
        String name = binding.etName.getText().toString().trim();
        String phone = binding.etMobile.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String bio = binding.etBio.getText().toString().trim();

        if (selectedImagePath.isEmpty()) {
            if (sessionManager.getPROFILE_IMG() != null && !sessionManager.getPROFILE_IMG().isEmpty())
                selectedImagePath = sessionManager.getPROFILE_IMG();
        }

        if (viewModel.isValidFormData(mActivity, selectedImagePath, name, phone, email, bio)) {

            HashMap<String, String> reqData = new HashMap<>();

            reqData.put("user_id", sessionManager.getUSERID());
            reqData.put("name", name);
            reqData.put("email", email);
            reqData.put("about", bio);
            reqData.put("mobile", phone);
            /*if (selectedImagePath != null)
                reqData.put("image", selectedImagePath);
            else
                reqData.put("image", "");*/

            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), mFileTemp);

            MultipartBody.Part req_id = MultipartBody.Part.createFormData("user_id", sessionManager.getUSERID());
            MultipartBody.Part req_name = MultipartBody.Part.createFormData("name", name);
            MultipartBody.Part req_email = MultipartBody.Part.createFormData("email", email);
            MultipartBody.Part req_phone = MultipartBody.Part.createFormData("mobile", phone);
            MultipartBody.Part req_about = MultipartBody.Part.createFormData("about", bio);
            MultipartBody.Part profile_photo = null;
            if (selectedImagePath.isEmpty()) {
            } else {
                profile_photo = MultipartBody.Part.createFormData("image", mFileTemp.getName(), requestBody);
            }

            Log.e(TAG, "Api parameters - " + reqData.toString());
            Log.e(TAG, "profile - " + profile_photo);

            viewModel.updateProfile(req_id, profile_photo, req_name, req_phone, req_email, req_about);
        }
    }

    @SuppressLint("SetTextI18n")
    private void handleUpdateProfileResult(ApiResponse<UpdateProfileData> result) {
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

                    sessionManager.setUSERID(result.getData().getData().getId());
                    sessionManager.setFIRST_NAME(result.getData().getData().getFirstname());
                    sessionManager.setLAST_NAME(result.getData().getData().getLastname());
                    sessionManager.setEMAIL(result.getData().getData().getEmail());
                    sessionManager.setMOBILE(result.getData().getData().getPhone());
                    sessionManager.setABOUT(result.getData().getData().getAbout());
                    sessionManager.setPROFILE_IMG(result.getData().getData().getProfileImage());

                    Utility.loadImage(imvUserProfile, sessionManager.getPROFILE_IMG());
                    txtuserName.setText(sessionManager.getFIRST_NAME() + " " + sessionManager.getLAST_NAME());
                    showSuccessDialog(result.getData().getMassage());

                } else {
                    Utility.showToastMessageError(mActivity, result.getData().getMassage());
                    Log.e(TAG, "error failure - " + result.getError().getMessage());
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
    public void loadImage(Uri imageUri, ImageView ivImage) {
        Glide.with(mActivity).load(imageUri).into(ivImage);

    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {

    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {
        selectedImagePath = uri.getPath();

        InputStream inputStream = null;
        try {
            inputStream = mActivity.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(mFileTemp);

            Utility.copyStream(inputStream, fileOutputStream);

            fileOutputStream.close();
            inputStream.close();
            UCrop.of(Uri.fromFile(mFileTemp), Uri.fromFile(mFileTemp))
                    .withAspectRatio(4, 4)
                    .start(mActivity, this, REQ_CODE_GALLERY_PICKER3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQ_CODE_GALLERY_PICKER3) {
            final Uri resultUri = UCrop.getOutput(data);
            selectedImagePath = resultUri.getPath();
            Bitmap bitmap = Utility.decodeUriToBitmap(mActivity, resultUri);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
            selectedImagePath = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);

            binding.imvUserProfile.setImageURI(resultUri);
            Log.e(TAG, "imageview selectImage - " + resultUri);
        }
    }

    @Override
    public void onDestroy() {
        viewModel.disposeSubscriber();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        selectedImagePath = "";
        super.onDestroyView();
    }
}