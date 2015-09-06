package com.csc3003.healthcaser;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.csc3003.databaseTools.HCFileManager;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
//Testing the pre-master
public class HealthCaseTestActivity extends ActionBarActivity implements DiagnosisDialog.DiagnosisDialogListener{
    TextView title,information;
    PopupMenu askMenu,testMenu;
    DialogFragment newFragment;
    int count = 0;
    PopupWindow auditTrail;
    Boolean externalStorageReadable;
//    final String EXTENRAL_HEALTH_CASES_FOLDER = getExternalFilesDir(null);
    MenuInflater inflater;
    View content;
    HealthCase hc ;
    ImageView testImage;//temp
    //stats variables
    //totalDiagnose - the number of diagnose attemps
    //firstDiagnose - the number of moves made before their first diagnose
    int totalMoves, totalDiagnose, firstDiagnose;

    boolean doubleBackToExitPressedOnce = false;
    //return to the health cases
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            Intent intent = new Intent (this, ChooseCaseActivity.class);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to return to the cases list", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    File[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_case_test);
        content = this.findViewById(android.R.id.content);
        //determine readable external storage
        externalStorageReadable = isExternalStorageReadable();
        Boolean createFolderCase;
        testImage = (ImageView)findViewById(R.id.test_image);
        if (externalStorageReadable){
            //~/health.caser
            //com.csc3003.healthcaser
            File externalStorage = Environment.getExternalStoragePublicDirectory("com.csc3003.healthcaser");
            if (!externalStorage.exists()){
                createFolderCase = externalStorage.mkdir();
            }
            System.out.println(externalStorage);
            images = externalStorage.listFiles();

            if (externalStorage!=null){
                //File[] cases = externalStorage.listFiles();
              /*  File cases  = new File (externalStorage.getPath()+"health.caser");
                images = cases.listFiles();
                for (int i = 0; i < images.length; i ++){
                    System.out.println(images[i]);
                }*/
            }

        }
        totalMoves = 0;
        totalDiagnose = 0;
        firstDiagnose = 0;
        auditTrail = new PopupWindow();
        //Save data when a user rotates
        if (savedInstanceState != null) {
            totalMoves = savedInstanceState.getInt("TOTAL_MOVES");
            totalDiagnose = savedInstanceState.getInt("TOTAL_DIAGNOSE");
            information.setText(savedInstanceState.getString("DISPLAYED_INFO"));
            firstDiagnose = savedInstanceState.getInt("FIRST_DIAGNOSE");

        }
        HCFileManager hcFileManager = new HCFileManager(getFilesDir().getPath());
        Intent intent = getIntent();
        String filename = intent.getStringExtra(ChooseCaseActivity.DATA_KEY_HEALTH_CASE);
        hc = hcFileManager.readHealthCaseFromXMLFile(filename);

        title = (TextView)findViewById(R.id.case_title);
        information = (TextView)findViewById(R.id.case_information);
        information.setText(hc.getStart());


    }

    /*Adapted http://developer.android.com/training/basics/data-storage/files.html*/
    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    //save data if the activity is destroyed
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("TOTAL_MOVES", totalMoves);
        outState.putInt("TOTAL_DIAGNOSE", totalDiagnose);
        outState.putInt("FIRST_DIAGNOSE", firstDiagnose);
        outState.putString("DISPLAYED_INFO", information.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_case_test, menu);
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

    //Display and capture diagnosis popup
    public void diagnose(View view){
         newFragment = new DiagnosisDialog();
         newFragment.show(getFragmentManager(), "diagnosis");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String diagnosis) {
        if (firstDiagnose==0){
            System.out.println("First diagnose is set");
            firstDiagnose = totalMoves;
        }
        totalDiagnose+=1;
        if (diagnosis.equals("CORRECT")){
            Intent resultsIntent = new Intent(this, HealthCaseTestResultActivity.class);
            resultsIntent.putExtra("TOTAL_MOVES", totalMoves);
            resultsIntent.putExtra("TOTAL_DIAGNOSE", totalDiagnose);
            resultsIntent.putExtra("FIRST_DIAGNOSE", firstDiagnose);
            startActivity(resultsIntent);
        }else{
            Toast.makeText(this, "Diagnosis incorrect.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        newFragment.dismiss();
    }

    //***********
    //POPUP MENU LISTENER
    private PopupMenu.OnMenuItemClickListener askMenuListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            information.setText("");
            for (String a : hc.getHistory()) {
                information.append(a + "\n");
            }
            totalMoves+=1;
            return false;
        }
    };

    private PopupMenu.OnMenuItemClickListener testMenuListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            information.setText(hc.getTests().get(0).getName() + ":\n");
            for (Test t : hc.getTests()) {
                information.append(t.getResults().get(0) + "\n");
            }
            totalMoves+=1;

            Drawable d = Drawable.createFromPath(images[count].getPath());
            testImage.setImageDrawable(d);

            if (count == images.length-1){
                count = 0 ;
            }else{
                count+=1;
            }
            return false;
        }
    };
    //POPUP MENU METHODS
    public void showAskPopup(View view) {
        //SETUP
        askMenu = new PopupMenu(this,view);
        inflater = askMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_ask, askMenu.getMenu());
        askMenu.setOnMenuItemClickListener(askMenuListener);
        askMenu.show();
    }

    public void showTestPopup(View view) {
        //SETUP
        testMenu = new PopupMenu(this, view);
        inflater = testMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_test, testMenu.getMenu());
        testMenu.setOnMenuItemClickListener(testMenuListener);
        testMenu.show();
    }









}
