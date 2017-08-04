package com.example.yjlove.objectmanager.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.adaptor.MessageTabAdapter;
import com.example.yjlove.objectmanager.base.AbsBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者： YJLove
 * 制作日期：2017/7/18.
 */
public class MessageFragment extends AbsBaseFragment {

    protected LayoutInflater mInflater;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    Unbinder unbinder;

    /**
     * Tab标题
     */
    private String[] title = new String[]{"tab1", "tab2", "tab3", "tab4"};

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null)
            return null;
        if (mViewGroup == null) {
            mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_message, null);
        }
        mInflater = LayoutInflater.from(mContext);
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void init() {

        for (int i = 0; i < title.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(title[i]));
        }

        MessageTabAdapter adapter = new MessageTabAdapter(title, getChildFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
