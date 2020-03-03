package com.example.doctello.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import com.example.doctello.Activity.MainActivity;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.Local.DAO.DoctorsDAO;
import com.example.doctello.Local.Database.HospitalDatabase;
import com.example.doctello.Local.Entity.DoctorsEntity;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.DoctorsData;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorsRepository {

    private DoctorsDAO doctorsDAO;
    private LiveData<List<DoctorsEntity>> allDoctors;
    private JsonApiHolder jsonApiHolder;
    private prefUtils sp;
    private Application application;

    public DoctorsRepository(Application application) {
        HospitalDatabase hospitalDatabase = HospitalDatabase.getInstance(application);
        doctorsDAO = hospitalDatabase.doctorsDAO();
        allDoctors = doctorsDAO.getAllDoctors();
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp = new prefUtils(application);
        this.application = application;
        getDoctors();
    }

    public void getDoctors() {
        Call<List<DoctorsData>> call = jsonApiHolder.getDoctorsByCategory("1", MainActivity.city,
                sp.getToken());

        call.enqueue(new Callback<List<DoctorsData>>() {
            @Override
            public void onResponse(Call<List<DoctorsData>> call, Response<List<DoctorsData>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_LONG).show();
                    return;
                }
                List<DoctorsData> doctorsDataList = response.body();
                List<DoctorsEntity> doctorsEntityArrayList = new ArrayList<>();
                if(doctorsDataList!=null) {
                    for(DoctorsData doctorsData: doctorsDataList) {
                        DoctorsEntity doctorsEntity = new DoctorsEntity(
                                1,
                                doctorsData.getDoctor_id(),
                                doctorsData.getDoctor_name(),
                                doctorsData.getFees(),
                                doctorsData.getService_id(),
                                doctorsData.getHospital_id());

                        doctorsEntityArrayList.add(doctorsEntity);
                    }
//                    doctorsDAO.insert(doctorsEntityArrayList);
                    new doctorsAsyncTask(doctorsDAO).execute(doctorsEntityArrayList);
                }
            }

            @Override
            public void onFailure(Call<List<DoctorsData>> call, Throwable t) {
                Toast.makeText(application, "No response from the server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class doctorsAsyncTask extends AsyncTask<List<DoctorsEntity>, Void, Void> {
        private DoctorsDAO doctorsDAO;

        public doctorsAsyncTask(DoctorsDAO doctorsDAO) {
            this.doctorsDAO = doctorsDAO;
        }

        @Override
        protected Void doInBackground(List<DoctorsEntity>... lists) {
            doctorsDAO.insert(lists[0]);
            return null;
        }
    }


    public LiveData<List<DoctorsEntity>> getAllDoctors() {
        return allDoctors;
    }
}
