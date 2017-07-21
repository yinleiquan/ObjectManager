package com.example.yjlove.objectmanager.bean.model.vo;

import com.example.yjlove.objectmanager.bean.model.list.LoginInfoList;

import java.io.Serializable;

/**
 * 作者 YJlvoe
 * 创建时间 2017/6/19.
 */
public class LoginInfoListVo implements Serializable {
    private String resCode;//0失败，1成功
    private String resDesc;//描述
    private LoginInfoList result;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public LoginInfoList getResult() {
        return result;
    }

    public void setResult(LoginInfoList result) {
        this.result = result;
    }
}
