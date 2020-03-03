package com.example.doctello.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileData {

    @SerializedName("UserName")
    private String UserName;

    @SerializedName("UserPhone")
    private String UserPhone;
}
