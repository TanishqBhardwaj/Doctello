package com.example.doctello.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.doctello.Fragments.SignUpHomeFragment;
import com.example.doctello.R;

public class LoginSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login ,
                new SignUpHomeFragment())
                .commit();
    }
}
