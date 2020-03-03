package com.example.doctello.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.R;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.signUpData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    private JsonApiHolder jsonApiHolder;
    public static int user_id;
    private EditText mobile_no_edit_text;
    private prefUtils sp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mobile_no_edit_text = view.findViewById(R.id.mobile_number_edit_text);
        TextView sign_in_text_view = view.findViewById(R.id.account_exists_text_view);
        sp = new prefUtils(getContext());
        sign_in_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container_login ,
                        new LoginFragment()).commit();
            }
        });
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        Button send_otp_button = view.findViewById(R.id.send_otp_button);

        send_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                if(!validatePhone()){
                    Toast.makeText(getContext(), "Invalid mobile number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String mobile_no = mobile_no_edit_text.getText().toString().trim();
                if(mobile_no.length() == 13){
                    signUp(mobile_no);
                }
                else if(mobile_no.length() == 10){
                    mobile_no = "+91" + mobile_no;
                    signUp(mobile_no);
                }

            }
        });


    }

    private boolean validatePhone() {

        String phone_number = mobile_no_edit_text.getText().toString().trim();

        if(phone_number.isEmpty()){
            return false;
        }
        else if(phone_number.length() < 10){
            return false;
        }
        else{
            return true;
        }
    }

    private void closeKeyboard() {

        View view = getActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }

    private void signUp(String mobile_no) {

        signUpData data = new signUpData(mobile_no);
        Call<Integer> call = jsonApiHolder.signUp(data);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                if (response.isSuccessful()){
                    user_id = response.body();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container_login ,
                            new verifyOtpFragment()).commit();

                }
                else {
                    Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
