package edu.csumb.flightapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AdminLogin extends AppCompatActivity {

    private static final String ADMIN_LOGIN_ACTIVITY = "AdminLoginActivity";
    private int attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(ADMIN_LOGIN_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Button login_button = findViewById(R.id.admin_login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(ADMIN_LOGIN_ACTIVITY, "onClick called");
                EditText username = findViewById(R.id.adminUsername);
                EditText password = findViewById(R.id.adminPassword);

                Log.d(ADMIN_LOGIN_ACTIVITY, "username: "+username.getText().toString()+" Password: "+password.getText().toString());

                //tryagain:
                if (attempts != 2){

                    if (username.getText().toString().equals("!admiM2") && password.getText().toString().equals("!admiM2")) {
                        Log.d(ADMIN_LOGIN_ACTIVITY, "Successful Admin Login");
                        Intent intent = new Intent(AdminLogin.this, ShowLogRecords.class);
                        startActivity(intent);
                    }
                    Log.d(ADMIN_LOGIN_ACTIVITY, "Invalid Attempt");
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AdminLogin.this);
                    builder.setTitle("Invalid Attempt.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    //TextView msg = findViewById(R.id.admin_message);
                    //msg.setText("Username not available.");
                    attempts++;
                    return;

                } else {
                    // If there has been two attempts
                    Log.d(ADMIN_LOGIN_ACTIVITY, "There has been two unsuccessful attempts");
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AdminLogin.this);
                    builder.setTitle("Too many fails. Please try again.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

    }
}
