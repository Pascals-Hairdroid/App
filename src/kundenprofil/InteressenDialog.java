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
		builder.setTitle("Interessen");
		String[] interessen = getActivity().getResources().getStringArray(R.array.interessen);
		Set myInteressen = getActivity().getSharedPreferences(Login.PREF_TAG,Context.MODE_PRIVATE).getStringSet(Login.LOGIN_INTERESSEN, new HashSet<String>());
		
		 boolean[] checked = new boolean[interessen.length];
		 for (int i =0; i < interessen.length; i ++) {
		   checked[i] = myInteressen.contains(interessen[i]);
		 } 
		
		builder.setMultiChoiceItems(R.array.interessen,checked, new DialogInterface.OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				// TODO Auto-generated method stub
				
			}
		}); {
			
			
		builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}); 
		 
		builder.setNegativeButton(R.string.Abbrechen, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}); 
					
	
		Dialog dialog = builder.create();
		
		return dialog;
	}
}
}
  