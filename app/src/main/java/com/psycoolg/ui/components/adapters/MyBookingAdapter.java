package com.psycoolg.ui.components.adapters;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import com.psycoolg.R;
import com.psycoolg.pojo.MyBookingData;
import com.psycoolg.ui.base.RecyclerBaseAdapter;
import com.psycoolg.utils.Constants;
import com.psycoolg.utils.Utility;

import java.util.ArrayList;


public class MyBookingAdapter extends RecyclerBaseAdapter {
    private AppCompatActivity mActivity;
    private View.OnClickListener onClickListener;
    private ArrayList<MyBookingData.DataItem> list;
    public  int selectedPos = 0;

    public MyBookingAdapter(AppCompatActivity mActivity, View.OnClickListener onClickListener, ArrayList<MyBookingData.DataItem> list) {
        this.mActivity = mActivity;
        this.onClickListener = onClickListener;
        this.list = list;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_my_booking;
    }

    @Override
    public Object getViewModel(int position) {
        return list.get(position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void putViewDataBinding(ViewDataBinding viewDataBinding, int position) {
        View view = viewDataBinding.getRoot();
        RelativeLayout rootHeader = view.findViewById(R.id.rowItem);
        rootHeader.setTag(position);
        rootHeader.setOnClickListener(onClickListener);
        LinearLayout linearGiveRating = view.findViewById(R.id.linearGiveRating);
        linearGiveRating.setOnClickListener(onClickListener);
        linearGiveRating.setTag(position);
        LinearLayout linearChat = view.findViewById(R.id.linearChat);
        linearChat.setOnClickListener(onClickListener);
        LinearLayout linearCall = view.findViewById(R.id.linearCall);
        linearCall.setOnClickListener(onClickListener);

        TextView txtuserName = view.findViewById(R.id.txtuserName);
        ImageView imvUserProfile = view.findViewById(R.id.imvUserProfile);
        TextView tvCategoryPsy = view.findViewById(R.id.tvCategoryPsy);
        TextView tvDate = view.findViewById(R.id.tvDate);

        txtuserName.setText(list.get(position).getFirstName() + " " + list.get(position).getLastName());
        Utility.loadImage(imvUserProfile, Constants.IMG_URL + list.get(position).getProfileImage());
        tvCategoryPsy.setText(list.get(position).getCategoryName());
        tvDate.setText(Utility.getChangeDate(list.get(position).getBookingDate()));


    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}

