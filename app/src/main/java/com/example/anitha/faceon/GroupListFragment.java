package com.example.anitha.faceon;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.anitha.communicate.MultiMessagingActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nora on 18-Feb-15.
 */
public class GroupListFragment extends ListFragment {

    HashMap<String,List<ParseObject>> map;
    private String currentUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        map = new HashMap<String, List<ParseObject>>();
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        findMember();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //find groups in which the user belongs to
    private void findMember(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserGroup");
        query.whereEqualTo("member",currentUserId);
        query.include("User");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e==null) {
                    Log.w("USER","paseobject found: "+parseObjects.size());
                    for (ParseObject o : parseObjects) {
                        Log.w("USER","number of  in the group: "+o.getString("name"));
                        Log.w("USER","Users in the group: ");
                        try {
                            List<ParseObject> members=o.getRelation("member").getQuery().find();
                            Log.w("USER","Users in the group: "+members.size());
                            map.put(o.getString("name"),members);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                        setFragmentList();
                }else{
                    e.printStackTrace();
                }
            }
        });

    }

    private void setFragmentList()  {
        if (map.size()>0) {
            ArrayList<Map<String, String>> list = buildData();
            String[] from = {"name", "friends"};
            int[] to = {android.R.id.text1, android.R.id.text2};
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
                    android.R.layout.simple_list_item_2, from, to);
            setListAdapter(adapter);
        }
    }

    private String getMembersNames(List<ParseObject> lo) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for(ParseObject l: lo) {
            sb.append(sep).append(l.get("username"));
            sep = ", ";
        }
        return sb.toString();
    }



    private ArrayList<Map<String, String>> buildData() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Map.Entry<String, List<ParseObject>> entry : map.entrySet()) {
            String group = entry.getKey();
            List<ParseObject> members = entry.getValue();
            getMembersNames(members);
            list.add(putData(group,getMembersNames(members)));
        }
        return list;
    }

    private HashMap<String, String> putData(String name, String purpose) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("friends", purpose);
        return item;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("Grouplist", "number in list:" + id);
        Intent intent = new Intent(getActivity() ,MultiMessagingActivity.class);
       TextView tv = (TextView) v.findViewById(android.R.id.text1);
        intent.putExtra("group", tv.getText().toString());
        startActivity(intent);
    }

}
