package com.example.yjlove.objectmanager.adaptor;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.bean.entity.MyObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者： YJLove
 * 制作日期：2017/7/19.
 */
public class MainObjectListAdapter extends BaseAdapter {

    private List<MyObject> mObjects;
    private Activity mActivity;

    public MainObjectListAdapter(FragmentActivity activity, List<MyObject> mObjects) {
        this.mObjects = mObjects;
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public MyObject getItem(int i) {
        return mObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(mActivity, R.layout.activity_item_main, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        MyObject item = getItem(i);
        holder.mainItemObjectName.setText(item.getObjectName());
        holder.mainItemObjectType.setText(item.getObjectType());
        holder.mainItemObjectTime.setText(item.getInputDate());
        holder.mainItemObjectCount.setText(String.valueOf(item.getObjectCount()));
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.main_item_object_photo)
        ImageView mainItemObjectPhoto;
        @BindView(R.id.main_item_object_name)
        TextView mainItemObjectName;
        @BindView(R.id.main_item_object_type)
        TextView mainItemObjectType;
        @BindView(R.id.main_item_object_time)
        TextView mainItemObjectTime;
        @BindView(R.id.main_item_object_count)
        TextView mainItemObjectCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
