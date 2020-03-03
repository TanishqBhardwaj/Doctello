package com.example.doctello.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.doctello.Local.Entity.DoctorsEntity;
import com.example.doctello.Repository.DoctorsRepository;
import java.util.List;

public class DoctorsViewModel extends AndroidViewModel {

    private DoctorsRepository doctorsRepository;
    private LiveData<List<DoctorsEntity>> doctorsList;

    public DoctorsViewModel(@NonNull Application application) {
        super(application);
        doctorsRepository = new DoctorsRepository(application);
        doctorsList = doctorsRepository.getAllDoctors();
    }

    public LiveData<List<DoctorsEntity>> getDoctorsList() {
        return doctorsList;
    }
}
