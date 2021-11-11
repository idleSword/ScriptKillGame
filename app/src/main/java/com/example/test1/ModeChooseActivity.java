package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class ModeChooseActivity extends AppCompatActivity {

    private RadioGroup mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_choose);
        mode =findViewById(R.id.mode_choose);
    }

    public void modeChose(View view){
        int modeId= mode.getCheckedRadioButtonId();
        switch (modeId)
        {
            case R.id.script_kill:
                Intent intent_spkl=new Intent(ModeChooseActivity.this,ScriptChooseActivity.class);
                this.finish();
                startActivity(intent_spkl);
                break;
            case R.id.among_us:
                Intent intent_amus=new Intent(ModeChooseActivity.this,AmusSceneChooseActivity.class);
                this.finish();
                startActivity(intent_amus);
                break;
        }
    }
}