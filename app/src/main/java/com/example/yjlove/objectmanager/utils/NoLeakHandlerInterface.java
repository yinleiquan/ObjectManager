package com.example.yjlove.objectmanager.utils;

import android.os.Message;

public interface NoLeakHandlerInterface {
	public boolean isValid();

	public void handleMessage(Message msg);
}
