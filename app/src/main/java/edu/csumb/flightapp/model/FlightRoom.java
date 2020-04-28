package edu.csumb.flightapp.model;

import android.content.Context;
import android.util.Log;

import java.util.List;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


//TODO  update this class to include LogRecord as entity

@Database(entities={Flight.class, User.class, LogRecord.class, Reservation.class}, version=1) //User.class
public abstract class FlightRoom extends RoomDatabase {
    // singleton
    private static FlightRoom instance;

    public abstract FlightDao dao();

    public static FlightRoom getFlightRoom(final Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FlightRoom.class,
                    "FlightDB") // database name
                    .allowMainThreadQueries()  // temporary for now
                    .build();
        }
        return instance;
    }

    public void loadData(Context context){

        // if flight table is empty, then load data for  flights

        List<Flight> flight_list = FlightRoom.getFlightRoom(context).dao().getAllFlights();
        if (flight_list.size() == 0) {
            Log.d("FlightRoom", "loading data flights ");
            loadFlights(context);
        }
    }

    private void loadUsers(Context context) {
        FlightDao dao = getFlightRoom(context).dao();

        User alice = new User("A@lice5", "@cSit100");
        User derek = new User("$BriAn7","123aBc##");
        User ashley = new User("!chriS12!", "CHrIS12!!");
        dao.addUser(alice);
        dao.addUser(derek);
        dao.addUser(ashley);
        Log.d("FlightRoom", "3 users added to database");
    }


    private void loadFlights(Context context){
        FlightDao dao = getFlightRoom(context).dao();

        Flight otter101 = new Flight("Otter101", "Monterey", "Los Angeles", "10:30(am)", 10, 150);
        dao.addFlight(otter101);
        Flight otter102 = new Flight("Otter102", "Los Angeles", "Monterey", "1:00(pm)", 10, 150);
        dao.addFlight(otter102);
        Flight otter201 = new Flight("Otter201", "Monterey", "Seattle", "11:00(am)", 5, 200.50);
        dao.addFlight(otter201);
        Flight otter205 = new Flight("Otter205", "Monterey", "Seattle", "3:45(pm)", 15, 150.00);
        dao.addFlight(otter205);
        Flight otter202 = new Flight("Otter202", "Seattle", "Monterey", "2:10(pm)", 5, 200.50);
        dao.addFlight(otter202);
        Flight otter301 = new Flight("Otter301", "Los Angeles", "Seattle", "12:00(PM)", 10, 350.50);
        dao.addFlight(otter301);
        Log.d("FlightRoom", "5 flights added to database");


    }
}
