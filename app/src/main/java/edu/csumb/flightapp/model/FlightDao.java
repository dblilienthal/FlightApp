package edu.csumb.flightapp.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


//TODO update this class to include
//   query method to read LogRecords
//      use the annotation @Query("select * from LogRecord order by datetime desc")
//   insert method to insert new LogRecord into database

@Dao
public interface FlightDao {

    @Query("select * from Flight")
    List<Flight> getAllFlights();

    @Query("select * from Flight where departure=:departure and arrival=:arrival")
    List<Flight> searchFlight(String departure, String arrival);

    // the generated id value is returned
    @Insert
    long addFlight(Flight flight);

    // return the number of rows actually updated.  Should be 1
    @Update
    int updateFlight(Flight flight);

    // TODO -- remove comments on following


//    @Query("select * from User")
//    List<Flight> getAllUsers();
//
//    @Query("select * from User where username = :username")//   List<User> getUserByUsername(String username);
//
//    @Insert
//    void addUser(User user);



}
