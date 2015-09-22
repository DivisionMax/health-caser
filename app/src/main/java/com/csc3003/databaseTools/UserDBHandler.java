package com.csc3003.databaseTools;

/**
 * Created by charles on 8/14/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

//package com.DatabaseTools;

/**
 * Created by charles on 8/12/2015.
 */
public class UserDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";

    //tables
    private static final String TABLE_USER = "user";
    private static final String TABLE_USERSTATS = "UserStats";


    //column for user table
    private static final String COLUMN_ID = "_id_user";
    private static final String COLUMN_PASSWORD = "password";

    //column for both user and stats table
    private static final String COLUMN_USER = "username";

    //column for userstats
    public static final String COLUMN_ID_STATS = "_id_stats";
    public static final String COLUMN_TOTAL_MOVES = "totalMoves";
    public static final String COLUMN_DIAGNOSIS_ACCURACY = "accuracy";
    public static final String COLUMN_DIAGNOSE_MOVE_RATIO = "diganoseMoveRatio";
    //public static final String COLUMN_TOTAL_DIAGNOSE = "totalDiagnose";
    //public static final String COLUMN_FIRST_DIAGNOSE = "firstDiagnose";
    public static final String COLUMN_DATE_FINISHED =  "dateFinished";

    private static final String CREATE_TABLE_USER =  "CREATE TABLE " +
            TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PASSWORD
            + " TEXT," + COLUMN_USER + " TEXT" + ")";

    private static final String CREATE_TABLE_USERSTATS =  "CREATE TABLE " +
            TABLE_USERSTATS + "("
            + COLUMN_ID_STATS + " INTEGER PRIMARY KEY," + COLUMN_TOTAL_MOVES
            + " INTEGER," + COLUMN_DIAGNOSIS_ACCURACY + " FLOAT, " + COLUMN_DIAGNOSE_MOVE_RATIO + " FLOAT,"+ COLUMN_DATE_FINISHED +" DATE,"+COLUMN_USER+" TEXT )";



    public UserDBHandler(Context context, String name,
                         SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_USERSTATS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {

    }

    public boolean isUserExist(String username)
    {
        String query = "Select COUNT(*) FROM " + TABLE_USER + " WHERE " + COLUMN_USER + " =  \"" + username + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        int numRecords = 0;

        if (cursor.moveToFirst()) {


            cursor.moveToFirst();
            numRecords = Integer.parseInt(cursor.getString(0));
            cursor.close();


        }

        if(numRecords == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }



    //remember you only call this method once


    public void addUser( String username, String password) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_PASSWORD, password);
            values.put(COLUMN_USER, username);

            SQLiteDatabase db = this.getWritableDatabase();

            db.insert(TABLE_USER, null, values);
            db.close();
            Log.i("DB", "New user persisted");



    }
    public void updatePasswordForUser(String user, String password )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String updatePathSQL = "UPDATE "+TABLE_USER +" SET "+COLUMN_PASSWORD+"= '"+password+"' where "+ COLUMN_USER +" = '"+ user+"'";
        db.execSQL(updatePathSQL);
    }
    public boolean isCorrectPassword(String username, String password)
    {
        if (password.equals( findPassword(username)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public String findPassword(String userName) {


        String query = "Select "+COLUMN_PASSWORD+  " FROM " + TABLE_USER + " WHERE " + COLUMN_USER + " =  \"" + userName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        String resultPath = "";

        if (cursor.moveToFirst()) {


            cursor.moveToFirst();
            resultPath = cursor.getString(0);
            cursor.close();


        }
        else
        {
            resultPath ="error user not found";
        }
        db.close();

        return resultPath;
    }

    //table userstats methods
    public void addNewStatsRecord( String username, int totalMoves, float diagnoseAccuracy, float diagnoseMoveRatio, Date dateFinished) {

        ContentValues values = new ContentValues();

        String dateFinishedStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        values.put(COLUMN_USER, username);
        values.put(COLUMN_TOTAL_MOVES, totalMoves);
        values.put(COLUMN_DIAGNOSIS_ACCURACY, diagnoseAccuracy);
        values.put(COLUMN_DIAGNOSE_MOVE_RATIO, diagnoseMoveRatio);
        values.put(COLUMN_DATE_FINISHED, dateFinishedStr );


        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_USERSTATS, null, values);

        db.close();
        Log.i("DB", "Test stats instance persisted");
    }
    public boolean recordExist(String username)
    {
        if (countRecords(username) > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public int countRecords (String userName ) {


        String query = "Select COUNT(*) FROM " + TABLE_USERSTATS + " WHERE " + COLUMN_USER + " =  \"" + userName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        int count ;

        if (cursor.moveToFirst()) {


            cursor.moveToFirst();
            count =  Integer.parseInt(cursor.getString(0));
            cursor.close();


        }
        else
        {
            count =0;
        }


        db.close();

        return count;
    }
    public float getSumStatistic(String username, String statistic)
    {
        String query = "Select SUM( "+statistic+  ") FROM " + TABLE_USERSTATS + " WHERE " + COLUMN_USER + " =  \"" + username + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        float sumStatisticF ;

        if (cursor.moveToFirst()) {


            cursor.moveToFirst();
            sumStatisticF =  Float.parseFloat(cursor.getString(0));
            cursor.close();


        }
        else
        {
            sumStatisticF =0;
        }
        db.close();
        return sumStatisticF;
    }

    public float getAverageStatistic(String username, String statistic) {
        float sumStatisticF = getSumStatistic(username, statistic);
        int numberOfRecords = countRecords( username );
        float statisticF;
        statisticF = sumStatisticF/numberOfRecords;
        Log.d("statistic test", statistic + ":" + statisticF);
        return statisticF;
    }


}
