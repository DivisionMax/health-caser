package com.csc3003.healthcaser;

import java.util.ArrayList;

/**
 * Created by Alan Berman on 8/10/2015.
 */
public class HealthCase {
    private String title;
    private String age;
    private ArrayList<String> history;
    private String chiefComplaint;
    private String diagnosis;
    private ArrayList<Test> tests;
    //Constructor
    public HealthCase(String XMLFile)
    {
        loadCase(XMLFile);
    }
    //Loads the title, age, chief complaint, tests, diagnosis and
    //history of the patient from the assoc. XML file
    public void loadCase(String file)
    {
        //do_something
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
