package com.csc3003.healthcaser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

/**
 * Created by GavinW on 2015-09-06.
 */
public class TestImageDialog extends DialogFragment {
    ImageView testImage;
    File[] images;
    int count;


    public static TestImageDialog newInstance(String[] input) {
        TestImageDialog frag = new TestImageDialog();
        Bundle args = new Bundle();
        args.putStringArray("images", input);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
//        testImage = new ImageView(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.alert_test_results, null);
        String[] imagePaths = getArguments().getStringArray("images");
        images = new File[imagePaths.length];
        for (int i = 0; i < imagePaths.length; i ++){
            System.out.println(imagePaths[i]);
            images[i] = new File (imagePaths[i]);
        }
        count = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //set the first image
        Drawable d = Drawable.createFromPath(imagePaths[count]);
        count+=1;
        //object doesn't change. image drawables mutates
        final ImageView testResults = (ImageView)content.findViewById(R.id.test_image);
        testResults.setImageDrawable(d);
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
                System.out.println("Image counter: " + count);
//                ImageView iv=(ImageView)content.findViewById(R.id.test_image);
                Drawable d = Drawable.createFromPath(images[count].getPath());
                testResults.setImageDrawable(d);
//                testResults.setCompoundDrawables(null, d, null, null);

                if (count == images.length-1){
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
