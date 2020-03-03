package com.example.doctello.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import com.example.doctello.Activity.MainActivity;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.Local.DAO.HospitalDAO;
import com.example.doctello.Local.DAO.HospitalDetailsDAO;
import com.example.doctello.Local.Database.HospitalDatabase;
import com.example.doctello.Local.Entity.HospitalDetailsEntity;
import com.example.doctello.Local.Entity.HospitalEntity;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.HospitalData;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalRepository {

    private HospitalDAO hospitalDAO;
    private LiveData<List<HospitalEntity>> allHospitals;
    private JsonApiHolder jsonApiHolder;
    private prefUtils sp;
    private Application application;

    public HospitalRepository(Application application) {
        HospitalDatabase hospitalDatabase = HospitalDatabase.getInstance(application);
        hospitalDAO = hospitalDatabase.hospitalDAO();
        allHospitals = hospitalDAO.getAllHospitals();
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp = new prefUtils(application);
        this.application = application;
        getHospitals();
    }

    private void getHospitals() {

        Call<List<HospitalData>> call = jsonApiHolder.getHospitals(MainActivity.city, sp.getToken());
        call.enqueue(new Callback<List<HospitalData>>() {
            @Override
            public void onResponse(Call<List<HospitalData>> call, Response<List<HospitalData>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_LONG).show();
                    return;
                }
                    List<HospitalData> hospitalDataLists = response.body();
                    List<HospitalEntity> hospitalEntityArrayList = new ArrayList<>();
                    if(hospitalDataLists!=null) {
                        for(HospitalData hospitalData: hospitalDataLists) {
                            HospitalEntity hospitalEntity = new HospitalEntity(
                                    1,
                                    hospitalData.getHospital_id(),
                                    hospitalData.getHospitaName(),
                                    hospitalData.getHospitalLocation(),
                                    hospitalData.getHospitalPhone(),
                                    hospitalData.getAverageRating(),
                                    hospitalData.getHospitalImage());

                            hospitalEntityArrayList.add(hospitalEntity);
                        }
//                        hospitalDAO.insert(hospitalEntityArrayList);
                        new hospitalAsyncTask(hospitalDAO).execute(hospitalEntityArrayList);
                    }
            }

            @Override
            public void onFailure(Call<List<HospitalData>> call, Throwable t) {
                Toast.makeText(application, "No response from the server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class hospitalAsyncTask extends AsyncTask<List<HospitalEntity>, Void, Void> {
        private HospitalDAO hospitalDAO;

        public hospitalAsyncTask(HospitalDAO hospitalDAO) {
            this.hospitalDAO = hospitalDAO;
        }

        @Override
        protected Void doInBackground(List<HospitalEntity>... lists) {
            hospitalDAO.insert(lists[0]);
            return null;
        }
    }

    public LiveData<List<HospitalEntity>> getAllHospitals() {
        return allHospitals;
    }
}
