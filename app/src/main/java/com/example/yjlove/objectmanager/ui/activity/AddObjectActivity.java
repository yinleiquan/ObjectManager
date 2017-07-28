package com.example.yjlove.objectmanager.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.application.AppContext;
import com.example.yjlove.objectmanager.bean.entity.MyObject;
import com.example.yjlove.objectmanager.constant.StrConstant;
import com.example.yjlove.objectmanager.greendao.gen.MyObjectDao;
import com.example.yjlove.objectmanager.popupwindow.CalendarPopupWindow;
import com.example.yjlove.objectmanager.popupwindow.SelectListPopupWindow;
import com.example.yjlove.objectmanager.utils.DBUtil;
import com.example.yjlove.objectmanager.utils.StringUtil;
import com.example.yjlove.objectmanager.utils.ToastUtil;
import com.example.yjlove.objectmanager.widget.LoadingDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者： YJLove
 * 制作日期：2017/7/21.
 */
public class AddObjectActivity extends AppCompatActivity {

    @BindView(R.id.object_back)
    ImageView objectBack;
    @BindView(R.id.object_object_photo)
    ImageView objectObjectPhoto;
    @BindView(R.id.object_object_name)
    EditText objectObjectName;
    @BindView(R.id.object_object_type)
    TextView objectObjectType;
    @BindView(R.id.object_object_type_layout)
    LinearLayout objectObjectTypeLayout;
    @BindView(R.id.object_object_count_add)
    ImageView objectObjectCountAdd;
    @BindView(R.id.object_object_count)
    TextView objectObjectCount;
    @BindView(R.id.object_object_count_remove)
    ImageView objectObjectCountRemove;
    @BindView(R.id.object_object_time)
    TextView objectObjectTime;
    @BindView(R.id.object_object_note)
    EditText objectObjectNote;
    @BindView(R.id.object_object_ok_button)
    Button objectObjectOkButton;

    private Date today = new Date(); // 今天
    private SimpleDateFormat df = new SimpleDateFormat(StrConstant.DATA_FORMAT);
    private MyObjectDao mMyObjectDao;// 取出物品表
    private LoadingDialog mLoadingDialog;
    private int mCount = 1;
    private ArrayList<String> mTypes = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mCount = 1;
                    objectObjectName.setText(StrConstant.EMPTY);
                    objectObjectType.setText(StrConstant.EMPTY);
                    objectObjectTime.setText(df.format(today));
                    objectObjectNote.setText(StrConstant.EMPTY);
                    objectObjectCount.setText(String.valueOf(mCount));
                    ToastUtil.showShort(AppContext.getInstance(), "物品入库成功");
                    mLoadingDialog.close();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide().setDuration(300));
            getWindow().setExitTransition(new Slide().setDuration(300));
        }

        setContentView(R.layout.activity_add_object);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        mLoadingDialog = new LoadingDialog(AddObjectActivity.this);
        mMyObjectDao = AppContext.getInstance().getDaoSession().getMyObjectDao();

        objectObjectCount.setText(String.valueOf(mCount));
        objectObjectTime.setText(df.format(today));
        objectObjectPhoto.setOnClickListener(onClickListener);
        objectObjectTime.setOnClickListener(onClickListener);
        objectObjectCountAdd.setOnClickListener(onClickListener);
        objectObjectCountRemove.setOnClickListener(onClickListener);
        objectObjectTypeLayout.setOnClickListener(onClickListener);
        objectBack.setOnClickListener(onClickListener);
        objectObjectOkButton.setOnClickListener(onClickListener);

        initType();
    }

    private void initType() {
        mTypes.clear();
        mTypes.addAll(DBUtil.getTypes());
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.object_object_time:
                    CalendarPopupWindow objectInputStartDateCalendar =
                            new CalendarPopupWindow(AddObjectActivity.this, R.id.activity_add_object, "入库开始日期", objectObjectTime.getText().toString());
                    objectInputStartDateCalendar.setOnCalendarPopClickListener(new CalendarPopupWindow.OnCalendarPopClickListener() {
                        @Override
                        public void onOk(String date) {
                            objectObjectTime.setText(date);
                        }
                    });
                    break;
                case R.id.object_object_type_layout:
                    SelectListPopupWindow selectListPopupWindow =
                            new SelectListPopupWindow(AddObjectActivity.this, R.id.activity_add_object, "选择物品类型", mTypes, objectObjectType.getText().toString());
                    selectListPopupWindow.setOnSelectListClickListener(new SelectListPopupWindow.OnSelectListClickListener() {
                        @Override
                        public void onOk(String data) {
                            objectObjectType.setText(data);
                        }
                    });
                    break;
                case R.id.object_object_count_add:
                    mCount = mCount + 1;
                    objectObjectCount.setText(String.valueOf(mCount));
                    break;
                case R.id.object_object_count_remove:
                    if (mCount > 1) {
                        mCount = mCount - 1;
                    }
                    objectObjectCount.setText(String.valueOf(mCount));
                    break;
                case R.id.object_object_photo:
                    ToastUtil.showShort(AppContext.getInstance(), "功能尚未开通");
                    break;
                case R.id.object_object_ok_button:
                    mLoadingDialog.show();
                    if (OkAdd()) {
                        MyObject object = new MyObject();
                        object.setObjectName(objectObjectName.getText().toString());
                        object.setObjectType(objectObjectType.getText().toString());
                        object.setInputDate(objectObjectTime.getText().toString());
                        object.setObjectCount(mCount);
                        mMyObjectDao.insert(object);
                        mHandler.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
                case R.id.object_back:
                    AddObjectActivity.this.finish();
                    overridePendingTransition(R.anim.close_enter, R.anim.close_exit);
                    break;
            }
        }
    };

    private boolean OkAdd() {
        if (StringUtil.isEmpty(objectObjectName.getText().toString())) {
            ToastUtil.showShort(AppContext.getInstance(), "物品名称不能为空");
            mLoadingDialog.close();
            return false;
        }
        if (StringUtil.isEmpty(objectObjectType.getText().toString())) {
            ToastUtil.showShort(AppContext.getInstance(), "物品类型不能为空");
            mLoadingDialog.close();
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            overridePendingTransition(R.anim.close_enter, R.anim.close_exit);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
