package com.example.golf;

import android.app.Activity;
import android.app.AlertDialog;
//import android.support.v4.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This activity is what contains the main functionality of the Golf card game.
 * It contains 2 fragments, Board Fragment and Player Fragment. The player fragment
 * is at the top of the screen and contains a horizontal list of player buttons.
 * When these are clicked, that player's cards are shown in the board fragment.
 * If the user is not clicking one of these, the current player's cards are shown
 * in the same board fragment. The board fragment is shown below the player fragment and
 * takes up the majority of the screen. The board frag also contains the deck and discard
 * piles.
 * This class manages the current player.
 * @author 
 *
 */
public class GolfActivity extends Activity implements
		PlayerFragment.OnCommunicateWithBoardFragment,
		BoardFragment.OnChangeTurnListener {

	private Player[] pArray;
	private BoardFragment boardFrag;
	private PlayerFragment playerFrag;
	private int numPlayers;
	private Deck deck;
	private int curPlayer;
	public static final String PREFS_NAME = "MyPrefsFile";
	public SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.NoActionBar);
		setContentView(R.layout.activity_golf);

		settings = getSharedPreferences(PREFS_NAME, 0);

		deck = new Deck();
		boardFrag = (BoardFragment) getFragmentManager().findFragmentById(
				R.id.board_fragment);
		playerFrag = (PlayerFragment) getFragmentManager().findFragmentById(
				R.id.player_fragment);

		// Get the number of players for this game
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			numPlayers = extras.getInt("NUM_PLAYERS");

		} else
			numPlayers = 2; // defaults so everything doesn't crash without
							// bundle

		pArray = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			Card[][] cards = new Card[][] {
					{ deck.draw(), deck.draw(), deck.draw(), deck.draw() },
					{ deck.draw(), deck.draw(), deck.draw(), deck.draw() } };
			pArray[i] = new Player("Player " + (i + 1), cards, i);
			playerFrag.addButton(pArray[i]);
		}
		curPlayer = 0;
		boardFrag.setPlayer(pArray[0], true);
		boardFrag.setDeck(deck);
		playerFrag.highlight(0);

	}
	
	
    @Override
    public void onBackPressed() {
            super.onBackPressed();
            this.finish();
    }
	

    /**
     * Sets the board fragment to show the player specified.
     */
	@Override
	public int showPlayer(Player p) {
		if (p == null) {
			boardFrag.setPlayer(pArray[curPlayer], true);
			return curPlayer;
		}
		// Replace all the cards on the board with the appropriate player
		boardFrag.setPlayer(p, (curPlayer == p.getId()) ? true : false);
		return curPlayer;
	}

	
	public Deck getDeck() {
		return deck;
	}

	/**
	 * Ends a players turn and checks if the game is over by checking if the player's
	 * cards are all flipped over.
	 */
	public void endTurn() {
		if (boardFrag.isGameOver()) {
			int lowestIndex = 0;
			pArray[0].flipAllCards();

			for (int i = 1; i < pArray.length; i++) {
				pArray[i].flipAllCards();
				if (pArray[i].getScore() < pArray[lowestIndex].getScore()) {
					lowestIndex = i;
				}
			}

			Toast.makeText(this, pArray[lowestIndex].getName() + " wins!",
					Toast.LENGTH_SHORT).show();
			final Player winner = pArray[lowestIndex];

			if (isHighScore(winner.getScore())) {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);

				alert.setTitle(pArray[lowestIndex].getName() + " wins!");
				alert.setMessage("Enter your name:");

				// Set an EditText view to get user input
				final EditText input = new EditText(this);
				alert.setView(input);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String value = " - " + input.getText().toString();
								setNewHighScore(value, winner.getScore());
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								//Do nothing
							}
						});

				alert.show();
			}
		} else {
			curPlayer++;
			curPlayer %= pArray.length;

			// display toast when turn changes if tutor mode is on
			if (settings.getBoolean("tutor", true)) {
				Toast.makeText(this, pArray[curPlayer].getName() + "\'s turn.",
						2000).show();
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			boardFrag.setPlayer(pArray[curPlayer], true);
			playerFrag.highlight(curPlayer);
		}
	}

	/**
	 * Checks the high score list and compares the winner of the current game
	 * against those values. If the value is lower than the highest value
	 * on the list, it returns true.
	 * @param winScore
	 * @return
	 */
	private boolean isHighScore(int winScore) {

		// get the current high scores
		String[] list = { settings.getString("score1", "No Score"),
				settings.getString("score2", "No Score"),
				settings.getString("score3", "No Score"),
				settings.getString("score4", "No Score"),
				settings.getString("score5", "No Score") };

		// used to store all the high score numbers
		int[] scoreList = new int[5];
		// gets the high scores
		for (int i = 0; i < list.length; i++) {
			
			if (list[i].equals("No Score") || list[i].equals("")) {
				scoreList[i] = 1000;
			} else {
				String[] sub = list[i].split("\\s+");
				Log.d("score", "SCORE IS : " + list[i]);
				scoreList[i] = Integer.parseInt(sub[0].toString());
			}
		}

		for (int i = 0; i < scoreList.length; i++) {
			if (winScore < scoreList[i]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Sets the current winner's score to one of the values
	 * on the high score list
	 * @param name
	 * @param score
	 */
	private void setNewHighScore(String name, int score) {

		// get the current high scores
		String[] list = { settings.getString("score1", "No Score"),
				settings.getString("score2", "No Score"),
				settings.getString("score3", "No Score"),
				settings.getString("score4", "No Score"),
				settings.getString("score5", "No Score") };

		// used to store all the high score numbers
		int[] scoreList = new int[5];
		// gets the high scores
		for (int i = 0; i < list.length; i++) {
			if (list[i].equals("No Score") || list[i].equals("")) {
				scoreList[i] = 1000;
			} else {
				String[] sub = list[i].split("\\s+");
				scoreList[i] = Integer.parseInt(sub[0]);
			}
		}

		String newScore = score + " " + name;
		
		//NEW HIGH SCORE
		if (scoreList[0] > score) {
			list[4] = list[3];
			list[3] = list[2];
			list[2] = list[1];
			list[1] = list[0];
			list[0] = newScore;
		}
		//so close, 2nd highest score
		else if (scoreList[1] > score) {
			list[4] = list[3];
			list[3] = list[2];
			list[2] = list[1];
			list[1] = newScore;
		}
		//3rd highest score, that's average
		else if (scoreList[2] > score) {
			list[4] = list[3];
			list[3] = list[2];
			list[2] = newScore;
		}
		//4th highest score, eh
		else if (scoreList[3] > score) {
			list[4] = list[3];
			list[3] = newScore;
		}
		//5th highest score :(
		else {
			list[4] = newScore;
		}

		// save the new values
		String[] scoreKeys = { "score1", "score2", "score3", "score4", "score5" };
		SharedPreferences.Editor editor = settings.edit();
		for (int i = 0; i < scoreKeys.length; i++) {
			editor.putString(scoreKeys[i], list[i]);
		}
		editor.commit();
	}
}
