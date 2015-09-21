package com.csc3003.healthcaser;

import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alan Berman on 9/20/2015.
 */
public class SimplifiedHealthCase {
    public SimplifiedHealthCase(File f,String name)
    {
        simplify(f,name);
    }
    //Using a medical dictionary, make a simplified Health Case
    //by substituting jargon for more easily-understandable, more
    //common phrases
    public void simplify(File f, String name) {
        Serializer serializer = new Persister();

        HealthCase h;

        File xmlFile = new File(name);
        Log.e("xml file path", xmlFile.toString());
        try {
            h = serializer.read(HealthCase.class, xmlFile);

            Map<String, String> simpler = new HashMap<String, String>();
            try {
                FileReader reader = new FileReader(f);
                BufferedReader br = new BufferedReader(reader);
                String key,val;
                //Make a HashMap of the entries in the medical dictionary
                while ((key = br.readLine()) != null) {
                    //simpler.put(line.substring(0, line.indexOf(" ")), line.substring(line.indexOf(" ") + 1));
                    if ((val=br.readLine())!=null)
                        simpler.put(key,val);
                }
                //Change the jargon in the Health Case's history
                //(Past Medical History, Past Medical Tests, Recent Medical History and Past Treatments)
                for (int i = 0; i < h.getHistory().getPastHistory().size(); i++) {
                    for (int j = 0; j < h.getHistory().getPastHistory().get(i).length(); j++) {
                        String[] arr = h.getHistory().getPastHistory().get(i).split(" ");
                        for (int g = 0; g < arr.length; g++) {
                            //If we can find a simpler term, change it
                            if (simpler.containsKey(arr[g])) {
                                arr[g] = simpler.get(arr[g]);
                            }
                        }
                        String s = arr.toString();
                        //Replace the original string with the simpler string
                        h.getHistory().getPastHistory().set(i, s);
                    }
                }
                for (int i = 0; i < h.getHistory().getRecentHistory().size(); i++) {
                    for (int j = 0; j < h.getHistory().getRecentHistory().get(i).length(); j++) {
                        String[] arr = h.getHistory().getRecentHistory().get(i).split(" ");
                        for (int g = 0; g < arr.length; g++) {
                            if (simpler.containsKey(arr[g])) {
                                arr[g] = simpler.get(arr[g]);
                            }
                        }
                        String s = arr.toString();
                        h.getHistory().getRecentHistory().set(i, s);
                    }
                }
                for (int i = 0; i < h.getHistory().getPastTests().size(); i++) {
                    for (int j = 0; j < h.getHistory().getPastTests().get(i).length(); j++) {
                        String[] arr = h.getHistory().getPastTests().get(i).split(" ");
                        for (int g = 0; g < arr.length; g++) {
                            if (simpler.containsKey(arr[g])) {
                                arr[g] = simpler.get(arr[g]);
                            }
                        }
                        String s = arr.toString();
                        h.getHistory().getPastTests().set(i, s);
                    }
                }
                for (int i = 0; i < h.getHistory().getPastTreatments().size(); i++) {
                    for (int j = 0; j < h.getHistory().getPastTreatments().get(i).length(); j++) {
                        String[] arr = h.getHistory().getPastTreatments().get(i).split(" ");
                        for (int g = 0; g < arr.length; g++) {
                            if (simpler.containsKey(arr[g])) {
                                arr[g] = simpler.get(arr[g]);
                            }
                        }
                        String s = arr.toString();
                        h.getHistory().getPastTreatments().set(i, s);
                    }
                }
                //For the results of each test, replace all jargon words
                //with easier-to-understand words using the HashMap
                for (int q = 0; q < h.getTests().size(); q++) {
                    String[] result = h.getTests().get(q).getResults().get(0).split(" ");
                    for (int r = 0; r < result.length; r++) {
                        if (simpler.containsKey(result[r])) {
                            result[r] = simpler.get(result[r]);
                        }
                    }
                    String replaced = result.toString();
                    h.getTests().get(q).setResults(replaced);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            //Write the new health case to a separate file
            File xmlFileSimple = new File(name + "_simpler.xml");


            try {
                serializer.write(h, xmlFileSimple);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            Log.e ("HCFileManagerError",e.toString() );
        }
    }
}
