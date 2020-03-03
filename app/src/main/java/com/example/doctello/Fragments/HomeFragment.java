package com.example.doctello.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.doctello.Activity.CategoryDoctorListActivity;
import com.example.doctello.Activity.HospitalDetailActivity;
import com.example.doctello.Activity.MainActivity;
import com.example.doctello.Adapters.HomeCategoryAdapter;
import com.example.doctello.Adapters.HomeHospitalAdapter;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.Local.Entity.CategoryEntity;
import com.example.doctello.R;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.ViewModel.CategoryViewModel;
import com.example.doctello.models.CategoryData;
import com.example.doctello.models.HospitalData;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public static final String SERVICE_ID = "service_id";
    public static final String HOSPITAL_ID = "hospital_id";
    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewHospital;
    private JsonApiHolder jsonApiHolder;
    private prefUtils sp;
    private HomeCategoryAdapter homeCategoryAdapter;
    private HomeHospitalAdapter homeHospitalAdapter;
    private List<CategoryEntity> categoryDataList = new ArrayList<>();
    private List<HospitalData> hospitalDataList = new ArrayList<>();
    private ProgressBar category_progress_bar;
    private ProgressBar hospital_progress_bar;
    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private String num;
    private CategoryViewModel categoryViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp = new prefUtils(getContext());
        category_progress_bar = view.findViewById(R.id.category_progress_bar);
        hospital_progress_bar = view.findViewById(R.id.hospitals_progress_bar);
        category_progress_bar.setVisibility(View.VISIBLE);
        hospital_progress_bar.setVisibility(View.VISIBLE);
//        Toast.makeText(getContext(), MainActivity.city, Toast.LENGTH_SHORT).show();
        setCategoryAdapter(view);

        recyclerViewHospital = view.findViewById(R.id.recycler_view_hospital_list);
        recyclerViewHospital.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerViewHospital.setHasFixedSize(true);
        homeHospitalAdapter = new HomeHospitalAdapter(hospitalDataList, getContext());
        recyclerViewHospital.setAdapter(homeHospitalAdapter);
        getCategories();
        getHospitals();

        return view;
    }

    private void setCategoryAdapter(View view) {

        recyclerViewCategory = view.findViewById(R.id.recycler_view_category);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setHasFixedSize(true);
        homeCategoryAdapter = new HomeCategoryAdapter(categoryDataList, getContext());
        recyclerViewCategory.setAdapter(homeCategoryAdapter);

    }

    private void getCategories() {

        categoryViewModel = ViewModelProviders.of(HomeFragment.this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryList().observe(HomeFragment.this,
                new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(List<CategoryEntity> categoryEntities) {
                homeCategoryAdapter = new HomeCategoryAdapter(categoryEntities, getContext());
                recyclerViewCategory.setAdapter(homeCategoryAdapter);
            }
        });

//        Call<List<CategoryData>> call = jsonApiHolder.getCategories(sp.getToken());
//        call.enqueue(new Callback<List<CategoryData>>() {
//
//            @Override
//            public void onResponse(Call<List<CategoryData>> call, Response<List<CategoryData>> response) {
//                category_progress_bar.setVisibility(View.INVISIBLE);
//                if(!response.isSuccessful()) {
//                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                categoryDataList = response.body();
//                homeCategoryAdapter = new HomeCategoryAdapter(categoryDataList, getContext());
//                recyclerViewCategory.setAdapter(homeCategoryAdapter);
//                homeCategoryAdapter.setOnCategoryClickListener(new HomeCategoryAdapter.OnCategoryClickListener() {
//                    @Override
//                    public void onCategoryClick(int position) {
//                        CategoryData clickedCategory = categoryDataList.get(position);
//                        Intent i = new Intent(getContext() , CategoryDoctorListActivity.class);
//                        i.putExtra(SERVICE_ID , clickedCategory.getServiceID());
//                        startActivity(i);
//                    }
//                });
//            }

//            @Override
//            public void onFailure(Call<List<CategoryData>> call, Throwable t) {
//                category_progress_bar.setVisibility(View.INVISIBLE);
//                Toast.makeText(getContext(), "No response from the server!", Toast.LENGTH_SHORT).show();            }
//        });
    }

    private void getHospitals() {

//        String city = "null";
        Call<List<HospitalData>> call = jsonApiHolder.getHospitals(MainActivity.city, sp.getToken());
//        Log.d(MainActivity.city, "getHospitals: CITY");
        call.enqueue(new Callback<List<HospitalData>>() {
            @Override
            public void onResponse(Call<List<HospitalData>> call, Response<List<HospitalData>> response) {
                hospital_progress_bar.setVisibility(View.INVISIBLE);
                if(response.isSuccessful()){
                    hospitalDataList = response.body();
                    homeHospitalAdapter = new HomeHospitalAdapter(hospitalDataList, getContext());
                    recyclerViewHospital.setAdapter(homeHospitalAdapter);
                    homeHospitalAdapter.setOnHospitalClickListener(new HomeHospitalAdapter.OnHospitalClickListener() {
                        @Override
                        public void onCallClick(int position) {
                            num = hospitalDataList.get(position).getHospitalPhone();
                            makePhoneCall(num);
                        }

                        @Override
                        public void onHospitalClick(int position) {
                            String id= hospitalDataList.get(position).getHospital_id();
                            Intent i = new Intent(getContext() , HospitalDetailActivity.class);
//                            i.putExtra(HOSPITAL_ID , id);
                            i.putExtra(HOSPITAL_ID , id);
                            startActivity(i);
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HospitalData>> call, Throwable t) {
                hospital_progress_bar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "No response from the server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void makePhoneCall(String num){
        if(ContextCompat.checkSelfPermission(getContext() , Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission
                    .CALL_PHONE} , MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }else{
            String dial = "tel:" + num.trim();
            startActivity(new Intent(Intent.ACTION_CALL , Uri.parse(dial)));
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall(num);
            }
            else {
                Toast.makeText(getContext() , "Permission Denied" , Toast.LENGTH_SHORT).show();
            }
        }
    }


}
