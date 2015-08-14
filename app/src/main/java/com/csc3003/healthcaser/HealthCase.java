package com.csc3003.healthcaser;

import java.util.ArrayList;
import org.simpleframework.xml.*;

/**
 * Created by Alan Berman on 8/10/2015.
 */
@Root
public class HealthCase {
    //HealthCase.java
//Author: Alan Berman
//10/08/15
    @Element
    private String start; //age, chief complaint
    @ElementList
    private ArrayList<String> history;
    @ElementList
    private ArrayList<String> pastTests;
    @ElementList
    private ArrayList<String> pastTreatments;
    @Element
    private String physicalState;
    @Element
    private String diagnosis;
    @ElementList
    private ArrayList<Test> tests;

    //Constructor
    public HealthCase() {
        history = new ArrayList<String>();
        pastTests = new ArrayList<String>();
        pastTreatments = new ArrayList<String>();
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

    public void setHistory(ArrayList<String> history) {
        for (int i = 0; i < history.size(); i++) {
            this.history.add(history.get(i));
        }
    }

    public void setTests(ArrayList<Test> t) {
        for (int i = 0; i < t.size(); i++) {
            tests.add(t.get(i));
        }
    }

    public void setPastTests(ArrayList<String> past_tests) {
        for (int i = 0; i < past_tests.size(); i++) {
            this.pastTreatments.add(past_tests.get(i));
        }
    }

    public void setPastTreatments(ArrayList<String> past_treatments) {
        for (int i = 0; i < past_treatments.size(); i++) {
            this.pastTreatments.add(past_treatments.get(i));
        }
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public ArrayList<String> getPastTreatments() {
        return pastTreatments;
    }

    public ArrayList<String> getPastTests() {
        return pastTests;
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