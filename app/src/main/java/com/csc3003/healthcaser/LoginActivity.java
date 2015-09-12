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
import com.csc3003.databaseTools.HCFileManager;
import com.csc3003.databaseTools.UserDBHandler;

import java.util.ArrayList;

public class LoginActivity extends Activity {

    EditText email, password;
    String emailStr, passwordStr;
    UserDBHandler userDB;

    final int duration = Toast.LENGTH_SHORT;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDB = new UserDBHandler(this, null, null, 1);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password.setTypeface(email.getTypeface());

        //laod standard
        loadStandardXML();
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
    //load the standard health case xml files
    public void loadStandardXML()
    {
        HCFileManager fileManager = new HCFileManager(getFilesDir().getPath());
        HealthCase hc =  new HealthCase();
        // Populate the health case arrays
        final ArrayList<Test> testList = new ArrayList<Test>();
        final ArrayList<String> results = new ArrayList<String>();
        final ArrayList<String> hist = new ArrayList<String>();
        final ArrayList<String> pt = new ArrayList<String>();

        results.add("Results: 120\\80");
        Test bloodPressureTest = new Test();
        bloodPressureTest.setName("Blood pressure");
        bloodPressureTest.setResults(results);
        testList.add(bloodPressureTest);

        pt.add("None.");
        hist.add("Orthotopic Liver Transplant in 1992 for cirrhosis secondary to alcohol use.\"");
        hist.add("Hypertension.");
        hist.add("Cadaveric Renal Transplant in 1994 for chronic renal failure secondary to glomerulonephritis.");
        hc.setStart("A 55 year-old man presents to the emergency room at Presbyterian University Hospital with a chief complaint of \"I have a severe headache and fever.\"");

        String diagnosis = "Diagnosis: Disseminated Cryptococcosis.";
        hc.setDiagnosis(diagnosis);

        hc.setPhysicalState("Alive");
        hc.setTests(testList);
        hc.setHistory(hist);
        hc.setPastTests(pt);
        hc.setPastTreatments(pt);
        //----

        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase1.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase2.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase3.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase4.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase5.xml");

//        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase.xml");
//        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase1.xml");
//        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase2.xml");
//        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase3.xml");
//        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase4.xml");
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
                        redirect();
                    }
                });
        alertDialog.show();
//        redirect();
    }

}