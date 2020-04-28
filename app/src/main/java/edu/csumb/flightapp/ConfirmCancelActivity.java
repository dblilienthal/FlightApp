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

import edu.csumb.flightapp.model.FlightDao;
import edu.csumb.flightapp.model.FlightRoom;
import edu.csumb.flightapp.model.LogRecord;
import edu.csumb.flightapp.model.Reservation;

public class ConfirmCancelActivity extends Activity {

    private static final String CancelFlight = "Cancel Flight";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfirm_cancel);

        Log.d(CancelFlight, "onCreate called");

        //Create a new reservation object
        final Reservation res = new Reservation();
        res.setUsername(ShowReservationActivity.cancel_username);
        res.setFlightNo(ShowReservationActivity.cancel_Flight_Number);
        res.setDeparture(ShowReservationActivity.cancel_departure);
        res.setArrival(ShowReservationActivity.cancel_arrival);
        res.setTickets(ShowReservationActivity.cancel_number_of_tickets);
        res.setTotal(ShowReservationActivity.cancel_total_amount);

        Log.d(CancelFlight, "Username: " + ShowReservationActivity.cancel_username +
                "\nFlight Number: "+ShowReservationActivity.cancel_Flight_Number+
                "\nDeparture: "+ShowReservationActivity.cancel_departure+
                "\nArrival: "+ShowReservationActivity.cancel_arrival+
                "\nNumber of Tickets: "+ShowReservationActivity.cancel_number_of_tickets+
                "\nReservation number: "+res.getId()+
                "\nTotal Amount: "+ShowReservationActivity.cancel_total_amount);

        // Populate the fields
        final TextView username = findViewById(R.id.cancel_username);
        username.setText(ShowReservationActivity.cancel_username);

        TextView flight_number = findViewById(R.id.cancel_flight_number);
        flight_number.setText(ShowReservationActivity.cancel_Flight_Number);

        TextView departure = findViewById(R.id.cancel_departure);
        departure.setText(ShowReservationActivity.cancel_departure);

        TextView arrival = findViewById(R.id.cancel_arrival);
        arrival.setText((ShowReservationActivity.cancel_arrival));

        TextView tickets = findViewById(R.id.cancel_tickets);
        tickets.setText(Integer.toString(res.getTickets()));

        TextView reservation = findViewById(R.id.cancel_reservation_number);
        reservation.setText(Long.toString(res.getId()));

        TextView total_amount = findViewById(R.id.cancel_total_amount);
        total_amount.setText(Double.toString(ShowReservationActivity.cancel_total_amount));

        Button confirm_flight = findViewById(R.id.cancel_flight);
        confirm_flight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.d(CancelFlight, "Confirmed Button Hit");

                //Add the reservation to the Log
                FlightDao dao = FlightRoom.getFlightRoom(ConfirmCancelActivity.this).dao();
                LogRecord rec = new LogRecord(1, ShowReservationActivity.cancel_username, "Username: " + ShowReservationActivity.cancel_username +
                        "\nFlight Number: "+ShowReservationActivity.cancel_Flight_Number+
                        "\nDeparture: "+ShowReservationActivity.cancel_departure+
                        "\nArrival: "+ShowReservationActivity.cancel_arrival+
                        "\nNumber of Tickets: "+ShowReservationActivity.cancel_number_of_tickets+
                        "\nReservation number: "+res.getId()+
                        "\nTotal Amount: "+ShowReservationActivity.cancel_total_amount);
                dao.addLogRecord(rec);

                //Add the reservation to the database
                dao.deleteReservation(res);

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmCancelActivity.this);
                builder.setTitle("Flight has been canceled!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                Intent intent = new Intent(ConfirmCancelActivity.this, MainActivity.class);
                startActivity(intent);
                return;
            }
        });
    }
}
