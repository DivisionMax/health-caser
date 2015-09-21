package com.csc3003.healthcaser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.DatabaseTools.UserDBHandler;
import com.csc3003.databaseTools.HCFileManager;
import com.csc3003.databaseTools.Setup;
import com.csc3003.databaseTools.UserDBHandler;

import java.io.File;
import java.util.ArrayList;

public class LoginActivity extends Activity {

    EditText email, password;
    String emailStr, passwordStr;
    UserDBHandler userDB;

    final int duration = Toast.LENGTH_SHORT;
    Intent intent;

    public static final String PREFS_HC = "HC";
    public static final String PREFS_HC_CURRENTUSER = "currentUser";

    SharedPreferences settings ;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        settings = getSharedPreferences(PREFS_HC, 0);
        editor = settings.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDB = new UserDBHandler(this, null, null, 1);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password.setTypeface(email.getTypeface());

        //load standard xml into internal via copying from asset folder
        loadStandardXML();
    }

    private void redirect (){
        intent = new Intent(this,ChooseCaseActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
           getMenuInflater().inflate(R.menu.menu_login, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.about) {
            DialogFragment newFragment = new LicenseDialog();
            newFragment.show(getFragmentManager(), "license");
        }
        return super.onOptionsItemSelected(item);
    }


    //    Login
    public void login(View view) {
        emailStr = email.getText().toString().toLowerCase();
        passwordStr = password.getText().toString();
//        redirect(); //TEMP. REMOVE THIS LINE AND UNCOMMENT THE CODE BELOW
        if(emailStr.equals("")||passwordStr.equals(""))
        {
            //Adapted from http://developer.android.com/guide/topics/ui/notifiers/toasts.html
            Context context = getApplicationContext();
            CharSequence msg = "Username and or Password are empty";
            Toast errReg = Toast.makeText(context,msg,duration);
            errReg.show();
        }
        else{
            if(userDB.isUserExist(emailStr)&& userDB.isCorrectPassword(emailStr,passwordStr)) {
                successfulLoginOrRegistration(emailStr, "You have logged in");
                //record current username of the person who has logged in
                editor.putString(PREFS_HC_CURRENTUSER, emailStr);
                editor.commit();
            }
            else
            {
                Context context = getApplicationContext();
                CharSequence msg = "Username or Password is incorrect";
                Toast errReg = Toast.makeText(context,msg,duration);
                errReg.show();
            }
        }

    }
    //copy the the standard health cases from the assets folder into the internal storage
    public void loadStandardXML()
    {

        String internal_path = getFilesDir().getPath();
        String full_internal_path = internal_path +"/HealthCases";
        //if the internal folder exists, it's like the files are already copied.
        if (!(new File(full_internal_path)).exists())
            Setup.copyAssetFolder(getAssets(),"HealthCases" ,internal_path+"/HealthCases" );
        }
    //    Register a User
    public void register(View view) {
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
        if(emailStr.equals("")||passwordStr.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence msg = "Registration failed. Username and Password cannot be empty";
            Toast errReg = Toast.makeText(context,msg,duration);
            errReg.show();
        }
        else{
            if(userDB.isUserExist(emailStr)){
                Context context = getApplicationContext();
                CharSequence msg = "Cannot register " + emailStr +", username is already registered";
                Toast errReg = Toast.makeText(context,msg,duration);
                errReg.show();
            }
            else
            {
                User aUser = new User();
                aUser.setUsername(emailStr);
                aUser.setPassword(passwordStr);
                userDB.addUser( emailStr , passwordStr);
                //record current username of the person who has logged in
                editor.putString(PREFS_HC_CURRENTUSER, emailStr);
                editor.commit();
                successfulLoginOrRegistration(emailStr, "Welcome. You have registered");
                Log.i("LOGIN", "User has registered");
            }
        }
    }
    //Remove code repetition
    AlertDialog alertDialog;
    private void successfulLoginOrRegistration(String username, String message){
        alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle("Welcome, " + username);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        redirect();
                    }
                });
        alertDialog.show();
    }

}