package com.psycoolg.ui.components.adapters;

import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import java.util.ArrayList;

import com.psycoolg.R;
import com.psycoolg.pojo.CategoryData;
import com.psycoolg.ui.base.RecyclerBaseAdapter;


public class CategoryAdapter extends RecyclerBaseAdapter {
    private AppCompatActivity mActivity;
    private View.OnClickListener onClickListener;
    private ArrayList<CategoryData.DataItem> list;
    public int selectedPos = 0;

    public CategoryAdapter(AppCompatActivity mActivity, View.OnClickListener onClickListener, ArrayList<CategoryData.DataItem> list) {
        this.mActivity = mActivity;
        this.onClickListener = onClickListener;
        this.list = list;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_category;
    }

    @Override
    public Object getViewModel(int position) {
        return list.get(position);
    }

    @Override
    protected void putViewDataBinding(ViewDataBinding viewDataBinding, int position) {
        View view = viewDataBinding.getRoot();
        LinearLayout rootHeader = view.findViewById(R.id.rowCategoryItem);
        rootHeader.setTag(position);
        rootHeader.setOnClickListener(onClickListener);
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}

