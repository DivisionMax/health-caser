package com.csc3003.healthcaser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.*;
/**
 * Created by Alan Berman on 8/10/2015.
 */
@Element
public class Image{
   @Element(required=false)
    private String description;
    @Element(required = false)
    private String name;
    //@Element(required = false)
   // private Test relatedTest;
    public Image(){}



    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String n)
    {
        name=n;
    }
   // public void setRelatedTest(Test relatedTest) {
   ///     this.relatedTest = relatedTest;
   // }
    public String getName()
    {
        return name;
    }
    public String getDescription() {
        return description;
    }

   // public Test getRelatedTest() {
   //     return relatedTest;
   // }
}
