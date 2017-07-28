package com.example.yjlove.objectmanager.js;

import android.webkit.JavascriptInterface;

import com.example.yjlove.objectmanager.application.AppContext;
import com.example.yjlove.objectmanager.bean.model.jsonbean.CountInfo;
import com.example.yjlove.objectmanager.utils.ToastUtil;
import com.google.gson.Gson;

/**
 * 作者： YJLove
 * 制作日期：2017/7/24.
 */
public class JavaScripService {

    @JavascriptInterface
    public void showToast(String msg){
        ToastUtil.showShort(AppContext.getInstance(), msg);
    }

    @JavascriptInterface
    public String setChatData(){
        String[] datas = new String[]{"衬衫","羊毛衫","A","裤子","高跟鞋","袜子"};
        String str = new Gson().toJson(new CountInfo(datas));
        return str;
    }
}
