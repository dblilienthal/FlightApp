package edu.csumb.flightapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.csumb.flightapp.model.FlightRoom;
import edu.csumb.flightapp.model.User;

public class UserLoginActivity extends Activity {

    private static final String UserLogin = "User Login Activity";
    private int attempts = 0;

    public static String logged_in_user;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(UserLogin, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Button login_button = findViewById(R.id.user_login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(UserLogin, "onClick called");
                EditText username = findViewById(R.id.user_username);
                EditText password = findViewById(R.id.user_password);

                Log.d(UserLogin, "Username: "+username.getText().toString()+" Password: "+password.getText().toString());

                if (attempts != 2){

                    User user = FlightRoom.getFlightRoom(UserLoginActivity.this).dao().searchForUser(username.getText().toString(), password.getText().toString());

                    if (user != null){
                        // Go to the autheticated user page
                        logged_in_user = username.getText().toString();
                        Intent intent = new Intent(UserLoginActivity.this, UserAuthenticatedBookFlightActivity.class);
                        startActivity(intent);
                    }
                    TextView msg = findViewById(R.id.user_message);
                    msg.setText("Incorrect Username");
                    attempts++;
                    return;

                } else {
                    // If there has been two attempts
                    Log.d(UserLogin, "There has been two unsuccessful attempts");
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserLoginActivity.this);
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
