package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private String usr,psw,confirmPsw;
    private EditText et_usr,et_psw,et_confPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Init();
    }

    private void Init(){
        et_usr=findViewById(R.id.edtt_usr);
        et_psw=findViewById(R.id.edtt_psw);
        et_confPsw=findViewById(R.id.edtt_psw_confirm);
    }

    public void register(View view) {
        usr = et_usr.getText().toString().trim();
        psw = et_psw.getText().toString().trim();
        confirmPsw = et_confPsw.getText().toString().trim();
        if (TextUtils.isEmpty(usr)) {
            Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(psw)) {
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(confirmPsw)) {
            Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
        } else if (!psw.equals(confirmPsw)) {
            Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
        } else if (isExistUserName(usr)) {
            Toast.makeText(RegisterActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

            saveRegisterInfo(usr, psw);
            //注册成功后把账号传递到LoginActivity.java中
            // 返回值到loginActivity显示
            Intent data = new Intent();
            data.putExtra("usr", usr);
            setResult(RESULT_OK, data);
            //RESULT_OK为Activity系统常量，状态码为-1，
            // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
            RegisterActivity.this.finish();
        }
    }

    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw=sp.getString(usr,"");//传入用户名获取密码
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }
    /**
     * 保存账号和密码到SharedPreferences中SharedPreferences
     */
    private void saveRegisterInfo(String userName,String psw){
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(usr, psw);
        editor.apply();
    }
}