package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;
import retoLogin.User;

import android.content.Intent;
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
    }

    /**
     * Checks if the LogOut button has been clicked, and finishes the Activity
     * @param view The current view
     */
    @Override
    public void onClick(View view) {
        finish();
    }
}
