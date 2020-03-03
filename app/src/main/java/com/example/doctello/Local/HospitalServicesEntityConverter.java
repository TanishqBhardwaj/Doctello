package com.example.doctello.Local;

import androidx.room.TypeConverter;
import com.example.doctello.Local.Entity.HospitalServicesEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class HospitalServicesEntityConverter {

    @TypeConverter
    public static HospitalServicesEntity.serviceModel fromString(String value) {
        Type listType = new TypeToken<HospitalServicesEntity.serviceModel>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromModel(HospitalServicesEntity.serviceModel serviceModel) {
        Gson gson = new Gson();
        return gson.toJson(serviceModel);
    }
}
