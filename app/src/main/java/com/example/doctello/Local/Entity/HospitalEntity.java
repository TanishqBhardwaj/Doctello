package com.example.doctello.Local.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "hospital_table")
public class HospitalEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

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

    public HospitalEntity(int id, String hospital_id, String hospitaName, String hospitalLocation, String hospitalPhone, String averageRating, String hospitalImage) {
        this.id = id;
        this.hospital_id = hospital_id;
        HospitaName = hospitaName;
        HospitalLocation = hospitalLocation;
        HospitalPhone = hospitalPhone;
        AverageRating = averageRating;
        HospitalImage = hospitalImage;
    }

    public HospitalEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getHospitaName() {
        return HospitaName;
    }

    public void setHospitaName(String hospitaName) {
        HospitaName = hospitaName;
    }

    public String getHospitalLocation() {
        return HospitalLocation;
    }

    public void setHospitalLocation(String hospitalLocation) {
        HospitalLocation = hospitalLocation;
    }

    public String getHospitalPhone() {
        return HospitalPhone;
    }

    public void setHospitalPhone(String hospitalPhone) {
        HospitalPhone = hospitalPhone;
    }

    public String getAverageRating() {
        return AverageRating;
    }

    public void setAverageRating(String averageRating) {
        AverageRating = averageRating;
    }

    public String getHospitalImage() {
        return HospitalImage;
    }

    public void setHospitalImage(String hospitalImage) {
        HospitalImage = hospitalImage;
    }
}
