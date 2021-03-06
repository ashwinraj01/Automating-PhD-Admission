package com.winash.uniapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView Register,Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Register=(TextView) findViewById(R.id.RegisterMain);
        Login=(TextView) findViewById(R.id.LoginMain);
        Register.setOnClickListener(this);
        Login.setOnClickListener(this);
    }
        public void onClick(View v){
            switch (v.getId()){
                case R.id.RegisterMain:
                    startActivity(new Intent(this,RegisterUser.class));
                    break;
                case R.id.LoginMain:
                    startActivity(new Intent(this,LoginUser.class));
                    break;
            }
        }
}