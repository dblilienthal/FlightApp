package edu.csumb.flightapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import edu.csumb.flightapp.model.FlightDao;
import edu.csumb.flightapp.model.FlightRoom;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(CREATE_ACCOUNT_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button create_button = findViewById(R.id.create_account_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(CREATE_ACCOUNT_ACTIVITY, "onClick called");

                // TODO check that username and password meets requirements
                //   one special char, one uppercase and one lowercase letters, one digit
                //   display error message using dialog

                //EditText usernameInput = findViewById(R.id.username);
                //EditText passwordInput = findViewById(R.id.password);
                String username = findViewById(R.id.username).toString();
                String password = findViewById(R.id.password).toString();

                // Check to see if the username is the administrator
                if (username.equals("!admiM2")) {
                    TextView msg = findViewById(R.id.message);
                    msg.setText("Username not available.");
                    Log.d(CREATE_ACCOUNT_ACTIVITY, "Administrator Account already exists");
                    return;
                }

                // Checks the input constraints
                if (checkInput(username) && checkInput(password)){

                    //Check to see if the user exists

                    User user = (User) FlightRoom.getFlightRoom(CreateAccountActivity.this).dao().getUserByUsername(username);
                    // If the user does not exist. create a new user.
                    if (user == null){
                        FlightDao dao = FlightRoom.getFlightRoom(CreateAccountActivity.this).dao();
                        User newUser = new User(username, password);
                        dao.addUser(newUser);
                    }



                }

                // TODO create new User Object and add to database.

                // TODO  write a record to Log table with message that new Account has been created.
                //  include username (but not password) in the message.

                finish();

            }
        });
    }

    private static boolean checkInput(String input){

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
                return true;
            }
        }
        return false;
    }

}