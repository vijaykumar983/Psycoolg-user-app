package com.psycoolg.ui.components.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.psycoolg.R;


public class CustomPagerAdapter extends PagerAdapter {
    private AppCompatActivity mContext;
    private int[] imagesArray;
    private String[] titleArray, subtitleArray;

    public CustomPagerAdapter(AppCompatActivity mContext, int[] imagesArray, String[] titleArray, String[] subtitleArray) {
        this.mContext = mContext;
        this.imagesArray = imagesArray;
        this.titleArray = titleArray;
        this.subtitleArray = subtitleArray;
    }


    @Override
    public int getCount() {
        return imagesArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.row_intro_activity, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageView);
        TextView txtTitle = itemView.findViewById(R.id.txtTitle);
        TextView txtSubTitle = itemView.findViewById(R.id.txtSubTitle);

        imageView.setImageResource(imagesArray[position]);
        txtTitle.setText(titleArray[position]);
        txtSubTitle.setText(subtitleArray[position]);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
