package com.example.yjlove.objectmanager.popupwindow;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.adaptor.PopupWindowSelectListAdapter;
import com.example.yjlove.objectmanager.widget.LoadingDialog;

import java.util.ArrayList;

/**
 * 作者 YJlvoe
 * 创建时间 2016/12/23.
 */
public class SelectListPopupWindow extends PopupWindow {

    private Activity mActivity;
    private LoadingDialog loadingDialog;
    private int mLayoutId;
    private String mTitle;
    private ArrayList<String> mData;
    private String mCurrentName;

    // 服务站列表弹出框
    private PopupWindow mSelectStationPopupWindow;//选择服务站弹出框
    private TextView popwindow_select_list_title;//弹出框标题
    private LinearLayout popwindow_select_list_close;//弹出框关闭按钮
    private ListView branch_company_select_station_list;//服务站列表

    public SelectListPopupWindow(Activity activity, int parentLayout, String title, ArrayList<String> data, String currentName) {
        mActivity = activity;
        this.mData = data;
        this.mTitle = title;
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(mActivity);
        }
        mLayoutId = parentLayout;
        mCurrentName = currentName;
        popupWindow();
    }

    private void popupWindow() {
        View newPopWindow = View.inflate(mActivity, R.layout.popwindow_select_list, null);

        if (mSelectStationPopupWindow != null) {
            mSelectStationPopupWindow.dismiss();
            mSelectStationPopupWindow = null;
        }

        mSelectStationPopupWindow = new PopupWindow(newPopWindow, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mSelectStationPopupWindow.setOutsideTouchable(true);
        //设置动画弹出效果
        //mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
        mSelectStationPopupWindow.setFocusable(false);

        //修改此处
        mSelectStationPopupWindow.showAtLocation(mActivity.findViewById(mLayoutId), Gravity.CENTER, 0, 0);

        popwindow_select_list_close = (LinearLayout) newPopWindow.findViewById(R.id.popwindow_select_list_close);
        popwindow_select_list_title = (TextView) newPopWindow.findViewById(R.id.popwindow_select_list_title);
        popwindow_select_list_title.setText(mTitle);
        popwindow_select_list_close.setOnClickListener(CloseClickListener);
        branch_company_select_station_list = (ListView) newPopWindow.findViewById(R.id.branch_company_select_station_list);//服务站列表

        iniStationListViewData();
    }

    // 服务站列表适配器
    private PopupWindowSelectListAdapter mPopupWindowSelectListAdapter;//选择服务站列表

    private void iniStationListViewData() {
        mPopupWindowSelectListAdapter = new PopupWindowSelectListAdapter(mActivity, mData, mCurrentName);
        branch_company_select_station_list.setAdapter(mPopupWindowSelectListAdapter);
        branch_company_select_station_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onSelectListClickListener.onOk(mData.get(i));
                if (mSelectStationPopupWindow != null) {
                    mSelectStationPopupWindow.dismiss();
                    mSelectStationPopupWindow = null;
                }
            }
        });
    }

    // 关闭点击事件
    private View.OnClickListener CloseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.popwindow_select_list_close:
                    //选择列表取消
                    if (mSelectStationPopupWindow != null) {
                        mSelectStationPopupWindow.dismiss();
                        mSelectStationPopupWindow = null;
                    }
                    break;
                default:
                    break;
            }
        }
    };


    private OnSelectListClickListener onSelectListClickListener;

    public void setOnSelectListClickListener(OnSelectListClickListener onSelectListClickListener) {
        this.onSelectListClickListener = onSelectListClickListener;
    }

    public interface OnSelectListClickListener {
        void onOk(String data);
    }
}
