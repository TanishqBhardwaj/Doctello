package com.example.doctello.Local.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.doctello.Local.Entity.HospitalDetailsEntity;
import java.util.List;

@Dao
public interface HospitalDetailsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<HospitalDetailsEntity> hospitalDetailsEntity);

    @Update
    void update(HospitalDetailsEntity hospitalDetailsEntity);

    @Delete
    void delete(HospitalDetailsEntity hospitalDetailsEntity);

    @Query("select * from hospital_details_table")
    LiveData<List<HospitalDetailsEntity>> getAllHospitalDetails();
}
