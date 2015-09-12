package com.csc3003.healthcaser;

import java.util.ArrayList;
import org.simpleframework.xml.*;

/**
 * Created by Alan Berman on 8/10/2015.
 */
@Root
public class HealthCase {
    @Element
    private String start; //age, chief complaint
    @Element
    private History history;
    @Element
    private String physicalState;
    @Element
    private String diagnosis;
    @ElementList(inline=true)
    private ArrayList<Test> tests;

    //Constructor
    public HealthCase() {
        history = new History();
        tests = new ArrayList<Test>();
    }

    //Loads the title, age, chief complaint, tests, diagnosis and
    //history of the patient from the assoc. XML file
    public void setStart(String start) {
        this.start = start;
    }

    public void setPhysicalState(String state) {
        physicalState = state;
    }


    public void setTests(ArrayList<Test> t) {
        for (int i = 0; i < t.size(); i++) {
            tests.add(t.get(i));
        }
    }


    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public History getHistory() {
        return history;
    }


    public ArrayList<Test> getTests() {
        return tests;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPhysicalState() {
        return physicalState;
    }

    public String getStart() {
        return start;
    }

    //Checks the user's diagnosis against the stored diagnosis
    public boolean checkDiagnosis(String userDiag) {
        return (userDiag.equals(diagnosis));
    }
}