package com.csc3003.healthcaser;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChooseCaseActivity extends ActionBarActivity {

    private ListView casesView;
    private List<String> cases;
    private List<String> casesPath;
    private Intent intent;
    static String HEALTH_CASE_FOLDER_NAME = "HEALTH_CASE_FOLDER_NAME";
    static String HEALTH_CASE_FILE_PATH = "HEALTH_CASE_FILE_PATH";
    private Random rand;
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
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_case);

        cases = new ArrayList<String>();
        casesPath = new ArrayList<String>();
        casesView = (ListView) findViewById(R.id.casesView);
        //load internal files

        HCFileManager fileManager;
        //interal health case folder
        String internalHCFolderPath = getFilesDir().getPath()+"/HealthCases";
        File internalfile = new File(internalHCFolderPath);
        //checks to see if the HealthCases folder is present at internal path, if it is ill load up the files
        if(internalfile.exists())
        {
            fileManager = new HCFileManager(internalHCFolderPath);
            File[] fileList =  fileManager.returnFileList();
            populateCasesView(fileList);
        }
        //this will check if there is a HealthCases Folder in the external Directory.
        String externalHCFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/HealthCases";
        File externalfile = new File(externalHCFolderPath);
        //the user has an external sd card
        if( isExternalStorageReadable())
        {
            if(externalfile.exists()) {
                fileManager = new HCFileManager(externalHCFolderPath);
                File[] fileList = fileManager.returnFileList();
                populateCasesView(fileList);
            }
        }
        //Start the health case when it is clicked
        casesView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                intent = new Intent(view.getContext(), HealthCaseTestActivity.class);
                String foldername = cases.get(position);
                String filePath = casesPath.get(position);
                intent.putExtra(HEALTH_CASE_FOLDER_NAME, foldername);
                intent.putExtra(HEALTH_CASE_FILE_PATH, filePath);
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
        if (id == R.id.user_statistics) {
             intent = new Intent(this, MyStatisticsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    //populate the spinner with externalSD cases
    public void populateCasesView(File[] fileNames) {
         //rand = new Random();
        int k;



        for (int i = 0 ; i < fileNames.length; i++) {


            cases.add(fileNames[i].getName());
            //images can be found + test name + scan the list
             casesPath.add(fileNames[i].getAbsolutePath());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cases);
        casesView.setAdapter(dataAdapter);
        casesView.setAdapter(dataAdapter);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    public void randomCase(View v){
        rand = new Random();
        int i = rand.nextInt(cases.size());
        intent = new Intent(this, HealthCaseTestActivity.class);
        String foldername = cases.get(i);
        String filePath = casesPath.get(i);
        intent.putExtra(HEALTH_CASE_FOLDER_NAME, foldername);
        intent.putExtra(HEALTH_CASE_FILE_PATH, filePath);
        startActivity(intent);
    }
}
