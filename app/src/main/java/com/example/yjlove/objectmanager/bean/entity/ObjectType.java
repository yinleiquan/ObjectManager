package com.example.yjlove.objectmanager.bean.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者： YJLove
 * 制作日期：2017/7/21.
 */
@Entity
public class ObjectType {
    @Id
    private Long id;
    @NotNull
    @Property
    private String objectType;
    public String getObjectType() {
        return this.objectType;
    }
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1458840794)
    public ObjectType(Long id, @NotNull String objectType) {
        this.id = id;
        this.objectType = objectType;
    }
    @Generated(hash = 1065611956)
    public ObjectType() {
    }
}
