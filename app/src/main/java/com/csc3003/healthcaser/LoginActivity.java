package com.csc3003.healthcaser;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "LoginActivity";

    Button login, register;
    EditText email, password;
    TextView feedback;

    String emailStr, passwordStr, feedbackMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        feedback = (TextView) findViewById(R.id.feedback);


//        login = (Button) findViewById(R.id.login);
//         register= (Button) findViewById(R.id.register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

        Intent intent = new Intent(this, ChooseCaseActivity.class);
        startActivity(intent);

/*
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();


        if (emailStr != null && passwordStr != null) {
            feedbackMsg = "User has logged in";
            Log.i(TAG, feedbackMsg );
            feedback.setText(feedbackMsg);
        }
        else {
        }
*/
    }

//    Register
public void register(View view) {

    emailStr = email.getText().toString();
    passwordStr = password.getText().toString();

    if (emailStr != null && passwordStr != null) {
        feedbackMsg = "User has logged in";
        Log.i(TAG, feedbackMsg );
        feedback.setText(feedbackMsg);
    }
    else {
    }
}


}
