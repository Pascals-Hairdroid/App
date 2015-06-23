package kundenprofil;

import java.util.HashSet;
import java.util.Set;

import utils.PrefUtils;

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
		String[] interessen = getActivity().getResources().getStringArray(R.array.interessen);
		// Die Interessen auswählen die der Kunde bereits hat aus Preferences
		Set<String> myInteressen = PrefUtils.getPreferences(getActivity(), Login.PREF_TAG).getStringSet(
				Login.LOGIN_INTERESSEN, new HashSet<String>());
		((KundenProfil) getActivity()).setTempInteressen(myInteressen);
		System.out.println(myInteressen);
		// boolean array das die liste der Interessen durchgeht und kontrolliert
		// ob das Feld schon angehackelt ist oder nicht wenn nicht dann macht
		// die Schleife das
		boolean[] checked = new boolean[interessen.length];
		for (int i = 0; i < interessen.length; i++) {
			 System.out.println(interessen[i]);
			checked[i] = myInteressen.contains(interessen[i]);
		}

		builder.setMultiChoiceItems(R.array.interessen, checked,
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						if (isChecked) {
							//wenn das element auf gecheckt gesetzt wird soll es in der getTempInteressen() ArrayList eintragen
							((KundenProfil) getActivity())
									.getTempInteressen()
									.add(getActivity().getResources()
											.getStringArray(R.array.interessen)[which]);

						} else {							
							//wenn das element auf nicht gecheckt gesetzt wird soll es aus der getTempInteressen() ArrayList gelöscht werden
							((KundenProfil) getActivity())
							.getTempInteressen()
							.remove(getActivity().getResources()
									.getStringArray(R.array.interessen)[which]);
						}
						//INTERESSEN_CHANGED setzen damit bei der save aktion die interessen mit geschickt werden
						((KundenProfil) getActivity()).setChanged(KundenProfil.INTERESSEN_CHANGED);
					}
				});
		
		// ok und Abbrechen Button mit ClickListener
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
		// Dialog erstellen
		Dialog dialog = builder.create();
		// return des Dialogs
		return dialog;
	}
}
