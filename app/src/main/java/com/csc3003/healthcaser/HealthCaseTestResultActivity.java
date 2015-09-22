package com.csc3003.healthcaser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.csc3003.databaseTools.UserDBHandler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;


public class HealthCaseTestResultActivity extends ActionBarActivity {
    float diagnoseMoveRatio, totalMoves, totalDiagnose, firstDiagnose, diagnoseAccuracy;
    TextView  totalMovesDisplay, firstDiagnoseDisplay, diagnoseAccuracyDisplay, diagnoseMoveRatioDisplay;
    /*
    * Total moves - total moves during the test
    * First diagnose - total moves before the user's first diagnose
    * Diagnosis accuracy - 1 / (diagnosis attempts)
    * Diagnosis vs moves - (num. diagnosise) / (total moves) -> A low ratio may indicate overly cautious.
    *   (cont.) and a high ratio may indicate risky diagnoses.
    * */
    boolean doubleBackToExitPressedOnce = false;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_case_test_result);
        UserDBHandler  userStatDB  ;
        //retrieve the stats
        //if the user rotated
        if (savedInstanceState != null) {
            totalMoves = savedInstanceState.getFloat("TOTAL_MOVES");
            totalDiagnose = savedInstanceState.getFloat("TOTAL_DIAGNOSE");
            firstDiagnose = savedInstanceState.getFloat("FIRST_DIAGNOSE");
        }
        //if the user arrived via an intent (a successful diagnosis)
        else {
            Bundle extras = getIntent().getExtras();
            totalMoves = (float) extras.getInt("TOTAL_MOVES");
            totalDiagnose = (float) extras.getInt("TOTAL_DIAGNOSE");
            firstDiagnose = (float) extras.getInt("FIRST_DIAGNOSE");
            userStatDB = new UserDBHandler(this, null, null, 1);

            //get current username of user
            SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_HC, 0);
            String strEmail = settings.getString(LoginActivity.PREFS_HC_CURRENTUSER, "Not found");
            //get the current date for record use
            Date currentDate = new Date();
            diagnoseAccuracy = 1/totalDiagnose;

            if (totalMoves==0){
                diagnoseMoveRatio = 0 ;
            }else{
                diagnoseMoveRatio = totalDiagnose/totalMoves;
            }


            //insert record
            userStatDB.addNewStatsRecord( strEmail , (int)totalMoves, diagnoseAccuracy, diagnoseMoveRatio, currentDate );
            //testing
        }
        totalMovesDisplay = (TextView)findViewById(R.id.total_moves_data);
        firstDiagnoseDisplay = (TextView)findViewById(R.id.first_diagnose_data);
        diagnoseAccuracyDisplay = (TextView)findViewById(R.id.diagnose_accuracy_data);
        diagnoseMoveRatioDisplay = (TextView)findViewById(R.id.diagnose_move_ratio_data);

        NumberFormat nf = NumberFormat.getInstance();
        NumberFormat percentageFormat = NumberFormat.getPercentInstance();

        totalMovesDisplay.setText(nf.format(totalMoves) + "");
        firstDiagnoseDisplay.setText(nf.format(firstDiagnose) + "");
        diagnoseAccuracyDisplay.setText(percentageFormat.format(diagnoseAccuracy) + "");
        diagnoseMoveRatioDisplay.setText(percentageFormat.format(diagnoseMoveRatio) + "");
    }
    //save data if the activity is destroyed
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("TOTAL_MOVES", totalMoves);
        outState.putFloat("TOTAL_DIAGNOSE", totalDiagnose);
        outState.putFloat("FIRST_DIAGNOSE", firstDiagnose);
        super.onSaveInstanceState(outState);
    }
    public void viewHealthCases(View view) {
        Intent chooseCase = new Intent(this, ChooseCaseActivity.class);
        startActivity(chooseCase);
    }

}
