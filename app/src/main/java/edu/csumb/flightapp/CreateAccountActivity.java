package edu.csumb.flightapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateAccountActivity  extends AppCompatActivity {

    private static final String CREATE_ACCOUNT_ACTIVITY = "CreateAccountActivity";

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

                // TODO create Account and add to database.

                // TODO  write a record to Log table with message that new Account has been created.
                //  include username (but not password) in the message.

                finish();

            }
        });
    }
}