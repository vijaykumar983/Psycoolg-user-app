package com.psycoolg.ui.components.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.ViewDataBinding;

import com.psycoolg.R;
import com.psycoolg.pojo.BlogCategoryData;
import com.psycoolg.pojo.BlogData;
import com.psycoolg.ui.base.RecyclerBaseAdapter;
import com.psycoolg.utils.Utility;

import java.util.ArrayList;


public class BlogAdapter extends RecyclerBaseAdapter {
    private AppCompatActivity mActivity;
    private View.OnClickListener onClickListener;
    private ArrayList<BlogData.DataItem> list;
    public int selectedPos = 0;



    public BlogAdapter(AppCompatActivity mActivity, View.OnClickListener onClickListener, ArrayList<BlogData.DataItem> list) {
       this.mActivity = mActivity;
        this.onClickListener = onClickListener;
        this.list = list;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.row_blog;
    }

    @Override
    public Object getViewModel(int position) {
        return list.get(position);
    }

    @Override
    protected void putViewDataBinding(ViewDataBinding viewDataBinding, int position) {
        View view = viewDataBinding.getRoot();
        CardView rootHeader = view.findViewById(R.id.rowBlogItem);
        rootHeader.setTag(position);
        rootHeader.setOnClickListener(onClickListener);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        ImageView ivBlogs = view.findViewById(R.id.ivBlogs);

        tvTitle.setText(list.get(position).getTitle());
        Utility.loadImage(ivBlogs, list.get(position).getThumbnail());
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}

