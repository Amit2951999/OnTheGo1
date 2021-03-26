package com.csgang.android.onthego;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    public static TextView txtUser;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gps = new GPSTracker(HomeActivity.this);

        txtUser = (TextView)findViewById(R.id.txtUser);
        String query = "SELECT name FROM profile";
        String name = DBClass.getSingleValue(query);
        query = "SELECT mobileno FROM profile";
        String mobileno = DBClass.getSingleValue(query);
        txtUser.setText("Welcome " + name.toUpperCase() + "(" + mobileno + ")");

        new BackgroundTask().execute();
        ReminderService.mContext = HomeActivity.this;
        startService(new Intent(HomeActivity.this, ReminderService.class));
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                if(gps.canGetLocation()) {
                    ReminderService.currentLatitude = gps.getLatitude();
                    ReminderService.currentLongitude = gps.getLongitude();
                }
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            new BackgroundTask().execute();
        }
    }

    public void btnAddReminderClick(View view)
    {
        String query = "SELECT * FROM profile";
        if(!DBClass.checkIfRecordExist(query))
        {
            Toast.makeText(this, "please update your profile first", Toast.LENGTH_SHORT).show();
        }
        else {
            AddReminderActivity.reminderid = "0";
            Intent intent = new Intent(this, AddReminderActivity.class);
            startActivity(intent);
        }
    }

    public void btnListRemindersClick(View view)
    {
        String query = "SELECT * FROM profile";
        if(!DBClass.checkIfRecordExist(query))
        {
            Toast.makeText(this, "please update your profile first", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, ListRemindersActivity.class);
            startActivity(intent);
        }
    }

    public void btnMyProfileClick(View view)
    {
        txtUser = (TextView)findViewById(R.id.txtUser);
        Intent intent = new Intent(this, MyProfileActivity.class);
        startActivity(intent);
    }

    public void btnMyLocationClick(View view)
    {
        String query = "SELECT * FROM profile";
        if(!DBClass.checkIfRecordExist(query))
        {
            Toast.makeText(this, "please update your profile first", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, MyLocationActivity.class);
            startActivity(intent);
        }
    }
}
