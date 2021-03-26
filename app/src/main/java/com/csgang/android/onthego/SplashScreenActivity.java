package com.csgang.android.onthego;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int SPLASH_SHOW_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        createDatabase();
        new BackgroundSplashTask().execute();
    }

    public void createDatabase() {
        String query;
        DBClass.database = openOrCreateDatabase("OnTheGo", MODE_PRIVATE, null);
        query= "CREATE TABLE IF NOT EXISTS profile(name VARCHAR, mobileno VARCHAR, password VARCHAR);";
        DBClass.execNonQuery(query);
        query= "CREATE TABLE IF NOT EXISTS reminders(reminderid INTEGER PRIMARY KEY AUTOINCREMENT, location VARCHAR, longitude VARCHAR, latitude VARCHAR, range VARCHAR, message VARCHAR, reminded VARCHAR);";
        DBClass.execNonQuery(query);
        query= "CREATE TABLE IF NOT EXISTS lastnotification(noti_time VARCHAR);";
        DBClass.execNonQuery(query);
    }

    private class BackgroundSplashTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Thread.sleep(SPLASH_SHOW_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Intent i = new Intent(SplashScreenActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }


}
