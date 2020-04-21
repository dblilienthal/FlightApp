package edu.csumb.flightapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.csumb.flightapp.model.FlightDao;
import edu.csumb.flightapp.model.FlightRoom;

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

        EditText flightNo = findViewById(R.id.flight_number_edit);
        EditText from = findViewById(R.id.from_edit);
        EditText to = findViewById(R.id.to_edit);
        EditText time = findViewById(R.id.time_edit);
        EditText capacity = findViewById(R.id.capacity_edit);
        EditText price = findViewById(R.id.price_edit);






    }
}
