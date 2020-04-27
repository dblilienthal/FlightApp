package edu.csumb.flightapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AdminActivity extends AppCompatActivity {

    public static final String ADMIN_ACTIVITY = "AdminActivity";
    boolean flag = false;
    public static boolean hasCreatedFlight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(ADMIN_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.adminToolbar);
        setSupportActionBar(toolbar);

        // button to show all flights
        Button flight_button = findViewById(R.id.show_flights);
        flight_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( ADMIN_ACTIVITY, "onClick show flights called");
                Intent intent = new Intent(AdminActivity.this, ShowFlightActivity.class);
                startActivity(intent);
            }
        });

        // button to add a flights
        Button add_flight_button = findViewById(R.id.add_flights);
        add_flight_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( ADMIN_ACTIVITY, "onClick show flights called");
                Intent intent = new Intent(AdminActivity.this, AddFlightActivity.class);
                startActivity(intent);
            }
        });

        // button to show all logs
        Button log_button = findViewById(R.id.show_log);
        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( ADMIN_ACTIVITY, "onClick show Log Called");
                Intent intent = new Intent(AdminActivity.this, ShowLogRecords.class);
                startActivity(intent);
            }
        });

        // button to return home. But first, it asks if you want to create a flight
        Button home_button = findViewById(R.id.return_button_home_admin);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome(flag);
                Log.d( ADMIN_ACTIVITY, "onClick return home Called");
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("Did you want to create a flight?.");
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flag = true;
                        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                        startActivity(intent);
                        //finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void goHome(boolean b){
        if (b){
            Intent intent = new Intent(AdminActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
