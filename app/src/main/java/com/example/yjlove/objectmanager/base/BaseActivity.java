package com.example.yjlove.objectmanager.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.yjlove.objectmanager.application.AppContext;
import com.example.yjlove.objectmanager.utils.PermissionUtils;
import com.example.yjlove.objectmanager.widget.LoadingDialog;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者 YJlvoe
 * 创建时间 2017/7/3.
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected Context mContext;
    protected LoadingDialog mLoadingDialog;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getViewLayoutId());
        initView();

        this.mContext = AppContext.getInstance();
        //友盟统计
        PushAgent.getInstance(mContext).onAppStart();
        init();
    }

    protected abstract int getViewLayoutId();

    protected abstract void init();

    private void initView() {
        mUnbinder = ButterKnife.bind(this);
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        // 权限检测
        PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_MULTI_PERMISSION:
                    // Toast.makeText(LoginActivity.this, "Result All Permission Grant", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
