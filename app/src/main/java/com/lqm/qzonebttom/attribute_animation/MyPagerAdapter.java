package com.lqm.qzonebttom.attribute_animation;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by @author 天空蓝蓝的 on 2017/4/2.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<View> views;
    public MyPagerAdapter(List<View> views){
        this.views=views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (( ViewPager)container).removeView(views.get(position%views.size()));
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        (( ViewPager)container).addView(views.get(position%views.size()),0);
        return views.get(position%views.size());
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
