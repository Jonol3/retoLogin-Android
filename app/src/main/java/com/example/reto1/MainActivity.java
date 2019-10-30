package com.example.reto1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retoLogin.exceptions.BadLoginException;
import retoLogin.exceptions.BadPasswordException;
import retoLogin.exceptions.LoginException;
import retoLogin.exceptions.NoThreadAvailableException;
import retoLogin.User;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.reto1.control.Client;
import com.example.reto1.control.ClientFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonLogin;
    Button buttonSignup;
    EditText tfLogin;
    EditText pfPassword;

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
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("You must enter a valid username.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return 1;
        }else if(login.length()<1 || passwd.length()<1){
            /*
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Empty username/password.");
            alert.setHeaderText(null);
            alert.setContentText("You must enter a username and a password.");

            alert.showAndWait();
            */

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("You must enter a username and a password.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

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

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Unexpected error.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }catch(BadLoginException e){
                /*
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Busy server. Please wait.");

                alert.showAndWait();
                */
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Enter a valid username.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }catch(NoThreadAvailableException e){
                /*
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Invalid User.");
                alert.setHeaderText(null);
                alert.setContentText("The user you have entered is not correct.");

                alert.showAndWait();
                 */
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Busy server.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }catch(BadPasswordException e){
                /*
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Empty username/password.");
                alert.setHeaderText(null);
                alert.setContentText("The password you have entered is not correct.");

                alert.showAndWait();
                 */
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("The password you have entered is not correct.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
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
        if(view.getId()==buttonLogin.getId() ) {

            handleLoginButtonAction(tfLogin.getText().toString(), pfPassword.getText().toString());
        }
           else if(view.getId()==buttonSignup.getId()){
                handleSignUpButtonAction();
        }
    }
}
