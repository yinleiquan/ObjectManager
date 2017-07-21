package com.example.yjlove.objectmanager.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.adaptor.MainObjectListAdapter;
import com.example.yjlove.objectmanager.application.AppContext;
import com.example.yjlove.objectmanager.base.AbsBaseFragment;
import com.example.yjlove.objectmanager.bean.entity.MyObject;
import com.example.yjlove.objectmanager.greendao.gen.MyObjectDao;
import com.example.yjlove.objectmanager.popupwindow.CalendarPopupWindow;
import com.example.yjlove.objectmanager.popupwindow.SelectListPopupWindow;
import com.example.yjlove.objectmanager.ui.activity.AddObjectActivity;
import com.example.yjlove.objectmanager.utils.DBUtil;
import com.example.yjlove.objectmanager.utils.DensityUtil;
import com.example.yjlove.objectmanager.utils.ToastUtil;
import com.example.yjlove.objectmanager.widget.LoadingDialog;
import com.example.yjlove.objectmanager.widget.swipe.SwipeMenu;
import com.example.yjlove.objectmanager.widget.swipe.SwipeMenuCreator;
import com.example.yjlove.objectmanager.widget.swipe.SwipeMenuItem;
import com.example.yjlove.objectmanager.widget.swipe.SwipeMenuListView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者： YJLove
 * 制作日期：2017/7/18.
 */
public class MainFragment extends AbsBaseFragment {

    @BindView(R.id.main_input_start_date)
    TextView mainInputStartDate;
    @BindView(R.id.main_input_end_date)
    TextView mainInputEndDate;
    //    @BindView(R.id.main_output_start_date)
//    TextView mainOutputStartDate;
//    @BindView(R.id.main_output_end_date)
//    TextView mainOutputEndDate;
    @BindView(R.id.main_object_type)
    TextView mainObjectType;
    @BindView(R.id.main_object_type_layout)
    LinearLayout mainObjectTypeLayout;
    @BindView(R.id.main_object_search_send)
    LinearLayout mainObjectSearchSend;
    @BindView(R.id.main_data_list)
    SwipeMenuListView mainDataList;
    @BindView(R.id.main_data_list_layout)
    LinearLayout mainDataListLayout;
    @BindView(R.id.main_no_data_layout)
    LinearLayout mainNoDataLayout;
    Unbinder unbinder;
    @BindView(R.id.main_add_object)
    ImageView mainAddObject;

    private Date today = new Date(); // 今天
    private MyObjectDao mMyObjectDao;// 取出物品表
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private List<MyObject> mObjects;

    protected LayoutInflater mInflater;
    private MainObjectListAdapter mMainObjectListAdapter;
    private LoadingDialog mLoadingDialog;
    private ArrayList<String> mTypes = new ArrayList<>();

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                if (mObjects.isEmpty()){
                    mainNoDataLayout.setVisibility(View.VISIBLE);
                    mainDataListLayout.setVisibility(View.GONE);
                } else {
                    mainNoDataLayout.setVisibility(View.GONE);
                    mainDataListLayout.setVisibility(View.VISIBLE);
                    mMainObjectListAdapter.notifyDataSetChanged();
                }
                mLoadingDialog.close();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null)
            return null;
        if (mViewGroup == null) {
            mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main, null);
        }
        mInflater = LayoutInflater.from(mContext);
        mLoadingDialog = new LoadingDialog(getActivity());
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void init() {
        mMyObjectDao = AppContext.getInstance().getDaoSession().getMyObjectDao();

        mainInputStartDate.setText("ago");
        mainInputEndDate.setText(df.format(today));
//        mainOutputStartDate.setText(getDateStr(-1));
//        mainOutputEndDate.setText(df.format(today));
        mainObjectType.setText("全部");

        mainInputStartDate.setOnClickListener(onClickListener);
        mainInputEndDate.setOnClickListener(onClickListener);
//        mainOutputStartDate.setOnClickListener(onClickListener);
//        mainOutputEndDate.setOnClickListener(onClickListener);
        mainObjectTypeLayout.setOnClickListener(onClickListener);
        mainObjectSearchSend.setOnClickListener(onClickListener);
        mainAddObject.setOnClickListener(onClickListener);

        mObjects = mMyObjectDao.loadAll();
        initType();
        initList();
    }

    private void initType() {
        mTypes.clear();
        mTypes.add(0, "全部");
        mTypes.addAll(DBUtil.getTypes());
    }

    private void initList() {
        if (mObjects == null || mObjects.isEmpty()) {
            mainNoDataLayout.setVisibility(View.VISIBLE);
            mainDataListLayout.setVisibility(View.GONE);
        } else {
            mainNoDataLayout.setVisibility(View.GONE);
            mainDataListLayout.setVisibility(View.VISIBLE);
        }
        mMainObjectListAdapter = new MainObjectListAdapter(getActivity(), mObjects);
        mainDataList.setAdapter(mMainObjectListAdapter);
        mainDataList.setMenuCreator(setCreator());
        mainDataList.setDivider(AppContext.getInstance().getResources().getDrawable(R.drawable.list_item_divider));
        mainDataList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //删除
                        mMyObjectDao.delete(mObjects.get(position));
                        initType();
                        mObjects.remove(position);
                        mMainObjectListAdapter.notifyDataSetChanged();
                        ToastUtil.showShort(mContext, "删除成功。");
                        break;
                }
            }
        });
    }

    private SwipeMenuCreator setCreator() {
        return new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0, 0, 0)));
                // set item width
                deleteItem.setWidth(DensityUtil.dp2px(AppContext.getInstance(), 100));
                // set item title
                deleteItem.setTitle("删 除");
                deleteItem.setTitleSize(DensityUtil.dp2px(AppContext.getInstance(), 5));
                deleteItem.setTitleColor(getResources().getColor(R.color.white));
                // set a icon
                //deleteItem.setIcon(R.drawable.cancel);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.main_input_start_date:
                    CalendarPopupWindow mainInputStartDateCalendar =
                            new CalendarPopupWindow(getActivity(), R.id.main_activity, "入库开始日期", getDateStr(-1));
                    mainInputStartDateCalendar.setOnCalendarPopClickListener(new CalendarPopupWindow.OnCalendarPopClickListener() {
                        @Override
                        public void onOk(String date) {
                            mainInputStartDate.setText(date);
                        }
                    });
                    break;
                case R.id.main_input_end_date:
                    CalendarPopupWindow mainInputEndDateCalendar =
                            new CalendarPopupWindow(getActivity(), R.id.main_activity, "入库结束日期", mainInputEndDate.getText().toString());
                    mainInputEndDateCalendar.setOnCalendarPopClickListener(new CalendarPopupWindow.OnCalendarPopClickListener() {
                        @Override
                        public void onOk(String date) {
                            mainInputEndDate.setText(date);
                        }
                    });
                    break;
//                case R.id.main_output_start_date:
//                    CalendarPopupWindow mainOutputStartDateCalendar =
//                            new CalendarPopupWindow(getActivity(), R.id.main_activity, "出库开始日期", mainOutputStartDate.getText().toString());
//                    mainOutputStartDateCalendar.setOnCalendarPopClickListener(new CalendarPopupWindow.OnCalendarPopClickListener() {
//                        @Override
//                        public void onOk(String date) {
//                            mainOutputStartDate.setText(date);
//                        }
//                    });
//                    break;
//                case R.id.main_output_end_date:
//                    CalendarPopupWindow mainOutputEndDateCalendar =
//                            new CalendarPopupWindow(getActivity(), R.id.main_activity, "出库结束日期", mainOutputEndDate.getText().toString());
//                    mainOutputEndDateCalendar.setOnCalendarPopClickListener(new CalendarPopupWindow.OnCalendarPopClickListener() {
//                        @Override
//                        public void onOk(String date) {
//                            mainOutputEndDate.setText(date);
//                        }
//                    });
//                    break;
                case R.id.main_object_type_layout:
                    SelectListPopupWindow selectListPopupWindow =
                            new SelectListPopupWindow(getActivity(), R.id.main_activity, "选择物品类型", mTypes, mainObjectType.getText().toString());
                    selectListPopupWindow.setOnSelectListClickListener(new SelectListPopupWindow.OnSelectListClickListener() {
                        @Override
                        public void onOk(String data) {
                            mainObjectType.setText(data);
                        }
                    });
                    break;
                case R.id.main_object_search_send:
                    if (!"ago".equals(mainInputStartDate.getText().toString())){
                        if (isCanSearch(mainInputStartDate.getText().toString(),mainInputEndDate.getText().toString())){
                            mLoadingDialog.show();
                            QueryBuilder<MyObject> qb = mMyObjectDao.queryBuilder();
                            if ("全部".equals(mainObjectType.getText().toString())) {
                                qb.where(qb.and(MyObjectDao.Properties.InputDate.ge(mainInputStartDate.getText().toString())
                                        , MyObjectDao.Properties.InputDate.le(mainInputEndDate.getText().toString())));
                            } else {
                                qb.where(MyObjectDao.Properties.ObjectType.eq(mainObjectType.getText().toString()),
                                        qb.and(MyObjectDao.Properties.InputDate.ge(mainInputStartDate.getText().toString())
                                                , MyObjectDao.Properties.InputDate.le(mainInputEndDate.getText().toString())));
                            }
                            mObjects.clear();
                            mObjects.addAll(qb.list());
                            mHandler.sendEmptyMessageDelayed(1, 1000);
                        } else {
                            ToastUtil.showShort(mContext, "请填入正确的查询时间");
                        }
                    } else {
                        ToastUtil.showShort(mContext, "请填入正确的查询时间");
                    }
//                    queryThread();
                    break;
                case R.id.main_add_object:
                    startActivity(new Intent(AppContext.getInstance(), AddObjectActivity.class));
                    break;
            }
        }
    };

//    private void queryThread() {
//        mLoadingDialog.show();
//        final QueryBuilder<MyObject> qb = mMyObjectDao.queryBuilder();
//        new Thread(){
//            @Override
//            public void run() {
//                if ("全部".equals(mainObjectType.getText().toString())){
//                    qb.where(qb.and(MyObjectDao.Properties.InputDate.ge(mainInputStartDate.getText().toString())
//                            ,MyObjectDao.Properties.InputDate.le(mainInputEndDate.getText().toString())));
//                } else {
//                    qb.where(MyObjectDao.Properties.ObjectType.eq(mainObjectType.getText().toString()),
//                            qb.and(MyObjectDao.Properties.InputDate.ge(mainInputStartDate.getText().toString())
//                                    ,MyObjectDao.Properties.InputDate.le(mainInputEndDate.getText().toString())));
//                }
//                mObjects.clear();
//                mObjects = qb.list();
//                mMainObjectListAdapter.notifyDataSetChanged();
//                mLoadingDialog.close();
//            }
//        }.start();
//    }

    private boolean isCanSearch(String begin,String end){
        boolean result = false;
        try {
            Date beginDate = df.parse(begin);
            Date endDate = df.parse(end);
            int i = beginDate.compareTo(endDate);
            //如果i大于零则说明beginDate>endDate
            if(i>0){
                result = false;
            }else {
                result = true;
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return result;
    }

    private String getDateStr(int length) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, length);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        return df.format(date);
    }

    @Override
    public void onResume() {
        init();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
