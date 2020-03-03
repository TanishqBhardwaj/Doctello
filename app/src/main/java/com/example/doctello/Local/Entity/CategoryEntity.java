package com.example.doctello.Local.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "category_table")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

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

    public CategoryEntity(int id, String serviceID, String serviceName, String doctorTable, String hospitalServiceTable, String image) {
        this.id = id;
        ServiceID = serviceID;
        ServiceName = serviceName;
        DoctorTable = doctorTable;
        HospitalServiceTable = hospitalServiceTable;
        Image = image;
    }

    public CategoryEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceID() {
        return ServiceID;
    }

    public void setServiceID(String serviceID) {
        ServiceID = serviceID;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getDoctorTable() {
        return DoctorTable;
    }

    public void setDoctorTable(String doctorTable) {
        DoctorTable = doctorTable;
    }

    public String getHospitalServiceTable() {
        return HospitalServiceTable;
    }

    public void setHospitalServiceTable(String hospitalServiceTable) {
        HospitalServiceTable = hospitalServiceTable;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
