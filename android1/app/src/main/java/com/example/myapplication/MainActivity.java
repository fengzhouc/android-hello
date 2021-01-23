package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //数据提交给下一个activity
    public void submit(View v){
        EditText name = findViewById(R.id.name);
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("name", name.getText().toString());

        startActivity(intent);

    }

    //从启动的activity中返回数据给前一个activity，即当前的
    public void submit2(View v){
        EditText name = findViewById(R.id.name);
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("name", name.getText().toString());

        startActivityForResult(intent, 1);

    }

    /**
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     *
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String name = data.getStringExtra("name");
        System.out.println(name);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }
}