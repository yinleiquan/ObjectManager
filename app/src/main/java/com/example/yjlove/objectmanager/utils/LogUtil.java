package com.example.yjlove.objectmanager.utils;

import android.util.Log;

/**
 * 作者 YJlvoe
 * 创建时间 2017/6/30.
 */
public class LogUtil {

    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean showLog = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化

    public static void i(Object objTag, Object objMsg) {
        if (showLog) {
            String tag = convertStringTag(objTag);
            String msg = convertStringMsg(objMsg);
            Log.i(tag, msg);
        }
    }

    public static void e(Object objTag, Object objMsg, Throwable error) {
        if (showLog) {
            String tag = convertStringTag(objTag);
            String msg = convertStringMsg(objMsg);
            Log.e(tag, msg, error);
        }
    }

    public static void d(Object objTag, Object objMsg) {
        if (showLog) {
            String tag = convertStringTag(objTag);
            String msg = convertStringMsg(objMsg);
            Log.d(tag, msg);
        }
    }

    public static void v(Object objTag, Object objMsg) {
        if (showLog) {
            String tag = convertStringTag(objTag);
            String msg = convertStringMsg(objMsg);
            Log.v(tag, msg);
        }
    }

    /** 把一个Object类型的Tag转换为String类型的Tag */
    private static String convertStringTag(Object objTag) {
        String tag;
        if (objTag == null) {
            tag = "null";
        } else if (objTag instanceof String) {	// 如果是String类型，则直接强转
            tag = (String) objTag;
        } else if (objTag instanceof Class) {	// 如果是字节，则取类名
            tag = ((Class<?>) objTag).getSimpleName();	// 取类名
        } else {
            tag = objTag.getClass().getSimpleName();// 取类名
        }
        return tag;
    }

    /** 把一个Object类型的Msg转换为String类型的Msg */
    private static String convertStringMsg(Object objMsg) {
        String msg;
        if (objMsg == null) {
            msg = "null";
        } else {
            msg = objMsg.toString();
        }
        return msg;
    }
}
