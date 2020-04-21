package edu.csumb.flightapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import edu.csumb.flightapp.model.FlightRoom;
import edu.csumb.flightapp.model.LogRecord;

public class ShowLogRecords extends AppCompatActivity {

    private static final String SHOWLOGRECORDS = "TestLogRecordsActivity";

    List<LogRecord> logs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(SHOWLOGRECORDS, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button return_main_button = findViewById(R.id.return_button_log);
        return_main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(SHOWLOGRECORDS, "onClick return called");
                finish();
            }
        });

        logs = FlightRoom.getFlightRoom(this).dao().getLogRecordOrderByTimeDesc();
        Log.d(SHOWLOGRECORDS, "Number of Log Records "+logs.size());

        ListView log_view = findViewById(R.id.log_list);
        log_view.setAdapter( new ShowLogRecords.LogListAdapter( this, logs) );

    }

    public class LogListAdapter extends ArrayAdapter<LogRecord> {

        public LogListAdapter (Activity context, List<LogRecord> logs){
            super(context, R.layout.row_layout , logs);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater=ShowLogRecords.this.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.row_layout, null,true);
            TextView rowField = rowView.findViewById(R.id.row_id);

            //set the value of a row in the ListView to the logRecords info using toString()
            rowField.setText(logs.get(position).toString());
            return rowView;
        }

    }
}
