package com.example.anitha.faceon;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private ArrayAdapter<String> listAdapter;
    public boolean hasGroups=false;
    final private String Groups="GROUPS";


    // Sets an ID for the notification
    int mNotificationId = 001;
    // Gets an instance of the NotificationManager service
    NotificationManager mNotifyMgr;
    NotificationCompat.Builder mBuilder;
    List<Map<String, String>> list;
    String groups="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar();
        //Is there already a file where group info is saved?
        if(fileExistance(Groups)){
            Log.d("MainActivity","GroupExists");
            hasGroups=true;
            groups=readFromFile();
        }
        else{//otherwise create it!
            Log.d("MainActivity","Group doesn't Exists");
            File file = new File(this.getFilesDir(), Groups);
            String string = "Testgroupname-Anton, Daniel, Elsa\n";
            FileOutputStream outputStream;

            try {
                outputStream = openFileOutput(Groups, Context.MODE_PRIVATE);
                outputStream.write(string.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FragmentManager fm = getFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putString("groups", groups);
        if (fm.findFragmentById(android.R.id.content) == null) {
            GroupListFragment list = new GroupListFragment();
            list.setArguments(bundle);//send group info
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
        mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


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
        }else if (id == R.id.social_person){
            startActivity(new Intent(this,EditProfileActivity.class));
        }else if (id == R.id.action_group){
            startActivity(new Intent(this,ListUsersActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    public void win (View view){
        Intent winIntent = new Intent(this,Win.class);
        startActivity(winIntent);
    }

    public void triggerNotification (View view){
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
    public String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput(Groups);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

}
