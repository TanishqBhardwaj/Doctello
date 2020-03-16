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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private CategoryDAO categoryDAO;
    private LiveData<List<CategoryEntity>> allCategories;
    private JsonApiHolder jsonApiHolder;
    private prefUtils sp;
    private Application application;
    private CompositeDisposable disposable = new CompositeDisposable();

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

        disposable.add(
                jsonApiHolder.getCategories(sp.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CategoryData>>() {
                    @Override
                    public void onSuccess(@NonNull List<CategoryData> categoryData) {
                        List<CategoryEntity> categoryEntityList = new ArrayList<>();
                        if (categoryData != null) {
                            for (CategoryData categoryDataObject : categoryData) {
                                CategoryEntity categoryEntity = new CategoryEntity(
                                        1,
                                        categoryDataObject.getServiceID(),
                                        categoryDataObject.getServiceName(),
                                        categoryDataObject.getDoctorTable(),
                                        categoryDataObject.getHospitalServiceTable(),
                                        categoryDataObject.getImage());

                                categoryEntityList.add(categoryEntity);
                            }
                            categoryDAO.insert(categoryEntityList);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                            Toast.makeText(application, "No response from the server!",
                                    Toast.LENGTH_SHORT).show();
                        }
                })
        );

//        Call<List<CategoryData>> call = jsonApiHolder.getCategories(sp.getToken());
//        call.enqueue(new Callback<List<CategoryData>>() {
//            @Override
//            public void onResponse(Call<List<CategoryData>> call, Response<List<CategoryData>> response) {
//                if(!response.isSuccessful()) {
//                    Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                List<CategoryData> categoryDataList = response.body();
//                List<CategoryEntity> categoryEntityList = new ArrayList<>();
//                if(categoryDataList!=null) {
//                    for(CategoryData categoryData: categoryDataList) {
//                        CategoryEntity categoryEntity = new CategoryEntity(
//                                1,
//                                categoryData.getServiceID(),
//                                categoryData.getServiceName(),
//                                categoryData.getDoctorTable(),
//                                categoryData.getHospitalServiceTable(),
//                                categoryData.getImage());
//
//                        categoryEntityList.add(categoryEntity);
//                    }
////                    categoryDAO.insert(categoryEntityList);
//                    new categoryAsyncTask(categoryDAO).execute(categoryEntityList);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<CategoryData>> call, Throwable t) {
//                Toast.makeText(application, "No response from the server!", Toast.LENGTH_SHORT).show();            }
//        });
    }

//    private static class categoryAsyncTask extends AsyncTask<List<CategoryEntity>, Void, Void> {
//        private CategoryDAO categoryDAO;
//
//        public categoryAsyncTask(CategoryDAO categoryDAO) {
//            this.categoryDAO = categoryDAO;
//        }
//
//        @Override
//        protected Void doInBackground(List<CategoryEntity>... lists) {
//            categoryDAO.insert(lists[0]);
//            return null;
//        }
//    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return allCategories;
    }
}
