package com.psycoolg.ui.components.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import com.psycoolg.R;
import com.psycoolg.pojo.ReferData;
import com.psycoolg.ui.base.RecyclerBaseAdapter;
import com.psycoolg.utils.Utility;

import java.util.ArrayList;


public class ReferFriendAdapter extends RecyclerBaseAdapter {
    private AppCompatActivity mActivity;
    private View.OnClickListener onClickListener;
    private ArrayList<ReferData.Data.ReferItem> list;

    public ReferFriendAdapter(AppCompatActivity mActivity, View.OnClickListener onClickListener, ArrayList<ReferData.Data.ReferItem> list) {
        this.mActivity = mActivity;
        this.onClickListener = onClickListener;
        this.list = list;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_refer_friend;
    }

    @Override
    public Object getViewModel(int position) {
        return list.get(position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void putViewDataBinding(ViewDataBinding viewDataBinding, int position) {
        View view = viewDataBinding.getRoot();
        LinearLayout rootHeader = view.findViewById(R.id.rowReferItem);
        rootHeader.setTag(position);
        rootHeader.setOnClickListener(onClickListener);
        TextView tvIndex = view.findViewById(R.id.tvIndex);
        TextView tvUserName = view.findViewById(R.id.tvUserName);
        TextView tvBonusAmt = view.findViewById(R.id.tvBonusAmt);

        tvIndex.setText(position+"");
        tvUserName.setText(list.get(position).getName());
        tvBonusAmt.setText("+"+ Utility.fmt(Double.parseDouble(list.get(position).getAmount())));
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}

