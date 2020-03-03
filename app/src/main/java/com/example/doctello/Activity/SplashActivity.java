package com.example.doctello.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.doctello.R;
import com.example.doctello.Utils.prefUtils;

public class SplashActivity extends AppCompatActivity {

    prefUtils pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pref = new prefUtils(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){

                if(pref.isLoggedIn()){
                    Intent intent = new Intent(SplashActivity.this , MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this , LoginSignupActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },1500);
    }
}
