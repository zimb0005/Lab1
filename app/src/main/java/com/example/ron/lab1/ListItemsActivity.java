package com.example.ron.lab1;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "List Items Activity";

    private ImageButton button;
    private Switch switch1;
    private CheckBox checkbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items_acivity);
        Log.i(ACTIVITY_NAME,"In onCreate()");

        button = (ImageButton)findViewById(R.id.imageButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        });

        switch1 = (Switch) findViewById(R.id.switch1);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Toast.makeText(ListItemsActivity.this, isChecked ? "Switch is On" : "Switch is Off", isChecked ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();

            }
        });

        checkbox = (CheckBox)findViewById(R.id.checkBox);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);

                builder.setMessage("Would you like to finish this activity and return to start?").setTitle("Finish Activity").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        Intent resultIntent = new Intent(  );
                        resultIntent.putExtra("Response", "My information to share");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){

                    }
                }).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            button.setImageBitmap(imageBitmap);
        }
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

}
