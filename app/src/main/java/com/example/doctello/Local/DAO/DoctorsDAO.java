package com.example.doctello.Local.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.doctello.Local.Entity.DoctorsEntity;
import java.util.List;

@Dao
public interface DoctorsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<DoctorsEntity> doctorsEntity);

    @Update
    void update(DoctorsEntity doctorsEntity);

    @Delete
    void delete(DoctorsEntity doctorsEntity);

    @Query("select * from doctors_table")
    LiveData<List<DoctorsEntity>> getAllDoctors();
}
