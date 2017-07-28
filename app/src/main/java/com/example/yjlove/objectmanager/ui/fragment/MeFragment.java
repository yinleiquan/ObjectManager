package com.example.yjlove.objectmanager.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.base.AbsBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者： YJLove
 * 制作日期：2017/7/18.
 */
public class MeFragment extends AbsBaseFragment {

    protected LayoutInflater mInflater;
    @BindView(R.id.me_user_name)
    TextView meUserName;
    Unbinder unbinder;

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null)
            return null;
        if (mViewGroup == null) {
            mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_me, null);
        }
        mInflater = LayoutInflater.from(mContext);
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void init() {
//        meUserName.setText();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("Cookies_Prefs", 0);
//        sharedPreferences.getString()
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
