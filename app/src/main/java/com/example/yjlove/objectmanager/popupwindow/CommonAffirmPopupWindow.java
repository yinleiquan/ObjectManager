package com.example.yjlove.objectmanager.popupwindow;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yjlove.objectmanager.R;

/**
 * 作者 YJlvoe
 * 创建时间 2017/4/14.
 */
public class CommonAffirmPopupWindow {

    private Activity mActivity;
    private int mParentLayout;

    private String mTilte;// 弹出框标题
    private String mContent;// 弹出框内容

    private PopupWindow mCommonPopupWindow;
    private TextView common_popwindow_confirm_title;//弹出框标题
    private TextView common_popwindow_confirm_content;//弹出框内容
    private LinearLayout common_popwindow_confirm_ok_layout;//确定
    private LinearLayout common_popwindow_confirm_cancel_layout;//取消

    public CommonAffirmPopupWindow(Activity activity, int parentLayout, String titile, String content) {
        this.mActivity = activity;
        this.mParentLayout = parentLayout;
        this.mTilte = titile;
        this.mContent = content;
        popupWindow();
    }

    //弹出框
    private void popupWindow(){
        View newPopWindow = View.inflate(mActivity, R.layout.popwindow_common_confirm, null);
        if (mCommonPopupWindow != null) {
            mCommonPopupWindow.dismiss();
            mCommonPopupWindow = null;
        }

        mCommonPopupWindow = new PopupWindow(newPopWindow, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mCommonPopupWindow.setOutsideTouchable(false);
        //设置动画弹出效果
        //mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
        mCommonPopupWindow.setFocusable(true);
        mCommonPopupWindow.showAtLocation(mActivity.findViewById(mParentLayout), Gravity.CENTER, 0, 0);

        common_popwindow_confirm_title = (TextView) newPopWindow.findViewById(R.id.common_popwindow_confirm_title);
        common_popwindow_confirm_content = (TextView) newPopWindow.findViewById(R.id.common_popwindow_confirm_content);
        common_popwindow_confirm_ok_layout = (LinearLayout)newPopWindow.findViewById(R.id.common_popwindow_confirm_ok_layout);
        common_popwindow_confirm_cancel_layout = (LinearLayout)newPopWindow.findViewById(R.id.common_popwindow_confirm_cancel_layout);

        common_popwindow_confirm_title.setText(mTilte);
        common_popwindow_confirm_content.setText(mContent);
        common_popwindow_confirm_ok_layout.setOnClickListener(commonPopClickListener);
        common_popwindow_confirm_cancel_layout.setOnClickListener(commonPopClickListener);
    }

    private View.OnClickListener commonPopClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mCommonPopupWindow!=null){
                mCommonPopupWindow.dismiss();
                mCommonPopupWindow = null;
            }
            switch (view.getId()) {
                case R.id.common_popwindow_confirm_ok_layout:
                    //确定
                    onAffirmCommonPopClickListener.onOk();
                    break;
                case R.id.common_popwindow_confirm_cancel_layout:
                    //取消
                    onAffirmCommonPopClickListener.onCancel();
                    break;
            }
        }
    };

    private OnAffirmCommonPopClickListener onAffirmCommonPopClickListener;

    public void setOnAffirmCommonPopClickListener(OnAffirmCommonPopClickListener onAffirmCommonPopClickListener){
        this.onAffirmCommonPopClickListener = onAffirmCommonPopClickListener;
    }

    public interface OnAffirmCommonPopClickListener{
        void onOk();
        void onCancel();
    }
}
