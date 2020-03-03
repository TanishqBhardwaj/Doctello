package com.example.doctello.Local.Entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.doctello.Local.HospitalServicesEntityConverter;
import com.example.doctello.models.HospitalServicesData;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "hospital_services_table")
public class HospitalServicesEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("HospitalID")
    private Integer hospital_id;

    @Embedded
//    @TypeConverters(HospitalServicesEntityConverter.class)
    @SerializedName("ServiceModel")
    private HospitalServicesData.serviceModel modelData;

    public static class serviceModel {

        @SerializedName("ServiceID")
        private Integer service_id;
        @SerializedName("ServiceName")
        private String service_name;

    }



    public HospitalServicesEntity(int id, Integer hospital_id, HospitalServicesData.serviceModel modelData) {
        this.id = id;
        this.hospital_id = hospital_id;
        this.modelData = modelData;
    }

    @Ignore
    public HospitalServicesEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(Integer hospital_id) {
        this.hospital_id = hospital_id;
    }

    public HospitalServicesData.serviceModel getModelData() {
        return modelData;
    }

    public void setModelData(HospitalServicesData.serviceModel modelData) {
        this.modelData = modelData;
    }
}
