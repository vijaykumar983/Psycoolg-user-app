package com.psycoolg.ui.components.adapters;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import com.psycoolg.R;
import com.psycoolg.pojo.AllDoctorData;
import com.psycoolg.ui.base.RecyclerBaseAdapter;
import com.psycoolg.utils.Constants;
import com.psycoolg.utils.Utility;

import java.util.ArrayList;


public class AllDoctorAdapter extends RecyclerBaseAdapter {
    private AppCompatActivity mActivity;
    private View.OnClickListener onClickListener;
    private ArrayList<AllDoctorData.DataItem> list;
    public int selectedPos = 0;


    public AllDoctorAdapter(AppCompatActivity mActivity, View.OnClickListener onClickListener, ArrayList<AllDoctorData.DataItem> list) {
        this.mActivity = mActivity;
        this.onClickListener = onClickListener;
        this.list = list;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_all_doctor;
    }

    @Override
    public Object getViewModel(int position) {
        return list.get(position);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void putViewDataBinding(ViewDataBinding viewDataBinding, int position) {
        View view = viewDataBinding.getRoot();
        RelativeLayout rootHeader = view.findViewById(R.id.rowAllDoctorItem);
        rootHeader.setOnClickListener(onClickListener);
        Button btnConsultNow = view.findViewById(R.id.btnConsultNow);
        btnConsultNow.setOnClickListener(onClickListener);
        btnConsultNow.setTag(position);
        TextView tvName = view.findViewById(R.id.tvName);
        ImageView imvUserProfile = view.findViewById(R.id.imvUserProfile);
        TextView tvOnlineStatus = view.findViewById(R.id.tvOnlineStatus);
        RatingBar rbDoctor = view.findViewById(R.id.rbDoctor);
        TextView tvRating = view.findViewById(R.id.tvRating);
        TextView tvCategoryPsy = view.findViewById(R.id.tvCategoryPsy);
        LinearLayout linearStatus = view.findViewById(R.id.linearStatus);
        View viewStatus = view.findViewById(R.id.viewStatus);

        tvName.setText(list.get(position).getFirstname() + " " + list.get(position).getLastname());
        Utility.loadImage(imvUserProfile, Constants.IMG_URL + list.get(position).getProfilePic());
        //tvOnlineStatus.setText(Utility.toTitleCase(list.get(position).getOnlineStatus()));
        rbDoctor.setRating(list.get(position).getRating());
        tvRating.setText(String.valueOf(list.get(position).getRating()));
        tvCategoryPsy.setText(list.get(position).getProfession());

        if (list.get(position).getOnlineStatus().equalsIgnoreCase("online")) {
            linearStatus.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_corner_green));
            viewStatus.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_ovel_rounded_green));
        } else {
            linearStatus.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_corner_gray));
            viewStatus.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_ovel_rounded_gray));
        }
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}

