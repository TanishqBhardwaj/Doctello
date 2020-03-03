package com.example.doctello.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class HospitalServicesData {

    @SerializedName("HospitalID")
    private Integer hospital_id;
    @SerializedName("ServiceModel")
    private serviceModel modelData;

//    @Data
    public static class serviceModel {

        @SerializedName("ServiceID")
        private Integer service_id;
        @SerializedName("ServiceName")
        private String service_name;

    public Integer getService_id() {
        return service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }
}


}
