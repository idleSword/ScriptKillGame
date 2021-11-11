package com.example.test1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String usr,psw,matchPsw;
    private EditText et_usr,et_psw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }

    private void Init(){
        et_usr=findViewById(R.id.edtt_usr);
        et_psw=findViewById(R.id.edtt_psw);
    }

    public void login(View view){//登录按钮点击事件
        usr=et_usr.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        matchPsw=readPsw(usr);
        if(TextUtils.isEmpty(usr)){
            Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(psw)){
            Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
        }
        else if(psw.equals(matchPsw)){
            saveLoginInfo(true, usr);
            Intent data =new Intent();
            data.putExtra("isLogin", true);
            setResult(RESULT_OK,data);
            MainActivity.this.finish();
            startActivity(new Intent(MainActivity.this,ModeChooseActivity.class));
        }
        else if(matchPsw!=null&&!TextUtils.isEmpty(matchPsw)&&!psw.equals(matchPsw))
            Toast.makeText(MainActivity.this, "用户名与密码不一致", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
    }

    private String readPsw(String usr){//从loginInfo数据文件中读取密码
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(usr, "");
    }

    private void saveLoginInfo(boolean status,String usr){//保存登录数据
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin", status);
        editor.putString("loginUsr", usr);
        editor.apply();
    }

    public void register(View view){//注册按钮点击事件
        Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String usr=data.getStringExtra("usr");
            if(!TextUtils.isEmpty(usr)){
                et_usr.setText(usr);
                et_usr.setSelection(usr.length());
            }
        }
    }
}