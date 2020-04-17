package edu.csumb.flightapp.model;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalTime;

import static java.time.LocalTime.now;

@Entity
public class LogRecord {
    /*
    LogRecord needs to record the Customer’s username, reservation number, flight number,
    departure/arrival information, departure time, number of tickets reserved, transaction type
    (=cancellation or = Reserve Seat), and current date/time.
     */

    public static final String TYPE_CANCELLATION = "cancellation";
    public static final String TYPE_RESERVE_SEAT = "reserve_seat";

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String customerUsername;
    private int reservationNumber;
    private int flightNumber;
    private String departure;
    private String arrival;
    private String departureTime;
    private int numberOfTickets;
    private String transactionType;
    private String time; //Time of the Logging

    public LogRecord(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Ignore
    public LogRecord(String customerUsername, int reservationNumber, int flightNumber, String departure, String arrival, String departureTime, int numberOfTickets, String transactionType, String time ){
        this.customerUsername = customerUsername;
        this.reservationNumber = reservationNumber;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.arrival = arrival;
        this.departureTime = departureTime;
        this.numberOfTickets = numberOfTickets;
        this.transactionType = transactionType;
        this.time = now().toString();
    }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
/*
    public java.util.Date getTime() {
        return new java.util.Date(time);
    }

    public void setTime(long time) {
        this.time = time;
    }
    */

}
