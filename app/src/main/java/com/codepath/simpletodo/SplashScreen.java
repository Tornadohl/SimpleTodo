package com.codepath.simpletodo;



import android.content.Intent;

import android.os.Bundle;

import android.os.Handler;



import androidx.appcompat.app.AppCompatActivity;



public class SplashScreen extends AppCompatActivity {



    /** Duration of wait **/

    private final int SPLASH_DISPLAY_LENGTH = 1500;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        /** Called when the activity is first created. */

        /* New Handler to start the Menu-Activity

         * and close this Splash-Screen after some seconds.*/

        new Handler().postDelayed(new Runnable(){

            @Override

            public void run() {

                /* Create an Intent that will start the Menu-Activity. */

                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);

                SplashScreen.this.startActivity(mainIntent);

                SplashScreen.this.finish();

            }

        }, SPLASH_DISPLAY_LENGTH);

    }

}