package com.example.yjlove.objectmanager.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.base.AbsBaseFragment;

/**
 * 作者： YJLove
 * 制作日期：2017/7/18.
 */
public class MessageFragment extends AbsBaseFragment {

    protected LayoutInflater mInflater;

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
        init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void init() {

    }

}
