package com.example.anitha.faceon;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anitha.communicate.MultiMessagingActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ListUsersActivity extends ActionBarActivity {

    List<String> names;
    String currentUserId;
    ListView usersListView;

    ArrayAdapter<String> namesArrayAdapter;
    ProgressDialog progressDialog;
    BroadcastReceiver receiver;

    List<ParseUser> users= new ArrayList<ParseUser>();
    String groupName;

    //List<String> group=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        Intent intent = getIntent();
        groupName = intent.getStringExtra("groupName");
        showSpinneraki();
        showUserList();
    }

    private void showSpinneraki(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
//broadcast receiver to listen for the broadcast
//from MessageService
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean success = intent.getBooleanExtra("success", false);

                //show a toast message if the Sinch
                //service failed to start
                if (!success) {
                    Toast.makeText(getApplicationContext(), "Messaging service failed to start", Toast.LENGTH_LONG).show();
                }
            }
        };
        progressDialog.dismiss();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("com.example.anitha.faceon.listusersactivity"));
    }

    private void showUserList(){
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        names = new ArrayList<String>();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
//don't include yourself
        query.whereNotEqualTo("objectId", currentUserId);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i=0; i<userList.size(); i++) {
                        names.add(userList.get(i).getUsername().toString());
                    }
                    usersListView = (ListView)findViewById(R.id.usersListView);
                    namesArrayAdapter =
                            new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.user_list_item, names);
                    usersListView.setAdapter(namesArrayAdapter);
                    usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                            addToGroupChat((ArrayList<String>) names, i);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_users, menu);
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
        }else if (id==R.id.action_done){
            storeGroup();
            //Intent intent = new Intent(getApplicationContext(), MultiMessagingActivity.class);
           // Log.i("dat","group size is:" +group.size());
           // intent.putExtra("RECIPIENTS_ID", group.toArray());
            //intent.putStringArrayListExtra("users", (ArrayList<String>) group);
            //startActivity(intent);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //intent.putExtra("RECIPIENT_ID", user.get(0).getObjectId());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void storeGroup(){
        if (users!=null && users.size()>0){
            ParseObject appGroup = new ParseObject("UserGroup");
            appGroup.put("name",groupName);
            users.add(ParseUser.getCurrentUser());
            ParseRelation<ParseObject> relation = appGroup.getRelation("member");


            Log.w("#NUBER","Number of users in the list: "+users.size());
            for (ParseUser u:users) {
                relation.add(u);
                //appGroup.put("member", u);
            }
//                gameScore.put("playerName", "Sean Plott");
//                gameScore.put("cheatMode", false);
         //user.saveInBackground();
            appGroup.saveInBackground();
        }
    }


    //open a conversation when the user clicks on another user name
    public void addToGroupChat(ArrayList<String> names, int pos) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", names.get(pos));
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> user, ParseException e) {
                if (e == null) {
                    users.add((ParseUser) user.get(0));
                    //start the messaging activity
//                    Intent intent = new Intent(getApplicationContext(), MultiMessagingActivity.class);
//                    intent.putExtra("RECIPIENT_ID", user.get(0));
//                    intent.putp
                    //startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error finding that user",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return null;
    }
}
