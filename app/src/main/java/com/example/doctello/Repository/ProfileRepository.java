package com.example.doctello.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.Local.DAO.ProfileDAO;
import com.example.doctello.Local.Database.HospitalDatabase;
import com.example.doctello.Local.Entity.ProfileEntity;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.ProfileData;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    private ProfileDAO profileDAO;
    private LiveData<List<ProfileEntity>> profile;
    private JsonApiHolder jsonApiHolder;
    private prefUtils sp;
    private Application application;

    public ProfileRepository(Application application) {
        HospitalDatabase hospitalDatabase = HospitalDatabase.getInstance(application);
        profileDAO = hospitalDatabase.profileDAO();
        profile = profileDAO.getAllProfileDetails();
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp = new prefUtils(application);
        this.application = application;
        getProfileDetails();
    }

    public void getProfileDetails() {
        Call<List<ProfileData>> call = jsonApiHolder.getProfileDetails(sp.getToken());
        call.enqueue(new Callback<List<ProfileData>>() {
            @Override
            public void onResponse(Call<List<ProfileData>> call, Response<List<ProfileData>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_LONG).show();
                    return;
                }
                List<ProfileData> profileDataList = response.body();
                List<ProfileEntity> profileEntityArrayList = new ArrayList<>();
                if(profileDataList!=null) {
                    for(ProfileData profileData: profileDataList) {
                        ProfileEntity profileEntity = new ProfileEntity(
                                1,
                                profileData.getUserName(),
                                profileData.getUserPhone());

                        profileEntityArrayList.add(profileEntity);
                    }
//                    profileDAO.insert(profileEntityArrayList);
                    new profileAsyncTask(profileDAO).execute(profileEntityArrayList);
                }
            }

            @Override
            public void onFailure(Call<List<ProfileData>> call, Throwable t) {
                Toast.makeText(application, "No response from the server!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private static class profileAsyncTask extends AsyncTask<List<ProfileEntity>, Void, Void> {
        private ProfileDAO profileDAO;

        public profileAsyncTask(ProfileDAO profileDAO) {
            this.profileDAO = profileDAO;
        }

        @Override
        protected Void doInBackground(List<ProfileEntity>... lists) {
            profileDAO.insert(lists[0]);
            return null;
        }
    }

    public LiveData<List<ProfileEntity>> getProfile() {
        return profile;
    }
}
