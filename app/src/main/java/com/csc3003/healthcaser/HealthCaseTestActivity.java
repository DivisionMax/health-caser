package com.csc3003.healthcaser;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
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
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.csc3003.databaseTools.HCFileManager;

import java.io.IOException;
import java.io.InputStream;

public class HealthCaseTestActivity extends ActionBarActivity implements DiagnosisDialog.DiagnosisDialogListener{
    TextView title,information;
    PopupMenu askMenu,testMenu;
    DialogFragment newFragment;
    Boolean externalStorageReadable;
    //    final String EXTERNAL_HEALTH_CASES_FOLDER = getExternalFilesDir(null);
    MenuInflater inflater;
    View content;
    HealthCase hc ;
    //learning how to use the assetmanager for health case defaults
    Drawable[] d;
    AssetManager assetManager;
    String[] files;
        /*stats variables
        totalDiagnose - the number of diagnose attemps
        firstDiagnose - the number of moves made before their first diagnose*/
    int totalMoves, totalDiagnose, firstDiagnose;
    final String FOLDER_NAME = "default-health-cases";
    //image files path are saved and passed to test popup
    String[] images = null;

    //quit and return to health case menu
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
        //determine readable external storage
        Boolean createFolderCase;
        assetManager = getAssets();

//        System.out.println("getFilesDir:" + getFilesDir ());
//        String [] filelist = fileList();
//        System.out.println("Filelist:");
//        for (int i = 0 ; i  < filelist.length;i++){
//            System.out.println(filelist[i]);
//        }
        try{
            //pass the filenames and folder_name
            files = assetManager.list(FOLDER_NAME);
//             d = new Drawable[files.length];
//            InputStream ims;
//            int i = 0;
/*
            for(String s : files){
                System.out.println(s);
                ims = assetManager.open(s);
                 d[i] = Drawable.createFromStream(ims, null);
                i++;
            }
*/
//            ims = assetManager.open(FOLDER_NAME + File.separator + "gross1.jpg");
        }catch(IOException e){
            e.printStackTrace();
        }
        //if external storage is available, read the health case fodler
//        externalStorageReadable = isExternalStorageReadable();
/*
        if (externalStorageReadable){
            //~/health.caser
            //com.csc3003.healthcaser
            File externalStorage = Environment.getExternalStoragePublicDirectory("com.csc3003.healthcaser");
            if (!externalStorage.exists()){
                createFolderCase = externalStorage.mkdir();
            }
            System.out.println(externalStorage);
            File[] temp = externalStorage.listFiles();
            //should read from internal storage
            images = new String[temp.length];
            for (int i = 0 ; i < temp.length;i++){
                images[i] = temp[i].getPath();
            }
        }
*/
        HCFileManager hcFileManager = new HCFileManager(getFilesDir().getPath());
        Intent intent = getIntent();
        String filename = intent.getStringExtra(ChooseCaseActivity.DATA_KEY_HEALTH_CASE);
        hc = hcFileManager.readHealthCaseFromXMLFile(filename);
        title = (TextView)findViewById(R.id.case_title);
        information = (TextView)findViewById(R.id.case_information);

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
        }
        information.setText(hc.getStart());
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
        if (id == R.id.audit_trail) {
            //the audit trail must be passed
            DialogFragment newFragment = AuditTrailDialog.newInstance(new String[1]);
            newFragment.show(getFragmentManager(), "dialog");
        }

        return super.onOptionsItemSelected(item);
    }

    /*Adapted http://developer.android.com/training/basics/data-storage/files.html*/
    /* Checks if external storage is available for read and write */
    /*public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }*/
    /* Checks if external storage is available to at least read */
    /*public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }*/
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

//            information.setText("");
//            for (String a : hc.getHistory()) {
//                information.append(a + "\n");
//            }
            information.setText("");
            int id = item.getItemId();
            String hey = ""+id;
            Log.e("yo",hey);
            if (id==3) {
                information.append("Past Medical History:\n");
                for (int i = 0; i < hc.getHistory().getPastHistory().size(); i++) {
                    information.append(hc.getHistory().getPastHistory().get(i).toString() + "\n");
                }
            }
            else if (id==4) {

                information.append("Recent Medical History:\n");

                for (int j = 0; j < hc.getHistory().getRecentHistory().size(); j++) {
                    information.append(hc.getHistory().getRecentHistory().get(j).toString() + "\n");
                }
            }
            else if (id==5) {

                information.append("Past Medical Tests:\n");

                for (int k = 0; k < hc.getHistory().getPastTests().size(); k++) {
                    information.append(hc.getHistory().getPastTests().get(k).toString() + "\n");
                }
            }
            else if (id==6) {

                    information.append("Past Treatments:\n");

                    for (int l=0;l<hc.getHistory().getPastTreatments().size();l++)
                    {
                        information.append(hc.getHistory().getPastTreatments().get(l).toString()+"\n");
                    }
            }
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
//            information.setText(hc.getTests().get(0).getName() + ":\n");
//            for (Test t : hc.getTests()) {
//                information.append(t.getResults().get(0) + "\n");
//            }
//            totalMoves+=1;
            information.setText("");
            int id = item.getItemId()-100;
            if (!(hc.getTests().get(id).getResults()==null))
            {
                information.append(hc.getTests().get(id).getName().toString()+" results"+
                        ":\n");
                information.append(hc.getTests().get(id).getResults().get(0)+"\n");
            }
            else
            {
                information.setText("No textual results");
            }
            totalMoves+=1;
                //MUST CHANGE: every test will show the same images
                DialogFragment newFragment = TestImageDialog.newInstance(FOLDER_NAME, files);
                newFragment.show(getFragmentManager(), "dialog");
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
//        testMenu = new PopupMenu(this, view);
//        inflater = testMenu.getMenuInflater();
//        inflater.inflate(R.menu.menu_popup_test, testMenu.getMenu());
//        testMenu.setOnMenuItemClickListener(testMenuListener);
//        testMenu.show();
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
