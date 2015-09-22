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
    int count;
    public static TestImageDialog newInstance(String imagePath,String[] imageNames, String test) {
        TestImageDialog frag = new TestImageDialog();
        Bundle args = new Bundle();
        args.putString("TEST", test);
        args.putStringArray("imageNames", imageNames);
        args.putString("imagePath", imagePath);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.alert_test_results, null);
        String[] imageNames = getArguments().getStringArray("imageNames");
        String imagePath = getArguments().getString("imagePath");
        String test = getArguments().getString("TEST");
        final Drawable[] d = new Drawable[imageNames.length];
        for (int i = 0; i < imageNames.length; i++) {
            d[i] = Drawable.createFromPath(imagePath + "/" + imageNames[i]);
        }
        count = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //set the first image
        final ImageView testResults = (ImageView)content.findViewById(R.id.test_image);
        testResults.setImageDrawable(d[count]);
        //object doesn't change. image drawables mutates
        builder.setView(content)
                .setTitle(test + " Visuals")
                .setNegativeButton(R.string.test_popup_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });

        Button nextResult = (Button) content.findViewById(R.id.next_result);
        nextResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == d.length - 1) {
                    count = 0;
                } else {
                    count += 1;
                }
                testResults.setImageDrawable(d[count]);
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
