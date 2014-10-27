package com.example.golf;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.view.View;

/**
 * This Activity is run from the main activity when a user presses the settings button.
 * The settings activity contains options for card deck design (image shown on back of card),
 * a tutor mode, and sound. All changes made in the settings activity are persistent.
 * Music/sound options are commented out for now.
 * @author
 *
 */
public class SettingsActivity extends Activity implements View.OnClickListener,
		OnItemSelectedListener {

	public static final String PREFS_NAME = "MyPrefsFile";
	public SharedPreferences settings;

	private CheckBox tutorMode;
	//private CheckBox sound;
	private Spinner deckDesign;
	//private Spinner musicChoice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);

		settings = getSharedPreferences(PREFS_NAME, 0);

		tutorMode = (CheckBox) findViewById(R.id.checkBox2);
		//sound = (CheckBox) findViewById(R.id.checkBox1);

		deckDesign = (Spinner) findViewById(R.id.spinner1);
		//musicChoice = (Spinner) findViewById(R.id.spinner2);

		tutorMode.setOnClickListener(this);
		//sound.setOnClickListener(this);
		deckDesign.setOnItemSelectedListener(this);
		//musicChoice.setOnItemSelectedListener(this);

		tutorMode.setChecked(settings.getBoolean("tutor", true));
		//sound.setChecked(settings.getBoolean("sound", true));

		String deck = settings.getString("deck_design", "classic");
		int deckPos = 0;
		if (deck.equals("Pokemon")) {
			deckPos = 1;
		} else if (deck.equals("Yugioh!")) {
			deckPos = 2;
		} else if (deck.equals("Scary")) {
			deckPos = 3;
		}
		// else it is Classic and deckPos is 0
		deckDesign.setSelection(deckPos);

//		String music = settings.getString("music_choice", "Birds");
//		int musicPos = 0;
//		if (music.equals("Sandstorm")) {
//			musicPos = 1;
//		} else if (music.equals("Guile\'s Theme")) {
//			musicPos = 2;
//		}
		// else it is Birs, and musicPos is 0
		//musicChoice.setSelection(musicPos);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
//
//		if (v.getId() == R.id.checkBox1) {
//			// sound on/off
//			SharedPreferences.Editor editor = settings.edit();
//
//			editor.putBoolean("sound", ((CheckBox) v).isChecked());
//			editor.commit();
//
//		} 
		if (v.getId() == R.id.checkBox2) {
			// tutor mode
			SharedPreferences.Editor editor = settings.edit();

			editor.putBoolean("tutor", ((CheckBox) v).isChecked());
			editor.commit();
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> spinner, View v, int arg2,
			long arg3) {

		if (spinner.getId() == R.id.spinner1) { // deck design
			SharedPreferences.Editor editor = settings.edit();

			editor.putString("deck_design", spinner.getItemAtPosition(arg2)
					.toString());
			editor.commit();
		} 
//		else if (spinner.getId() == R.id.spinner2) { // music choice
//			SharedPreferences.Editor editor = settings.edit();
//
//			editor.putString("music_choice", spinner.getItemAtPosition(arg2)
//					.toString());
//			editor.commit();
//		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> spinner) {
		SharedPreferences.Editor editor = settings.edit();

		editor.putString("deck_design", "Classic");
		editor.putString("music_choice", "Birds");
		editor.commit();

	}

}
