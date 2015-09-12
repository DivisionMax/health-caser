package com.csc3003.healthcaser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Alan Berman on 8/10/2015.
 */
@Element
public class Test {
    @ElementList(required = false,inline=true)
    private ArrayList<Image> images;
    @ElementList(required=false)
    private ArrayList<String> results;
    @Element
    private String name;

    public Test()
    {
        images = new ArrayList<Image>();
        results = new ArrayList<String>();
    }


    public void addImage(Image image) {

        images.add(image);

    }
    public void setResults(String result) {
        {
            results.add(result);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {return name;}

    public ArrayList<Image> getImages() {
        return images;
    }

    public ArrayList<String> getResults() {
        return results;
    }
}

