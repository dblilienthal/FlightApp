package edu.csumb.flightapp.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    private long id; // Reservation Numbers

    //private String time; // Time of reservation made
    private String username;

    private String flightNo;
    private String departure;
    private String arrival;
    private String departureTime;
    private double total;
    private int tickets; //amount of tickets

    public Reservation() {}

    @Ignore
    public Reservation(String username, Flight flight, int tickets){
        this.username = username;
        this.flightNo = flight.getFlightNo();
        this.departure = flight.getDeparture();
        this.arrival = flight.getArrival();
        this.departureTime = flight.getDepartureTime();
        this.total = tickets * flight.getPrice();
        this.tickets = tickets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", flightNo='" + flightNo + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", totalPrice=" + total +
                ", numberTickets=" + tickets +
                '}';
    }
}
