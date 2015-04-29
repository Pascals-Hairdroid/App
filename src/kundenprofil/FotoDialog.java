package kundenprofil;

import java.io.File;

import com.example.pascalshairdroid.R;
import com.example.pascalshairdroid.R.layout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FotoDialog extends DialogFragment {
	public static final int PICK_FROM_CAMERA = 1;
	public static final int PICK_FROM_FILE = 2;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Kundenfoto");

		builder.setItems(new String[] { "From Camera", "From SD-Card" },
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent;
						switch (which) {
						case 0:
							intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							File file = new File(Environment
									.getExternalStorageDirectory(),
									"tmp_avatar_"
											+ String.valueOf(System
													.currentTimeMillis())
											+ ".jpg");
							Uri mImageCaptureUri = Uri.fromFile(file);
							try {
								intent.putExtra(
										android.provider.MediaStore.EXTRA_OUTPUT,
										mImageCaptureUri);
								intent.putExtra("return-data", true);
								getActivity().startActivityForResult(intent, PICK_FROM_CAMERA);
							} catch (Exception e) {
								e.printStackTrace();
							}
							dialog.cancel();
							break;
						case 1:
							intent = new Intent();
							intent.setType("image/*");
							intent.setAction(Intent.ACTION_GET_CONTENT);
							getActivity().startActivityForResult(Intent.createChooser(intent,
									"Complete action using"), PICK_FROM_FILE);

							break;

						}

					}
				});

		/*
		 * builder.setPositiveButton(R.string.Ok, new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * TODO Auto-generated method stub
		 * 
		 * } });
		 * 
		 * builder.setNegativeButton(R.string.Abbrechen, new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * TODO Auto-generated method stub
		 * 
		 * } });
		 */

		Dialog dialog = builder.create();
		dialog.show();

		return dialog;
	}

	

}
