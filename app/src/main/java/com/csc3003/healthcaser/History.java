package com.csc3003.healthcaser;

import org.simpleframework.xml.*;

import java.util.ArrayList;

/**
 * Created by Alan Berman on 9/2/2015.
 */

public class History {
    @ElementList
    private ArrayList<String> pastHistory;
    @ElementList
    private ArrayList<String> recentHistory;
    @ElementList
    private ArrayList<String> pastTests;
    @ElementList
    private ArrayList<String> pastTreatments;

    public History(){
        pastHistory=new ArrayList<String>();
        recentHistory=new ArrayList<String>();
        pastTests=new ArrayList<String>();
        pastTreatments=new ArrayList<String>();
    }

    public void setPastHistory(ArrayList<String> pHistory) {
        for (int i=0;i<pHistory.size();i++)
        {
            pastHistory.add(pHistory.get(i));
        }
    }
    public void setRecentHistory(ArrayList<String> rHistory) {
        for (int i=0;i<rHistory.size();i++)
        {
            recentHistory.add(rHistory.get(i));
        }
    }

    public void setPastTests(ArrayList<String> pt) {
        for (int i=0;i<pt.size();i++)
        {
            pastTests.add(pt.get(i));
        }
    }

    public void setPastTreatments(ArrayList<String> ptr) {
        for (int i=0;i<ptr.size();i++)
        {
            pastTreatments.add(ptr.get(i));
        }
    }

    public ArrayList<String> getPastTreatments() {
        return pastTreatments;
    }

    public ArrayList<String> getPastTests() {
        return pastTests;
    }

    public ArrayList<String> getPastHistory() {
        return pastHistory;
    }

    public ArrayList<String> getRecentHistory() {
        return recentHistory;
    }
}
