package com.example.anitha.faceon;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class InvitationActivity extends ActionBarActivity {

    private ListView viewMembersList;
    private TextView viewGroupNameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        viewGroupNameText = (TextView) findViewById(R.id.viewGroupNameText);


        viewMembersList =(ListView) findViewById(R.id.viewMembersList);

        String[] membersName = new String[] { "Daniel, Karthik, Anitha, Anton, Nina :), Nora" };
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < membersName.length; ++i) {
            list.add(membersName[i]);
        }

        ArrayAdapter adapter= new ArrayAdapter (this,android.R.layout.simple_list_item_1, list);
        viewMembersList.setAdapter(adapter);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invitation, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
