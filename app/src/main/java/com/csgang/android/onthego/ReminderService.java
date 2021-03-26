package com.csgang.android.onthego;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ReminderService extends Service{
    public static Context mContext = null;
    public static double currentLatitude = 0, currentLongitude = 0;
    public ReminderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread(new MyAllTimeRunningThread(this, startId, intent));
        thread.start();
        Intent notificationIntent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent).build();
        startForeground(1337, notification);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60;
        dist = dist * 1852;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    final class MyAllTimeRunningThread implements Runnable {
        int serviceId;
        Context context;
        Intent intent;

        public MyAllTimeRunningThread(Context context, int setviceId, Intent intent) {
            this.serviceId = setviceId;
            this.context = context;
            this.intent = intent;
        }

        @Override
        public void run() {
            int i = 0;
            synchronized (this) {
                while (true) {
                    try {
                        Log.i("Current Location ", "Latitude / Longitude : " + currentLatitude + " " + currentLongitude);
                        currentLongitude= MyLocationActivity.longitude  ;
                        currentLatitude = MyLocationActivity.latitude ;
                        //check for reminder
                        String query = "SELECT location, longitude, latitude, range, message, reminderid FROM reminders WHERE reminded = 'No'";
                        Cursor cursor = DBClass.getCursorData(query);
                        while (cursor.moveToNext()) {
                            String rem_location = cursor.getString(0);
                            double rem_longitude = cursor.getDouble(1);
                            double rem_latitude = cursor.getDouble(2);
                            int rem_range = cursor.getInt(3);
                            String rem_message = cursor.getString(4);
                            String reminderid = cursor.getString(5);
                            double rem_distance = distance(currentLatitude, currentLongitude, rem_latitude, rem_longitude);

                            Log.i("Reminder ", "Details : " + rem_location + "  " + rem_latitude + " " + rem_longitude + " " + rem_range + " " + rem_message + " " + rem_distance);

                            if (rem_distance <= rem_range) {
                                //Alarm Activity
                            }
                        }
                        cursor.close();


                        wait(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
