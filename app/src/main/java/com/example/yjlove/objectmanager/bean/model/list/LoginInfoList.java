package com.example.yjlove.objectmanager.bean.model.list;

import com.example.yjlove.objectmanager.bean.model.info.LoginInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wxl on 2016-12-02.
 */
public class LoginInfoList implements Serializable{
    private List<LoginInfo> datas;

    public List<LoginInfo> getDatas() {
        return datas;
    }

    public void setDatas(List<LoginInfo> datas) {
        this.datas = datas;
    }
}
