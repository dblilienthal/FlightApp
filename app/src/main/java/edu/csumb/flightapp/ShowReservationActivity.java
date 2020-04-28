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
import edu.csumb.flightapp.model.Reservation;

public class ShowReservationActivity extends Activity {

    private static final String UserAuthCancelFlight = "Show Res Cancel Act";

    List<Reservation> flights;

    public static List<Reservation> cancelFlight;
    public static String cancel_username;
    public static String cancel_Flight_Number;
    public static String cancel_departure;
    public static String cancel_arrival;
    public static int cancel_number_of_tickets;
    public static double cancel_total_amount;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(UserAuthCancelFlight, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reservation);

        flights = FlightRoom.getFlightRoom(ShowReservationActivity.this).dao().getReservationsByUsername(CancelReservationLoginActivity.Cancel_Reservation_User);

        Log.d(UserAuthCancelFlight, "FlightSize: "+flights.size());
        if (flights.size() == 0){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ShowReservationActivity.this);
            builder.setTitle("Sorry you do not have any flights");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        ListView flights_view = findViewById(R.id.authenticated_cancel_flight_list);
        flights_view.setAdapter( new ShowReservationActivity.FlightListAdapter( this, flights) );

        // This button will go to the confirm book screen
        Button book_flight_button = findViewById(R.id.authenticated_cancel_book_flight);
        book_flight_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Log.d(UserAuthCancelFlight, "cancel flight button hit.");
                EditText flightNo = findViewById(R.id.authenticated_cancel_flight_field);
                Log.d(UserAuthCancelFlight, "Flight Number: "+flightNo.getText().toString());

                cancelFlight =  FlightRoom.getFlightRoom(ShowReservationActivity.this).dao().getReservationsByUsernameAndFlightNo(CancelReservationLoginActivity.Cancel_Reservation_User,flightNo.getText().toString());
                cancel_username = CancelReservationLoginActivity.Cancel_Reservation_User;
                cancel_Flight_Number = cancelFlight.get(0).getFlightNo();
                cancel_departure = cancelFlight.get(0).getDeparture();
                cancel_arrival = cancelFlight.get(0).getArrival();
                cancel_number_of_tickets = cancelFlight.get(0).getTickets();
                cancel_total_amount = cancelFlight.get(0).getTotal();

                Log.d(UserAuthCancelFlight, "booked_username: "+cancel_username+
                        " \nbooked_Flight_Number: "+ cancel_Flight_Number+
                        "\nbooked_departure: "+ cancel_departure+
                        "\nbooked_arrival: "+cancel_arrival+
                        "\nbooked_number_of_tickets: "+cancel_number_of_tickets+
                        "\n booked_total_amount: "+cancel_total_amount);

                if (flightNo == null){
                    Log.d(UserAuthCancelFlight, "Flight number is wrong");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowReservationActivity.this);
                    builder.setTitle("Wrong Flight Number");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            return;
                        }
                    });
                }
                Log.d(UserAuthCancelFlight, "Flight is correct, going to cancel the flight now.");
                Intent intent = new Intent(ShowReservationActivity.this, ConfirmCancelActivity.class);
                startActivity(intent);
            }
        });
    }

    public class FlightListAdapter extends ArrayAdapter<Reservation> {

        public FlightListAdapter (Activity context, List<Reservation> flights){
            super(context, R.layout.row_layout , flights);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater=ShowReservationActivity.this.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.row_layout, null,true);
            TextView rowField = rowView.findViewById(R.id.row_id);

            //set the value of a row in the ListView to the flight info using toString()
            rowField.setText(flights.get(position).toString());
            return rowView;
        }
    }
}
