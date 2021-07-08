package com.psycoolg.ui.components.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.ViewDataBinding;

import java.util.ArrayList;

import com.psycoolg.R;
import com.psycoolg.pojo.QuoteData;
import com.psycoolg.ui.base.RecyclerBaseAdapter;
import com.psycoolg.utils.Constants;
import com.psycoolg.utils.Utility;


public class QuoteAdapter extends RecyclerBaseAdapter {
    private AppCompatActivity mActivity;
    private View.OnClickListener onClickListener;
    private ArrayList<QuoteData.DataItem> list;


    public QuoteAdapter(AppCompatActivity mActivity, View.OnClickListener onClickListener, ArrayList<QuoteData.DataItem> list) {
        this.mActivity = mActivity;
        this.onClickListener = onClickListener;
        this.list = list;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_quotes;
    }

    @Override
    public Object getViewModel(int position) {
        return list.get(position);
    }

    @Override
    protected void putViewDataBinding(ViewDataBinding viewDataBinding, int position) {
        View view = viewDataBinding.getRoot();
        CardView rootHeader = view.findViewById(R.id.rowQuoteItem);
        rootHeader.setTag(position);
        rootHeader.setOnClickListener(onClickListener);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        ImageView ivQuote = view.findViewById(R.id.ivQuote);
        TextView tvDescrip = view.findViewById(R.id.tvDescrip);

        tvTitle.setText(list.get(position).getAuthor());
        Utility.loadImage(ivQuote, Constants.IMG_URL+list.get(position).getIcon());
        tvDescrip.setText(list.get(position).getContent());
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}

