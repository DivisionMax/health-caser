package com.csc3003.healthcaser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

/**
 * Created by Alan Berman on 8/10/2015.
 */
public class Test {

        @ElementList
        private ArrayList<XRay> xrays;
        @ElementList
        private ArrayList<MicroscopicImage> microImages;
        @ElementList
        private ArrayList<String> results;
        @Element
        private String name;

        public Test()
        {
            xrays = new ArrayList<XRay>();
            microImages = new ArrayList<MicroscopicImage>();
            results = new ArrayList<String>();
        }
        public void setXrays(ArrayList<XRay> xrays) {
            for (int i=0;i<xrays.size();i++)
            {
                this.xrays.add(xrays.get(i));
            }
        }

        public void setMicroImages(ArrayList<MicroscopicImage> microImages) {
            for (int i=0;i<microImages.size();i++)
            {
                this.microImages.add(microImages.get(i));
            }
        }
        public void setResults(ArrayList<String> results) {
            for (int i=0;i<results.size();i++) {
                this.results.add(results.get(i));
            }
        }

        public void setName(String name) {
            this.name = name;
        }

         public String getName() {return name;}

    public ArrayList<MicroscopicImage> getMicroImages() {
            return microImages;
        }

        public ArrayList<String> getResults() {
            return results;
        }

        public ArrayList<XRay> getXrays() {
            return xrays;
        }
    }

