package com.csc3003.healthcaser.DatabaseTools;

/**
 * Created by charles on 8/14/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//package com.DatabaseTools;

/**
 * Created by charles on 8/12/2015.
 */
public class UserDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_USER = "user";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USER = "username";

    public UserDBHandler(Context context, String name,
                         SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " +
                TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PASSWORD
                + " TEXT," + COLUMN_USER + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
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
        Log.i("number records", numRecords+"");
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

            Log.i("addition","password:"+password+"    username"+username);


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
        Log.i("Password", resultPath);
        return resultPath;
    }

}
