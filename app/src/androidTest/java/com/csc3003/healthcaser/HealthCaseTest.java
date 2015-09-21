package com.csc3003.healthcaser;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Alan Berman on 9/20/2015.
 */
public class HealthCaseTest extends AndroidTestCase {
    private HealthCase h;
    public HealthCaseTest()
    {
        super();
        h = new HealthCase();
    }
    //Test the checkDiagnosis method
    @SmallTest
    public void testDiagnosis()
    {
        String diagnosis = "AIDS";
        h.setDiagnosis(diagnosis);
        assertFalse(h.checkDiagnosis("Cancer"));
        assertTrue(h.checkDiagnosis("aids"));
    }
    //Test that the History constructor initializes the History object
    //and that its accessor and mutator methods work
    @SmallTest
    public void testHistory()
    {
        assertFalse(h.getHistory()==null);
        ArrayList<String> past = new ArrayList<String>();
        past.add("Diarrhoea");
        h.getHistory().setPastHistory(past);
        assertEquals(h.getHistory().getPastHistory().size(),1);
        String hist = "Diarrhoea";
        assertEquals(h.getHistory().getPastHistory().get(0),hist);
        assertFalse(h.getHistory().getPastTreatments()==null);
    }
}
