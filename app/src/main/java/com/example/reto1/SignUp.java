package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {
    private EditText fullName;
    private EditText email;
    private EditText login;
    private EditText password;
    private EditText confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = (EditText)findViewById(R.id.tfFullName);
        email = (EditText)findViewById(R.id.tfEmail);
        login = (EditText)findViewById(R.id.tfLogin);
        password = (EditText)findViewById(R.id.pfPassword);
        confirmPassword = (EditText)findViewById(R.id.pfConfirm);
    }
}
