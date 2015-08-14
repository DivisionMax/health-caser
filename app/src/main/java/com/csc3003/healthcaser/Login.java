package com.csc3003.healthcaser;
import android.content.Context;
import android.util.Log;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Created by Alan Berman on 8/7/2015.
 * Login stores a map of users,
 * with keys being usernames and values passwords.
 * Users can be added, and their credentials checked.
 */
public class Login {
    private String username, password;
    private Map<String, String> userList;
    private int numUsers;
    private JSONObject users;
    public Login() {
        numUsers = 0;
        userList = new HashMap<String,String>();
    }

    //Add user to list of users (i.e. register user)
    //Check username not already in dictionary, and
    //check passsword and user are of appropriate lengths
    public boolean addUser(String user, String pass) { //addUser
        //If both username and password are given
        //and are both at least 6 characters long
        if ((user != null && pass != null) &&
                (user.length() >= 6 && pass.length() >= 6)) {
            //If the username doesn't already exist in the dictionary
            if (userList.containsKey(user) == false) {
                userList.put(user, pass);
                Log.i("mytag","Added!");
                numUsers++;
                return true;
            } else {
                System.err.println("Error - username " + user + " is taken.");
                System.err.println("Please enter a different username");
                return false;
            }
        } else {
            System.err.println("Error - invalid username and/or password.");
            System.err.println("Make sure both fields have been entered and each field is" +
                    "at least 6 characters");
            return false;
        }
    }
    //Login the user. Check username exists and that the password entered is correct.
    public boolean attemptUserLogin(String name,String pass)
    {
        if (userList.containsKey(name)==false)
        {
            System.err.println("Error - username "+name +" is not registered.");
            System.err.println("Pleae re-enter the username");
            return false;
        }
        else if (!(userList.get(name).equals(pass)))
        {
            System.err.println("Error - incorrect password entered.");
            System.err.println("Pleae re-enter the password.");
            return false;
        }
        Log.i("mytag", "Logged in!");
        return true;
    }

    public void writeUserDatabase()
    {
        //Adapted from http://www.mkyong.com/java/json-simple-example-read-and-write-json/
        try {
            System.err.println("aweeee");
            FileWriter file = new FileWriter("johnson.txt");
            users = new JSONObject(userList);
            String jsonString = users.toString();
            System.out.println(jsonString.length()+ " yayoo");
            file.write(jsonString);
            file.flush();
            file.close();
            System.err.println("ayylmao!");
        }
        catch (IOException E) {
            E.printStackTrace();
        }

    }

}

