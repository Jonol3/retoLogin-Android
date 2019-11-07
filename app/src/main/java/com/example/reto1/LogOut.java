package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;
import retoLogin.User;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Class for the LogOut activity
 * @author Jon Calvo Gaminde
 */
public class LogOut extends AppCompatActivity implements View.OnClickListener {
    private User user = new User();
    private TextView tvGreeting;
    private Button btnLogOut;
    private MediaPlayer mp;

    /*
     * Initializes the Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("USER");

        tvGreeting = (TextView) findViewById(R.id.tvGreeting);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);

        tvGreeting.setText(getResources().getString(R.string.greeting) + " " + user.getFullName());

        btnLogOut.setOnClickListener(this);
        mp = MediaPlayer.create(this, R.raw.button);
    }

    /**
     * Checks if the LogOut button has been clicked, and finishes the Activity
     * @param view The current view
     */
    @Override
    public void onClick(View view) {
        mp.start();
        finish();
    }
}
