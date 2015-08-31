package com.csc3003.healthcaser;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_case_test_result);
        //retrieve the stats
        Bundle extras = getIntent().getExtras();
        totalMoves = (float)extras.getInt("TOTAL_MOVES");
        totalDiagnose = (float)extras.getInt("TOTAL_DIAGNOSE");
        firstDiagnose = (float)extras.getInt ("FIRST_DIAGNOSE");

        System.out.println("Total moves; " + totalMoves);
        System.out.println("Total diagnose; " + totalDiagnose);
        System.out.println("First diagnose; " + firstDiagnose);

        totalMovesDisplay = (TextView)findViewById(R.id.total_moves_data);
        firstDiagnoseDisplay = (TextView)findViewById(R.id.first_diagnose_data);
        diagnoseAccuracyDisplay = (TextView)findViewById(R.id.diagnose_accuracy_data);
        diagnoseMoveRatioDisplay = (TextView)findViewById(R.id.diagnose_move_ratio_data);

        diagnoseAccuracy = 1/totalDiagnose;

        if (totalMoves==0){
            diagnoseMoveRatio = 0 ;
        }else{
            diagnoseMoveRatio = totalDiagnose/totalMoves;
        }
        totalMovesDisplay.setText(totalMoves + "");
        firstDiagnoseDisplay.setText(firstDiagnose + "");
        diagnoseAccuracyDisplay.setText(diagnoseAccuracy + "");
        diagnoseMoveRatioDisplay.setText(diagnoseMoveRatio + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_case_test_result, menu);
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

    public void viewHealthCases(View view) {
        Intent chooseCase = new Intent(this, ChooseCaseActivity.class);
        startActivity(chooseCase);
    }

}
