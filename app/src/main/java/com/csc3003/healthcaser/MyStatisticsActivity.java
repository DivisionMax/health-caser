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


public class MyStatisticsActivity extends ActionBarActivity {
    private Intent intent;
    TextView  averageNumberOfMovesDisplay, averageDiagnoseAccuracyDisplay, averageDiagnoseMoveRatioDisplay, totalHealthCasesCompletedDisplay ;
    float averageNumberOfMoves, averageDiagnoseAccuracy, averageDiagnoseMoveRatio, totalHealthCasesCompleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_statistics);

        averageNumberOfMovesDisplay = (TextView)findViewById(R.id.average_number_of_moves_data);
        averageDiagnoseAccuracyDisplay = (TextView)findViewById(R.id.average_diagnose_accuracy_data);
        averageDiagnoseMoveRatioDisplay = (TextView)findViewById(R.id.average_diagnose_move_ratio_data);
        totalHealthCasesCompletedDisplay = (TextView)findViewById(R.id.total_health_cases_completed_data);

        UserDBHandler userStatDB = new UserDBHandler(this, null, null, 1);

        //get current user name
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_HC, 0);
        String strEmail = settings.getString(LoginActivity.PREFS_HC_CURRENTUSER, "Not found");

        //Log.e("sql",userStatDB.getAverageStatistic(strEmail, UserDBHandler.COLUMN_TOTAL_MOVES)+"");

        //only look up the user statistics if he or she has any
        if(userStatDB.countRecords(strEmail)!=0) {
            //get and calculate the relevant stats
            averageNumberOfMoves = userStatDB.getAverageStatistic(strEmail, UserDBHandler.COLUMN_TOTAL_MOVES);

            float averageDiagnose = userStatDB.getAverageStatistic(strEmail, UserDBHandler.COLUMN_TOTAL_DIAGNOSE);
            float totalDiagnose = userStatDB.getSumStatistic(strEmail, UserDBHandler.COLUMN_TOTAL_DIAGNOSE);
            totalHealthCasesCompleted = userStatDB.countRecords(strEmail);
            averageDiagnoseAccuracy =1/( averageDiagnose);
            float totalMove = userStatDB.getAverageStatistic(strEmail, UserDBHandler.COLUMN_TOTAL_MOVES);
            averageDiagnoseMoveRatio = totalDiagnose / totalMove;

            averageNumberOfMovesDisplay.setText(averageNumberOfMoves + "");
            averageDiagnoseMoveRatioDisplay.setText(averageDiagnoseMoveRatio + "");
            averageDiagnoseAccuracyDisplay.setText(averageDiagnoseAccuracy + "");
            totalHealthCasesCompletedDisplay.setText(totalHealthCasesCompleted + "");

        }




        //for now this is pointless but in preparation for later when we send intents with data
        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

//        GraphView graph = (GraphView) findViewById(R.id.UsageLineGraph);
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });
//        graph.addSeries(series);
//
//        GraphView graph1 = (GraphView) findViewById(R.id.CategoryBarChart);
//        BarGraphSeries<DataPoint> seriesBar = new BarGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(0, -1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });
//        graph1.addSeries(seriesBar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_statistics, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.link_cases) {
            intent = new Intent(this,ChooseCaseActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewHealthCasesStats(View view) {
        Intent chooseCase = new Intent(this, ChooseCaseActivity.class);
        startActivity(chooseCase);
    }
}
