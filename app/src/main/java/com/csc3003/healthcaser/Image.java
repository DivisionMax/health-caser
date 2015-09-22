package com.csc3003.healthcaser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.*;
/**
 * Created by Alan Berman on 8/10/2015.
 */
//Image class used in the Test class
    //Each contains a description (caption) and a name
    //With accessor and mutator methods for both
@Element
public class Image{
   @Element(required=false)
    private String description;
    @Element(required = false)
    private String name;
    public Image(){}
    public void setDescription(String description) {
        this.description = description;
    }
    public void setName(String n)
    {
        name=n;
    }
    public String getName()
    {
        return name;
    }
    public String getDescription() {
        return description;
    }


}
