package com.example.doctello.Local.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.doctello.Local.Entity.ProfileEntity;

import java.util.List;

@Dao
public interface ProfileDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ProfileEntity> profileEntity);

    @Update
    void update(ProfileEntity profileEntity);

    @Delete
    void delete(ProfileEntity profileEntity);

    @Query("select * from profile_table")
    LiveData<List<ProfileEntity>> getAllProfileDetails();
}
