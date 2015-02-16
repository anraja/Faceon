package com.example.anitha.faceon;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Nora on 16-Feb-15.
 */
public class EditProfileActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        ImageView imageView = (ImageView) findViewById(R.id.userImage);

    }
}
