package edu.csumb.flightapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GoToFlightActivity extends Activity {

    public static final String GoToFlight = "GoToFlight";

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(GoToFlight, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_flights);

        // Yes Button for going to flights
        Button yes_flight = findViewById(R.id.yes_flight);
        yes_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(GoToFlight, "onClick yes called");
                Intent intent = new Intent(GoToFlightActivity.this, AddFlightActivity.class);
                startActivity(intent);
            }
        });

        // No button to go home
        Button no_flight = findViewById(R.id.no_flight);
        no_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(GoToFlight, "onClick no called");
                Intent intent = new Intent(GoToFlightActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }
}
