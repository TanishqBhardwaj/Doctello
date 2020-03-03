package com.example.doctello.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.Local.DAO.CategoryDAO;
import com.example.doctello.Local.DAO.DoctorsDAO;
import com.example.doctello.Local.Database.HospitalDatabase;
import com.example.doctello.Local.Entity.CategoryEntity;
import com.example.doctello.Local.Entity.DoctorsEntity;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.CategoryData;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private CategoryDAO categoryDAO;
    private LiveData<List<CategoryEntity>> allCategories;
    private JsonApiHolder jsonApiHolder;
    private prefUtils sp;
    private Application application;

    public CategoryRepository(Application application) {

        HospitalDatabase hospitalDatabase = HospitalDatabase.getInstance(application);
        categoryDAO = hospitalDatabase.categoryDAO();
        allCategories = categoryDAO.getAllCategories();
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp = new prefUtils(application);
        this.application = application;
        getCategories();

    }

    public void getCategories() {

        Call<List<CategoryData>> call = jsonApiHolder.getCategories(sp.getToken());
        call.enqueue(new Callback<List<CategoryData>>() {
            @Override
            public void onResponse(Call<List<CategoryData>> call, Response<List<CategoryData>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_LONG).show();
                    return;
                }
                List<CategoryData> categoryDataList = response.body();
                List<CategoryEntity> categoryEntityList = new ArrayList<>();
                if(categoryDataList!=null) {
                    for(CategoryData categoryData: categoryDataList) {
                        CategoryEntity categoryEntity = new CategoryEntity(
                                1,
                                categoryData.getServiceID(),
                                categoryData.getServiceName(),
                                categoryData.getDoctorTable(),
                                categoryData.getHospitalServiceTable(),
                                categoryData.getImage());

                        categoryEntityList.add(categoryEntity);
                    }
//                    categoryDAO.insert(categoryEntityList);
                    new categoryAsyncTask(categoryDAO).execute(categoryEntityList);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryData>> call, Throwable t) {
                Toast.makeText(application, "No response from the server!", Toast.LENGTH_SHORT).show();            }
        });
    }

    private static class categoryAsyncTask extends AsyncTask<List<CategoryEntity>, Void, Void> {
        private CategoryDAO categoryDAO;

        public categoryAsyncTask(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(List<CategoryEntity>... lists) {
            categoryDAO.insert(lists[0]);
            return null;
        }
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return allCategories;
    }
}
