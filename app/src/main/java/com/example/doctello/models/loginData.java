package com.example.doctello.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class loginData {

    @SerializedName("UserEmail")
    private String email;
    @SerializedName("Password")
    private String password;
}
