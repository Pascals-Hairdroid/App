package kundenprofil;

import java.io.File;

import utils.Utils;

import login_register.Login;

import com.example.pascalshairdroid.Friseurstudio;
import com.example.pascalshairdroid.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;

public class KundenProfil extends Activity {

	private int id;
	private EditText vor;
	private EditText nach;
	private EditText pw;
	private EditText mail;
	private EditText tele;
	private ImageView image;
	final Context context = this;
	private boolean imageChanged = false;
	private boolean dataChanged = false;
	private boolean interessenChanged = false;
	private Bitmap imageRaw;

	public static final int PICK_FROM_CAMERA = 1;
	public static final int PICK_FROM_FILE = 2;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kunden_profil);

		// Textfelder finden 
		vor = (EditText) findViewById(R.id.vorname);
		nach = (EditText) findViewById(R.id.nachname);
		pw = (EditText) findViewById(R.id.passwort);
		mail = (EditText) findViewById(R.id.email);
		tele = (EditText) findViewById(R.id.phoneNr);
		image = (ImageView) findViewById(R.id.imageView1);
		
		// SharedPreferences instanzieren und von Login die Prev Tags bekommen
		SharedPreferences sharedPreferences = getSharedPreferences(
				Login.PREF_TAG, Context.MODE_PRIVATE);

		// text von den Preferences in die Textfelder schreiben
		vor.setText(sharedPreferences.getString(Login.LOGIN_VORNAME, ""));
		nach.setText(sharedPreferences.getString(Login.LOGIN_NACHNAME, ""));
		mail.setText(sharedPreferences.getString(Login.LOGIN_USERNAME, ""));
		tele.setText(sharedPreferences.getString(Login.LOGIN_TELEFON, ""));

		vor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				LayoutInflater layoutInflater = LayoutInflater
						.from(KundenProfil.this);
				View promptView = layoutInflater.inflate(
						R.layout.fragment_kundendaten, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						KundenProfil.this);
				// set prompts.xml to be the layout file of the alertdialog
				// builder
				alertDialogBuilder.setView(promptView);
				final EditText input = (EditText) promptView
						.findViewById(R.id.userInput);
				// setup a dialog window
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// get user input and set it to result
										vor.setText(input.getText());
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create an alert dialog
				AlertDialog alertD = alertDialogBuilder.create();
				alertD.show();

			}
		});

	}

	// Dialog öffnen und Bild auswählen 
	public void showImage(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Kundenfoto ändern");
		builder.setItems(new String[] { "From Camera", "From SD-Card" },
				new DialogInterface.OnClickListener() {
					@Override
					// on click welches ausgewählt wurde
					public void onClick(DialogInterface dialog, int which) {
						Intent intent;
						switch (which) {
						// Foto von kamera holen
						case 0:
							intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							Intent cameraIntent = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
							startActivityForResult(cameraIntent,
									PICK_FROM_CAMERA);
							dialog.dismiss();
							break;
							// foto von SD Karte holen
						case 1:
							intent = new Intent();
							intent.setType("image/*");
							intent.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(Intent.createChooser(intent,
									"Complete action using"), PICK_FROM_FILE);
							dialog.dismiss();
							break;
						}
					}
				});

		Dialog dialog = builder.create();
		dialog.show();

	}

	public void showInteressen(View v) {
		FragmentManager manager = getFragmentManager();
		InteressenDialog id = new InteressenDialog();
		id.show(manager, "asdf");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.kunden_profil, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Intent intent;
		switch (id) {
		case R.id.cancel:
			intent = new Intent(KundenProfil.this, Friseurstudio.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
			break;
		case R.id.save:

			
			
		}

		return super.onOptionsItemSelected(item);
	}

	// Result von onClick speichern 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			System.out
					.println("not returning RESULT_OK");
		}
		switch (requestCode) {
		case PICK_FROM_CAMERA:
			imageRaw = (Bitmap) data.getExtras().get("data");
			image.setImageBitmap(imageRaw);
			imageChanged = true;
			break;
		case PICK_FROM_FILE:
			Uri mImageCaptureUri = data.getData();
			System.out.println(mImageCaptureUri.toString());
			String path = Utils.getRealPathFromURI(this, mImageCaptureUri); // from Gallery
			if (path == null) {
				path = mImageCaptureUri.getPath(); // from File Manager
			}
			System.out.println(path);
			if (path != null) {
				imageRaw = BitmapFactory.decodeFile(path);
				image.setImageBitmap(imageRaw);
				imageChanged = true;
			}
			break;
		}

	}

	
}
