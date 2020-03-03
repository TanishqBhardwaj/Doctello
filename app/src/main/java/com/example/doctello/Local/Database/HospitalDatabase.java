package com.example.doctello.Local.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.doctello.Local.DAO.CategoryDAO;
import com.example.doctello.Local.DAO.DoctorsDAO;
import com.example.doctello.Local.DAO.HospitalDAO;
import com.example.doctello.Local.DAO.HospitalDetailsDAO;
import com.example.doctello.Local.DAO.HospitalServicesDAO;
import com.example.doctello.Local.DAO.ProfileDAO;
import com.example.doctello.Local.Entity.CategoryEntity;
import com.example.doctello.Local.Entity.DoctorsEntity;
import com.example.doctello.Local.Entity.HospitalDetailsEntity;
import com.example.doctello.Local.Entity.HospitalEntity;
import com.example.doctello.Local.Entity.HospitalServicesEntity;
import com.example.doctello.Local.Entity.ProfileEntity;
import com.example.doctello.Local.HospitalDetailsEnitityConverter;
import com.example.doctello.Local.HospitalServicesEntityConverter;

@Database(entities = {CategoryEntity.class, DoctorsEntity.class, HospitalDetailsEntity.class,
        HospitalEntity.class, HospitalServicesEntity.class, ProfileEntity.class}, version = 1, exportSchema = false)

@TypeConverters({HospitalDetailsEnitityConverter.class, HospitalServicesEntityConverter.class})

public abstract class HospitalDatabase extends RoomDatabase {

    private static HospitalDatabase instance;

    public abstract CategoryDAO categoryDAO();

    public abstract DoctorsDAO doctorsDAO();

    public abstract HospitalDAO hospitalDAO();

    public abstract HospitalDetailsDAO hospitalDetailsDAO();

    public abstract HospitalServicesDAO hospitalServicesDAO();

    public abstract ProfileDAO profileDAO();

    public static synchronized HospitalDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, HospitalDatabase.class, "hospital_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
