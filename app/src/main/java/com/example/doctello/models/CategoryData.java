package com.example.doctello.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CategoryData {

    @SerializedName("ServiceID")
    private String ServiceID;

    @SerializedName("ServiceName")
    private String ServiceName;

    @SerializedName("DoctorTable")
    private String DoctorTable;

    @SerializedName("HospitalServiceTable")
    private String HospitalServiceTable;

    @SerializedName("Image")
    private String Image;

}
