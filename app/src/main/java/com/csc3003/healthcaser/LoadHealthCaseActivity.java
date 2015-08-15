package com.csc3003.healthcaser;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import java.io.File;
import java.util.ArrayList;


public class LoadHealthCaseActivity extends Activity {

    Button histButton;
    Button testButton;
    Button diagButton;
    private TextView startText;
    private TextView histText;
    private TextView diagText;
    private TextView testText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_health_case);
        histButton = (Button)findViewById(R.id.MoreHistory);
        testButton = (Button)findViewById(R.id.tests);
        diagButton = (Button)findViewById(R.id.diagnose);
        final HealthCase hc = new HealthCase();
        final ArrayList<Test> testList = new ArrayList<Test>();
        final ArrayList<String> results = new ArrayList<String>();
        results.add("Results: 120\\80");

        Test bloodPressureTest = new Test();
        bloodPressureTest.setName("Blood pressure");
        bloodPressureTest.setResults(results);
        testList.add(bloodPressureTest);
        final ArrayList<String> hist = new ArrayList<String>();
        final ArrayList<String> pt = new ArrayList<String>();
        pt.add("None.");
        hist.add("Orthotopic Liver Transplant in 1992 for cirrhosis secondary to alcohol use.\"");
        hist.add("Hypertension.");
        hist.add("Cadaveric Renal Transplant in 1994 for chronic renal failure secondary to glomerulonephritis.");
        hc.setStart("A 55 year-old man presents to the emergency room at Presbyterian University Hospital with a chief complaint of \"I have a severe headache and fever.\"");
        startText = (TextView)(findViewById(R.id.startInfo));
        diagText = (TextView)(findViewById(R.id.diagInfo));
        testText = (TextView) (findViewById(R.id.testInfo));
        histText = (TextView) (findViewById(R.id.MoreHist));
        String diagnosis = "Diagnosis: Disseminated Cryptococcosis.";
        hc.setDiagnosis(diagnosis);
        startText.setText(hc.getStart());
        hc.setPhysicalState("Alive");
        hc.setTests(testList);
        hc.setHistory(hist);
        hc.setPastTests(pt);
        hc.setPastTreatments(pt);
        histButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                histText.setText("");
                for (String a : hc.getHistory()) {
                    histText.append(a + "\n");
                }
                startText.setVisibility(View.GONE);

            }

        });
        diagButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                diagText.setText(hc.getDiagnosis());
                startText.setVisibility(View.GONE);
                testText.setVisibility(View.GONE);

            }

        });
        testButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                testText.setText(hc.getTests().get(0).getName()+":\n");
                for (Test t : hc.getTests()) {
                    testText.append(t.getResults().get(0) + "\n");
                }
                histText.setVisibility(View.GONE);
                startText.setVisibility(View.GONE);

            }

        });
        File xmlFile = new File(getFilesDir().getPath() + "/HealthCase.xml");
        Serializer serializer = new Persister();
        //serializer.write(hc,xmlFile);
        try {
            serializer.write(hc, xmlFile);

            Log.e("objToXML","worked");
        }
        catch (Exception e)
        {
            Log.e("objToXMLProb",e.toString());
        }

        if (xmlFile.exists())
        {
            try
            {
                Serializer s= new Persister();
                HealthCase hc1 = serializer.read(HealthCase.class,xmlFile);
                Log.e("diagnosis",hc1.getDiagnosis());
            }
            catch (Exception e)
            {
                Log.e("xmltoObjProb", e.toString());
            }


        }


}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_health_case, menu);
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
}
