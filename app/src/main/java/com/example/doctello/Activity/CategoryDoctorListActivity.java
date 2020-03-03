package com.example.doctello.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.doctello.Adapters.DoctorsListAdapter;
import com.example.doctello.Fragments.HomeFragment;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.R;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.DoctorsData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDoctorListActivity extends AppCompatActivity {

    prefUtils pr;
    private List<DoctorsData> DoctorsList = new ArrayList<>();
    private JsonApiHolder jsonApiHolder;
    private String service_id;
    private RecyclerView doctorsRecyclerView;
    private DoctorsListAdapter doctorsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_doctor_list);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(this);
        Intent i = getIntent();
        service_id = i.getStringExtra(HomeFragment.SERVICE_ID);
        setAdapter();
        getDoctors();
    }

    private void getDoctors() {
//        String city = "null";
        Call<List<DoctorsData>> call = jsonApiHolder.getDoctorsByCategory(service_id, MainActivity.city,
                pr.getToken());

        call.enqueue(new Callback<List<DoctorsData>>() {
            @Override
            public void onResponse(Call<List<DoctorsData>> call, Response<List<DoctorsData>> response) {
                if(response.isSuccessful()){
                    DoctorsList = response.body();
                    doctorsListAdapter = new DoctorsListAdapter(DoctorsList);
                    doctorsRecyclerView.setAdapter(doctorsListAdapter);
                }
                else{
                    Toast.makeText(CategoryDoctorListActivity.this, response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DoctorsData>> call, Throwable t) {
                Toast.makeText(CategoryDoctorListActivity.this, "An Error Occurred!",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setAdapter() {
        doctorsRecyclerView = findViewById(R.id.doctors_recycler_view);
        doctorsRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        doctorsRecyclerView.setHasFixedSize(true);
        doctorsListAdapter = new DoctorsListAdapter(DoctorsList);
        doctorsRecyclerView.setAdapter(doctorsListAdapter);
    }
}
