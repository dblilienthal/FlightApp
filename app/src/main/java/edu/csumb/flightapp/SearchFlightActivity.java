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
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import edu.csumb.flightapp.model.Flight;
import edu.csumb.flightapp.model.FlightRoom;

public class SearchFlightActivity extends Activity {
    public static final String SearchFlightActivity = "Search for flights";
    private static EditText flightDeparture;
    private static EditText flightArrival;
    public static int numberOfTicketsSearched;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(SearchFlightActivity, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_flights);

        Button yes_flight = findViewById(R.id.search_flight_button);
        yes_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(SearchFlightActivity, "onClick called");

                EditText departure= findViewById(R.id.departureField);
                EditText arrival = findViewById(R.id.arrivalField);
                EditText numberOfTickets = findViewById(R.id.ticketField);

                if (departure == null || arrival == null || numberOfTickets == null){
                    Log.d(SearchFlightActivity, "Incomplete fields" );
                    TextView msg = findViewById(R.id.search_flight_error_field);
                    msg.setText("Incomplete Fields");
                    return;
                }

                flightArrival = arrival;
                flightDeparture = departure;

                Log.d(SearchFlightActivity, "Number of tickets selected: " +numberOfTickets.getText().toString() );
                int i = Integer.parseInt(numberOfTickets.getText().toString());
                if (i > 7){
                    Log.d(SearchFlightActivity, "Too many tickets selected: "+i );
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchFlightActivity.this);
                    builder.setTitle("Please select 7 or less tickets.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //return;
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                //List<Flight> flight = FlightRoom.getFlightRoom(SearchFlightActivity.this).dao().searchFlight(arrival.getText().toString(), departure.getText().toString());
                List<Flight> flight = FlightRoom.getFlightRoom(SearchFlightActivity.this).dao().searchFlightWithTickets(arrival.getText().toString(), departure.getText().toString(), i);
                // If there are no flights, return error
                if (flight.size() != 0){
                    numberOfTicketsSearched = i;
                    Log.d(SearchFlightActivity, "Departure: "+departure.getText().toString()+" Arrival: "+arrival.getText().toString()+" Number of tickets: "+i);
                    Intent intent = new Intent(SearchFlightActivity.this, ShowSearchedFlightsActivity.class);
                    startActivity(intent);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(SearchFlightActivity.this);
                builder.setTitle("No Flights Found");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SearchFlightActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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
