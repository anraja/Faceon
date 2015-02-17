package com.example.anitha.faceon;

import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ListActivity {

    private ArrayAdapter<String> listAdapter;
    // Sets an ID for the notification
    int mNotificationId = 001;
    // Gets an instance of the NotificationManager service
    NotificationManager mNotifyMgr;
    NotificationCompat.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar();
        mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        ListView listview = (ListView) findViewById(android.R.id.list);
        String[] values = new String[] { "KTH group", "Tennis group" , "Amazing group","Group B"};

        ArrayList<Map<String, String>> list = buildData();
        String[] from = { "name", "friends" };
        int[] to = { android.R.id.text1, android.R.id.text2 };
        SimpleAdapter adapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2, from, to);
        setListAdapter(adapter);
        mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Selfie Challenge")
                        .setContentText("From group A");

        Intent resultIntent = new Intent(this, Notification.class);
// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

    }
    private ArrayList<Map<String, String>> buildData() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(putData("Group A", "Anton, Anitha, Karthik"));
        list.add(putData("Group B", "Nora, Daniel, Karthik"));
        list.add(putData("Group N", "Anitha, Daniel, Anton"));
        return list;
    }

    private HashMap<String, String> putData(String name, String purpose) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("friends", purpose);
        return item;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_addGroup){
            startActivity(new Intent(this,AddGroupConversation.class));
        }

        return super.onOptionsItemSelected(item);
    }
    public void editProfile (View view){
        Intent  editProfileIntent = new Intent(this, EditProfileActivity.class);
        startActivity(editProfileIntent);
    }
    public void triggerNotification (View view){
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
