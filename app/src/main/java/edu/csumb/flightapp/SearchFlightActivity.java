package edu.csumb.flightapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import edu.csumb.flightapp.model.Flight;
import edu.csumb.flightapp.model.FlightRoom;

public class SearchFlightActivity extends Activity {
    public static final String SearchFlightActivity = "Search for flights";
    private static EditText flightDeparture;
    private static EditText flightArrival;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(SearchFlightActivity, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_searched_flights);

        Button yes_flight = findViewById(R.id.yes_flight);
        yes_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(SearchFlightActivity, "onClick called");

                EditText departure= findViewById(R.id.departureField);
                EditText arrival = findViewById(R.id.arrivalField);
                flightArrival = arrival;
                flightDeparture = departure;

                List<Flight> flight = FlightRoom.getFlightRoom(SearchFlightActivity.this).dao().searchFlight(arrival.getText().toString(), departure.getText().toString());
                if (flight != null){
                    Log.d(SearchFlightActivity, "Departure: "+departure.getText().toString()+" Arrival: "+arrival.getText().toString());

                    Intent intent = new Intent(SearchFlightActivity.this, AddFlightActivity.class);
                    startActivity(intent);
                }
                TextView msg = findViewById(R.id.search_flight_error_field);
                msg.setText("No Flights Found");
            }
        });
    }

    public static EditText getFlightArrival() {
        return flightArrival;
    }

    public void setFlightArrival(EditText flightArrival) {
        this.flightArrival = flightArrival;
    }

    public static EditText getFlightDeparture() {
        return flightDeparture;
    }

    public void setFlightDeparture(EditText flightDeparture) {
        this.flightDeparture = flightDeparture;
    }
}
