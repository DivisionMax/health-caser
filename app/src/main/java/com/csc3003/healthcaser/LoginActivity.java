package com.csc3003.healthcaser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.DatabaseTools.UserDBHandler;
import com.csc3003.databaseTools.HCFileManager;
import com.csc3003.databaseTools.UserDBHandler;

import java.util.ArrayList;

public class LoginActivity extends Activity {

    EditText email, password;
    String emailStr, passwordStr;
    UserDBHandler userDB;

    final int duration = Toast.LENGTH_SHORT;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDB = new UserDBHandler(this, null, null, 1);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password.setTypeface(email.getTypeface());

        //laod standard
        loadStandardXML();
    }

    private void redirect (){
        intent = new Intent(this,ChooseCaseActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //   getMenuInflater().inflate(R.menu.menu_l, menu);
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


    //    Login
    public void login(View view) {
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
//        redirect(); //TEMP. REMOVE THIS LINE AND UNCOMMENT THE CODE BELOW
        if(emailStr.equals("")||passwordStr.equals(""))
        {
            //Adapted from http://developer.android.com/guide/topics/ui/notifiers/toasts.html
            Context context = getApplicationContext();
            CharSequence msg = "Username and or Password are empty";
            Toast errReg = Toast.makeText(context,msg,duration);
            errReg.show();
        }
        else{
            if(userDB.isUserExist(emailStr)&& userDB.isCorrectPassword(emailStr,passwordStr)) {
                successfulLoginOrRegistration(emailStr, "You have logged in");
            }
            else
            {
                Context context = getApplicationContext();
                CharSequence msg = "Username or Password is incorrect";
                Toast errReg = Toast.makeText(context,msg,duration);
                errReg.show();
            }
        }

    }
    //load the standard health case xml files
    public void loadStandardXML()
    {
        HCFileManager fileManager = new HCFileManager(getFilesDir().getPath());
        HealthCase hc =  new HealthCase();

        ArrayList<Test> testList = new ArrayList<Test>();
        ArrayList<String> pastHist = new ArrayList<String>();
        ArrayList<String> recentHist = new ArrayList<String>();
        ArrayList<String> pt = new ArrayList<String>();
        ArrayList<String> ptr = new ArrayList<String>();
        hc.setStart("A 55 year-old man presents to the emergency room at Presbyterian University Hospital with a chief complaint of \"I have a severe headache and fever.\"");
        String diagnosis = "Diagnosis: Disseminated Cryptococcosis.";
        hc.setDiagnosis(diagnosis);
        hc.setPhysicalState("Tired, middle aged, man in no apparent distress.");
        pastHist.add("1. Bursitis of Left shoulder for one year treated by hydrocortisone injections, once every three months by an outside physician prior to presentation.\n");
        pastHist.add("2. Orthotopic Liver Transplant in 1992 for cirrhosis secondary to alcohol use.\n");
        pastHist.add("3. Cadaveric Renal Transplant in 1994 for chronic renal failure secondary to glomerulonephritis.\n");
        pastHist.add("4. Hypertension.\n");
        recentHist.add("The patient had been seen one week prior to presentation by his local physician with the complaints of headache and low grade fever.\n");
        recentHist.add("Two days prior to presentation at Presbyterian University Hospital he was seen in the emergency room at an outside hospital complaining of worsening of his headache and fever.");
        pt.add("Lumbar puncture was performed showing 169.7 mg/dl of protein, 38 mg/dl of glucose, and 1,464 white cells/ mm3.\n");
        pt.add("The cerebrospinal spinal fluid differential was noted for 100 neutrophils but no bacteria.\n");
        pt.add("Cryptococcal Antigen testing performed on cerebrospinal fluid resulted in a titer of 1:256.\n");
        ptr.add("Started on multiple antibiotics and transferred to Presbyterian University Hospital for further medical management.");
        Test vitalsTest = new Test();
        vitalsTest.setName("Vital Signs");
        vitalsTest.setResults("Blood pressure: Normal\nHeart Rate: Normal\nRespiration Rate: Normal\nTemperature: 38.5");
        testList.add(vitalsTest);
        Test ChestXRay = new Test();
        ChestXRay.setResults("None");
        Test ShoulderXRay = new Test();
        ShoulderXRay.setResults("None");
        Test ShoulderCTScan = new Test();
        ShoulderCTScan.setResults("None");
        ChestXRay.setName("Chest X-Ray");
        ShoulderCTScan.setName("Shoulder CT Scan");
        ShoulderXRay.setName("Shoulder X-Ray");
        Image chest = new Image();
        chest.setDescription("Chest x-ray showed cardiac enlargement, no acute pulmonary abnormality, and a lytic lesion of the distal left clavicle.");
       // chest.setRelatedTest(ChestXRay);
        chest.setName("img/gross1.jpg");
        ChestXRay.addImage(chest);
        Image shoulderX = new Image();
        shoulderX.setDescription("X-ray of left shoulder showed a pathologic fracture through a lytic lesion of the distal left clavicle most likely related to osteomyelitis.");
      //  shoulderX.setRelatedTest(ShoulderXRay);
        shoulderX.setName("img/gross2.jpg");
        ShoulderXRay.addImage(shoulderX);
        Image shoulderCT1 = new Image();
        shoulderCT1.setDescription("CT scan of the shoulder (bone window, soft tissue window) showed probable abscess anterior and subjacent to the distal left clavicle with lytic destruction of the adjacent clavicle.");
       // shoulderCT1.setRelatedTest(ShoulderCTScan);
        shoulderCT1.setName("img/gross3.jpg");
        ShoulderCTScan.addImage(shoulderCT1);
        Image shoulderCT2 = new Image();
        shoulderCT2.setDescription("CT scan of the shoulder (bone window, soft tissue window) showed probable abscess anterior and subjacent to the distal left clavicle with lytic destruction of the adjacent clavicle.");
        //shoulderCT2.setRelatedTest(ShoulderCTScan);
        shoulderCT2.setName("img/gross4.jpg");
        ShoulderCTScan.addImage(shoulderCT2);
        Test headCTScan = new Test();
        headCTScan.setName("Head CT Scan");
        headCTScan.setResults("Chronic lacunar infarct to the right lentiform nucleus but no other abnormality.");
        Test crypto = new Test();
        crypto.setName("Cryptococcal Test");
        crypto.setResults("Antibody titer 1:32.");
        Test leftClavicleTissue = new Test();
        leftClavicleTissue.setResults("None");
        leftClavicleTissue.setName("Left Clavical Tissue Test");
        Image leftClavicle1 = new Image();
        leftClavicle1.setDescription("Culture at 35 degrees C on 5% sheep blood agar and Sabouraud's dextrose agar grew slightly convex mucoid colonies with smooth edges.");
        leftClavicle1.setName("img/micro1.jpg");
       // leftClavicle1.setRelatedTest(leftClavicleTissue);
        Image leftClavicle2 = new Image();
        leftClavicle2.setDescription("On cornmeal-Tween 80 agar round, dark walled, narrow neck budding yeast, without hyphae were identified.");
       // leftClavicle2.setRelatedTest(leftClavicleTissue);
        leftClavicle2.setName("img/micro2.jpg");
        Image leftClavicle3 = new Image();
        leftClavicle3.setDescription("On cornmeal-Tween 80 agar round, dark walled, narrow neck budding yeast, without hyphae were identified. ");
       // leftClavicle3.setRelatedTest(leftClavicleTissue);
        leftClavicle3.setName("img/micro3.jpg");
        leftClavicleTissue.addImage(leftClavicle1);
        leftClavicleTissue.addImage(leftClavicle2);
        leftClavicleTissue.addImage(leftClavicle3);
       Test histologyTest = new Test();
        histologyTest.setResults("None");
        histologyTest.setName("Histology Test"); // India ink stain of the colonies revealed encapsulated yeast with narrow based budding.
        Image hist1 = new Image();
        hist1.setDescription("The tissue submitted consisted of fibroadipose tissue, acute inflammatory exudate, necrotic bone, and granulomatous inflammation.");
        hist1.setName("img/micro4.jpg"); // Numerous encapsulated yeast forms were found lying within pools of mucus. The organisms have the classical "poached egg" appearance described for this organism.
      //  hist1.setRelatedTest(histologyTest);
        Image hist2 = new Image();
        hist2.setDescription("Several narrow based budding yeast were identified and are highlighted by the mucicarmine stain.");
        hist2.setName("img/micro5.jpg");
     //   hist2.setRelatedTest(histologyTest);
        Image hist3 = new Image();
        hist3.setDescription("Several narrow based budding yeast were identified and are highlighted by the mucicarmine stain.");
        hist3.setName("img/micro5.jpg");
     //   hist3.setRelatedTest(histologyTest);
        histologyTest.addImage(hist1);
        histologyTest.addImage(hist2);
        histologyTest.addImage(hist3);
        testList.add(ChestXRay);
        testList.add(ShoulderXRay);
        testList.add(ShoulderCTScan);
        testList.add(headCTScan);
        testList.add(crypto);
        testList.add(leftClavicleTissue);
        testList.add(histologyTest);
        hc.setTests(testList);

        hc.getHistory().setPastHistory(pastHist);
        hc.getHistory().setRecentHistory(recentHist);
        hc.getHistory().setPastTests(pt);
        hc.getHistory().setPastTreatments(ptr);

//        // Populate the health case arrays
//        final ArrayList<Test> testList = new ArrayList<Test>();
//        final ArrayList<String> results = new ArrayList<String>();
//        final ArrayList<String> hist = new ArrayList<String>();
//        final ArrayList<String> pt = new ArrayList<String>();
//
//        results.add("Results: 120\\80");
//        Test bloodPressureTest = new Test();
//        bloodPressureTest.setName("Blood pressure");
//        bloodPressureTest.setResults(results);
//        testList.add(bloodPressureTest);
//
//        pt.add("None.");
//        hist.add("Orthotopic Liver Transplant in 1992 for cirrhosis secondary to alcohol use.\"");
//        hist.add("Hypertension.");
//        hist.add("Cadaveric Renal Transplant in 1994 for chronic renal failure secondary to glomerulonephritis.");
//        hc.setStart("A 55 year-old man presents to the emergency room at Presbyterian University Hospital with a chief complaint of \"I have a severe headache and fever.\"");
//
//        String diagnosis = "Diagnosis: Disseminated Cryptococcosis.";
//        hc.setDiagnosis(diagnosis);
//
//        hc.setPhysicalState("Alive");
//        hc.setTests(testList);
//        hc.setHistory(hist);
//        hc.setPastTests(pt);
//        hc.setPastTreatments(pt);
        //----

        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCaseImproved.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase1.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase2.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase3.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase4.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase5.xml");

//        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase.xml");
//        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase1.xml");
//        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase2.xml");
//        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase3.xml");
//        writeHealthCaseToXMLFilePath(hc, getFilesDir().getPath() + "/HealthCase4.xml");
    }
    //    Register a User
    public void register(View view) {
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
        if(emailStr.equals("")||passwordStr.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence msg = "Registration failed. Username and Password cannot be empty";
            Toast errReg = Toast.makeText(context,msg,duration);
            errReg.show();
        }
        else{
            if(userDB.isUserExist(emailStr)){
                Context context = getApplicationContext();
                CharSequence msg = "Cannot register " + emailStr +", username is already registered";
                Toast errReg = Toast.makeText(context,msg,duration);
                errReg.show();
            }
            else
            {
                User aUser = new User();
                aUser.setUsername(emailStr);
                aUser.setPassword(passwordStr);
                userDB.addUser( emailStr , passwordStr);
                successfulLoginOrRegistration(emailStr, "Welcome. You have registered");
            }
        }
    }
    //Remove code repetition
    AlertDialog alertDialog;
    private void successfulLoginOrRegistration(String username, String message){
        alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle("Welcome, " + username);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        redirect();
                    }
                });
        alertDialog.show();
//        redirect();
    }

}