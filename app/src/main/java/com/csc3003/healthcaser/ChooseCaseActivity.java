package com.csc3003.healthcaser;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.csc3003.databaseTools.HCFileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChooseCaseActivity extends ActionBarActivity {

    private ListView casesView;
    private List<String> cases;
    private Intent intent;
    static String DATA_KEY_HEALTH_CASE = "HEALTH_CASE";

    boolean doubleBackToExitPressedOnce = false;
    //return to the health cases
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press 'Back' again to close Health Caser", Toast.LENGTH_SHORT).show();

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
        setContentView(R.layout.activity_choose_case);

        casesView = (ListView) findViewById(R.id.casesView);

        HCFileManager fileManager = new HCFileManager(getFilesDir().getPath());

        File[] fileList =  fileManager.returnFileList();
        populateCasesView(fileList);
        //Start the health case when it is clicked
        casesView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
//                                int num = Integer.parseInt(cases.get(position));

                intent = new Intent(view.getContext(),HealthCaseTestActivity.class);




                String filename = cases.get(position);

                intent.putExtra(DATA_KEY_HEALTH_CASE, filename);

                startActivity(intent);
            }





        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_choose_case, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.user_statistics) {
             intent = new Intent(this, MyStatisticsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

//    populate the spinner with externalSD cases
    public void populateCasesView(File[] fileNames) {

        cases = new ArrayList<String>();
         //rand = new Random();
        int k;
        for (int i = 0 ; i < fileNames.length; i ++ ){
             //k = rand.nextInt(50) + 100;
            //cases.add("Case #" + k);
            cases.add(fileNames[i].getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cases);
        casesView.setAdapter(dataAdapter);
        casesView.setAdapter(dataAdapter);
    }
    public void chooseHealthCase(View v)
    {
        Button b = (Button)v;
        String healthCaseNameFile = b.getText().toString();
        intent.putExtra(DATA_KEY_HEALTH_CASE, healthCaseNameFile);
        intent = new Intent (this, HealthCaseTestActivity.class);
        startActivity(intent);
    }

    public void randomCase(View v){
//        rand = new Random();
//        int i = rand.nextInt(cases.size());

        //String text = b.getText().toString();

        intent = new Intent (this, HealthCaseTestActivity.class);

        startActivity(intent);

    }
}
