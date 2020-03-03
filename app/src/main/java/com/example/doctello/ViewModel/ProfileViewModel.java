package com.example.doctello.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.doctello.Local.Entity.ProfileEntity;
import com.example.doctello.Repository.ProfileRepository;
import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepository profileRepository;
    private LiveData<List<ProfileEntity>> profileDetails;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepository(application);
        profileDetails = profileRepository.getProfile();
    }

    public LiveData<List<ProfileEntity>> getProfileDetails() {
        return profileDetails;
    }
}
