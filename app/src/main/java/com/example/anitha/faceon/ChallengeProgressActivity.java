package com.example.anitha.faceon;

import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;


public class ChallengeProgressActivity extends ActionBarActivity {
    private ProgressBar progBar;
    private TableLayout tableMembersLeft;
    private EditText chatWall;
    private EditText inputTextField;
    private Button sendButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_progress);
        chatWall = (EditText) findViewById(R.id.viewChatBox);
        chatWall.setKeyListener(null);


        ArrayList<ImageView> membersImages = setupImageArrayListWithCreators();
        setUpMemberLeftTable(membersImages);
    }
    
    private ArrayList<ImageView> setupImageArrayListWithCreators (){
        ArrayList<ImageView> membersImages = new ArrayList<>();
        int membersImagesCount = 10;  // membersImages.size();
        
        ImageView memberPic = new ImageView(this);
        memberPic.setImageResource(R.drawable.anitha);
        membersImages.add(memberPic);
        
        memberPic = new ImageView(this);
        memberPic.setImageResource(R.drawable.daniel);
        membersImages.add(memberPic);
        
        memberPic = new ImageView(this);
        memberPic.setImageResource(R.drawable.anton);
        membersImages.add(memberPic);
        
        memberPic = new ImageView(this);
        memberPic.setImageResource(R.drawable.nora);
        membersImages.add(memberPic);
        
        memberPic = new ImageView(this);
        memberPic.setImageResource(R.drawable.karthik);
        membersImages.add(memberPic);
        
        return membersImages;
    }
    /**
     * Setting up a dummy array list for members images
     * @return List <Imageview>
     */
    private ArrayList<ImageView> setupImageArrayList (){
        ArrayList<ImageView> membersImages = new ArrayList<>();
        int membersImagesCount = 10;  // membersImages.size();
        for (int i=0 ;i< membersImagesCount;i++){
            ImageView memberPic = new ImageView(this);
            memberPic.setImageResource(R.drawable.anonymous_user);
            membersImages.add(memberPic);
        }
        return membersImages;
    }

    /**
     * Setting up images in the tableLayou
     * @param memberImages
     */
    private void setUpMemberLeftTable(ArrayList<ImageView> memberImages){
        tableMembersLeft  =(TableLayout)  findViewById(R.id.viewMemberLeftTable);
//        tableMembersLeft.setStretchAllColumns(true);
        tableMembersLeft.setShrinkAllColumns(true);

        int counter = 0;
        TableRow row = null;
        for(ImageView i :memberImages){
            if( (counter %2) == 0 ) {
                row = new TableRow(this);
                //row.setGravity(Gravity.CENTER);
                tableMembersLeft.addView(row);
            }
            row.addView(i);
            counter++;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenge_progress, menu);
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
