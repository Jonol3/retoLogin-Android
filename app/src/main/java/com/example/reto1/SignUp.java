package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retoLogin.User;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private EditText fullName;
    private EditText email;
    private EditText login;
    private EditText password;
    private EditText confirmPassword;
    private Button btnCancel;
    private Button btnRedo;
    private Button btnUndo;
    private Button btnSignUp;
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = (EditText)findViewById(R.id.tfFullName);
        email = (EditText)findViewById(R.id.tfEmail);
        login = (EditText)findViewById(R.id.tfLogin);
        password = (EditText)findViewById(R.id.pfPassword);
        confirmPassword = (EditText)findViewById(R.id.pfConfirm);

        btnCancel = (Button)findViewById(R.id.btnBack);
        btnCancel.setOnClickListener(this);
        btnRedo = (Button)findViewById(R.id.btnRedo);
        btnRedo.setOnClickListener(this);
        btnUndo = (Button)findViewById(R.id.btnUndo);
        btnUndo.setOnClickListener(this);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Toast.makeText(this,"Back button pressed",Toast.LENGTH_SHORT);
                break;
            case R.id.btnUndo:
                Toast.makeText(this,"Undo button pressed",Toast.LENGTH_SHORT);
                user.setFullName(fullName.getText().toString());
                user.setEmail(email.getText().toString());
                user.setLogin(login.getText().toString());
                fullName.setText("");
                email.setText("");
                login.setText("");
                password.setText("");
                confirmPassword.setText("");
                break;
            case R.id.btnRedo:
                Toast.makeText(this,"Redo button pressed",Toast.LENGTH_SHORT);
                fullName.setText(user.getFullName());
                email.setText(user.getEmail());
                login.setText(user.getLogin());
                break;
            case R.id.btnSignUp:
                Toast.makeText(this,"Sign Up button pressed",Toast.LENGTH_SHORT);
                if(!password.getText().equals(confirmPassword.getText())){
                    Toast.makeText(this,"The passwords doesn't match",Toast.LENGTH_SHORT);
                }else if(login.getText().equals("")||email.getText().equals("")||fullName.getText().equals("")||
                    password.getText().equals("")||confirmPassword.getText().equals("")){
                    Toast.makeText(this,"Some field is empty",Toast.LENGTH_SHORT);
                }
                break;
        }
    }
}
