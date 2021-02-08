package com.example.phonecalltv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO 如果要关闭来电显示功能，这里添加开关，默认开启
        //TODO 可以加个登录认证模块，这样限制适用人群
    }
}