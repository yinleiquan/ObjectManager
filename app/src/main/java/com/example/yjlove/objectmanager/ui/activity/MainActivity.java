package com.example.yjlove.objectmanager.ui.activity;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.base.AbsBaseFragment;
import com.example.yjlove.objectmanager.base.BaseActivity;
import com.example.yjlove.objectmanager.bean.model.NavigateModel;
import com.example.yjlove.objectmanager.ui.fragment.CountFragment;
import com.example.yjlove.objectmanager.ui.fragment.MainFragment;
import com.example.yjlove.objectmanager.ui.fragment.MeFragment;
import com.example.yjlove.objectmanager.ui.fragment.MessageFragment;
import com.example.yjlove.objectmanager.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.activity_main_container)
    FrameLayout activityMainContainer;
    @BindView(R.id.activity_main_bottom_bar)
    LinearLayout activityMainBottomBar;
    @BindView(R.id.activity_main_show_me)
    ImageView activityMainShowMe;
    @BindView(R.id.activity_main_navigation)
    NavigationView activityMainNavigation;
    @BindView(R.id.activity_main_drawer)
    DrawerLayout activityMainDrawer;

    private long mExitTime = 0;
    protected LayoutInflater mInflater;
    private List<NavigateModel> mNavigateModels = new ArrayList<>();

    private AbsBaseFragment mCur;
    private MainFragment mHomeFragment;//物品
    private CountFragment mObjectFragment;//统计
    private MessageFragment mMessageFragment;//消息
    private MeFragment mMeFragment;//我的

    @Override
    protected int getViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mNavigateModels.add(new NavigateModel("物品", R.drawable.bottombar_object_selector, NavigateModel.OBJECT_TAG));
        mNavigateModels.add(new NavigateModel("统计", R.drawable.bottombar_count_selector, NavigateModel.COUNT_TAG));
        mNavigateModels.add(new NavigateModel("消息", R.drawable.bottombar_message_selector, NavigateModel.MESSAGE_TAG));
        mNavigateModels.add(new NavigateModel("我的", R.drawable.bottombar_me_selector, NavigateModel.PERSON_TAG));

        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (NavigateModel navigateModel : mNavigateModels) {
            RelativeLayout button = (RelativeLayout) mInflater.inflate(R.layout.activity_item_bottombar, null, false);
            ViewHolder holder = new ViewHolder();
            holder.mIcon = (ImageView) button.findViewById(R.id.bottom_ico);
            holder.mTitleText = (TextView) button.findViewById(R.id.bottom_text);
            holder.mIcon.setImageResource(navigateModel.getIconResId());
            holder.mTips = (ImageView) button.findViewById(R.id.tips_v);
            holder.mTitleText.setText(navigateModel.getTitle());
            button.setTag(navigateModel.getTag());
            button.setOnClickListener(mBottomBarClickListener);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            button.setLayoutParams(params);
            activityMainBottomBar.addView(button);
        }
        switchPress(NavigateModel.OBJECT_TAG);
        showHomeFragment();

        activityMainShowMe.setOnClickListener(onClickListener);

        activityMainNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                // 关闭
                activityMainDrawer.closeDrawers();
                ToastUtil.showShort(mContext, menuItem.getTitle());
                return true;
            }
        });

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_main_show_me:
                    activityMainDrawer.openDrawer(GravityCompat.START);
                    break;
            }
        }
    };

    /**
     * 保存导航按钮按钮视图和文本视图的容器
     */
    private final class ViewHolder {
        public ImageView mIcon;
        public TextView mTitleText;
        public ImageView mTips;
    }

    public void switchPress(int index) {
        for (int i = 0; i < activityMainBottomBar.getChildCount(); ++i) {
            View v = activityMainBottomBar.getChildAt(i);
            Integer tag = (Integer) v.getTag();
            if (tag.intValue() == index) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
    }

    /**
     * 底部菜单栏响应点击 处理函数
     */
    private View.OnClickListener mBottomBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            Integer tag = (Integer) arg0.getTag();
            int index = tag.intValue();

            switchPress(index);

            switch (index) {
                case NavigateModel.OBJECT_TAG:
                    //首页
                    showHomeFragment();
                    break;
                case NavigateModel.COUNT_TAG:
                    //物品管理
                    showObjectFragment();
                    break;
                case NavigateModel.MESSAGE_TAG:
                    //消息中心
                    showMessageFragment();
                    break;
                case NavigateModel.PERSON_TAG:
                    //我的
                    showPersonFragment();
                    break;
                default:
                    showHomeFragment();
                    break;
            }
        }
    };

    public void showHomeFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = new MainFragment();
        }
        if (!mHomeFragment.isAdded()) {
            switchToFragment(mHomeFragment);
        }
    }

    public void showObjectFragment() {
        if (mObjectFragment == null) {
            mObjectFragment = new CountFragment();
        }
        if (!mObjectFragment.isAdded()) {
            switchToFragment(mObjectFragment);
        }
    }

    public void showMessageFragment() {
        if (mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
        }
        if (!mMessageFragment.isAdded()) {
            switchToFragment(mMessageFragment);
        }
    }

    public void showPersonFragment() {
        if (mMeFragment == null) {
            mMeFragment = new MeFragment();
        }
        if (!mMeFragment.isAdded()) {
            switchToFragment(mMeFragment);
        }
    }

    public void switchToFragment(AbsBaseFragment frag) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        if (null == mCur) {
            t.replace(R.id.activity_main_container, frag).attach(frag).commitAllowingStateLoss();
        } else {
            t.detach(mCur).replace(R.id.activity_main_container, frag).attach(frag).addToBackStack(null).commitAllowingStateLoss();
        }
        mCur = frag;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(
                R.id.activity_main_container);
        if (fragment != null && fragment instanceof AbsBaseFragment) {
            boolean ret = ((AbsBaseFragment) fragment)
                    .onKeyDown(keyCode, event);
            if (ret) {
                return true;
            }
        }

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出应用",
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
