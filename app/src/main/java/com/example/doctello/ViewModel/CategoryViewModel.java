package com.example.doctello.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.doctello.Local.Entity.CategoryEntity;
import com.example.doctello.Repository.CategoryRepository;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;
    private LiveData<List<CategoryEntity>> categoryList;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        categoryList = categoryRepository.getAllCategories();
    }

    public LiveData<List<CategoryEntity>> getCategoryList() {
        return categoryList;
    }
}
