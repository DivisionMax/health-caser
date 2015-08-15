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
import android.widget.TextView;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;

public class HealthCaseTestActivity extends ActionBarActivity implements DiagnosisDialog.DiagnosisDialogListener{
    TextView title,information;
    PopupMenu askMenu,testMenu;
    DialogFragment newFragment;

    MenuInflater inflater;

    View content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_case_test);
        content = this.findViewById(android.R.id.content);

        //SETUP POPUP MENUS
        askMenu = new PopupMenu(this,content);
        inflater = askMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_ask, askMenu.getMenu());

        testMenu = new PopupMenu(this, content);
        inflater = testMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_test, testMenu.getMenu());

        //ALAN ACTIVITY
        final HealthCase hc = new HealthCase();

            //Populate the health case arrays
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
//        serializeXML(hc);

//        getSupportActionBar().hide();

        //Menu Item listeners
        testMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                     information.setText(hc.getTests().get(0).getName() + ":\n");
            for (Test t : hc.getTests()) {
                information.append(t.getResults().get(0) + "\n");
            }
                    return false;
            }
        });
        askMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
                public boolean onMenuItemClick(MenuItem item) {
                      information.setText("");
            for (String a : hc.getHistory()) {
                information.append(a + "\n");
            }
                return false;
            }
        });

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

    /*    diagText.setText(hc.getDiagnosis());
        startText.setVisibility(View.GONE);
        testText.setVisibility(View.GONE);*/

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
//        EditText diagnosis = (EditText)findViewById(R.id.diagnosis);
//        title.setText(newFragment.diagnosis.getText());
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        newFragment.dismiss();
    }

    public void showAskPopup(View v) {
        askMenu.show();
    }

    public void showTestPopup(View v) {
        testMenu.show();
    }

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
