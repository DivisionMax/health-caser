package com.csc3003.healthcaser;

/**
 * Created by Alan Berman on 8/10/2015.
 */
public class XRay {
    private int width;
    private int length;
    private String description;
    private Test relatedTest;

    public XRay(String XMLFile)
    {
        loadXRay(XMLFile);
    }
    //Sets the dimensions and description of the XRay
    //as well as its related Test
    public void loadXRay(String file)
    {
        //do_something
    }
}
