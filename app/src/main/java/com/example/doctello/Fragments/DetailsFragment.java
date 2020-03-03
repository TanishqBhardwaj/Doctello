package com.example.doctello.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doctello.Activity.MainActivity;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.R;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.detailsData;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {

    private EditText name_edit_text;
    private EditText email_edit_text;
    private EditText dob_edit_text;
    private EditText password_edit_text;
    private EditText confirm_password_edit_text;
    private Spinner gender_drop_down;
    private JsonApiHolder jsonApiHolder;
    private prefUtils sp;
    private String message;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[a-zA-Z])" +
            "(?=.*[@#$%^&+=])" +
            "(?=\\S+$)" +
            ".{4,}" +
            "$");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        name_edit_text = view.findViewById(R.id.name_edit_text);
        email_edit_text = view.findViewById(R.id.email_edit_text);
        dob_edit_text = view.findViewById(R.id.dob_edit_text);
        password_edit_text = view.findViewById(R.id.details_password_edit_text);
        confirm_password_edit_text = view.findViewById(R.id.details_confirm_password_edit_text);
        gender_drop_down = view.findViewById(R.id.gender_drop_down);
        Button confirm_button = view.findViewById(R.id.confirm_details_button);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp = new prefUtils(getContext());

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkName() || !checkEmail() || !checkDOB() || !checkPassword() ||
                    !checkConfirmPassword()){
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = name_edit_text.getText().toString().trim();
                String email = email_edit_text.getText().toString().trim();
                String dob = dob_edit_text.getText().toString().trim();
                String password = password_edit_text.getText().toString().trim();
                String confirm_password = confirm_password_edit_text.getText().toString().trim();
                String gender =gender_drop_down.getSelectedItem().toString();
                confirmDetails(name , email , dob , password , confirm_password , gender);

            }
        });

    }

    private void confirmDetails(String name, String email,
                                String dob, String password, String confirm_password,
                                String gender) {


        detailsData data = new detailsData(name , email , gender , dob , password , confirm_password);
        Call<String> call = jsonApiHolder.enterDetails(SignUpFragment.user_id , data);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String token = response.body();
                    sp.createLogin(token);
                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
                else {
                    String m = response.body();
                    Toast.makeText(getContext(), String.valueOf(m), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean checkName(){
        String name = name_edit_text.getText().toString().trim();
        message = "Invalid Name!";
        return name.length() != 0;
    }

    private boolean checkEmail(){
        String email = email_edit_text.getText().toString().trim();
        message = "Invalid Email!";
        if(email.length() == 0){
            message = "Email can't be empty!";
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            message = "Invalid Email!";
            return false;
        }
        else{
            return true;
        }
    }

    private boolean checkDOB(){
        String dob = dob_edit_text.getText().toString().trim();
        message = "Invalid DOB!";
        return dob.length() != 0;
    }

    private boolean checkPassword(){
        String password = password_edit_text.getText().toString().trim();
        if(password.length() < 6){
            message = "Password should be at least 6 character long!";
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(password).matches()) {
            message = "Password should have at least 1 special character and 1 capital letter!";
            return false;
        }
        return true;
    }

    private boolean checkConfirmPassword(){
        String p = password_edit_text.getText().toString().trim();
        String p2 = confirm_password_edit_text.getText().toString().trim();
        return p.equals(p2);
    }
}
