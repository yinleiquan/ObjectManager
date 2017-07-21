package com.example.yjlove.objectmanager.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yjlove.objectmanager.R;

import java.util.List;

/**
 * 作者 YJlvoe
 * 创建时间 2016/12/23.
 */
public class PopupWindowSelectListAdapter extends BaseAdapter {
    private List<String> ls;
    private Context mContext;
    private LayoutInflater inflater;
    private String mCurrentName;

    public PopupWindowSelectListAdapter(Context context, List<String> list, String currentName) {
        ls = list;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.mCurrentName = currentName;
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public String getItem(int position) {
        return ls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlatListItemViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.popupwindow_item_select_list, parent, false);
            holder = new PlatListItemViewHolder();
            holder.select_list_item_name = (TextView) convertView.findViewById(R.id.select_list_item_name);
            holder.select_list_item_status = (ImageView) convertView.findViewById(R.id.select_list_item_status);
            convertView.setTag(holder);
        } else {
            holder = (PlatListItemViewHolder) convertView.getTag();
        }

        holder.select_list_item_name.setText(getItemByPositon(position));
        if (getItem(position).equals(mCurrentName)) {
            holder.select_list_item_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check_true));
        } else {
            holder.select_list_item_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check_false));
        }
        return convertView;
    }

    public String getItemByPositon(int position) {
        return ls.get(position);
    }

    public class PlatListItemViewHolder {
       public TextView select_list_item_name;
       public ImageView select_list_item_status;//选择框
    }
}
