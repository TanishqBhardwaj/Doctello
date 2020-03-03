package com.example.doctello.Interface;

import com.example.doctello.models.CategoryData;
import com.example.doctello.models.DoctorsData;
import com.example.doctello.models.HospitalData;
import com.example.doctello.models.HospitalDetailsData;
import com.example.doctello.models.HospitalServicesData;
import com.example.doctello.models.ProfileData;
import com.example.doctello.models.detailsData;
import com.example.doctello.models.loginData;
import com.example.doctello.models.signUpData;
import com.example.doctello.models.verifyOtpData;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApiHolder {

    @POST("User/AddPhone")
    Call<Integer> signUp(@Body signUpData data);

    @POST("User/EnterOTP/{userID}")
    Call<String> verifyOtp(@Body verifyOtpData otp , @Path("userID") int userID );

    @POST("User/EnterDetails/{ID}")
    Call<String> enterDetails(@Path("ID") int ID , @Body detailsData data);

    @POST("User/Login")
    Call<String> login(@Body loginData data);

    @GET("User/HomeServices")
    Call<List<CategoryData>> getCategories(@Query("token") String tokenValue);

    @GET("User/HospitalList/Location/{Location}")
    Call<List<HospitalData>> getHospitals(@Path("Location") String id, @Query("token") String token);

    @GET("User/Home/Doctor/{serviceid}/{Location}")
    Call<List<DoctorsData>> getDoctorsByCategory(@Path("serviceid") String serviceid,
                                                 @Path("Location") String location,
                                                 @Query("token") String token);

    @PUT("User/ResendOTP/{userID}")
    Call<ResponseBody> resendOTP(@Path("userID") int userID);

    @GET("User/Profile")
    Call<List<ProfileData>> getProfileDetails(@Query("token") String tokenValue);

    @GET("User/HospitalDetails/Services/{hospitalId}")
    Call<List<HospitalServicesData>> getHospitalServices(@Path("hospitalId") String hid,
                                                         @Query("token") String token);

    @GET("User/Hospital/Detail/{id}")
    Call<List<HospitalDetailsData>>  getHospitalDetails(@Path("id") String mId,
                                                        @Query("token") String token);
}
