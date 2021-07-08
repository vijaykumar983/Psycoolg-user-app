package com.psycoolg.ui.components.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.ViewDataBinding;

import java.util.ArrayList;

import com.psycoolg.R;
import com.psycoolg.pojo.HomeData;
import com.psycoolg.ui.base.RecyclerBaseAdapter;


public class HomeAdapter extends RecyclerBaseAdapter {
    private Context context;
    private View.OnClickListener onClickListener;
    private ArrayList<HomeData> list;
    public  int selectedPos = 0;

    public HomeAdapter(Context context, View.OnClickListener onClickListener, ArrayList<HomeData> list) {
        this.context = context;
        this.onClickListener = onClickListener;
        this.list = list;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_home_items;
    }

    @Override
    public Object getViewModel(int position) {
        return list.get(position);
    }

    @Override
    protected void putViewDataBinding(ViewDataBinding viewDataBinding, int position) {
        View view = viewDataBinding.getRoot();
        LinearLayout rootHeader = view.findViewById(R.id.rowHomeItem);
        rootHeader.setTag(position);
        rootHeader.setOnClickListener(onClickListener);
        TextView tvTitle = rootHeader.findViewById(R.id.tvTitle);
        ImageView ivItem = rootHeader.findViewById(R.id.ivItem);
        selectedPos = position;


        tvTitle.setText(list.get(position).getName());
        ivItem.setImageResource(list.get(position).getDrawerImage());
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}

