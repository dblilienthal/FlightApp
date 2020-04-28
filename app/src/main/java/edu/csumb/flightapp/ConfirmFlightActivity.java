package edu.csumb.flightapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import java.util.Date;

import edu.csumb.flightapp.model.FlightDao;
import edu.csumb.flightapp.model.FlightRoom;
import edu.csumb.flightapp.model.LogRecord;
import edu.csumb.flightapp.model.Reservation;


public class ConfirmFlightActivity extends Activity {

    private static final String ConfirmFlight = "ConfirmFlight";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfirm_flight);

        Log.d(ConfirmFlight, "onCreate called");

        //Create a new reservation object
        final Reservation res = new Reservation();
        res.setUsername(UserAuthenticatedBookFlightActivity.booked_username);
        res.setFlightNo(UserAuthenticatedBookFlightActivity.booked_Flight_Number);
        res.setDeparture(UserAuthenticatedBookFlightActivity.booked_departure);
        res.setArrival(UserAuthenticatedBookFlightActivity.booked_arrival);
        res.setTickets(UserAuthenticatedBookFlightActivity.booked_number_of_tickets);
        res.setTotal(UserAuthenticatedBookFlightActivity.booked_total_amount);

        Log.d(ConfirmFlight, "Username: " + UserAuthenticatedBookFlightActivity.booked_username +
                                    "\nFlight Number: "+UserAuthenticatedBookFlightActivity.booked_Flight_Number+
                                    "\nDeparture: "+UserAuthenticatedBookFlightActivity.booked_departure+
                                    "\nArrival: "+UserAuthenticatedBookFlightActivity.booked_arrival+
                                    "\nNumber of Tickets: "+UserAuthenticatedBookFlightActivity.booked_number_of_tickets+
                                    "\nReservation number: "+res.getId()+
                                    "\nTotal Amount: "+UserAuthenticatedBookFlightActivity.booked_total_amount);

        // Populate the fields
        final TextView username = findViewById(R.id.confirm_username);
        username.setText(UserAuthenticatedBookFlightActivity.booked_username);

        TextView flight_number = findViewById(R.id.confirm_flight_number);
        flight_number.setText(UserAuthenticatedBookFlightActivity.booked_Flight_Number);

        TextView departure = findViewById(R.id.confirm_departure);
        departure.setText(UserAuthenticatedBookFlightActivity.booked_departure);

        TextView arrival = findViewById(R.id.confirm_arrival);
        arrival.setText((UserAuthenticatedBookFlightActivity.booked_arrival));

        TextView tickets = findViewById(R.id.confirm_tickets);
        tickets.setText(Integer.toString(res.getTickets()));

        TextView reservation = findViewById(R.id.confirm_reservation_number);
        reservation.setText(Long.toString(res.getId()));

        TextView total_amount = findViewById(R.id.confirm_total_amount);
        total_amount.setText(Double.toString(UserAuthenticatedBookFlightActivity.booked_total_amount));

        Button confirm_flight = findViewById(R.id.confirm_flight);
        confirm_flight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.d(ConfirmFlight, "Confirmed Button Hit");

                //Add the reservation to the Log
                FlightDao dao = FlightRoom.getFlightRoom(ConfirmFlightActivity.this).dao();
                LogRecord rec = new LogRecord(2, UserAuthenticatedBookFlightActivity.booked_username, "Username: " + UserAuthenticatedBookFlightActivity.booked_username +
                        "\nFlight Number: "+UserAuthenticatedBookFlightActivity.booked_Flight_Number+
                        "\nDeparture: "+UserAuthenticatedBookFlightActivity.booked_departure+
                        "\nArrival: "+UserAuthenticatedBookFlightActivity.booked_arrival+
                        "\nNumber of Tickets: "+UserAuthenticatedBookFlightActivity.booked_number_of_tickets+
                        "\nReservation number: "+res.getId()+
                        "\nTotal Amount: "+UserAuthenticatedBookFlightActivity.booked_total_amount);
                dao.addLogRecord(rec);

                //Add the reservation to the database
                dao.addReservation(res);

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmFlightActivity.this);
                builder.setTitle("Flight has been confirmed!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                Intent intent = new Intent(ConfirmFlightActivity.this, MainActivity.class);
                startActivity(intent);
                return;
            }
        });
    }
}
