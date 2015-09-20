package com.csc3003.healthcaser;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.ArrayList;

/**
 * Created by Alan Berman on 9/20/2015.
 */
public class HistoryTest extends AndroidTestCase {
    History hist;
    public HistoryTest()
    {
        super();
        hist = new History();
    }
    //Test the constructor initializes the four
    //lists
    @SmallTest
    public void testConstructor()
    {
        assertFalse(hist.getPastHistory() == null);
        assertFalse(hist.getRecentHistory() == null);
        assertFalse(hist.getPastTreatments() == null);
        assertFalse(hist.getPastTests() == null);
        assertTrue(hist.getPastHistory().size() == 0);
    }
    //Test that the accessor and mutator methods work
    @SmallTest
    public void testGetAndSet() {
        ArrayList<String> pastHist = new ArrayList<String>();
        pastHist.add("Diptheria");
        hist.setPastHistory(pastHist);
        assertTrue(hist.getPastHistory().size()==1);
        assertEquals(hist.getPastHistory().get(0),"Diptheria");
    }
}
