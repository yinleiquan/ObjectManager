package com.example.yjlove.objectmanager.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yjlove.objectmanager.R;


/**
 * Created by wxl on 2016-12-01.
 */
public class LoadingDialog {
    private Context mContext;
    private ImageView image;
    private Dialog waitDialog;
    private AnimationDrawable mLoadingAnimation;

    public LoadingDialog(Context pContext){
        mContext = pContext;
        waitDialog = new Dialog(mContext, R.style.common_dialog);
        waitDialog.setCanceledOnTouchOutside(false);
        //waitDialog.setCancelable(true);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_loading, null);
        waitDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        /**
         * 设置幕布，也就是本dialog的背景层 dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的
         * ，1.0f时候，背景全部变黑暗。
         *
         * 如果要达到背景全部变暗的效果，需要设置
         * dialog.getWindow().addFlags(WindowManager.LayoutParams
         * .FLAG_DIM_BEHIND); ，否则，背景无效果。
         */
//        Window window = waitDialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();

//        lp.dimAmount = 0.8f;
//        window.setAttributes(lp);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        // waitDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        /**
         * 设置透明度，主要设置的是dialog自身的透明度
         */
//        loading_pic_bigView = (ImageView) waitDialog.findViewById(R.id.loading_progress);
//        loading_pic_bigView.setAlpha(0.6f);
//        image = (ImageView) waitDialog.findViewById(R.id.loading_pic_big);
//        mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.loading);

        image = (ImageView)view.findViewById(R.id.loading_logo_progress);
        image.post(new Runnable() {
            @Override
            public void run() {
                mLoadingAnimation = (AnimationDrawable) image.getBackground();
                mLoadingAnimation.start();
            }
        });

    }

    public void show() {
//        image.startAnimation(mAnimation);
        waitDialog.show();
    }

    public void close() {
        waitDialog.dismiss();
    }

    //用于网络请求中断操作
    public void setOnDismissListener(
            DialogInterface.OnDismissListener dismissListener) {
        waitDialog.setOnDismissListener(dismissListener);
    }
}
