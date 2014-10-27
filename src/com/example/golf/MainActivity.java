package com.example.golf;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * The main activity class for the Golf app.
 * This activity displays a menu screen where the user may choose to either play a game,
 * change settings, view high scores, or see info about the app.
 * Music functionality is commented out.
 */
public class MainActivity extends Activity implements View.OnClickListener {

	private AboutFragment aboutFrag;
	private HighScoreActivity scoreFrag;

	private Button playButton;
	private Button highScoreButton;
	private Button settingsButton;
	private Button aboutButton;

	//private AsyncTask<Void, Void, Void> backgroundMusic;
	//private boolean musicPlaying = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Activity me = this;
//		backgroundMusic = new AsyncTask<Void, Void, Void>() {
//			protected Void doInBackground(Void... arg0) {
//				MediaPlayer player = MediaPlayer.create(me, R.raw.birds); 
//				player.setLooping(true); // Set looping 
//				player.setVolume(100,100); 
//				player.start(); 
//				return null;
//			}
//		};

		playButton = (Button) findViewById(R.id.play_game_button);
		highScoreButton = (Button) findViewById(R.id.leaderboard_button);
		settingsButton = (Button) findViewById(R.id.settings_button);
		aboutButton = (Button) findViewById(R.id.about_button);

		playButton.setBackgroundResource(R.drawable.button_press);
		highScoreButton.setBackgroundResource(R.drawable.button_press);
		settingsButton.setBackgroundResource(R.drawable.button_press);
		aboutButton.setBackgroundResource(R.drawable.button_press);

		playButton.setOnClickListener(this);
		highScoreButton.setOnClickListener(this);
		settingsButton.setOnClickListener(this);
		aboutButton.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	/**
	 * Start the Game Menu activity where a user can choose options before they
	 * start playing the game. Options such as number of players, computer
	 * difficulty, etc...
	 * 
	 * @param view
	 */
	public void playGamePressed() {

		Intent gameMenuIntent = new Intent(this, GameMenuActivity.class);
		startActivity(gameMenuIntent);
	}

	public void settingsPressed() {

		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivity(settingsIntent);
	}

	// TODO: These 2 buttons (leaderboard and about) that display fragments
	// don't work yet

	public void highScorePressed() {
		// leaderboard button triggers a fragment, not an activity
		Intent highscoreIntent = new Intent(this, HighScoreActivity.class);
		startActivity(highscoreIntent);

	}

	public void aboutPressed() {
		// about button triggers a fragment, not an activity
		aboutFrag = new AboutFragment();

		FragmentTransaction fragTrans = getFragmentManager().beginTransaction();

		fragTrans.replace(R.id.frame_wrapper, aboutFrag);
		fragTrans.addToBackStack(null);
		fragTrans.commit();

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.play_game_button) {
			// Play game
			playGamePressed();

		} else if (v.getId() == R.id.settings_button) {
			// show settings
			settingsPressed();

		} else if (v.getId() == R.id.leaderboard_button) {
			// show leaderboard
			highScorePressed();

		} else if (v.getId() == R.id.about_button) {
			// show about
			aboutPressed();

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
//		if (!musicPlaying) {
//			//backgroundMusic.execute();
//			musicPlaying = true;
//		}
	}

	@Override
	protected void onPause() {
		super.onPause();
//		if (musicPlaying) {
//			backgroundMusic.cancel(true);
//			musicPlaying = false;
//		}
	}
}
