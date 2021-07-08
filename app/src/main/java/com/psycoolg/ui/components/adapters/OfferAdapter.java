package com.psycoolg.ui.components.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.ViewDataBinding;

import java.util.ArrayList;

import com.psycoolg.R;
import com.psycoolg.pojo.OfferData;
import com.psycoolg.ui.base.RecyclerBaseAdapter;
import com.psycoolg.utils.Utility;


public class OfferAdapter extends RecyclerBaseAdapter {
    private AppCompatActivity mActivity;
    private View.OnClickListener onClickListener;
    private ArrayList<OfferData.DataItem> list;


    public OfferAdapter(AppCompatActivity mActivity, View.OnClickListener onClickListener, ArrayList<OfferData.DataItem> list) {
        this.mActivity = mActivity;
        this.onClickListener = onClickListener;
        this.list = list;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_offers;
    }

    @Override
    public Object getViewModel(int position) {
        return list.get(position);
    }

    @Override
    protected void putViewDataBinding(ViewDataBinding viewDataBinding, int position) {
        View view = viewDataBinding.getRoot();
        CardView rootHeader = view.findViewById(R.id.rowOfferItem);
        rootHeader.setTag(position);
        rootHeader.setOnClickListener(onClickListener);
        TextView tvDescrip = view.findViewById(R.id.tvDescrip);
        ImageView ivOffer = view.findViewById(R.id.ivOffer);

        tvDescrip.setText(list.get(position).getTitle());
        Utility.loadImage(ivOffer, list.get(position).getImage());
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}

