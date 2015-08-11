package com.csc3003.healthcaser;

import java.util.ArrayList;

/**
 * Created by Alan Berman on 8/10/2015.
 */
public class Test {
    private ArrayList<XRay> xrays;
    private ArrayList<MicroscopicImage> microImages;
    private String[] results;
    private String name;
    public Test(String XMLFile)
    {
        loadTestInfo(XMLFile);
    }
    //Populates the ArrayLists of X-ray and
    //Microscopic images, as well as loads the results of the test
    //and the name thereof
    public void loadTestInfo(String file)
    {
        //do_something
    }
}
