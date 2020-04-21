package edu.csumb.flightapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AdminActivity extends AppCompatActivity {

    public static final String ADMIN_ACTIVITY = "AdminActivity";

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
    }
}
