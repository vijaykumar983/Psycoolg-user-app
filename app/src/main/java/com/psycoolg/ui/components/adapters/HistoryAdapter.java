package com.psycoolg.ui.components.adapters;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import com.psycoolg.R;
import com.psycoolg.pojo.MyHistoryData;
import com.psycoolg.ui.base.RecyclerBaseAdapter;
import com.psycoolg.utils.Constants;
import com.psycoolg.utils.Utility;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerBaseAdapter {
    private AppCompatActivity mActivity;
    private View.OnClickListener onClickListener;
    private ArrayList<MyHistoryData.DataItem> list;
    public  int selectedPos = 0;


    public HistoryAdapter(AppCompatActivity mActivity, View.OnClickListener onClickListener, ArrayList<MyHistoryData.DataItem> list) {
        this.mActivity = mActivity;
        this.onClickListener = onClickListener;
        this.list = list;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_history;
    }

    @Override
    public Object getViewModel(int position) {
        return list.get(position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void putViewDataBinding(ViewDataBinding viewDataBinding, int position) {
        View view = viewDataBinding.getRoot();
        RelativeLayout rootHeader = view.findViewById(R.id.rowHistoryItem);
        rootHeader.setTag(position);
        rootHeader.setOnClickListener(onClickListener);
        LinearLayout linearGiveRating = view.findViewById(R.id.linearGiveRating);
        linearGiveRating.setOnClickListener(onClickListener);
        linearGiveRating.setTag(position);
        ImageView imvUserProfile = view.findViewById(R.id.imvUserProfile);
        TextView txtuserName = view.findViewById(R.id.txtuserName);
        TextView tvDate = view.findViewById(R.id.tvDate);
        TextView tvCategoryPsy = view.findViewById(R.id.tvCategoryPsy);
        TextView tvAmountPaid = view.findViewById(R.id.tvAmountPaid);

        txtuserName.setText(list.get(position).getFirstName() + " " + list.get(position).getLastName());
        Utility.loadImage(imvUserProfile, Constants.IMG_URL + list.get(position).getProfileImage());
        tvDate.setText("Date : "+Utility.getFormatChangeDate(list.get(position).getBookingDate()));
        tvCategoryPsy.setText(list.get(position).getCategoryName());
        tvAmountPaid.setText("Amount Paid Rs. "+list.get(position).getAmount());
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}

