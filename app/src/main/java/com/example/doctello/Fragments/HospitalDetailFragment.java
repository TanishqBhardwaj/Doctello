package com.example.doctello.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctello.Adapters.DoctorHospitalListAdapter;
import com.example.doctello.Adapters.ServicesDataAdapter;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.R;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.HospitalDetailsData;
import com.example.doctello.models.HospitalServicesData;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalDetailFragment extends Fragment {

    private RecyclerView services_recycler_view;
    private RecyclerView recyclerViewDoctors;
    private JsonApiHolder jsonApiHolder;
    private prefUtils pr;
    private ServicesDataAdapter servicesDataAdapter;
    private List<HospitalServicesData.serviceModel> servicesList = new ArrayList<>();
    private Toolbar toolbar;
    private String id;
    private ImageView imageView;
    private TextView textViewAddress;
    private List<HospitalDetailsData> hospitalDetailsDataList = new ArrayList<>();
    private List<HospitalServicesData> hospitalServicesDataList = new ArrayList<>();
    private retrofitInstance instance = new retrofitInstance();
    ProgressBar progressBar;
    ImageView viewBlank;
    DoctorHospitalListAdapter doctorHospitalListAdapter;

    public HospitalDetailFragment(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_detail, container, false);

        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(getContext());
        toolbar = view.findViewById(R.id.toolbarHospital);
        textViewAddress = view.findViewById(R.id.textViewAddress);
        progressBar = view.findViewById(R.id.progressBar);
        viewBlank = view.findViewById(R.id.imageViewBlank);
        progressBar.setVisibility(View.VISIBLE);
        viewBlank.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        imageView = view.findViewById(R.id.imageViewHospitalDetailsProfile);
        getHospitalDetails();
        getDoctorsList(view);
        getServicesList(view);

        return view;
    }

    private void getDoctorsList(View view) {
        recyclerViewDoctors = view.findViewById(R.id.recycler_view_doctors_list);
        recyclerViewDoctors.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerViewDoctors.setHasFixedSize(true);
        doctorHospitalListAdapter = new DoctorHospitalListAdapter(hospitalDetailsDataList);
        recyclerViewDoctors.setAdapter(doctorHospitalListAdapter);

    }

    private void getServicesList(View view) {
        services_recycler_view = view.findViewById(R.id.services_recycler_view);
        services_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        services_recycler_view.setHasFixedSize(true);
        servicesDataAdapter = new ServicesDataAdapter(hospitalServicesDataList);
        services_recycler_view.setAdapter(servicesDataAdapter);
        Call<List<HospitalServicesData>> call = jsonApiHolder.getHospitalServices(id, pr.getToken());
        call.enqueue(new Callback<List<HospitalServicesData>>() {
            @Override
            public void onResponse(Call<List<HospitalServicesData>> call, Response<List<HospitalServicesData>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_LONG).show();
                    return;
                }
                hospitalServicesDataList = response.body();
                if(hospitalServicesDataList.size()!=0) {
                    servicesDataAdapter = new ServicesDataAdapter(hospitalServicesDataList);
                    services_recycler_view.setAdapter(servicesDataAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<HospitalServicesData>> call, Throwable t) {
                Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getHospitalDetails() {
        Call<List<HospitalDetailsData>> call = jsonApiHolder.getHospitalDetails(id, pr.getToken());
        call.enqueue(new Callback<List<HospitalDetailsData>>() {
            @Override
            public void onResponse(Call<List<HospitalDetailsData>> call,
                                   Response<List<HospitalDetailsData>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_LONG).show();
                    return;
                }
                hospitalDetailsDataList = response.body();
                if(hospitalDetailsDataList.size()!=0) {
                    toolbar.setTitle(hospitalDetailsDataList.get(0).getHospitalModel().getHospitalName());
                    String imageUrl = hospitalDetailsDataList.get(0).getHospitalModel().getHospitalImage();
                    textViewAddress.setText(hospitalDetailsDataList.get(0).getHospitalModel().getHospitalAddress());
                    Picasso.with(getContext()).load(instance.getURL().concat(imageUrl))
                            .error(R.id.imageViewHospitalProfile)
                            .into(imageView);
                    doctorHospitalListAdapter = new DoctorHospitalListAdapter(hospitalDetailsDataList);
                    recyclerViewDoctors.setAdapter(doctorHospitalListAdapter);
                    progressBar.setVisibility(View.GONE);
                    viewBlank.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }

            @Override
            public void onFailure(Call<List<HospitalDetailsData>> call, Throwable t) {
                Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
