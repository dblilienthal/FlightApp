package edu.csumb.flightapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class UserAuthenticatedBookFlightActivity extends Activity {

    private static final String UserAuthBookFlight = "UserAuthBookFlight";

    List<Flight> flights;

    public static Flight bookedFlight;
    public static String booked_username;
    public static String booked_Flight_Number;
    public static String booked_departure;
    public static String booked_arrival;
    public static int booked_number_of_tickets;
    public static double booked_total_amount;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(UserAuthBookFlight, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authenticated_book_flight);

        flights = FlightRoom.getFlightRoom(UserAuthenticatedBookFlightActivity.this).dao().searchFlight(SearchFlightActivity.getFlightDeparture().getText().toString(), SearchFlightActivity.getFlightArrival().getText().toString());

        ListView flights_view = findViewById(R.id.authenticated_searched_flight_list);
        flights_view.setAdapter( new UserAuthenticatedBookFlightActivity.FlightListAdapter( this, flights) );

        // This button will go to the confirm book screen
        Button book_flight_button = findViewById(R.id.authenticated_book_flight);
        book_flight_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Log.d(UserAuthBookFlight, "Book flight button hit.");
                EditText flightNo = findViewById(R.id.authenticated_book_flight_field);

                bookedFlight =  FlightRoom.getFlightRoom(UserAuthenticatedBookFlightActivity.this).dao().getFlightByFlightNo(flightNo.getText().toString());
                booked_username = UserLoginActivity.logged_in_user;
                booked_Flight_Number = bookedFlight.getFlightNo();
                booked_departure = bookedFlight.getDeparture();
                booked_arrival = bookedFlight.getArrival();
                booked_number_of_tickets = SearchFlightActivity.numberOfTicketsSearched;
                booked_total_amount = SearchFlightActivity.numberOfTicketsSearched * bookedFlight.getPrice();

                Log.d(UserAuthBookFlight, "booked_username: "+booked_username+
                        " \nbooked_Flight_Number: "+ booked_Flight_Number+
                        "\nbooked_departure: "+ booked_departure+
                        "\nbooked_arrival: "+booked_arrival+
                        "\nbooked_number_of_tickets: "+booked_number_of_tickets+
                        "\n booked_total_amount: "+booked_total_amount);

                if (flightNo == null){
                    Log.d(UserAuthBookFlight, "Flight number is wrong");
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserAuthenticatedBookFlightActivity.this);
                    builder.setTitle("Wrong Flight Number");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            return;
                        }
                    });
                }
                Log.d(UserAuthBookFlight, "Flight is correct, going to confirm the flight now.");
                Intent intent = new Intent(UserAuthenticatedBookFlightActivity.this, ConfirmFlightActivity.class);
                startActivity(intent);
            }
        });
    }

    public class FlightListAdapter extends ArrayAdapter<Flight> {

        public FlightListAdapter (Activity context, List<Flight> flights){
            super(context, R.layout.row_layout , flights);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater=UserAuthenticatedBookFlightActivity.this.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.row_layout, null,true);
            TextView rowField = rowView.findViewById(R.id.row_id);

            //set the value of a row in the ListView to the flight info using toString()
            rowField.setText(flights.get(position).toString());
            return rowView;
        }
    }
}
