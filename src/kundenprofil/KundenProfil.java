package kundenprofil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import kundenprofil.async.DataSaver;
import kundenprofil.async.ImageSaver;

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
import android.provider.MediaStore.Images;
import android.text.Editable;
import android.text.TextWatcher;
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
	private Bitmap imageRaw;
	private Set<String> tempInteressen = new HashSet<String>();
	private String sessionId = "";
	public static final int DATA_CHANGED = 0;
	public static final int INTERESSEN_CHANGED = 1;
	public static final int IMAGE_CHANGED = 2;
	private boolean[] changed = new boolean[] { false, false, false };
	public static final int PICK_FROM_CAMERA = 1;
	public static final int PICK_FROM_FILE = 2;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kunden_profil);

		sessionId = getSharedPreferences(Login.PREF_TAG, MODE_PRIVATE)
				.getString(Login.LOGIN_SESSION_ID, "");
		// Textfelder finden
		vor = (EditText) findViewById(R.id.vorname);
		nach = (EditText) findViewById(R.id.nachname);
		pw = (EditText) findViewById(R.id.passwort);
		tele = (EditText) findViewById(R.id.phoneNr);
		image = (ImageView) findViewById(R.id.imageView1);
		File myImage = new File(getFilesDir(), "myImage.jpg");
		if (myImage.exists()) {
			try {
				image.setImageBitmap(BitmapFactory
						.decodeStream(new FileInputStream(myImage)));
			} catch (FileNotFoundException e) {
				image.setImageResource(R.drawable.nobody_no);
				e.printStackTrace();
			}
		} else {
			image.setImageResource(R.drawable.nobody_no);
		}

		// SharedPreferences instanzieren und von Login die Prev Tags bekommen
		SharedPreferences sharedPreferences = getSharedPreferences(
				Login.PREF_TAG, Context.MODE_PRIVATE);

		// text von den Preferences in die Textfelder schreiben
		vor.setText(sharedPreferences.getString(Login.LOGIN_VORNAME, ""));
		nach.setText(sharedPreferences.getString(Login.LOGIN_NACHNAME, ""));
		tele.setText(sharedPreferences.getString(Login.LOGIN_TELEFON, ""));
		TextWatcher textWatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				changed[DATA_CHANGED] = true;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		};
		vor.addTextChangedListener(textWatcher);
		nach.addTextChangedListener(textWatcher);
		pw.addTextChangedListener(textWatcher);
		tele.addTextChangedListener(textWatcher);

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
		switch (id) {
		case R.id.cancel:
			finish();
			break;
		case R.id.save:
			if (changed[IMAGE_CHANGED]) {
				try {
					FileOutputStream fos = openFileOutput("myImage.jpg",
							MODE_PRIVATE);
					imageRaw.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					ImageSaver imageSaver = new ImageSaver(this, sessionId);
					imageSaver.execute();

					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			if (changed[DATA_CHANGED] || changed[INTERESSEN_CHANGED]) {
				DataSaver dataSaver = new DataSaver(this);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("sessionId",
						sessionId));
				nameValuePairs.add(new BasicNameValuePair("vorname", vor
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("nachname", nach
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("telnr", tele
						.getText().toString()));

				if (!pw.getText().toString().equals("")) {
					nameValuePairs.add(new BasicNameValuePair("passwort", Utils
							.MD5(pw.getText().toString())));
				}

				SharedPreferences preferences = this.getSharedPreferences(
						Login.PREF_TAG, MODE_PRIVATE);
				preferences
						.edit()
						.putString(Login.LOGIN_VORNAME,
								vor.getText().toString())
						.putString(Login.LOGIN_NACHNAME,
								nach.getText().toString())
						.putString(Login.LOGIN_TELEFON,
								tele.getText().toString()).commit();
				if (changed[INTERESSEN_CHANGED]) {
					preferences
							.edit()
							.putStringSet(Login.LOGIN_INTERESSEN,
									tempInteressen).commit();

					nameValuePairs.addAll(arrayAsNameValuePairs("interessen",
							interessenToIds(tempInteressen)));
				}
				dataSaver.execute(nameValuePairs);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	private List<BasicNameValuePair> arrayAsNameValuePairs(String name,
			String[] values) {
		List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
		for (int i = 0; i < values.length; i++) {
			basicNameValuePairs.add(new BasicNameValuePair("interessen[" + i
					+ "]", values[i]));
		}
		return basicNameValuePairs;
	}

	private String[] interessenToIds(Set<String> values) {
		String[] allId = getResources().getStringArray(R.array.interessen_ids);
		for (Iterator iterator = values.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
		}
		String[] ids = new String[values.size()];
		String[] all = getResources().getStringArray(R.array.interessen);
		int idIndex = 0;
		for (int i = 0; i < allId.length; i++) {
			if (values.contains(all[i])) {
				ids[idIndex++] = allId[i];
			}
		}
		return ids;
	}

	public void setChanged(int type) {
		changed[type] = true;
	}

	// Result von onClick in Bitmap speichern
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			System.out.println("not returning RESULT_OK");
			return;
		}
		switch (requestCode) {
		// wenn von Kamera das Foto
		case PICK_FROM_CAMERA:
			// in Bitmap speichern
			imageRaw = resize((Bitmap) data.getExtras().get("data"));
			// altes Foto durch neues ersetzen
			image.setImageBitmap(imageRaw);
			// Kontrolle auf true setzen
			changed[IMAGE_CHANGED] = true;
			break;
		// wenn von SD Karte das Foto
		case PICK_FROM_FILE:
			Uri mImageCaptureUri = data.getData();
			imageRaw = resize(Utils.getRealPathFromURI(this, mImageCaptureUri));
			image.setImageBitmap(imageRaw);
			changed[IMAGE_CHANGED] = true;
			break;
		}

	}

	// Resize von Image
	private Bitmap resize(Bitmap b) {
		int originalWidth = b.getWidth();
		int originalHeight = b.getHeight();
		int newWidth = 500;
		int newHeight = 500;

		if (newWidth < newHeight) {
			newHeight = Math.round(newWidth
					* ((float) originalHeight / originalWidth));
		} else {
			newWidth = Math.round(newHeight
					* ((float) originalWidth / originalHeight));
		}
		return Bitmap.createScaledBitmap(b, newWidth, newHeight, true);
	}

	public void onHttpFin(int type, JSONObject j) {
		changed[type] = false;
		boolean waiting = false;
		for (int i = 0; i < changed.length; i++) {
			if (changed[i]) {
				waiting = true;
			}
		}
		if (!waiting) {
			finish();
		}

	}

	public Set<String> getTempInteressen() {
		return tempInteressen;
	}

	public void setTempInteressen(Set<String> tempInteressen) {
		this.tempInteressen = tempInteressen;
	}

}
