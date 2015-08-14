package com.csc3003.healthcaser;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChooseCaseActivity extends ActionBarActivity {

    private ListView casesView;
    private List<String> cases;
    private Random rand;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_case);

        casesView = (ListView) findViewById(R.id.casesView);
        populateCasesView();

        //Start the health case when it is clicked
        casesView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
//                                int num = Integer.parseInt(cases.get(position));

                intent = new Intent(view.getContext(),HealthCaseTestActivity.class);
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
    public void populateCasesView() {

        cases = new ArrayList<String>();
         rand = new Random();
        int k;
        for (int i = 0 ; i < 5; i ++ ){
             k = rand.nextInt(50) + 100;
            cases.add("Case #" + k);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cases);
        casesView.setAdapter(dataAdapter);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        casesView.setAdapter(dataAdapter);
    }

    public void specificCase(View v){

//        String.valueOf(spinner1.getSelectedItem()

    }

    public void randomCase(View v){
//        rand = new Random();
//        int i = rand.nextInt(cases.size());
        intent = new Intent (this, HealthCaseTestActivity.class);
        startActivity(intent);

    }
}
