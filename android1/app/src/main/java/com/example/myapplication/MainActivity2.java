package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        EditText e = findViewById(R.id.name);

        setContentView(R.layout.activity_main2);
//        e.setText(name);
        System.out.println(name);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

    }

    public void submit(View v){
        EditText name = findViewById(R.id.name);
        //一个空的intent，只传输数据，不需要制定activity
        Intent intent = new Intent();
        intent.putExtra("name", name.getText().toString());

        //设置返回数据
        MainActivity2.this.setResult(RESULT_OK, intent);
        //关闭Activity
        MainActivity2.this.finish();
//        startActivity(intent);

    }

}