package com.example.doctello.Local;

import androidx.room.TypeConverter;
import com.example.doctello.Local.Entity.HospitalDetailsEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class HospitalDetailsEnitityConverter {

    @TypeConverter
    public static HospitalDetailsEntity.HospitalModel fromString(String value) {
        Type listType = new TypeToken<HospitalDetailsEntity.HospitalModel>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromModel(HospitalDetailsEntity.HospitalModel hospitalModel) {
        Gson gson = new Gson();
        return gson.toJson(hospitalModel);
    }
}
