package com.example.ron.lab1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "Start Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME,"In onCreate()");
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (StartActivity.this,ListItemsActivity.class);
                startActivityForResult(intent,5);
            }
        });

        Button startChatButton = (Button) findViewById(R.id.startChatButton);
        startChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME,"User clicked start chat");
                Intent intent = new Intent (StartActivity.this,MessageListActivity.class);
                startActivity(intent);
            }
        });

        Button weatherForecast = (Button) findViewById(R.id.weatherForecast);
        weatherForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (StartActivity.this,WeatherForecast.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }

    protected void onActivityResult(int requestCode,int responseCode,Intent data){
        Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult -outside if");
        if(requestCode==5) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult -in request code ==  5");
        }
        if(responseCode == Activity.RESULT_OK){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult -in result ok");
            String messagePassed = data.getStringExtra("Response");
            Toast.makeText(StartActivity.this, messagePassed,Toast.LENGTH_LONG).show();
        }


    }
}
