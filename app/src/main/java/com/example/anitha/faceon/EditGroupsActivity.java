package com.example.anitha.faceon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import static android.content.Intent.*;

/**
 * Created by saravana on 16/02/15.
 */
public class EditGroupsActivity extends Activity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_groups_layout);

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
