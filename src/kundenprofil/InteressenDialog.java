package kundenprofil;

import java.util.HashSet;
import java.util.Set;

import login_register.Login;

import com.example.pascalshairdroid.R;
import com.example.pascalshairdroid.R.layout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InteressenDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Interessen wählen");
		// String Array mit den Interessen befüllen die im String.xml stehen
		String[] interessen = getActivity().getResources().getStringArray(
				R.array.interessen);
		// Die Interessen auswählen die der Kunde bereits hat aus Preferences
		Set<String> myInteressen = getActivity().getSharedPreferences(
				Login.PREF_TAG, Context.MODE_PRIVATE).getStringSet(
				Login.LOGIN_INTERESSEN, new HashSet<String>());
		((KundenProfil) getActivity()).setTempInteressen(myInteressen);
		// boolean array das die liste der Interessen durchgeht und kontrolliert
		// ob das Feld schon angehackelt ist oder nicht wenn nicht dann macht
		// die Schleife das
		boolean[] checked = new boolean[interessen.length];
		for (int i = 0; i < interessen.length; i++) {
			// System.out.println(interessen[i]);
			// System.out.println(
			// Boolean.toString(myInteressen.contains(interessen[i])));
			checked[i] = myInteressen.contains(interessen[i]);
		}

		builder.setMultiChoiceItems(R.array.interessen, checked,
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						if (isChecked) {
							((KundenProfil) getActivity())
									.getTempInteressen()
									.add(getActivity().getResources()
											.getStringArray(R.array.interessen)[which]);

						} else {
							((KundenProfil) getActivity())
							.getTempInteressen()
							.remove(getActivity().getResources()
									.getStringArray(R.array.interessen)[which]);
						}
						((KundenProfil) getActivity()).setChanged(KundenProfil.INTERESSEN_CHANGED);
						
					}
				});

		builder.setPositiveButton(R.string.Ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

		builder.setNegativeButton(R.string.Abbrechen,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

		Dialog dialog = builder.create();
		return dialog;
	}
}
