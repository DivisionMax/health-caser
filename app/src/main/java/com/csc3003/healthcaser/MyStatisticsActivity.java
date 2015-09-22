package com.csc3003.healthcaser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.csc3003.databaseTools.UserDBHandler;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;


public class MyStatisticsActivity extends ActionBarActivity {
    private Intent intent;
    TextView  averageNumberOfMovesDisplay, averageDiagnoseAccuracyDisplay, averageDiagnoseMoveRatioDisplay, totalHealthCasesCompletedDisplay ;
    float averageNumberOfMoves, averageDiagnoseAccuracy, averageDiagnoseMoveRatio, totalHealthCasesCompleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_statistics);
        DecimalFormat df = new DecimalFormat("#.##");
        averageNumberOfMovesDisplay = (TextView)findViewById(R.id.average_number_of_moves_data);
        averageDiagnoseAccuracyDisplay = (TextView)findViewById(R.id.average_diagnose_accuracy_data);
        averageDiagnoseMoveRatioDisplay = (TextView)findViewById(R.id.average_diagnose_move_ratio_data);
        totalHealthCasesCompletedDisplay = (TextView)findViewById(R.id.total_health_cases_completed_data);
        UserDBHandler userStatDB = new UserDBHandler(this, null, null, 1);
        //get current user name
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_HC, 0);
        String strEmail = settings.getString(LoginActivity.PREFS_HC_CURRENTUSER, "Not found");
        //only look up the user statistics if he or she has any
        if(userStatDB.countRecords(strEmail)!=0) {
            //get and calculate the relevant stats
            averageNumberOfMoves = userStatDB.getAverageStatistic(strEmail, UserDBHandler.COLUMN_TOTAL_MOVES);
            totalHealthCasesCompleted = userStatDB.countRecords(strEmail);
            averageDiagnoseAccuracy =  userStatDB.getAverageStatistic(strEmail, UserDBHandler.COLUMN_DIAGNOSIS_ACCURACY);
            float totalMove = userStatDB.getAverageStatistic(strEmail, UserDBHandler.COLUMN_TOTAL_MOVES);
            averageDiagnoseMoveRatio =  userStatDB.getAverageStatistic(strEmail, UserDBHandler.COLUMN_DIAGNOSE_MOVE_RATIO);
            averageNumberOfMovesDisplay.setText(df.format(averageNumberOfMoves) + "");
            averageDiagnoseMoveRatioDisplay.setText(df.format(averageDiagnoseMoveRatio) + "");
            averageDiagnoseAccuracyDisplay.setText(df.format(averageDiagnoseAccuracy) + "");
            totalHealthCasesCompletedDisplay.setText(df.format(totalHealthCasesCompleted) + "");

        }

    }
    public void viewHealthCasesStats(View view) {
        Intent chooseCase = new Intent(this, ChooseCaseActivity.class);
        startActivity(chooseCase);
    }
}
