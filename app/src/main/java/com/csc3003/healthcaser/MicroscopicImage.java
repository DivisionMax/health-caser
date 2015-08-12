package com.csc3003.healthcaser;

/**
 * Created by Alan Berman on 8/10/2015.
 */
public class MicroscopicImage {
    private int width;
    private int length;
    private String description;
    private Test relatedTest;

    public MicroscopicImage(String XMLFile)
    {
        loadMicroscopicImage(XMLFile);
    }
    //Sets the dimensions and description of the MicroscopicImage
    //as well as its related Test
    public void loadMicroscopicImage(String file)
    {
        //do_something
    }
}
