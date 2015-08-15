package com.csc3003.healthcaser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.DatabaseTools.UserDBHandler;
import com.csc3003.databaseTools.UserDBHandler;

public class LoginActivity extends Activity {

    EditText email, password;
    TextView feedback;

    String emailStr, passwordStr, feedbackMsg;
    UserDBHandler userDB;

    final int duration = Toast.LENGTH_SHORT;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
<<<<<<< HEAD
        final int duration = Toast.LENGTH_SHORT;
        loginUsers = new Login();
        logButton = (Button)findViewById(R.id.login);
        regButton = (Button)findViewById(R.id.register);
        editName = (EditText)findViewById(R.id.email);
        editPass = (EditText)findViewById(R.id.password);
        //Login
        logButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                username = editName.getText().toString();
                password = editPass.getText().toString();
                if (loginUsers.attemptUserLogin(username, password))
                {
                    //Adapted from http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Welcome back, " + username + "!");
                    alertDialog.setMessage("You've logged in");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else
                {
                    //Adapted from http://developer.android.com/guide/topics/ui/notifiers/toasts.html
                    Context context = getApplicationContext();
                    CharSequence msg = "Login failed.";
                    Toast errReg = Toast.makeText(context,msg,duration);
                    errReg.show();
                }

            }
        });
        //Register
        regButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                username = editName.getText().toString();
                password = editPass.getText().toString();
                if (loginUsers.addUser(username, password))
                {

                    //Adapted from http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Welcome, " + username + "!");
                    alertDialog.setMessage("You've now registered!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence msg = "Registration failed.";
                    Toast errReg = Toast.makeText(context,msg,duration);
                    errReg.show();
                }
            }
        });
=======
        userDB = new UserDBHandler(this, null, null, 1);
>>>>>>> 00fea8cfaa7eeebea87bf738eebcf539d7e308ec

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        feedback = (TextView) findViewById(R.id.feedback);
    }

    private void redirect (){
        intent = new Intent(this,ChooseCaseActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //   getMenuInflater().inflate(R.menu.menu_l, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //    Login
    public void login(View view) {
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
        if(emailStr.equals("")||passwordStr.equals(""))
        {
            //Adapted from http://developer.android.com/guide/topics/ui/notifiers/toasts.html
            Context context = getApplicationContext();
            CharSequence msg = "Login failed";
            Toast errReg = Toast.makeText(context,msg,duration);
            errReg.show();
            feedbackMsg = "Username and or Password are empty";
        }
        else{
            if(userDB.isUserExist(emailStr)&& userDB.isCorrectPassword(emailStr,passwordStr)) {

                successfulLoginOrRegistration(emailStr, "You have logged in");
            }
            else
            {
                Context context = getApplicationContext();
                CharSequence msg = "Login failed";
                Toast errReg = Toast.makeText(context,msg,duration);
                errReg.show();
                feedbackMsg = "Username or Password is incorrect";
            }
        }

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
                userDB.addUser( emailStr , passwordStr);
                successfulLoginOrRegistration(emailStr, "Welcome. You have registered");
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
                    }
                });
        alertDialog.show();
        redirect();
    }

}