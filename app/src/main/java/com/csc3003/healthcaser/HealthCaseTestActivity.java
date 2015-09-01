package com.csc3003.healthcaser;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;

public class HealthCaseTestActivity extends ActionBarActivity implements DiagnosisDialog.DiagnosisDialogListener{
    TextView title,information;
    PopupMenu askMenu,testMenu;
    DialogFragment newFragment;
    PopupWindow auditTrail;

    MenuInflater inflater;


    View content;
    final HealthCase hc =  new HealthCase();

    //stats variables
    //totalDiagnose - the number of diagnose attemps
    //firstDiagnose - the number of moves made before their first diagnose
    int totalMoves, totalDiagnose, firstDiagnose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_case_test);
        content = this.findViewById(android.R.id.content);

        totalMoves = 0;
        totalDiagnose = 0;
        firstDiagnose = 0;
        auditTrail = new PopupWindow();

        //ALAN ACTIVITY
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

        title = (TextView)findViewById(R.id.case_title);
        information = (TextView)findViewById(R.id.case_information);
        information.setText(hc.getStart());

        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase.xml");
        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase1.xml");
        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase2.xml");
        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase3.xml");
        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase4.xml");
        testReturnFileList(getFilesDir().getPath());

        //Save data when a user rotates
        if (savedInstanceState != null) {
            totalMoves = savedInstanceState.getInt("TOTAL_MOVES");
            totalDiagnose = savedInstanceState.getInt("TOTAL_DIAGNOSE");
            information.setText(savedInstanceState.getString("DISPLAYED_INFO"));
            firstDiagnose = savedInstanceState.getInt("FIRST_DIAGNOSE");

        }

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
            firstDiagnose = totalMoves;
        }
        totalDiagnose+=1;
        if (diagnosis.equals("CORRECT")){
            Intent resultsIntent = new Intent(this, HealthCaseTestResultActivity.class);
            resultsIntent.putExtra("TOTAL_MOVES", totalMoves);
            resultsIntent.putExtra("TOTAL_DIAGNOSE", totalDiagnose);
            startActivity(resultsIntent);
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

    public boolean writeHealthCaseToXMLFilePath(Object hc, String path)
    {
        boolean status = false;
        File xmlFile = new File(path);
        Serializer serializer = new Persister();

        try {
            serializer.write( hc , xmlFile);
            Log.e("objToXML", "worked");
            status = true;

        }
        catch (Exception e)
        {
            Log.e("objToXMLProb",e.toString());
            status = false;
        }
        return status;

    }
    public  HealthCase readHealthCaseFromXMLFilePath(String path)
    {
        Serializer serializer = new Persister();

        HealthCase hc1;

        File xmlFile = new File(path);

            try
            {
                hc1 = serializer.read(HealthCase.class,xmlFile);
                Log.e("diagnosis",hc1.getDiagnosis());
                return hc1;
            }
            catch (Exception e)
            {
                Log.e("xmltoObjProb", e.toString());
                return null;

            }


    }
    public File[] returnFileList(String path)
    {
        return new File(path).listFiles();

    }

    public void testReturnFileList(String path)
    {
        File[] fileArr = returnFileList(getFilesDir().getPath());

        for (int i = 0; i < fileArr.length; i++)
        {
            Log.e("fileList",fileArr[i].getName());
        }

    }

    //OBJECT -> XML || XML -> OBJECT
    public boolean serializeXML(HealthCase hc){
        File xmlFile = new File(getFilesDir().getPath() + "/HealthCase.xml");
        Serializer serializer = new Persister();
        Boolean status;
        try {
            serializer.write(hc, xmlFile);
            Log.i("objToXML", "worked");
            status = true;
        }
        catch (Exception e)
        {
            Log.e("objToXMLProb",e.toString());
            status = false;
        }

        if (xmlFile.exists())
        {
            try
            {
                HealthCase hc1 = serializer.read(HealthCase.class,xmlFile);
                Log.e("diagnosis",hc1.getDiagnosis());
                status = true;
            }
            catch (Exception e)
            {
                Log.e("xmltoObjProb", e.toString());
                status = false;
            }
        }
        return status;
    }

}
