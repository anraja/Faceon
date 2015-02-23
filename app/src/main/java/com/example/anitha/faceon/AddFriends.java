package com.example.anitha.faceon;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.widget.SimpleCursorAdapter;


public class AddFriends extends ActionBarActivity {
    private static final int CONTACT_PICKER_RESULT = 1001;
    private String DEBUG_TAG = "Selectedcontact";
    ListView listView ;
    List<Map<String, List<String>>> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friends, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.addFriendseditText:
//                EditText groupMembersEditText = (EditText) findViewById(R.id.addFriendseditText);
//                Editable groupMembers = groupMembersEditText.getText();

                    Intent selectFriends = new Intent(this, Contacts.class);
                   // selectFriends.putExtra(getPackageName(), groupMembers.toString());
                    startActivity(selectFriends);
                    break;

            case R.id.action_cancelAddingFriends:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.action_editGroup:
                startActivity(new Intent(this,EditGroupsActivity.class));
                break;
            case R.id.action_addFriends:
                Intent addToGroup= new Intent(this, MainActivity.class);
                startActivity(addToGroup);


        }

        return super.onOptionsItemSelected(item);
    }




    public void addToGroup(View view){
        Intent friendsIntent = getIntent();
        startActivity(friendsIntent);
    }
    public void doLaunchContactPicker(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    Intent intent = getIntent();
                    String groupName = intent.getStringExtra("groupName");
                    Set<String> pickedContact = (Set<String>)intent.getSerializableExtra("group");
                    if (c.moveToFirst()) {

                         pickedContact.add(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                    }

                    listView = (ListView) findViewById(android.R.id.list);
                    final StableArrayAdapter adapter = new StableArrayAdapter(this,
                            android.R.layout.simple_list_item_1, new ArrayList<>(pickedContact));
                    listView.setAdapter(adapter);


                    break;
            }

        } else {
            // gracefully handle failure
            Log.v(DEBUG_TAG, "Warning: activity result not ok");
        }
    }


}
