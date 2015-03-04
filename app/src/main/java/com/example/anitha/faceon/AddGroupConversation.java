package com.example.anitha.faceon;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TreeSet;


public class AddGroupConversation extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_conversation);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_add_group_conversation, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch(item.getItemId()){
            case R.id.action_addGroupName:
                EditText groupNameEditText = (EditText) findViewById(R.id.groupNameEditText);
                Editable groupName = groupNameEditText.getText();
                if (groupName.length()>0 && groupName.length()<40) {
                    Intent selectFriends = new Intent(this, ListUsersActivity.class);
                    selectFriends.putExtra("groupName", groupName.toString());
                    //selectFriends.putExtra("group", new TreeSet<String>());
                    startActivity(selectFriends);
                    return true;
                }else if (groupName.length()==0){
                    Toast.makeText(getApplicationContext(), "Group name is empty", Toast.LENGTH_SHORT).show();
                } else if (groupName.length()>=40){
                    Toast.makeText(getApplicationContext(),"Group name is too large",Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.action_cancelGroupName:
                NavUtils.navigateUpFromSameTask(this);
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
