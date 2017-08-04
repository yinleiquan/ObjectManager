package com.example.yjlove.objectmanager.adaptor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.yjlove.objectmanager.ui.fragment.MessageItemFragment;

/**
 * 作者： YJLove
 * 制作日期：2017/8/3.
 */
public class MessageTabAdapter extends FragmentPagerAdapter {

    private String[] title;

    public MessageTabAdapter(String[] title, FragmentManager fm) {
        super(fm);
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putString("title",title[position]);
        MessageItemFragment messageItemFragment = new MessageItemFragment();
        messageItemFragment.setArguments(args);
        return messageItemFragment;
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
