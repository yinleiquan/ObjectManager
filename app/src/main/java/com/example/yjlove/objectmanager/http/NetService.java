package com.example.yjlove.objectmanager.http;

import com.example.yjlove.objectmanager.bean.model.info.LoginInfo;
import com.example.yjlove.objectmanager.bean.model.info.UpDataInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 作者 YJlvoe
 * 创建时间 2017/7/3.
 */
public interface NetService {

//    //POST请求
//    @FormUrlEncoded
//    @POST("register/login.jspx")
//    Observable<LoginInfoVo> netLoginPostService(@Field("loginname") String loginName, @Field("password") String password);
//
//    //POST请求
//    @FormUrlEncoded
//    @POST("location/uavJK/queryByuavnumber.jspx")
//    Observable<MonitorUavFlightInfoVo> netFlightLocationService(@Field("uavnumber") String uavNumber);
//
//    //POST请求
//    @FormUrlEncoded
//    @POST("location/uavJK/queryUavRealRoute.jspx")
//    Observable<MonitorUavAirLineVo> netMonitorUavAirLineService(@Field("boxno") String boxNo);
//
//    //GET请求（无参）
//    @GET("location/uavJK/queryAllUavList.jspx")
//    Observable<MonitorUavListVo> netMonitorListService();

    //GET请求（无参）
    @GET("netData/login.json")
    Observable<LoginInfo> netLoginService();

    @GET("netData/updata.json")
    Observable<UpDataInfo> netUpDateService();
}
