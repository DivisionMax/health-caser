package com.csc3003.healthcaser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by GavinW on 2015-09-06.
 */
public class TestImageDialog extends DialogFragment {
    ImageView testImage;
    File[] images;
    int count;
    AssetManager assetManager;
    /*public static TestImageDialog newInstance(String[] input) {
        TestImageDialog frag = new TestImageDialog();
        Bundle args = new Bundle();
        args.putStringArray("images", input);
        frag.setArguments(args);
        return frag;
    }*/
    public static TestImageDialog newInstance(String FOLDER_NAME,String[] files) {
        TestImageDialog frag = new TestImageDialog();
        Bundle args = new Bundle();
        args.putStringArray("images", files);
        args.putString("FOLDER_NAME", FOLDER_NAME);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
//        testImage = new ImageView(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.alert_test_results, null);

        AssetManager assetManager = getActivity().getApplicationContext().getAssets();
        String[] files = getArguments().getStringArray("images");
        String FOLDER_NAME = getArguments().getString("FOLDER_NAME");
        final Drawable[] d = new Drawable[files.length];

        InputStream ims;
        try{
            for (int i = 0; i < files.length; i++){
                ims = assetManager.open(FOLDER_NAME + File.separator + files[i]);
                d[i] = Drawable.createFromStream(ims, null);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
       /* images = new File[imagePaths.length];
        for (int i = 0; i < imagePaths.length; i ++){
            System.out.println(imagePaths[i]);
            images[i] = new File (imagePaths[i]);
        }*/
        count = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //set the first image
//        Drawable d = Drawable.createFromPath(imagePaths[count]);
        final ImageView testResults = (ImageView)content.findViewById(R.id.test_image);
        testResults.setImageDrawable(d[count]);
        count+=1;
        //object doesn't change. image drawables mutates

        builder.setView(content)
                .setTitle(R.string.test_popup_title)
                .setNegativeButton(R.string.test_popup_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });

        Button nextResult = (Button) content.findViewById(R.id.next_result);
        nextResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImageView iv=(ImageView)content.findViewById(R.id.test_image);
//                Drawable d = Drawable.createFromPath(images[count].getPath());
                testResults.setImageDrawable(d[count]);
//                testResults.setCompoundDrawables(null, d, null, null);
                if (count == d.length-1){
                    count = 0 ;
                }else{
                    count+=1;
                }
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
