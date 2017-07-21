package com.example.yjlove.objectmanager.popupwindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.widget.KCalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： YJLove
 * 制作日期：2017/7/17.
 */
public class CalendarPopupWindow extends PopupWindow {

    private Activity mActivity;
    private int mParentLayout;
    private String mTilte;// 弹出框标题
    private String mDate;

    public CalendarPopupWindow(Activity activity, int parentLayout, String title, String initDate) {
        this.mActivity = activity;
        this.mParentLayout = parentLayout;
        this.mTilte = title;
        this.mDate = initDate;
        popupWindow();
    }

    public void popupWindow() {

        View view = View.inflate(mActivity, R.layout.popupwindow_calendar, null);

        view.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.fade_in));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.push_bottom_in_1));

        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(mActivity.findViewById(mParentLayout), Gravity.CENTER, 0, 0);

        final TextView popupwindow_calendar_title = (TextView) view.findViewById(R.id.popupwindow_calendar_title);

        //开始时间
        popupwindow_calendar_title.setText(mTilte);
        update();

        final TextView popupwindow_calendar_month = (TextView) view.findViewById(R.id.popupwindow_calendar_month);
        final KCalendar calendar = (KCalendar) view.findViewById(R.id.popupwindow_calendar);
        Button popupwindow_calendar_bt_enter = (Button) view.findViewById(R.id.popupwindow_calendar_bt_enter);

        popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年" + calendar.getCalendarMonth() + "月");

        if (null != mDate) {
            int years = Integer.parseInt(mDate.substring(0, mDate.indexOf("-")));
            int month = Integer.parseInt(mDate.substring(mDate.indexOf("-") + 1, mDate.lastIndexOf("-")));
            popupwindow_calendar_month.setText(years + "年" + month + "月");

            calendar.showCalendar(years, month);
            calendar.setCalendarDayBgColor(mDate, R.drawable.calendar_date_focused);
        }

        List<String> list = new ArrayList<>(); //设置标记列表
        list.add("2016-01-01");
        list.add("2016-01-02");
        calendar.addMarks(list, 0);

        //监听所选中的日期
        calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {

            public void onCalendarClick(int row, int col, String dateFormat) {
                int month = Integer.parseInt(dateFormat.substring(
                        dateFormat.indexOf("-") + 1,
                        dateFormat.lastIndexOf("-")));

                if (calendar.getCalendarMonth() - month == 1//跨年跳转
                        || calendar.getCalendarMonth() - month == -11) {
                    calendar.lastMonth();

                } else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
                        || month - calendar.getCalendarMonth() == -11) {
                    calendar.nextMonth();

                } else {
                    calendar.removeAllBgColor();
                    calendar.setCalendarDayBgColor(dateFormat, R.drawable.calendar_date_focused);
                    mDate = dateFormat;//最后返回给全局 date
                }
            }
        });

        //监听当前月份
        calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
            @Override
            public void onCalendarDateChanged(int year, int month) {
                popupwindow_calendar_month
                        .setText(year + "年" + month + "月");
            }
        });

        //上月监听按钮
        RelativeLayout popupwindow_calendar_last_month = (RelativeLayout) view
                .findViewById(R.id.popupwindow_calendar_last_month);
        popupwindow_calendar_last_month
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        calendar.lastMonth();
                    }

                });

        //下月监听按钮
        RelativeLayout popupwindow_calendar_next_month = (RelativeLayout) view
                .findViewById(R.id.popupwindow_calendar_next_month);
        popupwindow_calendar_next_month
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        calendar.nextMonth();
                    }
                });

        //关闭窗口
        popupwindow_calendar_bt_enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
                onCalendarPopClickListener.onOk(mDate);
            }
        });
    }

    private OnCalendarPopClickListener onCalendarPopClickListener;

    public void setOnCalendarPopClickListener(OnCalendarPopClickListener onCalendarPopClickListener) {
        this.onCalendarPopClickListener = onCalendarPopClickListener;
    }

    public interface OnCalendarPopClickListener {
        void onOk(String date);
    }
}
