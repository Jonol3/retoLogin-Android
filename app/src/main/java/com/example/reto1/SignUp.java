package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reto1.control.Client;
import com.example.reto1.control.ClientFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retoLogin.User;
import retoLogin.exceptions.AlreadyExistsException;
import retoLogin.exceptions.NoThreadAvailableException;
import retoLogin.exceptions.RegisterException;

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

    private final String REGULAREXPRESSION = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();

        fullName = (EditText)findViewById(R.id.tfFullName);
        email = (EditText)findViewById(R.id.tfEmail);
        login = (EditText)findViewById(R.id.tfLogin);
        password = (EditText)findViewById(R.id.pfPassword);
        confirmPassword = (EditText)findViewById(R.id.pfConfirm);

        btnCancel = (Button)findViewById(R.id.btnBack);
        btnCancel.setOnClickListener(this);
        btnRedo = (Button)findViewById(R.id.btnRedo);
        btnRedo.setOnClickListener(this);
        btnRedo.setEnabled(false);
        btnUndo = (Button)findViewById(R.id.btnUndo);
        btnUndo.setOnClickListener(this);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Toast.makeText(this,"Back button pressed",Toast.LENGTH_SHORT).show();
                finish();//Go to the login activity
                break;
            case R.id.btnUndo:
                Toast.makeText(this,"Undo button pressed",Toast.LENGTH_SHORT).show();
                user.setFullName(fullName.getText().toString());
                user.setEmail(email.getText().toString());
                user.setLogin(login.getText().toString());
                fullName.setText("");
                email.setText("");
                login.setText("");
                password.setText("");
                confirmPassword.setText("");
                btnRedo.setEnabled(true);
                break;
            case R.id.btnRedo:
                Toast.makeText(this,"Redo button pressed",Toast.LENGTH_SHORT).show();
                fullName.setText(user.getFullName());
                email.setText(user.getEmail());
                login.setText(user.getLogin());
                btnRedo.setEnabled(false);
                break;
            case R.id.btnSignUp:
                Toast.makeText(this,"Sign Up button pressed",Toast.LENGTH_SHORT).show();
                Pattern pattern = Pattern.compile(REGULAREXPRESSION);
                Matcher matcher = pattern.matcher(email.getText());
                if(!password.getText().equals(confirmPassword.getText())){
                    Toast.makeText(this,"The passwords doesn't match",Toast.LENGTH_SHORT).show();
                }else if(login.getText().equals("")||email.getText().equals("")||fullName.getText().equals("")||
                    password.getText().equals("")||confirmPassword.getText().equals("")){
                    Toast.makeText(this,"Some field is empty",Toast.LENGTH_SHORT).show();
                }else if(login.getText().length()>50){
                    Toast.makeText(this,"Error: login too long",Toast.LENGTH_SHORT).show();
                }else if(email.getText().length()>80){
                    Toast.makeText(this,"Error: The email is too long",Toast.LENGTH_SHORT).show();
                }else if(fullName.getText().length()>85){
                    Toast.makeText(this,"Error: Full Name too long",Toast.LENGTH_SHORT).show();
                }else if(password.getText().length()>200 || confirmPassword.getText().length()>200){
                    Toast.makeText(this,"Error: Password is too long",Toast.LENGTH_SHORT).show();
                }else if(!matcher.matches()){
                    Toast.makeText(this,"Error: The email doesn't match the minimum requirements",Toast.LENGTH_SHORT).show();
                }else{
                    //TODO
                    //Check the things on the database and if everything goes well then go to Log Out activity
                    user.setLogin(login.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.setFullName(fullName.getText().toString());
                    user.setPassword(password.getText().toString());
                    try{
                        Client client =
                                ClientFactory.getClient(getResources()
                                        .getString(R.string.serverIp), getResources().getInteger(R.integer.serverPort));
                        client.registerUser(user);

                    }catch (NoThreadAvailableException e){
                        Toast.makeText(this,"Error: The server is bussy right now, please try again in a few minutes",Toast.LENGTH_LONG).show();
                    }catch (RegisterException e){
                        Toast.makeText(this,"Error: Cannot register the new user",Toast.LENGTH_LONG).show();
                    } catch (AlreadyExistsException e){
                        Toast.makeText(this,"Error: The user with the login you are trying to register already exists",Toast.LENGTH_LONG).show();
                    }finally{
                        Intent intent = new Intent(this, LogOut.class);
                        intent.putExtra("USER", user);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                    }
                }
                break;
        }
    }
}
