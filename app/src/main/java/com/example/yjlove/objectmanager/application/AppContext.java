package com.example.yjlove.objectmanager.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.yjlove.objectmanager.greendao.gen.DaoMaster;
import com.example.yjlove.objectmanager.greendao.gen.DaoSession;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * 作者 YJlvoe
 * 创建时间 2016/11/30.
 */
public class AppContext extends Application {

    private static AppContext instance = null;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private PushAgent mPushAgent;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setDatabase();
        setUmeng();
    }

    public static synchronized AppContext getInstance() {
        return instance;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * 设置友盟统计
     */
    private void setUmeng() {
        mPushAgent = PushAgent.getInstance(instance);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
//                LogUtil.i("AppContext", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
//                LogUtil.i("AppContext", s + s1);
            }
        });
    }


    /*private AppContext(){
        this.mContext = getApplicationContext();
    }
    public static synchronized AppContext getInstance() {
        if (instance == null){
            instance = new AppContext();
        }
        return instance;
    }*/

}
