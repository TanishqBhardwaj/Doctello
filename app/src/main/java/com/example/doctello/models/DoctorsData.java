package com.example.doctello.models;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DoctorsData {

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
}
