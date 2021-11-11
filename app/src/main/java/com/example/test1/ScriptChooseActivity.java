package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class ScriptChooseActivity extends AppCompatActivity {

    private RadioGroup rg_script;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_choose);
        rg_script=findViewById(R.id.script_choose);
    }

    public void scriptChose(View view){
        int scriptId=rg_script.getCheckedRadioButtonId();
        /*switch (scriptId)
        {
            case R.id.script0:
                Intent intent_script0=new Intent(this,RoomActivity.class);
                this.finish();
                startActivity(intent_script0);
                break;
            case R.id.script1:
                Intent intent_script1=new Intent(this,RoomActivity.class);
                this.finish();
                startActivity(intent_script1);
                break;
        }*/
        Intent intent=new Intent(this, PlayerActivity.class);
        this.finish();
        startActivity(intent);
        System.out.println("打开界面");
    }
}