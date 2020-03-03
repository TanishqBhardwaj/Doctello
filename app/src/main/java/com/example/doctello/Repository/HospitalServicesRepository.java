package com.example.doctello.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.Local.DAO.HospitalDAO;
import com.example.doctello.Local.DAO.HospitalServicesDAO;
import com.example.doctello.Local.Database.HospitalDatabase;
import com.example.doctello.Local.Entity.CategoryEntity;
import com.example.doctello.Local.Entity.HospitalEntity;
import com.example.doctello.Local.Entity.HospitalServicesEntity;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.HospitalServicesData;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalServicesRepository {
    private HospitalServicesDAO hospitalServicesDAO;
    private LiveData<List<HospitalServicesEntity>> allHospitalServices;
    private JsonApiHolder jsonApiHolder;
    private prefUtils sp;
    private Application application;

    public HospitalServicesRepository(Application application) {
        HospitalDatabase hospitalDatabase = HospitalDatabase.getInstance(application);
        hospitalServicesDAO = hospitalDatabase.hospitalServicesDAO();
        allHospitalServices = hospitalServicesDAO.getAllHospitalServices();
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp = new prefUtils(application);
        this.application = application;
        getHospitalServices();
    }

    public void getHospitalServices() {
        Call<List<HospitalServicesData>> call = jsonApiHolder.getHospitalServices("1", sp.getToken());
        call.enqueue(new Callback<List<HospitalServicesData>>() {
            @Override
            public void onResponse(Call<List<HospitalServicesData>> call, Response<List<HospitalServicesData>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_LONG).show();
                    return;
                }
                List<HospitalServicesData> hospitalServicesDataList = response.body();
                List<HospitalServicesEntity> hospitalServicesEntityArrayList = new ArrayList<>();
                if(hospitalServicesDataList!=null) {
                    for(HospitalServicesData hospitalServicesData: hospitalServicesDataList) {
                        HospitalServicesEntity hospitalServicesEntity = new HospitalServicesEntity(
                                1,
                                hospitalServicesData.getHospital_id(),
                                hospitalServicesData.getModelData());

                        hospitalServicesEntityArrayList.add(hospitalServicesEntity);
                    }
//                    hospitalServicesDAO.insert(hospitalServicesEntityArrayList);
                    new hospitalServicesAsyncTask(hospitalServicesDAO).execute(hospitalServicesEntityArrayList);
                }
            }

            @Override
            public void onFailure(Call<List<HospitalServicesData>> call, Throwable t) {
                Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private static class hospitalServicesAsyncTask extends AsyncTask<List<HospitalServicesEntity>, Void, Void> {
        private HospitalServicesDAO hospitalServicesDAO;

        public hospitalServicesAsyncTask(HospitalServicesDAO hospitalServicesDAO) {
            this.hospitalServicesDAO = hospitalServicesDAO;
        }

        @Override
        protected Void doInBackground(List<HospitalServicesEntity>... lists) {
            hospitalServicesDAO.insert(lists[0]);
            return null;
        }
    }

    public LiveData<List<HospitalServicesEntity>> getAllHospitalServices() {
        return allHospitalServices;
    }
}
