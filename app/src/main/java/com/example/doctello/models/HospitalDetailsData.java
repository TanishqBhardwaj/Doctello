package com.example.doctello.models;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class HospitalDetailsData {

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

        @SerializedName("HospitalModel")
        private HospitalModel hospitalModel;

//        @Data
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

    public String getHospitalName() {
        return hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public String getHospitalEmail() {
        return hospitalEmail;
    }

    public String getHospitalPhone() {
        return hospitalPhone;
    }

    public String getAboutHospital() {
        return aboutHospital;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public String getHospitalImage() {
        return hospitalImage;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public void setHospitalEmail(String hospitalEmail) {
        this.hospitalEmail = hospitalEmail;
    }

    public void setHospitalPhone(String hospitalPhone) {
        this.hospitalPhone = hospitalPhone;
    }

    public void setAboutHospital(String aboutHospital) {
        this.aboutHospital = aboutHospital;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setHospitalImage(String hospitalImage) {
        this.hospitalImage = hospitalImage;
    }
}
    }