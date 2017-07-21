package com.example.yjlove.objectmanager.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yjlove.objectmanager.R;

public class LoadingView extends RelativeLayout {

	@SuppressWarnings("unused")
	private final static String TAG = LoadingView.class.getSimpleName();

	public final static int STYLE_WITHOUT_MSG = 0;
	public final static int STYLE_WITH_MSG = 1;
	public final static int STYLE_SIMPLE = 2;
	public final static int STYLE_SIMPLE_WITH_BG = 3;

	// views
	private AnimationDrawable mLoadingAnimation;
	private ImageView mProgressView;
	private ImageView mLoadingIcon;
	private TextView mLoadingMsgView;

	// data
	private String mLoadingMsg = "";

	public LoadingView(Context context) {
		super(context);
		setupViews();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}

	public LoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupViews();
	}

	private void setupViews() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.view_loading, this, true);
//		mLoadingIcon = (ImageView) viewGroup.findViewById(R.id.loading_logo);
		mProgressView = (ImageView) viewGroup.findViewById(R.id.loading_logo_progress);
//		mLoadingMsgView = (TextView) viewGroup.findViewById(R.id.loading_msg);
		mProgressView.post(new Runnable() {
			@Override
			public void run() {
				mLoadingAnimation = (AnimationDrawable) mProgressView.getBackground();
				mLoadingAnimation.start();
			}
		});
	}

	public void show() {
//		show(STYLE_WITH_MSG, null);
		setVisibility(View.VISIBLE);
	}

	public void show(int style) {
		show(style, null);
	}

	public void show(String msg) {
		show(STYLE_WITH_MSG, msg);
	}

	public void show(int style, String msg) {
		if (msg == null) {
			msg = mLoadingMsg;
		}

		setVisibility(View.VISIBLE);

		switch (style) {
//		case STYLE_WITHOUT_MSG:
//			mLoadingIcon.setVisibility(View.GONE);
//			mLoadingMsgView.setVisibility(View.GONE);
////			setBackgroundResource(R.drawable.frame_bg);
//			break;
//
//		case STYLE_WITH_MSG:
//			mLoadingIcon.setVisibility(View.GONE);
//			mLoadingMsgView.setText(msg);
//			mLoadingMsgView.setVisibility(View.VISIBLE);
////			setBackgroundResource(R.drawable.frame_bg);
//			break;
//
//		case STYLE_SIMPLE:
//			mLoadingIcon.setVisibility(View.GONE);
//			mLoadingMsgView.setVisibility(View.GONE);
//			setBackgroundResource(R.color.transparent);
//			break;
//
//		case STYLE_SIMPLE_WITH_BG:
//			mLoadingIcon.setVisibility(View.GONE);
//			mLoadingMsgView.setVisibility(View.GONE);
//			setBackgroundColor(0x20000000);
//			break;
		}

//		mLoadingIcon.post(new Runnable() {
//			@Override
//			public void run() {
//				mLoadingAnimation = (AnimationDrawable) (AnimationDrawable) mProgressView
//						.getBackground();
//				mLoadingAnimation.start();
//			}
//		});
	}

	public void hide() {
		if (mLoadingAnimation != null) {
			mLoadingAnimation.stop();
			mLoadingAnimation = null;
		}
		setVisibility(View.GONE);
	}

	public void setText(String msg) {
//		mLoadingMsg = msg;
//		mLoadingMsgView.setText(msg);
	}

	public void setText(int resId) {
//		mLoadingMsg = getContext().getString(resId);
//		mLoadingMsgView.setText(mLoadingMsg);
	}

	public void setTextColor(int color) {
//		mLoadingMsgView.setText(color);
	}

	public void setTextSize(float size) {
//		mLoadingMsgView.setTextSize(size);
	}

	public void setTextSize(int unit, float size) {
//		mLoadingMsgView.setTextSize(unit, size);
	}
}
