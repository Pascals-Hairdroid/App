package kundenprofil;

import com.example.pascalshairdroid.R;
import com.example.pascalshairdroid.R.layout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FotoDialog extends DialogFragment {
	

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Kundenfoto");
		
		
		
		
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
  