package com.example.gqapp.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.gqapp.R;
import com.example.gqapp.widget.SemicircleImageView;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/5/20 15:07
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class VerticalPagerAdapter  extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int[] images ;

    public VerticalPagerAdapter(Context context,int[] images) {
        mContext = context;
        this.images = images;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view =  mLayoutInflater.inflate(R.layout.image_bg_item, container, false);
        FrameLayout fl = view.findViewById(R.id.fl_img_bg);
        SemicircleImageView imageView = view.findViewById(R.id.img);
        imageView.setImageResource(images[position]);
        imageView.setTag(images[position]);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
