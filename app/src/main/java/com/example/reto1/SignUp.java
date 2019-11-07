package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
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

/**
 * Class for the SignUp activity
 * @author Unai Pérez Sánchez
 */
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
    private MediaPlayer mp;

    private final String REGULAREXPRESSION = "^[A-Za-z0-9+_.-]+@(.+)$";

    /*
     * Initializes the Activity
     */
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
        mp = MediaPlayer.create(this, R.raw.button);
    }

    /**
     * Checks if a button has been clicked, and executes their actions
     * @param v The current view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                mp.start();
                finish();//Go to the login activity
                break;
            case R.id.btnUndo:
                mp.start();
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
                mp.start();
                fullName.setText(user.getFullName());
                email.setText(user.getEmail());
                login.setText(user.getLogin());
                btnRedo.setEnabled(false);
                break;
            case R.id.btnSignUp:
                Pattern pattern = Pattern.compile(REGULAREXPRESSION);
                Matcher matcher = pattern.matcher(email.getText().toString());
                Pattern patt = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher match = patt.matcher(login.getText().toString());
                boolean specialChars = match.find();
                if(specialChars) {
                    Toast.makeText(this,"You must enter a valid username.",Toast.LENGTH_LONG).show();
                }else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Toast.makeText(this,"The passwords doesn't match",Toast.LENGTH_LONG).show();
                }else if(login.getText().toString().equals("")||email.getText().toString().equals("")||fullName.getText().toString().equals("")||
                    password.getText().toString().equals("")||confirmPassword.getText().toString().equals("")){
                    Toast.makeText(this,"Some field is empty",Toast.LENGTH_LONG).show();
                }else if(login.getText().toString().length()>50){
                    Toast.makeText(this,"Error: login too long",Toast.LENGTH_LONG).show();
                }else if(email.getText().toString().length()>80){
                    Toast.makeText(this,"Error: The email is too long",Toast.LENGTH_LONG).show();
                }else if(fullName.getText().toString().length()>85){
                    Toast.makeText(this,"Error: Full Name too long",Toast.LENGTH_LONG).show();
                }else if(password.getText().toString().length()>200 || confirmPassword.getText().length()>200){
                    Toast.makeText(this,"Error: Password is too long",Toast.LENGTH_LONG).show();
                }else if(!matcher.matches()){
                    Toast.makeText(this,"Error: The email doesn't match the minimum requirements",Toast.LENGTH_LONG).show();
                }else {
                    //Check the things on the database and if everything goes well then go to Log Out activity
                    mp.start();
                    user.setLogin(login.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.setFullName(fullName.getText().toString());
                    user.setPassword(password.getText().toString());
                    try {
                        Client client =
                                ClientFactory.getClient(getResources()
                                        .getString(R.string.serverIp), getResources().getInteger(R.integer.serverPort));
                        client.registerUser(user);

                        Intent intent = new Intent(this, LogOut.class);
                        intent.putExtra("USER", user);
                        setResult(RESULT_OK, intent);
                        startActivity(intent);
                        finish();

                    } catch (NoThreadAvailableException e) {
                        Toast.makeText(this, "Error: The server is bussy right now, please try again in a few minutes", Toast.LENGTH_LONG).show();
                    } catch (RegisterException e) {
                        Toast.makeText(this, "Error: Cannot register the new user", Toast.LENGTH_LONG).show();
                    } catch (AlreadyExistsException e) {
                        Toast.makeText(this, "Error: The user with the login you are trying to register already exists", Toast.LENGTH_LONG).show();
                    }
                }
                break;

        }
    }
}
