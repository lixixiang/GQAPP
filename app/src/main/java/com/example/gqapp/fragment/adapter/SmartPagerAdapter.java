package com.example.gqapp.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/24 10:04
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class SmartPagerAdapter extends PagerAdapter {

    List<ImageView> mList;

    public SmartPagerAdapter(Context context,List<ImageView> mList) {
        this.mList = mList;
        initDatas(mList);
    }

    private void initDatas(List<ImageView> mList) {
        if (mList == null) {
            this.mList = new ArrayList<ImageView>() ;
        }else {
            this.mList = mList;
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) { //初始化 item
        container.addView(mList.get(position%mList.size()),0);
        return mList.get(position%mList.size());

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mList.get(position%mList.size()));
    }
}
