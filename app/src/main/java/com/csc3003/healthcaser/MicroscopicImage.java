package com.csc3003.healthcaser;

import org.simpleframework.xml.Element;

/**
 * Created by Alan Berman on 8/10/2015.
 */
public class MicroscopicImage {
    @Element
    private int width;
    @Element
    private int length;
    @Element
    private String description;
    @Element
    private Test relatedTest;
    public MicroscopicImage(){}

    public void setLength(int length) {
        this.length = length;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRelatedTest(Test relatedTest) {
        this.relatedTest = relatedTest;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public String getDescription() {
        return description;
    }

    public Test getRelatedTest() {
        return relatedTest;
    }
}
