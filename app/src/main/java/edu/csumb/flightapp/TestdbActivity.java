package edu.csumb.flightapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.csumb.flightapp.model.Flight;
import edu.csumb.flightapp.model.FlightDao;
import edu.csumb.flightapp.model.FlightRoom;


public class TestdbActivity extends AppCompatActivity {

    private static final String TESTDB_ACTIVITY = "TestdbActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TESTDB_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdb);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button return_main_button = findViewById(R.id.return_button);
        return_main_button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TESTDB_ACTIVITY, "onClick return called");
                finish();
            }
        });

        Flight flight = new Flight("Otter 101", "from city", "to city", "0800 AM", 10, 95.75);

        FlightDao dao = FlightRoom.getFlightRoom(this).dao();
        long flightId = dao.addFlight(flight);
        flight.setId( flightId);
        List<Flight> flights = dao.getAllFlights();
        Log.d(TESTDB_ACTIVITY, "flights size "+flights.size());
        TextView flight_testdb = findViewById(R.id.flights_testdb);
        if (flights.size() >= 1) flight_testdb.setTextColor(Color.GREEN);

        // TODO  uncomment and complete the following test code

        // create and retrieve a Reservation
        // Reservation user = new Reservation(...);
        // dao.addReservation(reservation);
        // List<Reservation> reserves = dao.getAllReservations();
        // if (reserves.size() >= 1) findViewById(R.id.reserve_testdb).setTextColor(Color.GREEN);

        // list users
        // User user = new User(...);
        // dao.addUser(user);
        // List<User> users = dao.getAllUsers();
        // if (users.size() >= 1) findViewById(R.id.users_testdb).setTextColor(Color.GREEN);

        // create and retrieve LogRecord
        // LogRecord record = new LogRecord(...);
        // dao.addLogRecord(record);
        // List<LogRecord> records  = dao.getAllLogRecords();
        // if (records.size() >= 1) findViewById(R.id.logrec_testdb).setTextColor(Color.GREEN);


    }

}
