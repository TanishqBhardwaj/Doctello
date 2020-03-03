package com.example.doctello.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.doctello.Fragments.HomeFragment;
import com.example.doctello.Fragments.HospitalDetailFragment;
import com.example.doctello.Fragments.SignUpHomeFragment;
import com.example.doctello.R;

public class HospitalDetailActivity extends AppCompatActivity {

    private String hospital_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);
        Intent i = getIntent();
        hospital_id = i.getStringExtra(HomeFragment.HOSPITAL_ID);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_hospital_detail ,
                new HospitalDetailFragment(hospital_id))
                .commit();

    }

}
