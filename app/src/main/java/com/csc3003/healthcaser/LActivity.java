package com.csc3003.healthcaser;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//Activity that facilitates logins and registrations
//Users can log in or register, and relevant errors/
//notifications are displayed on login/registration
public class LActivity extends Activity {
    Button logButton;
    Button regButton;
    EditText editPass;
    EditText editName;
    Login loginUsers;
    private String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l);
        final int duration = Toast.LENGTH_SHORT;
        loginUsers = new Login();
        logButton = (Button)findViewById(R.id.login);
        regButton = (Button)findViewById(R.id.register);
        editName = (EditText)findViewById(R.id.email);
        editPass = (EditText)findViewById(R.id.password);
        //Login
        logButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                username = editName.getText().toString();
                password = editPass.getText().toString();
                if (loginUsers.attemptUserLogin(username, password))
                {
                    //Adapted from http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
                    AlertDialog alertDialog = new AlertDialog.Builder(LActivity.this).create();
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
        regButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                username = editName.getText().toString();
                password = editPass.getText().toString();
                if (loginUsers.addUser(username,password))
                {

                    //Adapted from http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
                    AlertDialog alertDialog = new AlertDialog.Builder(LActivity.this).create();
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
}