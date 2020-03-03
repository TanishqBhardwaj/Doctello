package com.example.doctello.Local.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.doctello.Local.Entity.HospitalServicesEntity;
import java.util.List;

@Dao
public interface HospitalServicesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<HospitalServicesEntity> hospitalServicesEntity);

    @Update
    void update(HospitalServicesEntity hospitalServicesEntity);

    @Delete
    void delete(HospitalServicesEntity hospitalServicesEntity);

    @Query("select * from hospital_services_table")
    LiveData<List<HospitalServicesEntity>> getAllHospitalServices();
}
