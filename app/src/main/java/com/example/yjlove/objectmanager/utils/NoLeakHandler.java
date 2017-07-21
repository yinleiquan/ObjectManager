package com.example.yjlove.objectmanager.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public class NoLeakHandler implements NoLeakHandlerInterface {
	private final NoLeakHandlerInterface _host;
	private final WeakRefHandler _handler;

	public NoLeakHandler() {
		_host = this;
		_handler = new WeakRefHandler(_host);
	}

	public NoLeakHandler(NoLeakHandlerInterface host) {
		_host = host;
		_handler = new WeakRefHandler(_host);
	}

	public NoLeakHandler(Looper looper) {
		_host = this;
		_handler = new WeakRefHandler(looper, _host);
	}

	public NoLeakHandler(Looper looper, NoLeakHandlerInterface host) {
		_host = host;
		_handler = new WeakRefHandler(looper, _host);
	}

	public final Handler handler() {
		return _handler;
	}

	private final NoLeakHandlerInterface innerHandler() {
		return _host;
	}

	public final void removeMessages(int what) {
		handler().removeMessages(what);
	}

	public final boolean sendEmptyMessageDelayed(int what, long delayMillis) {
		return handler().sendEmptyMessageDelayed(what, delayMillis);
	}

	public final boolean sendEmptyMessageDelayedWithRef(int what,
			long delayMillis) {
		Message msg = Message.obtain(handler(), what, innerHandler());
		return handler().sendMessageDelayed(msg, delayMillis);
	}

	public final Message obtainMessage(int what, int arg1, int arg2) {
		return handler().obtainMessage(what, arg1, arg2);
	}

	public final boolean sendMessage(Message msg) {
		return handler().sendMessage(msg);
	}

	public final boolean sendEmptyMessage(int what) {
		return handler().sendEmptyMessage(what);
	}

	public final void removeCallbacksAndMessages(Object token) {
		handler().removeCallbacksAndMessages(token);
	}

	public final boolean hasMessages(int what) {
		return handler().hasMessages(what);
	}

	public final Message obtainMessage() {
		return handler().obtainMessage();
	}

	public final Message obtainMessage(int what) {
		return handler().obtainMessage(what);
	}

	public final Message obtainMessage(int what, Object obj) {
		return handler().obtainMessage(what, obj);
	}

	public final Message obtainMessage(int what, int arg1, int arg2, Object obj) {
		return handler().obtainMessage(what, arg1, arg2, obj);
	}

	public final Looper getLooper() {
		return handler().getLooper();
	}

	public final boolean sendMessageDelayed(Message msg, long delayMillis) {
		return handler().sendMessageDelayed(msg, delayMillis);
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void handleMessage(Message msg) {
	}

	private static class WeakRefHandler extends Handler {
		private final WeakReference<NoLeakHandlerInterface> _host;

		public WeakRefHandler(NoLeakHandlerInterface host) {
			_host = new WeakReference<NoLeakHandlerInterface>(host);
		}

		public WeakRefHandler(Looper looper, NoLeakHandlerInterface host) {
			super(looper);
			_host = new WeakReference<NoLeakHandlerInterface>(host);
		}

		@Override
		public void handleMessage(Message msg) {
			NoLeakHandlerInterface host = (null != _host) ? _host.get() : null;
			if (null != host && host.isValid()) {
				host.handleMessage(msg);
			}
		}
	};
}
