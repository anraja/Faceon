package com.example.anitha.faceon;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Intent.*;

/**
 * Created by saravana on 16/02/15.
 */
public class EditGroupsActivity extends ListActivity{

    ListView listView ;
    List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_groups_layout);
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
//
        // Defined Array values to show in ListView
        list = buildData();
        String[] from = { "name", "friends" };
        int[] to = { android.R.id.text1, android.R.id.text2 };
        SimpleAdapter adapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2, from, to);
        setListAdapter(adapter);



    }
    private List<Map<String, String>> buildData() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(putData("Group A", "Anton, Anitha, Karthik"));
        list.add(putData("Group B", "Nora, Daniel, Karthik"));
        list.add(putData("Group N", "Anitha, Daniel, Anton"));
        return list;
    }

    private Map<String, String> putData(String name, String purpose) {
        Map<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("friends", purpose);
        return item;
    }

    public void selectFriends(View view){
        Intent friendsIntent = new Intent(this,AddFriends.class);
        startActivity(friendsIntent);
    }
    public void startNewGroup(View view){
        Intent addGroupIntent = new Intent(this,AddGroupConversation.class);
        startActivity(addGroupIntent);
    }




}
