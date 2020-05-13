package com.example.gqapp.fragment.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/23 10:09
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> broadenFragments;

    public FragmentAdapter(@NonNull FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        initDatas(fragments) ;
    }
    private void initDatas(ArrayList<Fragment> fragments) {
        if(fragments == null){
            broadenFragments = new ArrayList<Fragment>() ;
        }else{
            broadenFragments = fragments ;
        }
    }


    @Override
    public Fragment getItem(int position) {
        return broadenFragments.get(position);
    }

    @Override
    public int getCount() {
        return broadenFragments.size();
    }
}
