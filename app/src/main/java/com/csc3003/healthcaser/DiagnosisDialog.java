package com.csc3003.healthcaser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

/**
 * Created by GavinW on 2015-08-12.
 */
public class DiagnosisDialog extends DialogFragment {

    private EditText diagnosis;

    public interface DiagnosisDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String diagnosis);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    DiagnosisDialogListener mListener;
    //Adapted: http://developer.android.com/guide/topics/ui/dialogs.html
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DiagnosisDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement DiagnosisDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        diagnosis = new EditText(getActivity());
        builder.setView(diagnosis)
                .setTitle("Enter Your Diagnosis")
                .setPositiveButton(R.string.diagnose, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(DiagnosisDialog.this, diagnosis.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(DiagnosisDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
