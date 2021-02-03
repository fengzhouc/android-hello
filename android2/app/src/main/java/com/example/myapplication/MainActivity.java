package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.btnbind);
        stop = (Button) findViewById(R.id.btnstop);

        //创建启动Service的Intent,以及Intent属性
        final Intent intent = new Intent();
        intent.setAction("com.example.myapplication.MyService");
        //fix: Service Intent must be explicit, 需要将隐含意图转换为显示的意图，这里为什么要转换呢？？？
        intent.setPackage("com.example.myapplication");
        //为两个按钮设置点击事件,分别是启动与停止service
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);

            }
        });

    }
    public void submit(View v){

        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);

    }

    public void submit1(View v){

        Intent intent = new Intent(this, IntentServiceActivity3.class);
        startActivity(intent);

    }
}