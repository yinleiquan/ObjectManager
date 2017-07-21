package com.example.yjlove.objectmanager.bean.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者： YJLove
 * 制作日期：2017/7/17.
 */
@Entity
public class MyObject {
    @Id
    private Long id;
    @Property
    @NotNull
    private String objectName;
    @NotNull
    private String objectType;
    @NotNull
    private String inputDate;
    @NotNull
    private int objectCount;

    private String outputDate;
    private String note;

    public String getOutputDate() {
        return this.outputDate;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }

    public String getInputDate() {
        return this.inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getObjectType() {
        return this.objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectName() {
        return this.objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getObjectCount() {
        return this.objectCount;
    }

    public void setObjectCount(int objectCount) {
        this.objectCount = objectCount;
    }

    @Generated(hash = 1227957030)
    public MyObject(Long id, @NotNull String objectName,
            @NotNull String objectType, @NotNull String inputDate, int objectCount,
            String outputDate, String note) {
        this.id = id;
        this.objectName = objectName;
        this.objectType = objectType;
        this.inputDate = inputDate;
        this.objectCount = objectCount;
        this.outputDate = outputDate;
        this.note = note;
    }

    @Generated(hash = 91472123)
    public MyObject() {
    }
}
