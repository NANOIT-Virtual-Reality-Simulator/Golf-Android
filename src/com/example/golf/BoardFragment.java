package com.example.golf;

import android.app.Activity;
import android.app.Fragment;
//import android.support.v4.app.Fragment;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * The Board Fragment is a part of the Golf Activity and is displayed graphically
 * as the lower left portion of the activity. This fragment contains the current
 * players set of 8 cards, and changes each turn depending on which player is going.
 * It may also be changed by clicking on another player's button in the Player Fragment,
 * which is also a part of the Golf Activity.
 */
public class BoardFragment extends Fragment implements View.OnTouchListener {
	private ImageView[][] cardImages;
	private ImageView deckImage, discardImage, discardUnderImage, dragImage;
	private TextView score;
	private Swappable source = null;
	private Swappable destination = null;
	private Player player = null;
	private ImageLoader loader;
	private Deck deck;
	private Card discard;
	private int windowWidth;
	private int windowHeight;
	private boolean isActive;
	private boolean dragging;
	private OnChangeTurnListener turnListener;
	private View sourceView;
	public SharedPreferences settings;
	public static final String PREFS_NAME = "MyPrefsFile";

	/**
	 * Interface to get current player to be displayed when it's their turn
	 * 
	 * @author
	 * 
	 */
	public interface OnChangeTurnListener {

		public void endTurn();
	}

	/**
	 * Creates a players board and displays the card deck image specified in the
	 * 	settings menu
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.board_fragment, container, false);

		settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);

		Display display = getActivity().getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		windowWidth = size.x;
		windowHeight = size.y;

		discard = null;

		String cardDesign = settings.getString("deck_design", "Classic");

		loader = new ImageLoader(getResources(), cardDesign);
		cardImages = new ImageView[2][4];

		cardImages[0][0] = (ImageView) view.findViewById(R.id.card1);
		cardImages[0][1] = (ImageView) view.findViewById(R.id.card2);
		cardImages[0][2] = (ImageView) view.findViewById(R.id.card3);
		cardImages[0][3] = (ImageView) view.findViewById(R.id.card4);
		cardImages[1][0] = (ImageView) view.findViewById(R.id.card5);
		cardImages[1][1] = (ImageView) view.findViewById(R.id.card6);
		cardImages[1][2] = (ImageView) view.findViewById(R.id.card7);
		cardImages[1][3] = (ImageView) view.findViewById(R.id.card8);

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {

				//set card design
				if (cardDesign.equals("Pokemon")) {
					cardImages[i][j].setBackgroundResource(R.drawable.pokemon);
				} else if (cardDesign.equals("Yugioh!")) {
					cardImages[i][j].setBackgroundResource(R.drawable.yugioh);
				} else if (cardDesign.equals("Scary")) {
					cardImages[i][j].setBackgroundResource(R.drawable.creeper);
				} else {
					cardImages[i][j]
							.setBackgroundResource(R.drawable.blue_facedown_card);
				}

				cardImages[i][j].setOnTouchListener(new OnTouchListener() {
					public boolean onTouch(View view, MotionEvent event) {
						if (!isActive)
							return true;
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							view.setVisibility(View.INVISIBLE);
							sourceView = view;
						}
						return false;
					}
				});
			}
		}

		view.setOnTouchListener(this);

		deckImage = (ImageView) view.findViewById(R.id.deck);

		if (cardDesign.equals("Pokemon")) {
			deckImage.setBackgroundResource(R.drawable.pokemon);
		} else if (cardDesign.equals("Yugioh!")) {
			deckImage.setBackgroundResource(R.drawable.yugioh);
		} else if (cardDesign.equals("Scary")) {
			deckImage.setBackgroundResource(R.drawable.creeper);
		} else {
			deckImage.setBackgroundResource(R.drawable.blue_facedown_card);
		}
		
		deckImage.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return !isActive;
			}
		});

		score = (TextView) view.findViewById(R.id.score);
		dragImage = (ImageView) view.findViewById(R.id.drag);
		discardImage = (ImageView) view.findViewById(R.id.discard);
		discardUnderImage = (ImageView) view.findViewById(R.id.discard2);
		discardImage.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				if (!isActive)
					return true;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					view.setVisibility(View.INVISIBLE);
					sourceView = view;
				}

				return false;
			}
		});
		dragging = false;
		return view;
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);

		turnListener = (OnChangeTurnListener) activity;
	}

	public void setDeck(Deck d) {
		deck = d;
	}

	public void setPlayer(Player p, boolean a) {
		player = p;
		isActive = a;
		redraw();
	}

	/**
	 * Updates the screen to account for user actions on their cards.
	 */
	public void redraw() {
		score.setText(" Score: " + player.getScore() + " ");
		Card[][] cards = player.getCards();
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				cardImages[i][j].setImageBitmap(loader.getImage(cards[i][j]));
			}
		}

		if (discard != null) {
			discard.faceUp = true;
		}
		discardImage.setImageBitmap(loader.getImage(discard));

		if (dragging) {
			if (source != null) {
				if (!source.isFromDeck()) {
					dragImage.setImageBitmap(loader.getImage(source.getCard()));
				} else {
					dragImage.setImageBitmap(loader.getImage(deck.preview()));
				}
			}
		}

	}

	/**
	 * Implements the dragging feature of the player's cards and checks
	 * 	if a user's turn should be over.
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		boolean isTurnOver = false;

		if (!isActive) {
			return true;
		}
		Log.d("onTouch", "touch detected");
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			destination = null;
			source = checkCardCoordValues(event.getRawX(), event.getRawY());
			Log.d("onTouch", "source == " + source);
			if (source != null) {
				dragging = true;
			}
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (dragging) {
				dragImage.setVisibility(View.VISIBLE);

				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dragImage
						.getLayoutParams();

				// start the drag
				int x_cord = (int) event.getX();
				int y_cord = (int) event.getY();

				if (x_cord + (dragImage.getWidth() / 2) > windowWidth) {
					x_cord = windowWidth;
				}
				if (y_cord + (dragImage.getHeight() / 2) > windowHeight) {
					y_cord = windowHeight;
				}

				params.leftMargin = x_cord - (dragImage.getWidth() / 2);
				params.topMargin = y_cord - (dragImage.getHeight() / 2);

				dragImage.setLayoutParams(params);

				redraw();
				return true;
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (sourceView != null) {
				sourceView.setVisibility(View.VISIBLE);
				sourceView = null;
			}
			dragging = false;
			dragImage.setVisibility(View.INVISIBLE);
			dragImage.setImageBitmap(null);
			destination = checkCardCoordValues(event.getRawX(), event.getRawY());
			Log.d("onTouch", "destination == " + destination);
			if (source == null || destination == null) {
				source = null;
				destination = null;
				return false;
			} else if ((source == deck && destination == discard)
					|| (source == discard && destination == deck)) {
				source = null;
				destination = null;
				return false;
			} else if (source == destination) {
				if (source == deck || source == discard) {
					source = null;
					destination = null;
					return false;
				}

				if (!source.getCard().faceUp) {
					source.getCard().faceUp = true;
					isTurnOver = true;
					redraw();

				}

			} else {
				Card newDiscard = source.swapCard(destination);

				if (newDiscard != null) {
					discard(newDiscard);
				}
				isTurnOver = true;
				redraw();

			}

		}

		redraw();
		source = null;
		destination = null;

		if (isTurnOver) {
			isActive = false;

			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {
					turnListener.endTurn();
				}
			}, 1);

		}

		return false;
	}

	/**
	 * Checks if the x, y location on the screen where a user has
	 * touched contains a card. Returns the swappable object.
	 * @param x
	 * @param y
	 * @return
	 */
	public Swappable checkCardCoordValues(float x, float y) {

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				if (isViewContains(cardImages[i][j], x, y)) {
					return player.getCards()[i][j];
				}
			}
		}

		if (isViewContains(deckImage, x, y)) {
			return deck;
		}
		if (isViewContains(discardImage, x, y)) {
			return discard;
		}

		return null;

	}

	/**
	 * Returns true if the location where a user presses down is on a card.
	 * @param view
	 * @param rx
	 * @param ry
	 * @return
	 */
	private boolean isViewContains(View view, float rx, float ry) {
		int[] l = new int[2];
		view.getLocationOnScreen(l);

		int x = l[0];
		int y = l[1];
		int w = view.getWidth();
		int h = view.getHeight();

		if (rx < x || rx > x + w || ry < y || ry > y + h) {
			return false;
		}
		return true;
	}

	private void discard(Card c) {
		discardUnderImage.setImageBitmap(loader.getImage(discard));
		discardImage.setImageBitmap(loader.getImage(c));
		discard = c;
	}

	/**
	 * Returns true when all 8 of the player's cards have been flipped.
	 * @return
	 */
	public boolean isGameOver() {
		Card[][] cards = player.getCards();
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				if (!cards[i][j].getCard().faceUp) {
					return false;
				}
			}
		}
		return true;
	}
}
