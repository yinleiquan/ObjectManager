package com.example.yjlove.objectmanager.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.application.AppContext;
import com.example.yjlove.objectmanager.bean.entity.ObjectType;
import com.example.yjlove.objectmanager.bean.model.info.UpDataInfo;
import com.example.yjlove.objectmanager.constant.SPConstant;
import com.example.yjlove.objectmanager.greendao.gen.ObjectTypeDao;
import com.example.yjlove.objectmanager.http.NetUtil;
import com.example.yjlove.objectmanager.http.NetWorks;
import com.example.yjlove.objectmanager.popupwindow.CommonAffirmPopupWindow;
import com.example.yjlove.objectmanager.utils.SPUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

/**
 * 作者 YJlvoe
 * 创建时间 2017/7/3.
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_timer)
    TextView splashTimer;
    private Context mContext;
    private Intent mIntent;
    private boolean mIsFirstOpen = false;
    private String mVersionName;
    private UpDataInfo mUpDataInfo;

    private int mCount;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            splashTimer.setText(String.valueOf(msg.arg1));
        }
    };
    private ObjectTypeDao mObjectTypeDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mContext = AppContext.getInstance();
//        asycVersion();
        startTimer();
        // 判断是否是第一次开启应用
        mIsFirstOpen = (boolean) SPUtil.get(mContext, SPConstant.JUDGE_IS_FIRST_LOGIN, true);
        // 如果是第一次启动，则先进入功能引导页
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (mIsFirstOpen) {
//                    enterGuideActivity();
//                } else {
//                    enterLoginActivity();
//                }
                enterLoginActivity();
            }
        }, 3000);
        initDB();
    }

    private void startTimer() {
        mCount = 3;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.arg1 = mCount;
                mCount = mCount - 1;
                mHandler.sendMessage(msg);
            }
        }, 0, 995);
    }

    private void initDB() {
        mObjectTypeDao = AppContext.getInstance().getDaoSession().getObjectTypeDao();

        ObjectType type1 = new ObjectType();
        type1.setObjectType("服饰鞋包");
        ObjectType type2 = new ObjectType();
        type2.setObjectType("家居日用");
        ObjectType type3 = new ObjectType();
        type3.setObjectType("家电数码");
        ObjectType type4 = new ObjectType();
        type4.setObjectType("食材酒饮");

        List<ObjectType> types = new ArrayList<>();
        types.add(type1);
        types.add(type2);
        types.add(type3);
        types.add(type4);

        mObjectTypeDao.insertInTx(types);
    }

//    private void enterGuideActivity() {
//        mIntent = new Intent(mContext, GuideActivity.class);
//        this.startActivity(mIntent);
//        this.finish();
//    }

    private void enterLoginActivity() {
//        mIntent = new Intent(mContext, LoginActivity.class);
        mIntent = new Intent(mContext, MainActivity.class);
        startActivity(mIntent);
        finish();
    }

    private void asycVersion() {
        mVersionName = getVersionName();
        NetWorks.netUpData(new Observer<UpDataInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                ToastUtil.showShort(mContext, e.getMessage());
            }

            @Override
            public void onNext(UpDataInfo upDataInfo) {
                mUpDataInfo = upDataInfo;
                if (!upDataInfo.getVersion().equals(mVersionName)) {
                    //对话框通知用户升级程序
                    CommonAffirmPopupWindow updataPopupWindow =
                            new CommonAffirmPopupWindow(SplashActivity.this, R.id.activity_splash_layout, "更新提示", "发现新版本（" + upDataInfo.getVersion() + "）需要更新！");
                    updataPopupWindow.setOnAffirmCommonPopClickListener(new CommonAffirmPopupWindow.OnAffirmCommonPopClickListener() {
                        @Override
                        public void onOk() {
                            asycApp();
                        }

                        @Override
                        public void onCancel() {
                            SplashActivity.this.finish();
                        }
                    });
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            enterLoginActivity();
                        }
                    }, 2000);
                }
            }
        });
    }

    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionName;
    }

    private InputStream is = null;
    private byte[] buf = new byte[2048];
    private int len = 0;
    private File file = null;
    private FileOutputStream fos = null;
    private String downloadPath = Environment.getExternalStorageDirectory().toString() + "/download";
    private String fileName;
    private String appURL = "http://yjlove.top/netData/ObjectManager.apk";
    private ProgressDialog pd;    //进度条对话框
    private boolean isCancel = false;

    private void asycApp() {
        if (NetUtil.isConnected(mContext)) {
            pd = new ProgressDialog(this);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("下载中请等待");
            pd.setButton2("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    isCancel = true;
                    return;
                }
            });
            pd.show();

            DownLoadTask cv = new DownLoadTask();
            new Thread(cv).start();

        } else {
            Toast.makeText(getApplicationContext(), "未连接网络,无法下载更新！", Toast.LENGTH_SHORT).show();
        }
    }

    public class DownLoadTask implements Runnable {
        @Override
        public void run() {
            try {
                isCancel = false;

                downloadPath = Environment.getExternalStorageDirectory().toString() + "/download";
                File dir = new File(downloadPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                fileName = mUpDataInfo.getFtpApkFile();
                downloadPath += "/" + fileName;
                file = new File(downloadPath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();

                // 构造URL
                URL url = new URL(appURL);
                // 打开连接
                URLConnection con = url.openConnection();
                //获得文件的长度
                int contentLength = con.getContentLength();
                is = con.getInputStream();
                String fileSize = FormetFileSize(contentLength);
                pd.setProgressNumberFormat("0 /" + fileSize);
                fos = new FileOutputStream(file);
                long step = contentLength / 100;
                long process = 0;
                long currentSize = 0;

                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    currentSize += len;
                    //获取当前下载量
                    if (currentSize / step != process) {
                        process = currentSize / step;
                        if (process % 5 == 0) {  //每隔%5的进度返回一次
                            String processSize = FormetFileSize(currentSize);
                            pd.setProgressNumberFormat(processSize + "/" + fileSize);
                            pd.setProgress(Integer.valueOf(String.valueOf(process)));
                        }
                    }
                }
                fos.flush();
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }

                pd.dismiss(); //结束掉进度条对话框
                if (file != null) {
                    if (!isCancel) {
                        installApk(file);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                pd.dismiss(); //结束掉进度条对话框
                //createDownLoadFailPopupWindow();
            }
        }

    }

    //转换文件大小
    public String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + " B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + " K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + " M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + " G";
        }
        return fileSizeString;
    }

    //安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
