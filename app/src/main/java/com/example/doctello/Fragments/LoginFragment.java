package com.example.doctello.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.doctello.Activity.MainActivity;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.R;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.loginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private EditText email_edit_text;
    private EditText password_edit_text;
    private JsonApiHolder jsonApiHolder;
    private prefUtils sp;
    private String message;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.log_in_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        email_edit_text = view.findViewById(R.id.signup_email_address_edit_text);
        password_edit_text = view.findViewById(R.id.signup_password_edit_text);
        Button login_button = view.findViewById(R.id.login_button);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp = new prefUtils(getContext());
        TextView sign_up = view.findViewById(R.id.sign_up_here_text_view);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container_login ,
                        new SignUpFragment()).commit();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                if(!validatePassword() | !validateEmail()) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    return;
                }
                    String email = email_edit_text.getText().toString().trim();
                    String password = password_edit_text.getText().toString().trim();
                    login(email, password);
            }
        });

    }

    private void closeKeyboard() {

        View view = getActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }

    private void login(String email, String password) {
        loginData data = new loginData(email , password);
        Call<String> call = jsonApiHolder.login(data);

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


    private boolean validateEmail(){

        String email = email_edit_text.getText().toString().trim();

        if(email.isEmpty()){
            message = "Invalid Email!";
            return false;
        }
        else{
            return true;
        }
    }

    private boolean validatePassword(){

        String password = password_edit_text.getText().toString().trim();

        if(password.isEmpty()){
            message = "Invalid Password!";
            return false;
        }
        else{
            return true;
        }
    }

}
