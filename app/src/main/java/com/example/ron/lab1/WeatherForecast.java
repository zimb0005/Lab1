package com.example.ron.lab1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class HTTPUtils {
    public static Bitmap getImage(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public static Bitmap getImage(String urlString) {
        try {
            URL url = new URL(urlString);
            return getImage(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }

}

public class WeatherForecast extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "Weather Forecast";
    public static final String URL_LINK =
            "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    ProgressBar progressBar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = (ProgressBar) findViewById(R.id.Progress_Bar);
        progressBar.setVisibility(View.VISIBLE);

        new ForecastQuery().execute(URL_LINK);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    } // end of method onCreate

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "WeatherForecast Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.ron.lab1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "WeatherForecast Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.ron.lab1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String min;
        String max;
        String current;
        String iconValue;
        Bitmap iconImage;
        InputStream inputStream;

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(URL_LINK);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                inputStream = connection.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inputStream, null);
                parser.nextTag();

                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {

                    if (parser.getEventType() == XmlPullParser.START_TAG) {

                        if (parser.getName().equals("temperature")) {
                            current = parser.getAttributeValue(null, "value");
                            SystemClock.sleep(1000);
                            publishProgress(25);

                            min = parser.getAttributeValue(null, "min");
                            SystemClock.sleep(1000);
                            publishProgress(50);

                            max = parser.getAttributeValue(null, "max");
                            SystemClock.sleep(1000);
                            publishProgress(75);
                        } // end if statement

                        if (parser.getName().equals("weather")) {
                            iconValue = parser.getAttributeValue(null, "icon");

                            String imgURL = "http://openweathermap.org/img/w/" + iconValue + ".png";

                            if (fileExistance(iconValue + ".png")) {
                                FileInputStream fileInputStream = openFileInput(iconValue + ".png");
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                                iconImage = BitmapFactory.decodeStream(bufferedInputStream);
                                bufferedInputStream.close();
                                Log.i(ACTIVITY_NAME, "Found " + iconValue + ".png locally");
                                SystemClock.sleep(1000);
                                publishProgress(100);

                            } else {
                                iconImage = HTTPUtils.getImage(imgURL);
                                SystemClock.sleep(1000);
                                publishProgress(100);
                                FileOutputStream fileOutputStream = openFileOutput(iconValue + ".png", Context.MODE_PRIVATE);
                                iconImage.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.close();

                                Log.i(ACTIVITY_NAME, "Downloaded " + iconValue + ".png");

                            } // end if else statement

                        } // end if statement

                    } // end If parser == Start Tag
                    parser.next();

                } // end while loop

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } // end of try catch bock

            return null;

        } // end of method doInBackground

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        } // end of method onProgressUpdate

        @Override
        protected void onPostExecute(String s) {
            TextView currentTemperatureLabel = (TextView) findViewById(R.id.Current_Temperature_Label);
            currentTemperatureLabel.setText("Current:  " + current + " °C");

            TextView minTemperatureLabel = (TextView) findViewById(R.id.Min_Temperature_Label);
            minTemperatureLabel.setText("Min:  " + min + " °C");

            TextView maxTemperatureLabel = (TextView) findViewById(R.id.Max_Temperature_Label);
            maxTemperatureLabel.setText("Max:  " + max + " °C");

            ImageView weatherIcon = (ImageView) findViewById(R.id.Weather_Icon);
            weatherIcon.setImageBitmap(iconImage);

            progressBar.setVisibility(View.INVISIBLE);

        } // end of method onPostExecute

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        } // end of method fileExistance

    } // end of inner class ForecastQuery which extends AsyncTask

} // end of class WeatherForecast