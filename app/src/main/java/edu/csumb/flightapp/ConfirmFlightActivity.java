package edu.csumb.flightapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.net.URL;
import java.util.Scanner;

import edu.csumb.flightapp.domain.Condition;
import edu.csumb.flightapp.domain.Weather;
import edu.csumb.flightapp.model.FlightDao;
import edu.csumb.flightapp.model.FlightRoom;
import edu.csumb.flightapp.model.LogRecord;
import edu.csumb.flightapp.model.Reservation;


public class ConfirmFlightActivity extends Activity {

    private static final String ConfirmFlight = "ConfirmFlight";

    static final String TAG = "MainActivityWeather";
    static final String OPENWEATHER = "http://api.openweathermap.org/data/2.5/weather";
    static final String APIKEY = "4cdc90111e0528db2d929c9090ee6e3c";
    public static String message_text;

    Handler handler;
    String city_name;
    AlertDialog dialog;
    TextView output_message;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfirm_flight);

        Log.d(ConfirmFlight, "onCreate called");

        //Create a new reservation object
        final Reservation res = new Reservation();
        res.setUsername(UserAuthenticatedBookFlightActivity.booked_username);
        res.setFlightNo(UserAuthenticatedBookFlightActivity.booked_Flight_Number);
        res.setDeparture(UserAuthenticatedBookFlightActivity.booked_departure);
        res.setArrival(UserAuthenticatedBookFlightActivity.booked_arrival);
        res.setTickets(UserAuthenticatedBookFlightActivity.booked_number_of_tickets);
        res.setTotal(UserAuthenticatedBookFlightActivity.booked_total_amount);

        Log.d(ConfirmFlight, "Username: " + UserAuthenticatedBookFlightActivity.booked_username +
                                    "\nFlight Number: "+UserAuthenticatedBookFlightActivity.booked_Flight_Number+
                                    "\nDeparture: "+UserAuthenticatedBookFlightActivity.booked_departure+
                                    "\nArrival: "+UserAuthenticatedBookFlightActivity.booked_arrival+
                                    "\nNumber of Tickets: "+UserAuthenticatedBookFlightActivity.booked_number_of_tickets+
                                    "\nReservation number: "+res.getId()+
                                    "\nTotal Amount: "+UserAuthenticatedBookFlightActivity.booked_total_amount);

        // Populate the fields
        final TextView username = findViewById(R.id.confirm_username);
        username.setText(UserAuthenticatedBookFlightActivity.booked_username);

        TextView flight_number = findViewById(R.id.confirm_flight_number);
        flight_number.setText(UserAuthenticatedBookFlightActivity.booked_Flight_Number);

        TextView departure = findViewById(R.id.confirm_departure);
        departure.setText(UserAuthenticatedBookFlightActivity.booked_departure);

        TextView arrival = findViewById(R.id.confirm_arrival);
        arrival.setText((UserAuthenticatedBookFlightActivity.booked_arrival));

        TextView tickets = findViewById(R.id.confirm_tickets);
        tickets.setText(Integer.toString(res.getTickets()));

        TextView reservation = findViewById(R.id.confirm_reservation_number);
        reservation.setText(Long.toString(res.getId()));

        TextView total_amount = findViewById(R.id.confirm_total_amount);
        total_amount.setText(Double.toString(UserAuthenticatedBookFlightActivity.booked_total_amount));

        Thread t = new Thread(new WorkerTask());
        t.start();



        Button confirm_flight = findViewById(R.id.confirm_flight);
        confirm_flight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.d(ConfirmFlight, "Confirmed Button Hit");

                //Add the reservation to the Log
                FlightDao dao = FlightRoom.getFlightRoom(ConfirmFlightActivity.this).dao();
                LogRecord rec = new LogRecord(2, UserAuthenticatedBookFlightActivity.booked_username, "Username: " + UserAuthenticatedBookFlightActivity.booked_username +
                        "\nFlight Number: "+UserAuthenticatedBookFlightActivity.booked_Flight_Number+
                        "\nDeparture: "+UserAuthenticatedBookFlightActivity.booked_departure+
                        "\nArrival: "+UserAuthenticatedBookFlightActivity.booked_arrival+
                        "\nNumber of Tickets: "+UserAuthenticatedBookFlightActivity.booked_number_of_tickets+
                        "\nReservation number: "+res.getId()+
                        "\nTotal Amount: "+UserAuthenticatedBookFlightActivity.booked_total_amount);
                dao.addLogRecord(rec);

                //Add the reservation to the database
                dao.addReservation(res);

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmFlightActivity.this);
                builder.setTitle("Flight has been confirmed!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                Intent intent = new Intent(ConfirmFlightActivity.this, MainActivity.class);
                startActivity(intent);
                return;
            }
        });
    }

    /**
     * background task to retrieve weather info from weather server
     */
    public class WorkerTask implements Runnable {


        @Override
        public void run() {
            Log.d(TAG, "WorkerTask started.");
            try {
                //Thread.sleep(5000);  //  delay to show dialog
                // get weather data for city.  reply is JSON string.
                String weather_json = getWeather(UserAuthenticatedBookFlightActivity.booked_arrival);
                Log.d(TAG, "weather_json: " + weather_json);

                // extract necessary data from reply string and convert to double
                Gson gson = new Gson();
                Weather weather = gson.fromJson(weather_json, Weather.class);
                //Condition condition = gson.fromJson(weather_json, Condition.class);
                double temp = weather.getMain().getTemp();
                temp = (temp-273.15)*9.0/5.0 + 32.0;

                //message_text = String.format("Current Temp = %.0f \u00B0F", temp);
                Log.d(TAG, "This is the temp " + Double.toString(temp));

                char[] tempArray = weather_json.toCharArray();
                int i = 0;
                StringBuffer sb = new StringBuffer();
                for (Character s: tempArray) {
                    if(weather_json.indexOf("description") +13 < i) {
                        if (weather_json.indexOf(",\"icon") -1 > i) {
                            sb.append(s);
                        }
                    }
                    i++;
                }
                String result = sb.toString();

                Log.d(TAG, "This is the current weather " + result);

                //Condition[] tempConditions = weather.getWeather();
                //Log.d(TAG, "tempConditions: " + tempConditions[2]);
                //Log.d(TAG, "tempConditions: " + condition.getDescription());

                message_text = String.format("Current Temp = %.0f \u00B0F \n%s", temp, result.toUpperCase());
                Log.d(TAG, "This is the message_text " +message_text);
                TextView w = findViewById(R.id.weather_field);
                w.setText(message_text);

            } catch (Exception e) {
                Log.d(TAG, "exception WorkerTask.run " + e.getMessage());
                TextView w = findViewById(R.id.weather_field);
                //message_text = "Error. Enter a valid city name.";
                w.setText(message_text);
            }
            //  we are done. so dismiss the progress dialog
            //  and update layout textview with current temp
/*            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "handler called.");
                    TextView w = findViewById(R.id.weather_field);
                    w.setText(message_text);
                    // dismiss the dialog and let user enter another request
                    dialog.dismiss();
                }
            });*/
            Log.d(TAG, "WorkerTask ending.");
        }



        /**
         * Given name of city, return weather information in JSON format
         *
         * @param city name
         * @return string value containing JSON data from server.  null value if there is error.
         */
        private String getWeather(String city) {
            Scanner reader = null;
            String urlstring = OPENWEATHER+"?q=" + city + "&appid=" + APIKEY;
            Log.d(TAG, "getWeather " + urlstring);

            try {
                URL url = new URL(urlstring);
                reader = new Scanner(url.openConnection().getInputStream());
                StringBuffer sb = new StringBuffer();
                while (reader.hasNext())
                    sb.append(reader.nextLine());
                Log.d(TAG, "getWeather end");
                String result = sb.toString();
                Log.d(TAG, result);
                return result;
            } catch (Exception e) {
                Log.d(TAG, "getWeather exception " + e.getMessage());
                return null;
            } finally {
                if (reader != null) reader.close();
            }
        }
    }

}
