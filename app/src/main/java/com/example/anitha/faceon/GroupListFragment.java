package com.example.anitha.faceon;

import android.app.ListFragment;
import android.os.Bundle;
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

    String[] numbers_text = new String[] { "Group A", "Group B", "Group C" };
    String[] numbers_digits = new String[] { "1", "2", "3" };

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/*        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                numbers_text);
        setListAdapter(adapter);*/

        String[] values = new String[] { "KTH group", "Tennis group" , "Amazing group","Group B"};

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

}
