package com.csgang.android.onthego;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBClass extends SQLiteOpenHelper {
    public static SQLiteDatabase database;

    public DBClass(Context context){
        super(context, "OnTheGo", null, 1);
    }

    public void onCreate(SQLiteDatabase arg) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    public static void execNonQuery(String query){
        //Execute Insert, Update, Delete, Create table queries
        database.execSQL(query);
    }

    public static Cursor getCursorData(String query){
        Log.i("Query", query);
        Cursor res =  database.rawQuery(query, null);
        Log.i("SELECT Query Count", "Record Count : " + res.getCount());
        return res;
    }

    public static String getSingleValue(String query) {
        try {
            Cursor res = getCursorData(query);
            String value = "";
            if (res.moveToNext()) {
                return res.getString(0);
            }
            return value;
        }
        catch (Exception ex)
        {
            return "";
        }
    }

    public static int getNoOfRows(String query){
        Cursor res =  database.rawQuery(query, null );
        return res.getCount();
    }

    public static boolean checkIfRecordExist(String query){
        Cursor res =  database.rawQuery(query, null );
        if(res.getCount() > 0)
            return true;
        else
            return false;
    }

}
