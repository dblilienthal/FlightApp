package edu.csumb.flightapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Date;

import edu.csumb.flightapp.model.Flight;
import edu.csumb.flightapp.model.FlightDao;
import edu.csumb.flightapp.model.FlightRoom;
import edu.csumb.flightapp.model.LogRecord;

public class AddFlightActivity extends AppCompatActivity {

    public static final String ADD_FLIGHT = "AddFlight";

    FlightDao dao = FlightRoom.getFlightRoom(AddFlightActivity.this).dao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(ADD_FLIGHT, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button addFlight = findViewById(R.id.add_flight);
        addFlight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText flightNo = findViewById(R.id.flight_number_edit);
                EditText from = findViewById(R.id.from_edit);
                EditText to = findViewById(R.id.to_edit);
                EditText time = findViewById(R.id.time_edit);
                EditText capacity = findViewById(R.id.capacity_edit);
                EditText price = findViewById(R.id.price_edit);

                String flightNo1 = flightNo.getText().toString();
                String from1 = from.getText().toString();
                String to1 = to.getText().toString();
                String time1 = time.getText().toString();
                int capacity1 = Integer.parseInt(capacity.getText().toString());
                double price1 = Double.parseDouble(price.getText().toString());

                //Check to see if any of the strings are empty
                boolean flag = true;

                Flight flight = FlightRoom.getFlightRoom(AddFlightActivity.this).dao().getFlightByFlightNo(flightNo.getText().toString());

                if (flightNo1.isEmpty() || from1.isEmpty() || to1.isEmpty() || time1.isEmpty() || capacity.getText().toString().isEmpty() || price.getText().toString().isEmpty() || flight.getFlightNo() != null) {
                    flag = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddFlightActivity.this);
                    builder.setTitle("Flight already exists or you didn't fill out the form correctly.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*Intent intent = new Intent(AddFlightActivity.this, MainActivity.class);
                            finish();
                            startActivity(intent);*/
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                if(dao.getFlightByFlightNo(flightNo1)==null && flag) {
                    Flight addFlight = new Flight(flightNo1, from1, to1, time1, capacity1, price1);
                    //addFlight.setId((int) dao.addFlight(addFlight));

                    dao.addFlight(addFlight);

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddFlightActivity.this);
                    builder.setTitle("Flight added.");
                    builder.setPositiveButton("Confirm.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(AddFlightActivity.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }
}
