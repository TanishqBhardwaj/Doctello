package com.example.doctello.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.doctello.Local.Entity.HospitalDetailsEntity;
import com.example.doctello.Repository.HospitalDetailsRepository;
import java.util.List;

public class HospitalDetailsViewModel extends AndroidViewModel {

    private HospitalDetailsRepository hospitalDetailsRepository;
    private LiveData<List<HospitalDetailsEntity>> hospitalDetails;

    public HospitalDetailsViewModel(@NonNull Application application) {
        super(application);
        hospitalDetailsRepository = new HospitalDetailsRepository(application);
        hospitalDetails = hospitalDetailsRepository.getHospitalDetailsList();
    }

    public LiveData<List<HospitalDetailsEntity>> getHospitalDetails() {
        return hospitalDetails;
    }
}
