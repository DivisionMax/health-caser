package com.csc3003.healthcaser;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChooseCaseActivity extends ActionBarActivity {

    private ListView casesView;
    private List<String> cases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_case);

        casesView = (ListView) findViewById(R.id.casesView);
        populateCasesView();
//        start health case if it is clicked
        casesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                                String selected = cases.get(position);
                                System.out.println(selected);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    populate the spinner with externalSD cases
    public void populateCasesView() {

        cases = new ArrayList<String>();
        cases.add("list 1");
        cases.add("list 2");
        cases.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cases);
        casesView.setAdapter(dataAdapter);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        casesView.setAdapter(dataAdapter);
    }

    public boolean specificCase(){

//        String.valueOf(spinner1.getSelectedItem()

        return true;
    }

    public boolean randomCase(){
        Random rand = new Random();
        int i = rand.nextInt(cases.size());

//        cases.get(i);

        return true;
    }
}
