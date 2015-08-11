package com.csc3003.healthcaser;

import java.util.ArrayList;

/**
 * Created by Alan Berman on 8/10/2015.
 */
public class HealthCase {
    private String start; //age, chief complaint
    private ArrayList<String> history,pastTests,pastTreatments;
    private String physicalState,diagnosis;
    private ArrayList<Test> tests;
    //Constructor
    public HealthCase()
    {

    }
    //Loads the title, age, chief complaint, tests, diagnosis and
    //history of the patient from the assoc. XML file
    public void setStart(String start)
    {
        this.start=start;
    }
    public void setPhysicalState(String state)
    {
        physicalState=state;
    }
    public void setHistory(ArrayList<String> history)
    {
        for (int i=0;i<history.size();i++)
        {
            this.history.add(history.get(i));
        }
    }
    public void setPastTests(ArrayList<String> past_tests)
    {
        for (int i=0;i<past_tests.size();i++)
        {
            this.pastTreatments.add(past_tests.get(i));
        }
    }
    public void setPastTreatments(ArrayList<String> past_treatments)
    {
        for (int i=0;i<past_treatments.size();i++)
        {
            this.pastTreatments.add(past_treatments.get(i));
        }
    }
    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis=diagnosis;
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

    //Checks the user's diagnosis against the stored diagnosis
    public boolean checkDiagnosis(String userDiag)
    {
        return (userDiag.equals(diagnosis));
    }
    //Generate statistics describing a user's performance on the case
  /*  public Statistics generateUserStatistics(User aUser)
    {
        //do_something
        return null;
    } */
}
