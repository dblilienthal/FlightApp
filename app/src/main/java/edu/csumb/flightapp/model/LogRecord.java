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

    public static final int LOG_TYPE_CANCEL = 1;
    public static final int LOG_TYPE_RESERVE = 2;
    public static final int LOG_TYPE_NEWUSER = 3;

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long time; // unix time
    private int type;
    private String username;
    private String msg;

    public LogRecord(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Ignore
    public LogRecord(int type, String username, String msg) {
        this.type = type;
        this.username = username;
        this.time = System.currentTimeMillis();
        this.msg = msg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String typeString() {
        switch (type) {
            case 1: return "CANCEL";
            case 2: return "RESERVE";
            case 3: return "NEW_USER";
            default: return "INVALID";
        }
    }

    @Override
    public String toString() {
        return "LogRecord{" +
                "id=" + id +
                ", time=" + new java.util.Date(time).toString() +
                ", type=" + typeString() +
                ", username='" + username + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

}
