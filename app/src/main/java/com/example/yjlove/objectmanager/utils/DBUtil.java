package com.example.yjlove.objectmanager.utils;

import com.example.yjlove.objectmanager.application.AppContext;
import com.example.yjlove.objectmanager.bean.entity.ObjectType;
import com.example.yjlove.objectmanager.greendao.gen.ObjectTypeDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： YJLove
 * 制作日期：2017/7/21.
 */
public class DBUtil {

    private static ObjectTypeDao mObjectTypeDao;// 取出类型

    public static ArrayList<String> getTypes(){
        mObjectTypeDao = AppContext.getInstance().getDaoSession().getObjectTypeDao();
        List<ObjectType> objectTypes = mObjectTypeDao.loadAll();
        ArrayList<String> types = new ArrayList<>();
        for (int i = 0; i < objectTypes.size(); i++) {
            if (!types.contains(objectTypes.get(i).getObjectType())) {
                types.add(objectTypes.get(i).getObjectType());
            }
        }
        return types;
    }
}
