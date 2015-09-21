package com.csc3003.healthcaser;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * Created by Alan Berman on 9/20/2015.
 */
public class TestClassTest extends AndroidTestCase {
    Test t;
    public TestClassTest()
    {
        super();
        t=new Test();
    }
    //Test the constructor initializes the Results and Images
    //lists
    @SmallTest
    public void testConstructor()
    {
        assertFalse(t.getImages() == null);
        assertFalse(t.getResults() == null);
        assertTrue(t.getResults().size() == 0);

    }
    //Test that the accessor and mutator methods work
    @SmallTest
    public void testGetAndSet() {
        Image i = new Image();
        i.setName("smile.jpg");
        t.addImage(i);
        assertEquals(t.getImages().get(0).getName(), "smile.jpg");
        assertTrue(t.getImages().get(0).getDescription()==null);
    }

}
