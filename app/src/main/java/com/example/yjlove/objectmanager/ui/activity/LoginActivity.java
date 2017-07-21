package com.example.yjlove.objectmanager.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.yjlove.objectmanager.R;
import com.example.yjlove.objectmanager.base.BaseActivity;
import com.example.yjlove.objectmanager.bean.model.info.LoginInfo;
import com.example.yjlove.objectmanager.constant.SPConstant;
import com.example.yjlove.objectmanager.http.NetWorks;
import com.example.yjlove.objectmanager.utils.SPUtil;
import com.example.yjlove.objectmanager.utils.StringUtil;
import com.example.yjlove.objectmanager.utils.ToastUtil;

import butterknife.BindView;
import rx.Observer;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.activity_monitor_login_default_user_ico)
    ImageView activityMonitorLoginDefaultUserIco;
    @BindView(R.id.activity_monitor_login_user_name_edit_text)
    AutoCompleteTextView activityMonitorLoginUserNameEditText;
    @BindView(R.id.activity_monitor_login_user_name_clean)
    ImageView activityMonitorLoginUserNameClean;
    @BindView(R.id.activity_monitor_login_user_password_edit_text)
    EditText activityMonitorLoginUserPasswordEditText;
    @BindView(R.id.activity_monitor_login_user_password_clean)
    ImageView activityMonitorLoginUserPasswordClean;
    @BindView(R.id.activity_monitor_login_send_button)
    Button activityMonitorLoginSendButton;
    @BindView(R.id.activity_monitor_login_remember_password_checkbox)
    CheckBox activityMonitorLoginRememberPasswordCheckbox;
    @BindView(R.id.activity_monitor_login_layout)
    RelativeLayout activityMonitorLoginLayout;

    private int nearHistory = 3;

    @Override
    protected int getViewLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        activityMonitorLoginSendButton.setOnClickListener(LoginClickListener);//登录
        activityMonitorLoginUserNameClean.setOnClickListener(LoginClickListener);
        activityMonitorLoginUserPasswordClean.setOnClickListener(LoginClickListener);
        cleanEdit();
        initUserNameAndPassword();
        initHistory();
        initEvent();
    }

    private void cleanEdit() {
        activityMonitorLoginUserNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty() || s.length() == 0) {
                    activityMonitorLoginUserNameClean.setVisibility(View.GONE);
                } else {
                    activityMonitorLoginUserNameEditText.setSelection(s.length());
                    activityMonitorLoginUserNameClean.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityMonitorLoginUserPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty() || s.length() == 0) {
                    activityMonitorLoginUserPasswordClean.setVisibility(View.GONE);
                } else {
                    activityMonitorLoginUserPasswordClean.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initUserNameAndPassword() {
        String mUserName = (String) SPUtil.get(mContext, SPConstant.LOGIN_USER, "");
        String mPassWord = (String) SPUtil.get(mContext, SPConstant.LOGIN_PASSWORD, "");
        Boolean judgeIsRememberPassword = (Boolean) SPUtil.get(mContext, SPConstant.JUDGE_IS_REMEMBER_PASSWORD, false);
        activityMonitorLoginUserNameEditText.setText(mUserName);
        if (judgeIsRememberPassword) {
            activityMonitorLoginUserPasswordEditText.setText(mPassWord);
            activityMonitorLoginRememberPasswordCheckbox.setChecked(true);
        } else {
            activityMonitorLoginUserPasswordEditText.setText("");
            activityMonitorLoginRememberPasswordCheckbox.setChecked(false);
        }
    }

    private void saveUserNameAndPassword(String username, String password) {
        SPUtil.put(mContext, SPConstant.LOGIN_USER, username);
        SPUtil.put(mContext, SPConstant.LOGIN_PASSWORD, password);
        if (activityMonitorLoginRememberPasswordCheckbox.isChecked()) {
            SPUtil.put(mContext, SPConstant.JUDGE_IS_REMEMBER_PASSWORD, true);
        } else {
            SPUtil.put(mContext, SPConstant.JUDGE_IS_REMEMBER_PASSWORD, false);
        }
    }

    private void initHistory() {
        String history = (String) SPUtil.get(mContext, SPConstant.LOGIN_REMEMBER_USER, "");
        String[] hisArrays = history.split(",");
        ArrayAdapter<String> hisAdapter = new ArrayAdapter<>(this, R.layout.view_item_auto_text, hisArrays);
        if (hisArrays.length > nearHistory) {
            String[] newArrays = new String[nearHistory];
            System.arraycopy(hisArrays, 0, newArrays, 0, nearHistory);
            hisAdapter = new ArrayAdapter<>(this, R.layout.view_item_auto_text, newArrays);
        }
        activityMonitorLoginUserNameEditText.setAdapter(hisAdapter);
        activityMonitorLoginUserNameEditText.setThreshold(1);
        activityMonitorLoginUserNameEditText.setCompletionHint(" ");
    }

    private void initEvent() {
        activityMonitorLoginUserNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                AutoCompleteTextView view = (AutoCompleteTextView) v;
                if (hasFocus) {
                    String history = (String) SPUtil.get(mContext, SPConstant.LOGIN_REMEMBER_USER, "");
                    if (!TextUtils.isEmpty(history))
                        view.showDropDown();
                }
            }
        });
    }

    private void saveHistory(String autoTv) {
        String history = (String) SPUtil.get(mContext, SPConstant.LOGIN_REMEMBER_USER, "");
        if (!TextUtils.isEmpty(autoTv)) {
            if (history.contains(autoTv + ",")) {
                history = history.replaceAll(autoTv + ",", "");
            }
            StringBuilder sb = new StringBuilder(history);
            sb.insert(0, autoTv + ",");
            SPUtil.put(mContext, SPConstant.LOGIN_REMEMBER_USER, sb.toString());
        } else {
        }
    }

    //点击事件
    private OnClickListener LoginClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.activity_monitor_login_user_name_clean:
                    if (!activityMonitorLoginUserNameEditText.getText().toString().isEmpty()) {
                        activityMonitorLoginUserNameEditText.setText("");
                    }
                    break;
                case R.id.activity_monitor_login_user_password_clean:
                    if (!activityMonitorLoginUserPasswordEditText.getText().toString().isEmpty()) {
                        activityMonitorLoginUserPasswordEditText.setText("");
                    }
                    break;
                case R.id.activity_monitor_login_send_button:
                    //登录
//                    OkHttpClient okHttpClient = OkHttp3Util.getOkHttpClient();
//                    Request.Builder requestBuilder = new Request.Builder().url("http://yjlove.top/2017/03/09/博客搭建/login.json");
//                    requestBuilder.method("GET",null);
//                    Request request = requestBuilder.build();
//                    Call mcall= okHttpClient.newCall(request);
//                    mcall.enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            ToastUtil.showShort(mContext, "网络未连接" + e.getMessage());
//                            LogUtil.i("LoginActivity", e.getMessage());
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            //JsonUtil.json2Bean()
//                            ToastUtil.showShort(mContext, response.body().toString());
//                            LogUtil.i("LoginActivity", response.body().string());
//                        }
//                    });

                    NetWorks.netLogin(new Observer<LoginInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
//                            ToastUtil.showShort(mContext, "网络未连接");
                        }

                        @Override
                        public void onNext(LoginInfo loginInfo) {
                            if (isAllowLogin()) {
                                String userName = activityMonitorLoginUserNameEditText.getText().toString().trim();
                                String password = activityMonitorLoginUserPasswordEditText.getText().toString().trim();
                                loginServer(userName, password, loginInfo);
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    private boolean isAllowLogin() {
        boolean result = false;
        if (StringUtil.isEmpty(activityMonitorLoginUserNameEditText.getText().toString().trim())) {
            ToastUtil.showShort(mContext, "请输入账号");
            return result;
        }
        if (StringUtil.isEmpty(activityMonitorLoginUserPasswordEditText.getText().toString().trim())) {
            ToastUtil.showShort(mContext, "请输入密码");
            return result;
        }
        result = true;
        return result;
    }

    private void loginServer(final String username, final String password, LoginInfo loginInfo) {
        mLoadingDialog.show();

        if (username.equals(loginInfo.getUserName()) && password.equals(loginInfo.getPassword())){
            mLoadingDialog.close();
            saveHistory(username);//记住登录账号
            saveUserNameAndPassword(username, password);
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        } else {
            ToastUtil.showShort(mContext, "请输入正确的用户名或密码！");
            mLoadingDialog.close();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
