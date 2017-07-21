package com.example.yjlove.objectmanager.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.base.AbsBaseFragment;
import com.example.yjlove.objectmanager.base.BaseFragment;
import com.example.yjlove.objectmanager.utils.DBUtil;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者： YJLove
 * 制作日期：2017/7/18.
 */
public class CountFragment extends AbsBaseFragment {

    @BindView(R.id.count_indicator)
    TabPageIndicator countIndicator;
    @BindView(R.id.count_viewpager)
    ViewPager countViewpager;
    Unbinder unbinder;
    /**
     * Tab标题
     */
    private String[] title;
    /**
     * 内容页
     */
    private BaseFragment[] pagers;

    protected LayoutInflater mInflater;

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null)
            return null;
        if (mViewGroup == null) {
            mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_count, null);
        }
        mInflater = LayoutInflater.from(mContext);
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void init() {
        int size = DBUtil.getTypes().size();
        title = DBUtil.getTypes().toArray(new String[size]);
        pagers = new BaseFragment[size];
        for (int i = 0; i < title.length; i++) {
            pagers[i] = new CountItemFragment();
        }

        TabAdapter adapter = new TabAdapter(getChildFragmentManager());
        countViewpager.setAdapter(adapter);
        countIndicator.setViewPager(countViewpager);
        countIndicator.setVisibility(View.VISIBLE);
    }
    /**
     * 适配器代码
     */
    class TabAdapter extends FragmentPagerAdapter {


        public TabAdapter(FragmentManager fm) {
            super(fm);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
