package com.csc3003.healthcaser;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.csc3003.databaseTools.HCFileManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HealthCaseTestActivity extends ActionBarActivity implements DiagnosisDialog.DiagnosisDialogListener{
    TextView title,information;
    PopupMenu askMenu,testMenu;
    //track the test you've run, viewing the results again won't affect your moves.
    ArrayList<Integer> runTests;
    DialogFragment newFragment;
    MenuInflater inflater;
    View content;
    HealthCase hc ;
    AssetManager assetManager;
    String[] files = null;
    int pastHistCount=0;
    int recentHistCount=0;
    int pastTestCount=0;
    int pastTreatmentCount=0;
        /*stats variables
        totalDiagnose - the number of diagnose attemps
        firstDiagnose - the number of moves made before their first diagnose*/
    int totalMoves, totalDiagnose, firstDiagnose;
    final String FOLDER_NAME = "default-health-cases";


    ArrayList<String> auditTrail = new ArrayList<String>();

    //quit and return to health case menu
    String imagePath ;

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
    //*********************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_case_test);
        content = this.findViewById(android.R.id.content);
        //this must be removed, just so running a test doesn't crash
        assetManager = this.getAssets();
        try {
            files = assetManager.list(FOLDER_NAME);
        }catch(IOException e){
            e.printStackTrace();
        }
        //***********************************************************
        //determine readable external storage
        Intent intent = getIntent();
        String foldername = intent.getStringExtra(ChooseCaseActivity.HEALTH_CASE_FOLDER_NAME);
        String filepath = intent.getStringExtra(ChooseCaseActivity.HEALTH_CASE_FILE_PATH);
        String XMLFileName = foldername + ".xml";
        imagePath = filepath+"/images";
        HCFileManager hcFileManager = new HCFileManager(filepath);

       Log.e("the path is ", filepath );
        hc = hcFileManager.readHealthCaseFromXMLFile(XMLFileName);

        title = (TextView)findViewById(R.id.case_title);
        information = (TextView)findViewById(R.id.case_information);
        information.setMovementMethod(new ScrollingMovementMethod());

        //Statistics initialize
        totalMoves = 0;
        totalDiagnose = 0;
        firstDiagnose = 0;
        //Save data when a user rotates
        if (savedInstanceState != null) {
            totalMoves = savedInstanceState.getInt("TOTAL_MOVES");
            totalDiagnose = savedInstanceState.getInt("TOTAL_DIAGNOSE");
            information.setText(savedInstanceState.getString("DISPLAYED_INFO"));
            firstDiagnose = savedInstanceState.getInt("FIRST_DIAGNOSE");
            auditTrail = savedInstanceState.getStringArrayList("AUDIT_TRAIL");
            runTests = savedInstanceState.getIntegerArrayList("RUN_TESTS");
        }
        //if there is no saved state
        else{
            title.setText("Patient Arrives");
            information.setText(hc.getStart());
            auditTrail.add("Patient Arrives: " + information.getText().toString() + "\n");
            runTests = new ArrayList<Integer>();
        }
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
        if (id == R.id.audit_trail) {
            //the audit trail must be passed
            DialogFragment newFragment = AuditTrailDialog.newInstance(auditTrail);
            newFragment.show(getFragmentManager(), "dialog");
        }
        return super.onOptionsItemSelected(item);
    }
    //save data if the activity is destroyed
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("TOTAL_MOVES", totalMoves);
        outState.putInt("TOTAL_DIAGNOSE", totalDiagnose);
        outState.putInt("FIRST_DIAGNOSE", firstDiagnose);
        outState.putString("DISPLAYED_INFO", information.getText().toString());
        outState.putStringArrayList("AUDIT_TRAIL", auditTrail);
        outState.putIntegerArrayList("RUN_TESTS", runTests);
        super.onSaveInstanceState(outState);
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
        //healthcase.correctDiagnosis
        if (diagnosis.toLowerCase().equals(hc.getDiagnosis().toLowerCase())){
            Intent resultsIntent = new Intent(this, HealthCaseTestResultActivity.class);
            resultsIntent.putExtra("TOTAL_MOVES", totalMoves);
            resultsIntent.putExtra("TOTAL_DIAGNOSE", totalDiagnose);
            resultsIntent.putExtra("FIRST_DIAGNOSE", firstDiagnose);
            startActivity(resultsIntent);
        }else{
            Toast.makeText(this, "Diagnosis incorrect.", Toast.LENGTH_SHORT).show();
            auditTrail.add("Incorrect Diagnosis: " + diagnosis.toLowerCase() +"\n");
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
            int id = item.getItemId();
            if (id==3) {
                title.setText("Past Medical History:");


                if (pastHistCount<=hc.getHistory().getPastHistory().size()-1) {

                    information.append(hc.getHistory().getPastHistory().get(pastHistCount).toString() + "\n");
                    pastHistCount++;

                }
                else
                {
                    information.append(hc.getHistory().getPastHistory().get(hc.getHistory().getPastHistory().size() - 1).toString() + "\n");
                }
                auditTrail.add("Past Medical History: " + information.getText().toString());



            }
            else if (id==4) {
                title.setText("Recent Medical History:");
                if (recentHistCount <= hc.getHistory().getRecentHistory().size() - 1) {
                    information.append(hc.getHistory().getRecentHistory().get(recentHistCount).toString() + "\n");
                    auditTrail.add(information.getText().toString());
                    recentHistCount++;

                } else {
                    information.append(hc.getHistory().getRecentHistory().get(hc.getHistory().getRecentHistory().size() - 1).toString() + "\n");

                }
                auditTrail.add("Recent Medical History: " + information.getText().toString());

            }
            else if (id==5) {
                title.setText("Past Medical Tests:");

                if (pastTestCount<=hc.getHistory().getPastTests().size()-1) {

                    information.append(hc.getHistory().getPastTests().get(pastTestCount).toString() + "\n");
                    auditTrail.add(information.getText().toString());
                    pastTestCount++;

                }
                else
                {
                    information.append(hc.getHistory().getPastTests().get(hc.getHistory().getPastTests().size() - 1).toString() + "\n");

                }
                auditTrail.add("Past Medical Tests: " +  information.getText().toString());

            }
             else if (id == 6) {
                title.setText("Past Treatments:");
                if (pastTreatmentCount<=hc.getHistory().getPastTreatments().size()-1) {

                    information.append(hc.getHistory().getPastTreatments().get(pastTreatmentCount).toString() + "\n");
                    auditTrail.add(information.getText().toString());
                    pastTreatmentCount++;

                }
                else
                {
                    information.append(hc.getHistory().getPastTreatments().get(hc.getHistory().getPastTreatments().size()-1).toString() + "\n");
                }
                auditTrail.add("Past Treatment History: " + information.getText().toString());
            }
            //asking a question again increases moves, they should check 'what we know'
            totalMoves+=1;
            return false;
    }
    };

    //test is chosen. if images, load images from storage into an array. pass the array to the popup.
    private PopupMenu.OnMenuItemClickListener testMenuListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            //item.getTitle() returns label
            information.setText(hc.getTests().get(0).getName() + ":\n");
            for (Test t : hc.getTests()) {
                information.append(t.getResults().get(0) + "\n");
            }
            information.setText("");
            int id = item.getItemId()-100;
            //track performed tests.
            //if there are results
            if (!(hc.getTests().get(id).getResults()==null))
            {
                information.append(hc.getTests().get(id).getName().toString()+" results"+
                        ":\n");
                information.append(hc.getTests().get(id).getResults().get(0) + "\n");

                if (runTests.contains(id)){
                    auditTrail.add("Viewed test results: " + item.getTitle() +"\n");
                }else{
                    auditTrail.add("Ran test: " + item.getTitle() +"\n");
                }
            }
            else
            {
                if (runTests.contains(id)){
                    auditTrail.add("Viewed test results: " + item.getTitle() +"\n");
                }else{
                    auditTrail.add("Ran test: " + item.getTitle() +". It was not a necessary test\n");
                }
                information.setText("No textual results");
            }
            //user can view text results again
            if (!runTests.contains(id)){
                runTests.add(id);
                totalMoves+=1;
            }
            if( hc.getTests().get(id).getImages().size()>0)
            {    //get images arraylist

                ArrayList<Image> tempImages;
                tempImages = hc.getTests().get(id).getImages() ;

                String[] imageNames = new String[tempImages.size()];

                for(int i = 0; i < imageNames.length; i++)
                {
                    imageNames[i] = tempImages.get(i).getName();
                }

                // files = imageNames;

                DialogFragment newFragment = TestImageDialog.newInstance(imagePath, imageNames);

                newFragment.show(getFragmentManager(), "dialog");
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
        String pastH="Past Medical History";
        String recentH="Recent Medical History";
        String pastTest="Past Medical Tests";
        String pastTreatments="Past Treatments";
        int groupId = Menu.NONE;
        int itemIdph = 3;
        int itemIdrh = 4;
        int itemIdpt = 5;
        int itemIdptr = 6;
        int order = Menu.NONE;
        askMenu.getMenu().add(groupId, itemIdph, order, pastH);
        askMenu.getMenu().add(groupId, itemIdrh, order, recentH);
        askMenu.getMenu().add(groupId, itemIdpt, order, pastTest);
        askMenu.getMenu().add(groupId, itemIdptr, order, pastTreatments);
        askMenu.setOnMenuItemClickListener(askMenuListener);
        askMenu.show();
    }

    public void showTestPopup(View view) {
        //SETUP
        testMenu = new PopupMenu(this, view);
        inflater = testMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_test, testMenu.getMenu());
        testMenu.setOnMenuItemClickListener(testMenuListener);
        for (int i=0;i<hc.getTests().size();i++)
        {
            String name = hc.getTests().get(i).getName();
            int groupId = Menu.NONE;
            int itemId = 100+i;
            int order = Menu.NONE;
            testMenu.getMenu().add(groupId,itemId,order,name);
        }
        testMenu.show();
    }

}
