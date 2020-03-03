package com.example.doctello.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class detailsData {

    @SerializedName("UserName")
    private String user_name;
    @SerializedName("UserEmail")
    private String email;
    @SerializedName("UserGender")
    private String gender;
    @SerializedName("UserDOB")
    private String dob;
    @SerializedName("Password")
    private String password;
    @SerializedName("ConfirmPassword")
    private String confirm_password;
}
