package com.csgang.android.onthego;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListRemindersActivity extends AppCompatActivity {
    Context context;
    private List<Reminder> reminderList = new ArrayList<>();
    ReminderAdapter reminderAdapter;
    private RecyclerView rview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reminders);
        context = this;
        rview = (RecyclerView) findViewById(R.id.rview);

        reminderAdapter = new ReminderAdapter(reminderList);

        rview.setHasFixedSize(true);
        RecyclerView.LayoutManager menuLayoutManager = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(menuLayoutManager);
        rview.setAdapter(reminderAdapter);

        String query = "SELECT reminderid, location, longitude, latitude, range, message, reminded FROM reminders ORDER BY reminderid DESC";
        Cursor cursor = DBClass.getCursorData(query);
        while(cursor.moveToNext()) {
            Reminder reminder = new Reminder();
            reminder.reminderid = cursor.getString(0);
            reminder.location = cursor.getString(1);
            reminder.longitude = cursor.getString(2);
            reminder.latitude = cursor.getString(3);
            reminder.range = cursor.getString(4);
            reminder.message = cursor.getString(5);
            reminder.reminded = cursor.getString(6);
            reminderList.add(reminder);
        }
        cursor.close();
        reminderAdapter.notifyDataSetChanged();
    }
}
