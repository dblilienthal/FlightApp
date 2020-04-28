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

public class CancelReservationLoginActivity extends Activity {
    private static final String CancelLogin = "User Login Activity";
    private int attempts = 0;

    public static String Cancel_Reservation_User;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(CancelLogin, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation_login);

        Button login_button = findViewById(R.id.cancel_reservation_login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(CancelLogin, "onClick called");
                EditText username = findViewById(R.id.cancel_reservation_username);
                EditText password = findViewById(R.id.cancel_reservation_password);

                Log.d(CancelLogin, "Username: "+username.getText().toString()+" Password: "+password.getText().toString());

                if (attempts != 2){

                    User user = FlightRoom.getFlightRoom(CancelReservationLoginActivity.this).dao().searchForUser(username.getText().toString(), password.getText().toString());

                    if (user != null){
                        // Go to the autheticated user page
                        Cancel_Reservation_User = username.getText().toString();
                        Log.d(CancelLogin, "This is the username of the canceled: "+Cancel_Reservation_User);
                        Intent intent = new Intent(CancelReservationLoginActivity.this, ShowReservationActivity.class);
                        startActivity(intent);
                    }
                    TextView msg = findViewById(R.id.cancel_reservation_error_message);
                    msg.setText("Incorrect Username or Password");
                    attempts++;
                    return;

                } else {
                    // If there has been two attempts
                    Log.d(CancelLogin, "There has been two unsuccessful attempts");
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CancelReservationLoginActivity.this);
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
