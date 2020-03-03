package com.example.doctello.Local.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.doctello.Local.Entity.HospitalEntity;
import java.util.List;

@Dao
public interface HospitalDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<HospitalEntity> hospitalEntity);

    @Update
    void update(HospitalEntity hospitalEntity);

    @Delete
    void delete(HospitalEntity hospitalEntity);

    @Query("select * from hospital_table")
    LiveData<List<HospitalEntity>> getAllHospitals();
}
