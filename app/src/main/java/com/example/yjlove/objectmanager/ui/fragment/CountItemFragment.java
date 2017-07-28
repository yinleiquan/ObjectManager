package com.example.yjlove.objectmanager.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.application.AppContext;
import com.example.yjlove.objectmanager.base.BaseFragment;
import com.example.yjlove.objectmanager.greendao.gen.MyObjectDao;
import com.example.yjlove.objectmanager.js.JavaScripService;

import butterknife.BindView;

/**
 * 作者： YJLove
 * 制作日期：2017/7/21.
 */
public class CountItemFragment extends BaseFragment {

    @BindView(R.id.count_item_web_view)
    WebView countItemWebView;

    private String mTitle;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_count_item;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        MyObjectDao mMyObjectDao = AppContext.getInstance().getDaoSession().getMyObjectDao();
        Bundle args = getArguments();
        mTitle = args.getString("title");
//        countItemTitle.setText(mTitle);

        //进行webwiev的一堆设置
        //开启本地文件读取（默认为true，不设置也可以）
        countItemWebView.getSettings().setAllowFileAccess(true);
        //设置编码
        countItemWebView.getSettings().setDefaultTextEncodingName("utf-8");
        // 设置可以支持缩放
        countItemWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        countItemWebView.getSettings().setBuiltInZoomControls(false);
        // 清除浏览器缓存
        countItemWebView.clearCache(true);
        // 开启脚本支持
        countItemWebView.getSettings().setJavaScriptEnabled(true);

        // android向js传递方法
        JavaScripService jsInterface = new JavaScripService();
        countItemWebView.addJavascriptInterface(jsInterface, "test");

        // 获取Assets目录下的文件
        countItemWebView.loadUrl("file:///android_asset/echart/index.html");

//        String[] datas = new String[]{"衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"};
//        final String str = new Gson().toJson(new CountInfo(datas));

        //在当前页面打开链接了
        countItemWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                countItemWebView.loadUrl("javascript:showChart("+ str +");");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                countItemWebView.loadUrl("javascript:showChart("+ str +");");
            }
        });
        //js加上这个就好啦！
        countItemWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            countItemWebView.evaluateJavascript("javascript:showChart("+ str +");", new ValueCallback<String>() {
//                @Override
//                public void onReceiveValue(String value) {
//                    //此处为 js 返回的结果
//                }
//            });
//        } else {
//            countItemWebView.loadUrl("javascript:showChart("+ str +");");
//        }
    }
}
