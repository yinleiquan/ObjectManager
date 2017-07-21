package com.example.yjlove.objectmanager.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

import com.example.yjlove.objectmanager.utils.NoLeakHandler;
import com.example.yjlove.objectmanager.utils.NoLeakHandlerInterface;
import com.example.yjlove.objectmanager.widget.LoadingView;

public abstract class AbsBaseFragment extends Fragment implements
        NoLeakHandlerInterface {

    protected final NoLeakHandler mHandler = new NoLeakHandler(this);

    /**
     * 消息
     */
    public static final int MSG_LOAD_SUCCESS = 1;
    public static final int MSG_LOAD_FAIL = 2;
    public static final int MSG_LOAD_MORE_SUCCESS = 3;
    public static final int MSG_LOAD_MORE_FAIL = 4;
    protected final static int MSG_LOAD_FINISH = -10000;

    protected final static int MSG_START_LOAD_DATA = -10001;

    protected final static int MSG_START_REFRESH = -10002;
    /**
     * 获取递增条目数
     */
    protected static final int SPAN = 36;
    protected final static int LOAD_DELAY_TIME = 300;

    // views
    protected ViewGroup mViewGroup;
    protected Context mContext;
    private LoadingView mLoadingView;

    private RelativeLayout.LayoutParams mLoadingViewLayoutParams;
    @Override
    public boolean isValid() {
        // TODO Auto-generated method stub
        return isAdded() && !isDetached();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getBaseContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mViewGroup != null) {
            if (mViewGroup.getParent() != null) {
                ((ViewGroup) mViewGroup.getParent()).removeView(mViewGroup);
            }
        }
        return mViewGroup;
    }

    protected void dismissLoadingView(Animation animation) {
        if (mLoadingView == null) {
            return;
        }
        if (animation == null) {
            removeLoadingView();
            return;
        }
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                removeLoadingView();
            }
        });
        mLoadingView.startAnimation(animation);
    }

    protected void addLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView(mContext);
        }
        ViewGroup parent = (ViewGroup) mLoadingView.getParent();
        if (parent != mViewGroup) {
            if (parent != null) {
                parent.removeView(mLoadingView);
            }
            mViewGroup.addView(mLoadingView);
        }
        initLoadingViewParams();
        mLoadingView.setLayoutParams(mLoadingViewLayoutParams);
    }

    private void removeLoadingView() {
        if (mLoadingView != null && mLoadingView.getParent() != null) {
            ((ViewGroup) mLoadingView.getParent()).removeView(mLoadingView);
            mLoadingView = null;
        }
    }

    protected void setLoadingViewLayoutParams(RelativeLayout.LayoutParams params) {
        mLoadingViewLayoutParams = params;
        if (mLoadingView != null) {
            mLoadingView.setLayoutParams(params);
        }
    }

    protected void showLoadingView() {
        showLoadingView(LoadingView.STYLE_WITH_MSG, null);
    }

    protected void showLoadingView(int style) {
        showLoadingView(style, null);
    }

    protected void showLoadingView(String msg) {
        showLoadingView(LoadingView.STYLE_WITH_MSG, msg);
    }

    protected void showLoadingView(int style, String msg) {
        addLoadingView();
        mLoadingView.show(style, msg);
    }

    protected void dismissLoadingView() {
        dismissLoadingView(null);
    }

    protected void initLoadingViewParams() {
        if (mLoadingViewLayoutParams == null) {
            mLoadingViewLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            // mLoadingViewLayoutParams.addRule(RelativeLayout.BELOW,
            // R.id.titlebar);
        }
    }
}
