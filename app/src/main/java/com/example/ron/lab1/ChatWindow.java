package com.example.ron.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    SQLiteDatabase database;
    ChatDataBaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, ": onCreate()");

       db = new ChatDataBaseHelper(this);
       database = db.getWritableDatabase();

        arrayList =  new ArrayList<>();

        String query = "SELECT * FROM chats WHERE 1;";

        Cursor c = database.rawQuery(query,null);

        c.moveToFirst();

        while(!c.isAfterLast()){
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: "+c.getString(c.getColumnIndex(ChatDataBaseHelper.KEY_MESSAGE)));
            if(c.getString(c.getColumnIndex(ChatDataBaseHelper.KEY_MESSAGE))!= null) {
                arrayList.add(c.getString(c.getColumnIndex(ChatDataBaseHelper.KEY_MESSAGE)));
            }
            c.moveToNext();
        }
        Log.i(ACTIVITY_NAME,"Cursor Column Count= "+c.getColumnCount());
        int i;
        for(i=0;i<c.getColumnCount();i++){
            Log.i(ACTIVITY_NAME,"Column: "+c.getColumnName(i));
        }

        sendButton = (Button) findViewById(R.id.sendButton);
        listView = (ListView) findViewById(R.id.listView);
        final ChatAdapter messageAdapter = new ChatAdapter( this );
        listView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {

            EditText message = (EditText) findViewById(R.id.editTextMessage);

            @Override
            public void onClick(View v) {

                Log.i(ACTIVITY_NAME, ": onClick()");
                ContentValues value = new ContentValues();
                value.put(db.KEY_MESSAGE,message.getText().toString());
                database.insert(db.TABLE_NAME,null,value);

                arrayList.add(message.getText().toString());
                messageAdapter.notifyDataSetChanged();
                message.setText("");

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
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
