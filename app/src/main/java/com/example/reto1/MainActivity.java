package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;
import retoLogin.exceptions.BadLoginException;
import retoLogin.exceptions.BadPasswordException;
import retoLogin.exceptions.LoginException;
import retoLogin.exceptions.NoThreadAvailableException;
import retoLogin.User;

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

/**
 * Class for the Login activity
 * @author Daira Eguzkiza, Jon Calvo Gaminde
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonLogin;
    private Button buttonSignup;
    private EditText tfLogin;
    private EditText pfPassword;
    private MediaPlayer mp;


    /**
     * Initializes the Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLogin = (Button)findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(this);
        buttonSignup = (Button)findViewById(R.id.btnSignUp);
        buttonSignup.setOnClickListener(this);
        tfLogin = (EditText)findViewById(R.id.tfLogin);
        pfPassword = (EditText)findViewById(R.id.pfPassword);
        mp = MediaPlayer.create(this, R.raw.button);



    }

    /**
     * This will try to log in the user. Controlls wether the data entered is
     * valid or not.
     * @param login The users login
     * @param passwd The users password
     */
    public void handleLoginButtonAction(String login, String passwd){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(login);
        boolean specialChars = m.find();
        //The limiter should do its job, but this code double checks
        if(login.length()>30 || specialChars){
            Toast.makeText(this,"You must enter a valid username.",Toast.LENGTH_LONG).show();
        }else if(login.length()<1 || passwd.length()<1){
            Toast.makeText(this,"You must enter a username and a password.",Toast.LENGTH_LONG).show();
        }else{
            try{
                mp.start();
                User user = new User();
                user.setLogin(login);
                user.setPassword(passwd);

                Client client = ClientFactory.getClient(getResources().getString(R.string.serverIp), getResources().getInteger(R.integer.serverPort));
                user = client.loginUser(user);

                Intent intent = new Intent(this, LogOut.class);
                intent.putExtra("USER", user);
                setResult(RESULT_OK, intent);
                startActivity(intent);
                tfLogin.setText("");
                pfPassword.setText("");
            }catch(LoginException e){
                Toast.makeText(this, "Unexpected error happened.",Toast.LENGTH_LONG).show();
            }catch(BadLoginException e){
                Toast.makeText(this,"The user you have entered is not correct.",Toast.LENGTH_LONG).show();
            }catch(NoThreadAvailableException e){
                Toast.makeText(this,"Busy server. Please wait.",Toast.LENGTH_LONG).show();
            }catch(BadPasswordException e){
                Toast.makeText(this,"The password you have entered is not correct.",Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * This will try to open the sign up window.
     */
    public void handleSignUpButtonAction(){
        mp.start();
        Intent intent = new Intent(this, SignUp.class);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }

    /**
     * Checks if a button has been clicked, and calls their method
     * @param view The current view
     */
    public void onClick(View view){

        if(view.getId()==buttonLogin.getId() ) {
            handleLoginButtonAction(tfLogin.getText().toString(), pfPassword.getText().toString());
        }
           else if(view.getId()==buttonSignup.getId()){
                handleSignUpButtonAction();
        }
    }
}
