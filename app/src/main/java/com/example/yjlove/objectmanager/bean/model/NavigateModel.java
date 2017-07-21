package com.example.yjlove.objectmanager.bean.model;

public class NavigateModel {
	public static final int OBJECT_TAG = 0;//物品中心
	public static final int COUNT_TAG = 1;//统计中心
	public static final int MESSAGE_TAG = 2;//消息中心
	public static final int PERSON_TAG = 3;//我的


	private String mTitle = "";
	private int mIconIdRes = 0;
	private int mTag = OBJECT_TAG;

	public NavigateModel(String title, int icoRes, int tag) {
		mTitle = title;
		mIconIdRes = icoRes;
		mTag = tag;
	}

	public String getTitle() {
		return mTitle;
	}

	public int getIconResId() {
		return mIconIdRes;
	}

	public void setTag(int tag) {
		mTag = tag;
	}

	public int getTag() {
		return mTag;
	}
}
