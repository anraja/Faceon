package com.example.anitha.faceon;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nora on 18-Feb-15.
 */
public class GroupListFragment extends ListFragment {

    int nrOfGroups=0;

    String[] Grp=new String[20];
    String[] GrpName=new String[20];
    String Members=new String();
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("Grouplist", "number in list:" + id);
        Intent intent = new Intent(getActivity() ,ChallengeProgressActivity.class);
        intent.putExtra("Groupname", GrpName[position]);
        intent.putExtra("Members",Members);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




       String bundle = getArguments().getString("groups");
       String[] GroupList=bundle.split("\n");
       nrOfGroups=GroupList.length;
        String[][] content=new String[nrOfGroups][];
        for(int i=0;i<nrOfGroups;i++){
            Grp[i]=GroupList[i];
            Log.d("Testing",GroupList[i]);
        }


        ArrayList<Map<String, String>> list = buildData();
        String[] from = { "name", "friends" };
        int[] to = { android.R.id.text1, android.R.id.text2 };
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
                android.R.layout.simple_list_item_2, from, to);
        setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private ArrayList<Map<String, String>> buildData() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for(int i=0;i<nrOfGroups;i++){
            String[] content=Grp[i].split("-");
            if(content.length<2)break;
            Members=content[1];
            GrpName[i]=content[0];
            list.add(putData(content[0],"Members: "+content[1]));
        }
        /*list.add(putData("Group A", "Anton, Anitha, Karthik"));
        list.add(putData("Group B", "Nora, Daniel, Karthik"));
        list.add(putData("Group N", "Anitha, Daniel, Anton"));*/
        return list;
    }

    private HashMap<String, String> putData(String name, String purpose) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("friends", purpose);
        return item;
    }

}
