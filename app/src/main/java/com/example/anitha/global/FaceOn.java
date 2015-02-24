package com.example.anitha.global;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Nora on 23-Feb-15.
 */
public class FaceOn extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "sTPwsZhM68p3J4KqTJ315XxVO4I13adeQQ6VSWLP", "pvdd427ziGRJOryEqeZ7Eak5eoA3fYceAb0XG01E");

    }
}
