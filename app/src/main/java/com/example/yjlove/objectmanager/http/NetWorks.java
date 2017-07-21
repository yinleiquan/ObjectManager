package com.example.yjlove.objectmanager.http;

import com.example.yjlove.objectmanager.bean.model.info.LoginInfo;
import com.example.yjlove.objectmanager.bean.model.info.UpDataInfo;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者 YJlvoe
 * 创建时间 2017/7/3.
 */
public class NetWorks extends RetrofitUtil {
    //创建实现接口调用
    protected static final NetService service = getRetrofit().create(NetService.class);

    //设缓存有效期为1天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，使用缓存
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    /** --------------------- 网络请求 ---------------------------------*/
//    // 登录
//    public static void netLogin(String user, String password, Observer<LoginInfoVo> observer) {
//        setSubscribe(service.netLoginPostService(user, password), observer);
//    }
//
//    // 定位指定的飞机
//    public static void netFlightLocation(String uavNumber, Observer<MonitorUavFlightInfoVo> observer) {
//        setSubscribe(service.netFlightLocationService(uavNumber), observer);
//    }
//
//    // 实时监控-当前飞机的实时飞行轨迹
//    public static void netMonitorUavAirLine(String boxNo, Observer<MonitorUavAirLineVo> observer) {
//        setSubscribe(service.netMonitorUavAirLineService(boxNo), observer);
//    }
//
//    // 查询在所有飞行和闲置的飞机
//    public static void netMonitorList(Observer<MonitorUavListVo> observer) {
//        setSubscribe(service.netMonitorListService(), observer);
//    }

    // 登录
    public static void netLogin(Observer<LoginInfo> observer) {
        setSubscribe(service.netLoginService(), observer);
    }
    // 更新
    public static void netUpData(Observer<UpDataInfo> observer) {
        setSubscribe(service.netUpDateService(), observer);
    }
    /** --------------------- 网络请求 ---------------------------------*/

    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
}
