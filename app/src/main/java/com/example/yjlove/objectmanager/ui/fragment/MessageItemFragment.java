package com.example.yjlove.objectmanager.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.base.BaseFragment;

import butterknife.BindView;

/**
 * 作者： YJLove
 * 制作日期：2017/8/3.
 */
public class MessageItemFragment extends BaseFragment {

    @BindView(R.id.message_item_name)
    TextView messageItemName;

    private String mTitle;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message_item;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle args = getArguments();
        mTitle = args.getString("title");
        messageItemName.setText(mTitle);
    }

}
