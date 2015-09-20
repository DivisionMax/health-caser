package com.csc3003.databaseTools;

import android.content.Context;
import android.util.Log;

import com.csc3003.healthcaser.HealthCase;

//import org.simpleframework.xml.Serializer;
//import org.simpleframework.xml.core.Persister;

import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Adapted from Alans code.
 * Created by charles on 8/21/2015.
 *
 */
public class HCFileManager {
    String internalPath;

    public HCFileManager(String path)
    {
        internalPath = path;
        //make sure that the path ends with a / so that it point to a folder.
        if(internalPath.lastIndexOf("/")!=internalPath.length()-1)
        {
            internalPath = internalPath + "/";

        }

    }
    public File[] returnFileList()
    {
        return new File(internalPath).listFiles();

    }

//    public void testReturnFileList()
//    {
//        File[] fileArr = returnFileList();
//
//        for (int i = 0; i < fileArr.length; i++)
//        {
//            Log.e("fileList", fileArr[i].getName());
//        }
//
//    }
    public boolean writeHealthCaseToXMLFilePath(Object hc,String fileName)
    {
        boolean status = false;
        File xmlFile = new File(internalPath+fileName);
        Serializer serializer = new Persister();

        try {
            serializer.write( hc , xmlFile);

            status = true;

        }
        catch (Exception e)
        {

            status = false;
        }

        try
        {
            FileInputStream fis = new FileInputStream(xmlFile);
            int oneByte;
            String output = "";
            while((oneByte = fis.read()) != -1)
            {
                output = output + ""+(char)oneByte ;

            }
            Log.e("filecontents",output );
        }
        catch(IOException e)
        {
                Log.e("filecontents","noefile");
        }

        return status;

    }

    public HealthCase readHealthCaseFromXMLFile(String fileName)
    {
        Serializer serializer = new Persister();

        HealthCase hc1;

        File xmlFile = new File(internalPath + fileName);
        Log.e("xml file path", xmlFile.toString());
        try
        {
            hc1 = serializer.read(HealthCase.class,xmlFile);

            return hc1;
        }
        catch (Exception e)
        {
            Log.e ("HCFileManagerError",e.toString() );
            return null;

        }


    }
}
