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
import java.util.ArrayList;

/**
 * Created by GavinW on 2015-09-06.
 */
public class AuditTrailDialog extends DialogFragment {

    public static AuditTrailDialog newInstance(ArrayList<String> input) {
        AuditTrailDialog frag = new AuditTrailDialog();
        Bundle args = new Bundle();
        for (String s: input){
            System.out.println(s);
        }
        args.putStringArrayList("trail", input);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList<String> auditTrail = getArguments().getStringArrayList("trail");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.dialog_audit_trail, null);
        TextView auditView = (TextView)content.findViewById(R.id.audit_view);
        //dummy text
        if (auditTrail!=null){
        auditView.setText("");
        for (String s: auditTrail){
            auditView.append(s);
        }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(content)
                .setTitle(R.string.audit_trail)
                .setNegativeButton(R.string.test_popup_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
