package edu.csumb.flightapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Date;

import edu.csumb.flightapp.model.FlightDao;
import edu.csumb.flightapp.model.FlightRoom;
import edu.csumb.flightapp.model.LogRecord;
import edu.csumb.flightapp.model.User;

public class CreateAccountActivity  extends AppCompatActivity {

    private static final String CREATE_ACCOUNT_ACTIVITY = "CreateAccountActivity";

    private static String acceptedCharacters = "!@#$";
    private static String acceptedNumbers = "1234567890";
    private static String alphabetUpper = "abcdefghijklmnopqrstuvwxyz";
    private static String alphabetLower = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static char[] alphabetUpperArray = alphabetUpper.toCharArray();
    private static char[] alphabetLowerArray = alphabetLower.toCharArray();
    private static char[] acceptedNumberArray = acceptedNumbers.toCharArray();
    private static char[] acceptedCharactersArray = acceptedCharacters.toCharArray();
    private int attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(CREATE_ACCOUNT_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button create_button = findViewById(R.id.create_account_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                Log.d(CREATE_ACCOUNT_ACTIVITY, "onClick called");

                // TODO check that username and password meets requirements
                //   one special char, one uppercase and one lowercase letters, one digit
                //   display error message using dialog

                EditText username = findViewById(R.id.username);
                EditText password = findViewById(R.id.password);

                //tryagain:
                if (attempts != 1){
                    // Check to see if the username is the administrator
                    if (username.getText().toString().equals("!admiM2")) {
                        //TextView msg = findViewById(R.id.message);
                        //msg.setText("Username not available.");
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
                        builder.setTitle("Username not available.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        Log.d(CREATE_ACCOUNT_ACTIVITY, "Administrator Account already exists");
                        attempts++;
                        return;
                        //break tryagain;
                        //return;
                    }

                    // Checks the input constraints
                    if (checkInput(username.getText().toString()) && checkInput(password.getText().toString())){

                        //Check to see if the user exists

                        User user = FlightRoom.getFlightRoom(CreateAccountActivity.this).dao().getUserByUsername(username.getText().toString());
                        // If the user does not exist. create a new user.
                        if (user == null){
                            FlightDao dao = FlightRoom.getFlightRoom(CreateAccountActivity.this).dao();
                            User newUser = new User(username.getText().toString(), password.getText().toString());
                            dao.addUser(newUser);
                            Log.d(CREATE_ACCOUNT_ACTIVITY, "User has been successfully created.");

                            //Add this to the log record
                            Date now = new Date();
                            LogRecord rec = new LogRecord(3, username.getText().toString(), "");
                            dao.addLogRecord(rec);

                            // Create a popup button for the user to click
                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
                            builder.setTitle("Account successfully created.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        // If the username already exists
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CreateAccountActivity.this);
                        builder.setTitle("Username already exists.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { finish();
                            }
                        });Log.d(CREATE_ACCOUNT_ACTIVITY, "Username Exists");
                        TextView msg = findViewById(R.id.message);
                        msg.setText("Username not available.");
                        attempts++;
                        return;
                        //break tryagain;

                    } else {
                        // Username or Password did not meet the requirements
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
                        builder.setTitle("Username or Password did not meet the requirements. Please try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        Log.d(CREATE_ACCOUNT_ACTIVITY, "Unsuccessful Attempt At Creating An Account");
                        attempts++;
                        return;
                        //break tryagain;
                    }
                } else {
                    // If there has been two attempts
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
                    builder.setTitle("Too many fails. Please try again.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }


                // TODO create new User Object and add to database.

                // TODO  write a record to Log table with message that new Account has been created.
                //  include username (but not password) in the message.

                //finish();



            }
        });
    }

    private boolean checkInput(String input){

        char[] inputArray = input.toCharArray();

        boolean containsUpperAlphabet = false;
        boolean containsLowerAlphabet = false;
        boolean containsNumbers = false;
        boolean containsCharacters = false;

        for (char value : inputArray) {
            if (!containsUpperAlphabet) {
                for (char c : alphabetUpperArray) {
                    if (value == c) {
                        containsUpperAlphabet = true;
                        Log.d(CREATE_ACCOUNT_ACTIVITY, "containsUpperAlphabet = true;");
                        break;
                    }
                }
            }
            if (!containsLowerAlphabet) {
                for (char c : alphabetLowerArray) {
                    if (value == c) {
                        containsLowerAlphabet = true;
                        Log.d(CREATE_ACCOUNT_ACTIVITY, "containsLowerAlphabet = true;");
                        break;
                    }
                }
            }
            if (!containsNumbers) {
                for (char c : acceptedNumberArray) {
                    if (value == c) {
                        containsNumbers = true;
                        Log.d(CREATE_ACCOUNT_ACTIVITY, "containsNumbers = true;");
                        break;
                    }
                }
            }
            if (!containsCharacters) {
                for (char c : acceptedCharactersArray) {
                    if (value == c) {
                        containsCharacters = true;
                        Log.d(CREATE_ACCOUNT_ACTIVITY, "containsCharacters = true;");
                        break;
                    }
                }
            }
            if (containsUpperAlphabet && containsLowerAlphabet && containsNumbers && containsCharacters) {
                Log.d(CREATE_ACCOUNT_ACTIVITY, "Username and Password are valid");
                return true;
            }
        }
        Log.d(CREATE_ACCOUNT_ACTIVITY, "Username and Password are NOT valid");

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
        builder.setTitle("Password or Username is not valid.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }
}