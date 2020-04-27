package edu.csumb.flightapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import edu.csumb.flightapp.model.Flight;
import edu.csumb.flightapp.model.FlightRoom;

public class ShowSearchedFlightsActivity extends Activity {

    List<Flight> flights;


    protected void onCreate(Bundle savedInstanceState) {
        //Log.d(SearchFlightActivity, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_flights);

        //Button for searching for flights
        Button yes_flight = findViewById(R.id.yes_flight);
        yes_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(SearchFlightActivity, "onClick called");


                flights = FlightRoom.getFlightRoom(ShowSearchedFlightsActivity.this).dao().searchFlight(SearchFlightActivity.getFlightDeparture().getText().toString(), SearchFlightActivity.getFlightArrival().getText().toString());
                //Log.d(SHOWFLIGHT_ACTIVITY, "flights count "+flights.size());

                ListView flights_view = findViewById(R.id.searched_flight_list);
                flights_view.setAdapter( new FlightListAdapter( this, flights) );

            }
        });
    }

    public class FlightListAdapter extends ArrayAdapter<Flight> {

        public FlightListAdapter (View.OnClickListener context, List<Flight> flights){
            super((Context) context, R.layout.row_layout , flights);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater=ShowSearchedFlightsActivity.this.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.row_layout, null,true);
            TextView rowField = rowView.findViewById(R.id.row_id);

            //set the value of a row in the ListView to the flight info using toString()
            rowField.setText(flights.get(position).toString());
            return rowView;
        }

    }
}
