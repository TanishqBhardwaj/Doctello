package com.example.doctello.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.doctello.Local.Entity.HospitalServicesEntity;
import com.example.doctello.Repository.HospitalServicesRepository;
import java.util.List;

public class HospitalServicesViewModel extends AndroidViewModel {

    private HospitalServicesRepository hospitalServicesRepository;
    private LiveData<List<HospitalServicesEntity>> hospitalServicesList;

    public HospitalServicesViewModel(@NonNull Application application) {
        super(application);
        hospitalServicesRepository = new HospitalServicesRepository(application);
        hospitalServicesList = hospitalServicesRepository.getAllHospitalServices();
    }

    public LiveData<List<HospitalServicesEntity>> getHospitalServicesList() {
        return hospitalServicesList;
    }
}
