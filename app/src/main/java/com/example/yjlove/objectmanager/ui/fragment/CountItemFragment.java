package com.example.yjlove.objectmanager.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.application.AppContext;
import com.example.yjlove.objectmanager.base.BaseFragment;
import com.example.yjlove.objectmanager.greendao.gen.MyObjectDao;

import butterknife.BindView;

/**
 * 作者： YJLove
 * 制作日期：2017/7/21.
 */
public class CountItemFragment extends BaseFragment {

    @BindView(R.id.count_item_title)
    TextView countItemTitle;

    private String mTitle;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_count_item;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        MyObjectDao mMyObjectDao = AppContext.getInstance().getDaoSession().getMyObjectDao();
        Bundle args = getArguments();
        mTitle = args.getString("title");
        countItemTitle.setText(mTitle);
    }

}
