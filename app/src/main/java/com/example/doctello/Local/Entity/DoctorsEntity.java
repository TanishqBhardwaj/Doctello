package com.example.doctello.Local.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "doctors_table")
public class DoctorsEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("DoctorID")
    private int doctor_id;
    @SerializedName("DoctorName")
    private String doctor_name;
    @SerializedName("DoctorFees")
    private Integer fees;
    @SerializedName("ServiceID")
    private Integer service_id;
    @SerializedName("HospitalID")
    private Integer hospital_id;

    public DoctorsEntity(int id, int doctor_id, String doctor_name, Integer fees, Integer service_id, Integer hospital_id) {
        this.id = id;
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.fees = fees;
        this.service_id = service_id;
        this.hospital_id = hospital_id;
    }

    @Ignore
    public DoctorsEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public Integer getFees() {
        return fees;
    }

    public void setFees(Integer fees) {
        this.fees = fees;
    }

    public Integer getService_id() {
        return service_id;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }

    public Integer getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(Integer hospital_id) {
        this.hospital_id = hospital_id;
    }
}
