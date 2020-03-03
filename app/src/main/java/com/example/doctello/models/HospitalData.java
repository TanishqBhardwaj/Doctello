package com.example.doctello.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class HospitalData {

    @SerializedName("HospitalID")
    private String hospital_id;

    @SerializedName("HospitalName")
    private String HospitaName;

    public String getHospital_id() {
        return hospital_id;
    }

    @SerializedName("HospitalLocation")
    private String HospitalLocation;

    @SerializedName("HospitalPhone")
    private String HospitalPhone;

    @SerializedName("AverageRating")
    private String AverageRating;

    @SerializedName("HospitalImage")
    private String HospitalImage;
}
