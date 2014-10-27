package com.example.golf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * The Game Menu Activity is started when a user presses the Play button from the
 * main activity menu. It contains options for single player, multiplayer, and contains
 * options for the number of players which may be 2, 3, or 4. If the user does not
 * select any options it defaults to 2 player multiplayer mode.
 * @author 
 *
 */
public class GameMenuActivity extends Activity implements View.OnClickListener {

	private Button playGameButton;

	private Button singlePlayer;
	private Button multiplayer;

	private Button numPlayers2;
	private Button numPlayers3;
	private Button numPlayers4;
	
	private boolean isPlayBlack;
	private boolean isSingleGray;
	private boolean isMultiGray;
	private boolean is2Gray;
	private boolean is3Gray;
	private boolean is4Gray;


	private int numPlayers = 2;
	private LinearLayout leftBackgroundView;
	private LinearLayout rightBackgroundView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pregame_menu);

		playGameButton = (Button) findViewById(R.id.play_game);

		singlePlayer = (Button) findViewById(R.id.single_player);
		multiplayer = (Button) findViewById(R.id.multiplayer);

		numPlayers2 = (Button) findViewById(R.id.players2);
		numPlayers3 = (Button) findViewById(R.id.players3);
		numPlayers4 = (Button) findViewById(R.id.players4);

		leftBackgroundView = (LinearLayout) findViewById(R.id.background1);
		rightBackgroundView = (LinearLayout) findViewById(R.id.background2);

		leftBackgroundView
				.setBackgroundResource(R.drawable.rectangle_transparent_black);
		rightBackgroundView
				.setBackgroundResource(R.drawable.rectangle_transparent_black);

		
		isPlayBlack = true;
		isSingleGray = true;
		//These 2 below are false because the multiplayer button and the
		//2 players button are on by default
		isMultiGray = false;
		is2Gray = false;
		is3Gray = true;
		is4Gray = true;
		
		multiplayer.setBackgroundResource(R.drawable.rectangle_transparent_white);
		multiplayer.setTextColor(Color.BLACK);
		
		numPlayers2.setBackgroundResource(R.drawable.rectangle_transparent_white);
		numPlayers2.setTextColor(Color.BLACK);
		
		playGameButton.setOnClickListener(this);
		singlePlayer.setOnClickListener(this);
		multiplayer.setOnClickListener(this);
		numPlayers2.setOnClickListener(this);
		numPlayers3.setOnClickListener(this);
		numPlayers4.setOnClickListener(this);
		multiplayer.performClick();
		numPlayers2.performClick();
	}

	/**
	 * Account for buttons that may have been white colored from being pressed when
	 * you return to the activity.
	 */
	@Override
	protected void onResume() {
		if (!isPlayBlack) {
			playGameButton.setBackgroundResource(R.drawable.rectangle_transparent_black);
			playGameButton.setTextColor(Color.WHITE);
			isPlayBlack = true;
		}
		if (!isSingleGray) {
			singlePlayer.setBackgroundResource(R.drawable.rectangle_transparent_gray);
			singlePlayer.setTextColor(Color.WHITE);
			isSingleGray = true;
		}
		if (!isMultiGray) {
			multiplayer.setBackgroundResource(R.drawable.rectangle_transparent_gray);
			multiplayer.setTextColor(Color.WHITE);
			isMultiGray = true;
		}
		if (!is2Gray) {
			numPlayers2.setBackgroundResource(R.drawable.rectangle_transparent_gray);
			numPlayers2.setTextColor(Color.WHITE);
			is2Gray = true;
		}
		if (!is3Gray) {
			numPlayers3.setBackgroundResource(R.drawable.rectangle_transparent_gray);
			numPlayers3.setTextColor(Color.WHITE);
			is3Gray = true;
		}
		if (!is4Gray) {
			numPlayers4.setBackgroundResource(R.drawable.rectangle_transparent_gray);
			numPlayers4.setTextColor(Color.WHITE);
			is4Gray = true;
		}

		super.onResume();
	}
	
	
	/**
	 * Get the number of players based on which button was pressed and start the game.
	 */
	public void playGame() {

		//set the number of players depending on which button is pressed
		if (!is3Gray) {
			numPlayers = 3;
		}
		else if (!is4Gray) {
			numPlayers = 4;
		}
		else {
			//2 by default
			numPlayers = 2;
		}
		
		
		// handle num of players with buttons now

		// Create an intent to start the game and pass it the number
		// of players for this game.
		Intent startGameIntent = new Intent(this, GolfActivity.class);
		startGameIntent.putExtra("NUM_PLAYERS", numPlayers);

		startActivity(startGameIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * If a button is pressed, it's color is set to a white color scheme and any buttons
	 * in the same group of button that had previously been pressed are set back to the 
	 * default dark color scheme.
	 * For instance, if a user selects the 3 button for 3 players, then changes his/her
	 * selection to 2 players, the 2 button turns white and the 3 button turns back to a dark
	 * coloring. Then when the game is started it has 2 players.
	 */
	@Override
	public void onClick(View view) {

		if (view.getId() == R.id.play_game) {
			// Play game
			playGameButton.setBackgroundResource(R.drawable.rectangle_transparent_white);
			playGameButton.setTextColor(Color.BLACK);
			isPlayBlack = false;
			playGame();
		}
		else if (view.getId() == R.id.single_player) {
			
			if (!isMultiGray) {
				
				multiplayer.setBackgroundResource(R.drawable.rectangle_transparent_gray);
				multiplayer.setTextColor(Color.WHITE);
				isMultiGray = true;
				
				singlePlayer.setBackgroundResource(R.drawable.rectangle_transparent_white);
				singlePlayer.setTextColor(Color.BLACK);
				isSingleGray = false;
			}
			else {
				singlePlayer.setBackgroundResource(R.drawable.rectangle_transparent_white);
				singlePlayer.setTextColor(Color.BLACK);
				isSingleGray = false;
			}
			

		}
		else if (view.getId() == R.id.multiplayer) {
			
			if (!isSingleGray) {
				singlePlayer.setBackgroundResource(R.drawable.rectangle_transparent_gray);
				singlePlayer.setTextColor(Color.WHITE);
				isSingleGray = true;
				
				multiplayer.setBackgroundResource(R.drawable.rectangle_transparent_white);
				multiplayer.setTextColor(Color.BLACK);
				isMultiGray = false;
			}
			else {
				multiplayer.setBackgroundResource(R.drawable.rectangle_transparent_white);
				multiplayer.setTextColor(Color.BLACK);
				isMultiGray = false;
			}

		}
		else if (view.getId() == R.id.players2) {
			
			if (!is3Gray || !is4Gray) {
				numPlayers3.setBackgroundResource(R.drawable.rectangle_transparent_gray);
				numPlayers3.setTextColor(Color.WHITE);
				is3Gray = true;
				
				numPlayers4.setBackgroundResource(R.drawable.rectangle_transparent_gray);
				numPlayers4.setTextColor(Color.WHITE);
				is4Gray = true;
				
				numPlayers2.setBackgroundResource(R.drawable.rectangle_transparent_white);
				numPlayers2.setTextColor(Color.BLACK);
				is2Gray = false;
			}
			else {
				numPlayers2.setBackgroundResource(R.drawable.rectangle_transparent_white);
				numPlayers2.setTextColor(Color.BLACK);
				is2Gray = false;
			}

		}
		else if (view.getId() == R.id.players3) {
			
			if (!is2Gray || !is4Gray) {
				numPlayers2.setBackgroundResource(R.drawable.rectangle_transparent_gray);
				numPlayers2.setTextColor(Color.WHITE);
				is2Gray = true;
				
				numPlayers4.setBackgroundResource(R.drawable.rectangle_transparent_gray);
				numPlayers4.setTextColor(Color.WHITE);
				is4Gray = true;
				
				numPlayers3.setBackgroundResource(R.drawable.rectangle_transparent_white);
				numPlayers3.setTextColor(Color.BLACK);
				is3Gray = false;
			}
			else {
				numPlayers3.setBackgroundResource(R.drawable.rectangle_transparent_white);
				numPlayers3.setTextColor(Color.BLACK);
				is3Gray = false;
			}
			

		}
		else if (view.getId() == R.id.players4) {
			
			if (!is2Gray || !is3Gray) {
				numPlayers3.setBackgroundResource(R.drawable.rectangle_transparent_gray);
				numPlayers3.setTextColor(Color.WHITE);
				is3Gray = true;
				
				numPlayers2.setBackgroundResource(R.drawable.rectangle_transparent_gray);
				numPlayers2.setTextColor(Color.WHITE);
				is2Gray = true;
				
				numPlayers4.setBackgroundResource(R.drawable.rectangle_transparent_white);
				numPlayers4.setTextColor(Color.BLACK);
				is4Gray = false;
			}
			else {
				numPlayers4.setBackgroundResource(R.drawable.rectangle_transparent_white);
				numPlayers4.setTextColor(Color.BLACK);
				is4Gray = false;
			}
			
		}

		
	}

}
