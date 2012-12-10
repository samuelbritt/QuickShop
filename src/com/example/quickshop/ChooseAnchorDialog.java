package com.example.quickshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class ChooseAnchorDialog extends DialogFragment {
	private static final String TAG = "QuickShop.ChooseAnchorDialog";
	private boolean isChecked = false;
	
	public void setChecked(boolean checked) {
		isChecked = checked;
	}
	public boolean isChecked() {
		return this.isChecked;
	}

	/*
	 * The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks. Each method
	 * passes the DialogFragment in case the host needs to query it.
	 */
	public interface Listener {
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}

	// Use this instance of the interface to deliver action events
	Listener listener;

	// Override the Fragment.onAttach() method to instantiate the
	// NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			listener = (Listener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.keep_at_bottom_title)
				// Set the check box
				.setMultiChoiceItems(
						new String[] { getString(R.string.keep_at_bottom_option) },
						new boolean[] { isChecked },
						new DialogInterface.OnMultiChoiceClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked_) {
								isChecked = isChecked_;
							}
						})
				// set the OK button
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Log.d(TAG, "user clicked ok");
								listener.onDialogPositiveClick(ChooseAnchorDialog.this);
							}
						})
				// Set the cancel button
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Log.d(TAG, "user canceled dialog");
								listener.onDialogNegativeClick(ChooseAnchorDialog.this);
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
