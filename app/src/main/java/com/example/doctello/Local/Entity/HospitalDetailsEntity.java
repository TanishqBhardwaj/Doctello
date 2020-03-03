package com.example.doctello.Local.Entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.doctello.Local.HospitalDetailsEnitityConverter;
import com.example.doctello.models.HospitalDetailsData;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "hospital_details_table")
public class HospitalDetailsEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("DoctorID")
    private Integer doctorId;

    @SerializedName("DoctorName")
    private String doctorName;

    @SerializedName("DoctorRegNo")
    private String doctorRegNumber;

    @SerializedName("DoctorFees")
    private Integer doctorFees;

    @SerializedName("AvailableDay")
    private String doctorAvailableDay;

    @SerializedName("AvailableTimeMorning")
    private String doctorAvailableTimeMorning;

    @SerializedName("AvailableTimeEvening")
    private String doctorAvailableTimeEvening;

//    @TypeConverters(HospitalDetailsEnitityConverter.class)
    @Embedded
    @SerializedName("HospitalModel")
    private HospitalDetailsData.HospitalModel hospitalModel;

    public static class HospitalModel {

        @SerializedName("HospitalName")
        private String hospitalName;

        @SerializedName("HospitalAddress")
        private String hospitalAddress;

        @SerializedName("HospitalEmail")
        private String hospitalEmail;

        @SerializedName("HospitalPhone")
        private String hospitalPhone;

        @SerializedName("AboutHospital")
        private String aboutHospital;

        @SerializedName("AverageRating")
        private Double averageRating;

        @SerializedName("HospitalImage")
        private String hospitalImage;
    }

    public HospitalDetailsEntity(int id, Integer doctorId, String doctorName, String doctorRegNumber, Integer doctorFees, String doctorAvailableDay, String doctorAvailableTimeMorning, String doctorAvailableTimeEvening, HospitalDetailsData.HospitalModel hospitalModel) {
        this.id = id;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorRegNumber = doctorRegNumber;
        this.doctorFees = doctorFees;
        this.doctorAvailableDay = doctorAvailableDay;
        this.doctorAvailableTimeMorning = doctorAvailableTimeMorning;
        this.doctorAvailableTimeEvening = doctorAvailableTimeEvening;
        this.hospitalModel = hospitalModel;
    }

    @Ignore
    public HospitalDetailsEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorRegNumber() {
        return doctorRegNumber;
    }

    public void setDoctorRegNumber(String doctorRegNumber) {
        this.doctorRegNumber = doctorRegNumber;
    }

    public Integer getDoctorFees() {
        return doctorFees;
    }

    public void setDoctorFees(Integer doctorFees) {
        this.doctorFees = doctorFees;
    }

    public String getDoctorAvailableDay() {
        return doctorAvailableDay;
    }

    public void setDoctorAvailableDay(String doctorAvailableDay) {
        this.doctorAvailableDay = doctorAvailableDay;
    }

    public String getDoctorAvailableTimeMorning() {
        return doctorAvailableTimeMorning;
    }

    public void setDoctorAvailableTimeMorning(String doctorAvailableTimeMorning) {
        this.doctorAvailableTimeMorning = doctorAvailableTimeMorning;
    }

    public String getDoctorAvailableTimeEvening() {
        return doctorAvailableTimeEvening;
    }

    public void setDoctorAvailableTimeEvening(String doctorAvailableTimeEvening) {
        this.doctorAvailableTimeEvening = doctorAvailableTimeEvening;
    }

    public HospitalDetailsData.HospitalModel getHospitalModel() {
        return hospitalModel;
    }

    public void setHospitalModel(HospitalDetailsData.HospitalModel hospitalModel) {
        this.hospitalModel = hospitalModel;
    }
}
