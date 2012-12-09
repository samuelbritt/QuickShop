package com.example.quickshop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class ChooseAnchorDialog extends DialogFragment {
	private static final String TAG = "QuickShop.ChooseAnchorDialog";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.keep_at_bottom)
		       // Set the check box
		       .setMultiChoiceItems(new String[] {"Keep at bottom"},
		                            null,
		                            new DialogInterface.OnMultiChoiceClickListener() {
			                            @Override
			                            public void onClick(DialogInterface dialog,
			                                                int which,
			                                                boolean isChecked) {
				                            if (isChecked) {
					                            // If the user checked the item,
												// add it to the selected items
					                            //mSelectedItems.add(which);
				                            	Log.d(TAG, "clicked the button");
				                            } else {
					                            // Else, if the item is already
												// in the array, remove it
					                            //mSelectedItems.remove(Integer.valueOf(which));
				                            	Log.d(TAG, "unclicked the button");
				                            }
			                            }
		                            })
		       // set the OK button
		       .setPositiveButton(android.R.string.ok,
		                          new DialogInterface.OnClickListener() {
			                          public void onClick(DialogInterface dialog,
			                                              int id) {
				                          Log.d(TAG, "user clicked ok");
			                          }
		                          })
		       // Set the cancel button
		       .setNegativeButton(android.R.string.cancel,
		                          new DialogInterface.OnClickListener() {
			                          public void onClick(DialogInterface dialog,
			                                              int id) {
				                          Log.d(TAG, "user canceled dialog");
			                          }
		                          });
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
