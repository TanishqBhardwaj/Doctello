package com.example.doctello.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.Local.DAO.DoctorsDAO;
import com.example.doctello.Local.DAO.HospitalDetailsDAO;
import com.example.doctello.Local.Database.HospitalDatabase;
import com.example.doctello.Local.Entity.DoctorsEntity;
import com.example.doctello.Local.Entity.HospitalDetailsEntity;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.HospitalDetailsData;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalDetailsRepository {

    private HospitalDetailsDAO hospitalDetailsDAO;
    private LiveData<List<HospitalDetailsEntity>> hospitalDetailsList;
    private JsonApiHolder jsonApiHolder;
    private prefUtils sp;
    private Application application;

    public HospitalDetailsRepository(Application application) {
        HospitalDatabase hospitalDatabase = HospitalDatabase.getInstance(application);
        hospitalDetailsDAO = hospitalDatabase.hospitalDetailsDAO();
        hospitalDetailsList = hospitalDetailsDAO.getAllHospitalDetails();
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp = new prefUtils(application);
        this.application = application;
        getHospitalDetails();
    }

    public void getHospitalDetails() {
        Call<List<HospitalDetailsData>> call = jsonApiHolder.getHospitalDetails("1", sp.getToken());
        call.enqueue(new Callback<List<HospitalDetailsData>>() {
            @Override
            public void onResponse(Call<List<HospitalDetailsData>> call,
                                   Response<List<HospitalDetailsData>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_LONG).show();
                    return;
                }
                List<HospitalDetailsData> hospitalDetailsDataList = response.body();
                List<HospitalDetailsEntity> hospitalDetailsEntityList = new ArrayList<>();
                if(hospitalDetailsDataList!=null) {
                    for(HospitalDetailsData hospitalDetailsData: hospitalDetailsDataList) {
                        HospitalDetailsEntity hospitalDetailsEntity = new HospitalDetailsEntity(
                                1,
                                hospitalDetailsData.getDoctorId(),
                                hospitalDetailsData.getDoctorName(),
                                hospitalDetailsData.getDoctorRegNumber(),
                                hospitalDetailsData.getDoctorFees(),
                                hospitalDetailsData.getDoctorAvailableDay(),
                                hospitalDetailsData.getDoctorAvailableTimeMorning(),
                                hospitalDetailsData.getDoctorAvailableTimeEvening(),
                                hospitalDetailsData.getHospitalModel());

                        hospitalDetailsEntityList.add(hospitalDetailsEntity);
                    }
//                    hospitalDetailsDAO.insert(hospitalDetailsEntityList);
                    new hospitalDetailsAsyncTask(hospitalDetailsDAO).execute(hospitalDetailsEntityList);
                }
            }

            @Override
            public void onFailure(Call<List<HospitalDetailsData>> call, Throwable t) {
                Toast.makeText(application, "No response from the server!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private static class hospitalDetailsAsyncTask extends AsyncTask<List<HospitalDetailsEntity>, Void, Void> {
        private HospitalDetailsDAO hospitalDetailsDAO;

        public hospitalDetailsAsyncTask(HospitalDetailsDAO hospitalDetailsDAO) {
            this.hospitalDetailsDAO = hospitalDetailsDAO;
        }

        @Override
        protected Void doInBackground(List<HospitalDetailsEntity>... lists) {
            hospitalDetailsDAO.insert(lists[0]);
            return null;
        }
    }

    public LiveData<List<HospitalDetailsEntity>> getHospitalDetailsList() {
        return hospitalDetailsList;
    }
}
