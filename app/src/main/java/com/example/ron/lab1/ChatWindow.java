package com.example.ron.lab1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ChatWindow";
    ArrayList<String>  arrayList;
    Button sendButton;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, ": onCreate()");

        arrayList =  new ArrayList<>();
        sendButton = (Button) findViewById(R.id.sendButton);
        listView = (ListView) findViewById(R.id.listView);
       final ChatAdapter messageAdapter = new ChatAdapter( this );
        listView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {

            EditText message = (EditText) findViewById(R.id.editTextMessage);

            @Override
            public void onClick(View v) {

                Log.i(ACTIVITY_NAME, ": onClick()");

                arrayList.add(message.getText().toString());
                messageAdapter.notifyDataSetChanged();
                message.setText("");

            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx){
            super(ctx,0);
        }

        public int getCount(){
           return arrayList.size();
        }

        public String getItem(int position){
            Log.i(ACTIVITY_NAME, ": getItem()");
            return arrayList.get(position);
        }



        public View getView(int position, View ConvertView, ViewGroup parent){
            Log.i(ACTIVITY_NAME, ": getView()");

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null ;

            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

           TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;

        }
    }
}
