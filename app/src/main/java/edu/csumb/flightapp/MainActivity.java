package edu.csumb.flightapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.csumb.flightapp.model.FlightRoom;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    public static final String MAINACTIVITY = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MAINACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);

        // the following is to enable the menu on the screen
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // check database, if empty load flights and users
        FlightRoom.getFlightRoom(this).loadData(this);


        // button for Create Account Activity.
        Button create_account_button = findViewById(R.id.create_account);
        create_account_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(MAINACTIVITY, "onClick create account called");
                // The Intent constructor first argument must be an Activity class.
                // This code is in an inner class of MainActivity, but is not a subclass
                //   of AppCompatActivity.
                // To get reference to the MainActivity class from within inner class
                //   use syntax  "MainActivity.this"
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);

            }
        });

        // Button for administrator
        Button create_administrator_button = findViewById(R.id.manage_system);
        create_administrator_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(MAINACTIVITY, "onClick admin button called");
                Intent intent = new Intent(MainActivity.this, AdminLogin.class);
                startActivity(intent);
            }
        });


        // TODO  for development use.  Should be deleted
/*
        // button to show all flights
        Button flight_button = findViewById(R.id.show_flights);
        flight_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MAINACTIVITY, "onClick show flights called");
                Intent intent = new Intent(MainActivity.this, ShowFlightActivity.class);
                startActivity(intent);
            }
        });


        // button for TestDB activity
        Button testdb_button = findViewById(R.id.test_db);
        testdb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MAINACTIVITY, "onClick  test db called");
                Intent intent = new Intent(MainActivity.this, TestdbActivity.class);
                startActivity(intent);
            }
        });

        Button manage_system_button = findViewById(R.id.manage_system);
        manage_system_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MAINACTIVITY, "onClick  manage_system called");
                Intent intent = new Intent(MainActivity.this, CreateLogRecords.class);
                startActivity(intent);
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar row_layout clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
