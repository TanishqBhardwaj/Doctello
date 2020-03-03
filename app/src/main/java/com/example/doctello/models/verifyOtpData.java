package com.example.doctello.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class verifyOtpData {

    @SerializedName("OTP")
    private String otp;
}
