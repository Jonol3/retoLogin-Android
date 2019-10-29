package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;
import retoLogin.exceptions.BadLoginException;
import retoLogin.exceptions.BadPasswordException;
import retoLogin.exceptions.NoThreadAvailableException;
import retoLogin.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.LoginException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    Button button2;
    EditText editText;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.btnLogin);
        button.setOnClickListener(this);
        button = (Button)findViewById(R.id.btnSignUp);
        button2.setOnClickListener(this);
        editText = (EditText)findViewById(R.id.tfLogin);
        editText2 = (EditText)findViewById(R.id.pfPassword);
    }


    public int handleLoginButtonAction(String login, String passwd){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(login);
        boolean specialChars = m.find();
        //THE LIMITER SHOULD DO ITS JOB, BUT STILL I AM CHECKING THE LENGTH
        //JUST IN CASE...
        if(login.length()>30 || specialChars){
            /*Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Invalid username.");
            alert.setHeaderText(null);
            alert.setContentText("You must enter a valid username.");

            alert.showAndWait();

            */
            return 1;
        }else if(login.length()<1 || passwd.length()<1){
            /*
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Empty username/password.");
            alert.setHeaderText(null);
            alert.setContentText("You must enter a username and a password.");

            alert.showAndWait();
            */

            return 2;
        }else{
            try{
                User user = new User();
                user.setLogin(login);
                user.setPassword(passwd);


                Client client = ClientFactory.getClient();
                user = client.loginUser(user);

                //TRY TO CONNECT AND ALL THAT MOVIDA
            }catch(LoginException e){
                /*
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Unexpected error");

                alert.showAndWait();
                 */
            }catch(BadLoginException e){
                /*
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Busy server. Please wait.");

                alert.showAndWait();
                */
            }catch(NoThreadAvailableException e){
                /*
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Invalid User.");
                alert.setHeaderText(null);
                alert.setContentText("The user you have entered is not correct.");

                alert.showAndWait();
                 */
            }catch(BadPasswordException e){
                /*
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Empty username/password.");
                alert.setHeaderText(null);
                alert.setContentText("The password you have entered is not correct.");

                alert.showAndWait();
                 */
            }

            /*
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass()
                        .getResource("view/signOut.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            FXMLDocumentControllerSignOut controller = new FXMLDocumentControllerSignOut();
            controller.setUser(user);
            controller.initStage(root);
             */
        }
        return 3;
    }

    public void handleSignUpButtonAction(){
        //ESTO IRÍA AL SIGN UP
    }
    public void onClick(View view){
        switch(view.getId()){
            case "button":
                handleLoginButtonAction(editText.getText().toString(), editText2.getText().toString();
                ;
            case "button2":
                handleSignUpButtonAction();
                ;
        }
    }
}
