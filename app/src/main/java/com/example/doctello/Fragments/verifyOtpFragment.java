package com.example.doctello.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.R;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.verifyOtpData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class verifyOtpFragment extends Fragment {

    private JsonApiHolder jsonApiHolder;
    private EditText otp_edit_text;
    Button resend_otp_button;
    private TextView timer_text_view;
    private CountDownTimer countDownTimer;
    private long timeLeft = 120000;
    private boolean timerRunning = false;
    private TextView resend_otp_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.verify_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        otp_edit_text = view.findViewById(R.id.enter_otp_edit_text);
        Button confirm_otp_button = view.findViewById(R.id.verify_otp_button);
        resend_otp_button = view.findViewById(R.id.resend_otp_button);
        resend_otp_button.setVisibility(View.INVISIBLE);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        timer_text_view = view.findViewById(R.id.timer_text_view);
        resend_otp_text = view.findViewById(R.id.resend_otp_text);
        startTimer();


        confirm_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkOTP()) {
                    String otp = otp_edit_text.getText().toString().trim();
                    stopTimer();
                    timer_text_view.setVisibility(View.INVISIBLE);
                    resend_otp_text.setVisibility(View.INVISIBLE);
                    verifyOtp(otp);
                }
                else{
                    Toast.makeText(getContext(), "Invalid OTP!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resend_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp();
            }
        });

    }

    private void stopTimer() {

        countDownTimer.cancel();
        timerRunning = false;
    }

    private boolean checkOTP() {
        String otp = otp_edit_text.getText().toString().trim();
        return otp.length() != 0;
    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(timeLeft , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();

            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerRunning = true;
    }

    private void updateTimer() {

        int minutes = (int) timeLeft / 60000;
        int seconds = (int) timeLeft % 60000 / 1000;
        String timeLeftText  = "";

        timeLeftText += "" + minutes;
        timeLeftText += ":";
        if(seconds < 10 ){
            timeLeftText += "0";
        }
        timeLeftText += seconds;
        timer_text_view.setText(timeLeftText);
        if(timeLeftText.equals("0:00")){
            timer_text_view.setVisibility(View.INVISIBLE);
            resend_otp_text.setVisibility(View.INVISIBLE);
            resend_otp_button.setVisibility(View.VISIBLE);
            otp_edit_text.setVisibility(View.INVISIBLE);
        }

    }

    private void verifyOtp(String otp) {

        verifyOtpData data=  new verifyOtpData(otp);
        Call<String> call = jsonApiHolder.verifyOtp(data , SignUpFragment.user_id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container_login ,
                            new DetailsFragment()).commit();
                }
                else{
                    String message = response.body();
                    Toast.makeText(getContext(), String.valueOf(message), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                resend_otp_button.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resendOtp() {

        Call<ResponseBody> call = jsonApiHolder.resendOTP(SignUpFragment.user_id );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    resend_otp_button.setVisibility(View.INVISIBLE);
                    resend_otp_text.setVisibility(View.VISIBLE);
                    timer_text_view.setVisibility(View.VISIBLE);
                    otp_edit_text.setVisibility(View.VISIBLE);
                    timeLeft = 120000;
                    startTimer();
                }
                else{
                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
