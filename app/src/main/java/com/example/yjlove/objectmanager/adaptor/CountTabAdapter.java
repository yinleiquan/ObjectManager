package com.example.yjlove.objectmanager.adaptor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.yjlove.objectmanager.base.BaseFragment;

/**
 * 作者： YJLove
 * 制作日期：2017/8/3.
 */
public class CountTabAdapter extends FragmentPagerAdapter {

    private String[] title;
    private BaseFragment[] pagers;

    public CountTabAdapter(String[] title, BaseFragment[] pagers, FragmentManager fm) {
        super(fm);
        this.title = title;
        this.pagers = pagers;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = pagers[position];
        Bundle args = new Bundle();
        args.putString("title",title[position]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
