package com.example.doctello.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.doctello.Local.Entity.HospitalEntity;
import com.example.doctello.Repository.HospitalRepository;
import java.util.List;

public class HospitalViewModel extends AndroidViewModel {

    private HospitalRepository hospitalRepository;
    private LiveData<List<HospitalEntity>> hospitalList;

    public HospitalViewModel(@NonNull Application application) {
        super(application);
        hospitalRepository = new HospitalRepository(application);
        hospitalList = hospitalRepository.getAllHospitals();
    }

    public LiveData<List<HospitalEntity>> getHospitalList() {
        return hospitalList;
    }
}
