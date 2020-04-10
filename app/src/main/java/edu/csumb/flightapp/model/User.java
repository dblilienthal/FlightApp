package edu.csumb.flightapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String username;
    private String password;

    // no arg constructor

    // getters and setters

    // toString

}
