package com.csc3003.healthcaser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public static final String PREFS_HC = "HC";
    public static final String PREFS_HC_CURRENTUSER = "currentUser";

    SharedPreferences settings ;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        settings = getSharedPreferences(PREFS_HC, 0);
        editor = settings.edit();


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
        emailStr = email.getText().toString().toLowerCase();
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
                //record current username of the person who has logged in
                editor.putString(PREFS_HC_CURRENTUSER, emailStr);



                editor.commit();
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
        HealthCase hc2 = new HealthCase();
        ArrayList<Test> testList1 = new ArrayList<Test>();
        ArrayList<String> pastHist1 = new ArrayList<String>();
        ArrayList<String> recentHist1 = new ArrayList<String>();
        ArrayList<String> pt1 = new ArrayList<String>();
        ArrayList<String> ptr1 = new ArrayList<String>();
        String d = "Tuberculosis";
        hc2.setDiagnosis(d);
        hc2.setPhysicalState("Alive");
        hc2.setStart("A 75-year-old white male was admitted to the medical center complaining of chest pain radiating to his left arm, and pain in the left wrist and elbow.");
        pastHist1.add("1. Hypothyroidism.");
        pastHist1.add("2. Chronic Obstructive Pulmonary Disease (COPD).");
        pastHist1.add("3. Coronary atherosclerosis treated with bypass surgery.");
        pastHist1.add("4. Multiple transient cerebral ischemic attacks treated with vertebrobasilar angioplasty.");
        pastHist1.add("5. Repairs of an abdominal aortic aneurysm.");
        pastHist1.add("6. Bilateral iliac artery aneurysm repairs.");
        recentHist1.add("1. Several stenoses in the coronary arteries");
        recentHist1.add("2. Masses present on his left wrist and right elbow for about six months prior to admission.");
        pt1.add("1. Left orchiectomy for epididymitis and orchitis.");
        pt1.add("2. Chest X-ray.");
        ptr1.add("1. Nitroglycerin for chest pain.");
        ptr1.add("2. Treatment for gouty arthritis.");
        ptr1.add("3. Both joints injected with corticosteroids without relief of symptoms.");
        recentHist1.add("3. Intermittent joint pain.");
        Test lwrist = new Test();
        lwrist.setName("Left Wrist Culture");
        lwrist.setResults("Sodium urate crystals were found in fluid aspirated from the left wrist.");
        Test urine = new Test();
        urine.setName("Urine culture");
        urine.setResults("Growth of  Mycobacterium Tuberculosis.");
        Test relbow = new Test();
        relbow.setName("Right Elbow X-ray");
        relbow.setResults("Loss of articular cartilage of the capitulum, the radial head, and moderately of the trochlea on the olecranon process of the ulna.");
        Image relb = new Image();
        relb.setName("gross1.jpg");
        relb.setDescription("Ill-defined intra-articular bodies anterior to the ulna consistent with TB arthritis.");
        relbow.addImage(relb);
        Test tiss = new Test();
        Image t1 = new Image();
        t1.setName("micro1.jpg");
        t1.setDescription("Chronic granulomatous epididymitis and granulomatous orchitis.");
        tiss.addImage(t1);
        Image t2 = new Image();
        t2.setName("micro2.jpg");
        t2.setDescription("Chronic granulomatous epididymitis and granulomatous orchitis.");
        tiss.addImage(t2);
        Image t3 = new Image();
        t3.setName("micro3.jpg");
        t3.setDescription("Chronic granulomatous epididymitis and granulomatous orchitis.");
        tiss.addImage(t3);
        tiss.setName("Tissue Exam");
        tiss.setResults("None");
        Test sus = new Test();
        sus.setName("Susceptibility Test");
        sus.setResults("Susceptibility to Streptomycin, Isoniazid, Rifampin, Ethambutol, and Pyrazinamide.");
        Test bio = new Test();
        bio.setName("Arthroscopic Joint Biopsy");
        bio.setResults("Growth of acid-fast bacilli.");
        Image bac = new Image();
        bac.setName("micro4.jpg");
        bac.setDescription("Acid-fast bacilli (Mycobacterium Tuberculosis).");
        bio.addImage(bac);
        bio.setResults("None");
        Test Cx = new Test();
        Cx.setName("Chest X-ray");
        Cx.setResults("Ill-defined area of parenchymal infiltration involving the lower lobe of the right lung, consistent with a pneumonia, and minimal amount of right-sided pleural effusion.");
        Image c = new Image();
        c.setName("gross2.jpg");
        c.setDescription("Diffuse interstitial markings, compatible with miliary TB.");
        Test vitalsTest1 = new Test();
        vitalsTest1.setName("Vital Signs");
        vitalsTest1.setResults("Blood pressure: Normal\nHeart Rate: Normal\nRespiration Rate: Normal\nTemperature: 38.5");
        testList1.add(vitalsTest1);
        testList1.add(Cx);
        testList1.add(sus);
        testList1.add(tiss);
        testList1.add(bio);
        testList1.add(relbow);
        testList1.add(lwrist);
        testList1.add(urine);
        hc2.setTests(testList1);
        hc2.getHistory().setPastHistory(pastHist1);
        hc2.getHistory().setRecentHistory(recentHist1);
        hc2.getHistory().setPastTests(pt1);
        hc2.getHistory().setPastTreatments(ptr1);
        //--------------------
        HealthCase hc1 =  new HealthCase();
        ArrayList<Test> testList = new ArrayList<Test>();
        ArrayList<String> pastHist = new ArrayList<String>();
        ArrayList<String> recentHist = new ArrayList<String>();
        ArrayList<String> pt = new ArrayList<String>();
        ArrayList<String> ptr = new ArrayList<String>();
        hc1.setStart("A twelve-year-old girl was referred to the pediatric pulmonary service for evaluation of recurrent lung nodules.");
        String diag = "Lymphocytic Interstitial Pneumonia";
        hc1.setDiagnosis(diag);
        hc1.setPhysicalState("Alive");
        pastHist.add("1.Multiple immunologic disorders, including idiopathic thrombocytopenic purpura (ITP).");
        pastHist.add("2. Common Variable Immune Deficiency (CVID).");
        pastHist.add("3. Autoimmune Hemolytic Anemia (AIHA).");
        pastHist.add("4. Some fatigue with exertion but was otherwise asymptomatic.");
        pastHist.add("5. Physical examinationfor bilateral crackles in the lung bases.");
        recentHist.add("No recent history.");
        pt.add("1. Chest X-ray, revealing lung nodules.");
        pt.add("2. Computerized Tomography (CT) scan, revealing lung nodules.");
        pt.add("3. Open lung biopsy. ");
        pt.add("4. Pulmonary function studies,revealing combined obstructive and restrictive patterns.");
        ptr.add("1. Intravenous gamma globulin every other week.");
        ptr.add("2. Anemia treated with steroids and the lung nodules resolved.");
        ptr.add("3. Hospitalization for an exacerbation of autoimmune hemolytic anemia.");
        Test ChestXRay = new Test();
        ChestXRay.setName("Chest X-ray");
        ChestXRay.setResults("None");
        Image chest1 = new Image();
        Image chest2 = new Image();
        chest1.setDescription("A reticulonodular pattern, most prominent in the lung bases. ");
        chest2.setDescription("A reticulonodular pattern, most prominent in the lung bases. ");
        chest1.setName("gross1.jpg");
        chest2.setName("gross2.jpg");
        ChestXRay.addImage(chest1);
        ChestXRay.addImage(chest2);
        Test RecentChest = new Test();
        RecentChest.setName("Recent Chest X-ray");
        RecentChest.setResults("None");
        Image r1 = new Image();
        r1.setName("gross3.jpg");
        r1.setDescription("A more pronounced reticulonodular pattern in the lower lobes.");
        Image r2 = new Image();
        r2.setName("gross4.jpg");
        r2.setDescription("A more pronounced reticulonodular pattern in the lower lobes.");
        RecentChest.addImage(r1);
        RecentChest.addImage(r2);
        Test vitalsTest = new Test();
        vitalsTest.setName("Vital Signs");
        vitalsTest.setResults("Blood pressure: Normal\nHeart Rate: Normal\nRespiration Rate: Normal\nTemperature: 38.5");
        testList.add(vitalsTest);
        Test chestCT = new Test();
        chestCT.setName("Chest CT Scan");
        chestCT.setResults("Recurrent nodular interstitial lung disease, with more extensive involvement than before.");
        Test biopsy = new Test();
        biopsy.setName("Lung Biopsy");
        biopsy.setResults("The biopsy of the lung parenchyma was firm, with focal yellow to white vaguely nodular areas.");
        Image pc1 = new Image();
        pc1.setName("micro1.jpg");
        pc1.setDescription("Mature lymphocytes and plasma cells.");
        Image pc2 = new Image();
        pc2.setName("micro2.jpg");
        pc2.setDescription("Mature lymphocytes and plasma cells.");
        Image pc3 = new Image();
        pc3.setName("micro3.jpg");
        pc3.setDescription("Mature lymphocytes and plasma cells.");
        Image pc4 = new Image();
        pc4.setName("micro4.jpg");
        pc4.setDescription("Mature lymphocytes and plasma cells.");
        biopsy.addImage(pc1);
        biopsy.addImage(pc2);
        biopsy.addImage(pc3);
        biopsy.addImage(pc4);
        Image agg1 = new Image();
        agg1.setName("micro5.jpg");
        agg1.setDescription("Multiple lymphoid aggregates.");
        Image agg2 = new Image();
        agg2.setName("micro6.jpg");
        agg2.setDescription("Active germinal centers.");
        Image agg3 = new Image();
        agg3.setName("micro7.jpg");
        agg3.setDescription("Active germinal centers.");
        biopsy.addImage(agg1);
        biopsy.addImage(agg2);
        biopsy.addImage(agg3);
        Image hist = new Image();
        hist.setName("micro8.jpg");
        hist.setDescription("Abundant collections of histiocytes.");
        Image hyp = new Image();
        hyp.setName("micro9.jpg");
        hyp.setDescription("Focal prominent type II pneumocyte hyperplasia.");
        biopsy.addImage(hist);
        biopsy.addImage(hyp);
        testList.add(ChestXRay);
        testList.add(RecentChest);
        testList.add(biopsy);
        testList.add(chestCT);
        hc1.setTests(testList);


//        hc.setStart("A 55 year-old man presents to the emergency room at Presbyterian University Hospital with a chief complaint of \"I have a severe headache and fever.\"");
//        String diagnosis = "Diagnosis: Disseminated Cryptococcosis.";
//        hc.setDiagnosis(diagnosis);
//        hc.setPhysicalState("Tired, middle aged, man in no apparent distress.");
//        pastHist.add("1. Bursitis of Left shoulder for one year treated by hydrocortisone injections, once every three months by an outside physician prior to presentation.\n");
//        pastHist.add("2. Orthotopic Liver Transplant in 1992 for cirrhosis secondary to alcohol use.\n");
//        pastHist.add("3. Cadaveric Renal Transplant in 1994 for chronic renal failure secondary to glomerulonephritis.\n");
//        pastHist.add("4. Hypertension.\n");
//        recentHist.add("The patient had been seen one week prior to presentation by his local physician with the complaints of headache and low grade fever.\n");
//        recentHist.add("Two days prior to presentation at Presbyterian University Hospital he was seen in the emergency room at an outside hospital complaining of worsening of his headache and fever.");
//        pt.add("Lumbar puncture was performed showing 169.7 mg/dl of protein, 38 mg/dl of glucose, and 1,464 white cells/ mm3.\n");
//        pt.add("The cerebrospinal spinal fluid differential was noted for 100 neutrophils but no bacteria.\n");
//        pt.add("Cryptococcal Antigen testing performed on cerebrospinal fluid resulted in a titer of 1:256.\n");
//        ptr.add("Started on multiple antibiotics and transferred to Presbyterian University Hospital for further medical management.");
//        Test vitalsTest = new Test();
//        vitalsTest.setName("Vital Signs");
//        vitalsTest.setResults("Blood pressure: Normal\nHeart Rate: Normal\nRespiration Rate: Normal\nTemperature: 38.5");
//        testList.add(vitalsTest);
//        Test ChestXRay = new Test();
//        ChestXRay.setResults("None");
//        Test ShoulderXRay = new Test();
//        ShoulderXRay.setResults("None");
//        Test ShoulderCTScan = new Test();
//        ShoulderCTScan.setResults("None");
//        ChestXRay.setName("Chest X-Ray");
//        ShoulderCTScan.setName("Shoulder CT Scan");
//        ShoulderXRay.setName("Shoulder X-Ray");
//        Image chest = new Image();
//        chest.setDescription("Chest x-ray showed cardiac enlargement, no acute pulmonary abnormality, and a lytic lesion of the distal left clavicle.");
//       // chest.setRelatedTest(ChestXRay);
//        chest.setName("img/gross1.jpg");
//        ChestXRay.addImage(chest);
//        Image shoulderX = new Image();
//        shoulderX.setDescription("X-ray of left shoulder showed a pathologic fracture through a lytic lesion of the distal left clavicle most likely related to osteomyelitis.");
//      //  shoulderX.setRelatedTest(ShoulderXRay);
//        shoulderX.setName("img/gross2.jpg");
//        ShoulderXRay.addImage(shoulderX);
//        Image shoulderCT1 = new Image();
//        shoulderCT1.setDescription("CT scan of the shoulder (bone window, soft tissue window) showed probable abscess anterior and subjacent to the distal left clavicle with lytic destruction of the adjacent clavicle.");
//       // shoulderCT1.setRelatedTest(ShoulderCTScan);
//        shoulderCT1.setName("img/gross3.jpg");
//        ShoulderCTScan.addImage(shoulderCT1);
//        Image shoulderCT2 = new Image();
//        shoulderCT2.setDescription("CT scan of the shoulder (bone window, soft tissue window) showed probable abscess anterior and subjacent to the distal left clavicle with lytic destruction of the adjacent clavicle.");
//        //shoulderCT2.setRelatedTest(ShoulderCTScan);
//        shoulderCT2.setName("img/gross4.jpg");
//        ShoulderCTScan.addImage(shoulderCT2);
//        Test headCTScan = new Test();
//        headCTScan.setName("Head CT Scan");
//        headCTScan.setResults("Chronic lacunar infarct to the right lentiform nucleus but no other abnormality.");
//        Test crypto = new Test();
//        crypto.setName("Cryptococcal Test");
//        crypto.setResults("Antibody titer 1:32.");
//        Test leftClavicleTissue = new Test();
//        leftClavicleTissue.setResults("None");
//        leftClavicleTissue.setName("Left Clavical Tissue Test");
//        Image leftClavicle1 = new Image();
//        leftClavicle1.setDescription("Culture at 35 degrees C on 5% sheep blood agar and Sabouraud's dextrose agar grew slightly convex mucoid colonies with smooth edges.");
//        leftClavicle1.setName("img/micro1.jpg");
//       // leftClavicle1.setRelatedTest(leftClavicleTissue);
//        Image leftClavicle2 = new Image();
//        leftClavicle2.setDescription("On cornmeal-Tween 80 agar round, dark walled, narrow neck budding yeast, without hyphae were identified.");
//       // leftClavicle2.setRelatedTest(leftClavicleTissue);
//        leftClavicle2.setName("img/micro2.jpg");
//        Image leftClavicle3 = new Image();
//        leftClavicle3.setDescription("On cornmeal-Tween 80 agar round, dark walled, narrow neck budding yeast, without hyphae were identified. ");
//       // leftClavicle3.setRelatedTest(leftClavicleTissue);
//        leftClavicle3.setName("img/micro3.jpg");
//        leftClavicleTissue.addImage(leftClavicle1);
//        leftClavicleTissue.addImage(leftClavicle2);
//        leftClavicleTissue.addImage(leftClavicle3);
//       Test histologyTest = new Test();
//        histologyTest.setResults("None");
//        histologyTest.setName("Histology Test"); // India ink stain of the colonies revealed encapsulated yeast with narrow based budding.
//        Image hist1 = new Image();
//        hist1.setDescription("The tissue submitted consisted of fibroadipose tissue, acute inflammatory exudate, necrotic bone, and granulomatous inflammation.");
//        hist1.setName("img/micro4.jpg"); // Numerous encapsulated yeast forms were found lying within pools of mucus. The organisms have the classical "poached egg" appearance described for this organism.
//      //  hist1.setRelatedTest(histologyTest);
//        Image hist2 = new Image();
//        hist2.setDescription("Several narrow based budding yeast were identified and are highlighted by the mucicarmine stain.");
//        hist2.setName("img/micro5.jpg");
//     //   hist2.setRelatedTest(histologyTest);
//        Image hist3 = new Image();
//        hist3.setDescription("Several narrow based budding yeast were identified and are highlighted by the mucicarmine stain.");
//        hist3.setName("img/micro5.jpg");
//     //   hist3.setRelatedTest(histologyTest);
//        histologyTest.addImage(hist1);
//        histologyTest.addImage(hist2);
//        histologyTest.addImage(hist3);
//        testList.add(ChestXRay);
//        testList.add(ShoulderXRay);
//        testList.add(ShoulderCTScan);
//        testList.add(headCTScan);
//        testList.add(crypto);
//        testList.add(leftClavicleTissue);
//        testList.add(histologyTest);
//        hc.setTests(testList);
//
        hc1.getHistory().setPastHistory(pastHist);
        hc1.getHistory().setRecentHistory(recentHist);
        hc1.getHistory().setPastTests(pt);
        hc1.getHistory().setPastTreatments(ptr);
//
////        // Populate the health case arrays
////        final ArrayList<Test> testList = new ArrayList<Test>();
////        final ArrayList<String> results = new ArrayList<String>();
////        final ArrayList<String> hist = new ArrayList<String>();
////        final ArrayList<String> pt = new ArrayList<String>();
////
////        results.add("Results: 120\\80");
////        Test bloodPressureTest = new Test();
////        bloodPressureTest.setName("Blood pressure");
////        bloodPressureTest.setResults(results);
////        testList.add(bloodPressureTest);
////
////        pt.add("None.");
////        hist.add("Orthotopic Liver Transplant in 1992 for cirrhosis secondary to alcohol use.\"");
////        hist.add("Hypertension.");
////        hist.add("Cadaveric Renal Transplant in 1994 for chronic renal failure secondary to glomerulonephritis.");
////        hc.setStart("A 55 year-old man presents to the emergency room at Presbyterian University Hospital with a chief complaint of \"I have a severe headache and fever.\"");
////
////        String diagnosis = "Diagnosis: Disseminated Cryptococcosis.";
////        hc.setDiagnosis(diagnosis);
////
////        hc.setPhysicalState("Alive");
////        hc.setTests(testList);
////        hc.setHistory(hist);
////        hc.setPastTests(pt);
////        hc.setPastTreatments(pt);
//        //----
//
 //      fileManager.writeHealthCaseToXMLFilePath(hc, "176.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc1, "HealthCase1.xml");
        fileManager.writeHealthCaseToXMLFilePath(hc2, "HealthCase2.xml");
//        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase3.xml");
//        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase4.xml");
//        fileManager.writeHealthCaseToXMLFilePath(hc, "HealthCase5.xml");

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

                //record current username of the person who has logged in
                editor.putString(PREFS_HC_CURRENTUSER, emailStr);



                editor.commit();

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