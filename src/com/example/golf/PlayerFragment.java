package com.example.golf;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
//import android.support.v4.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * The player fragment is displayed as a part of the Golf Activity next to the Board Fragment.
 * It contains a horizontal list of buttons representing each player. When one of these buttons
 * is pressed, that player's cards are displayed in the Board Fragment below. The current
 * player's cards are always displayed unless you press and hold down one of the player buttons
 * in this fragment.
 * @author 
 *
 */
public class PlayerFragment extends Fragment {

	OnCommunicateWithBoardFragment boardListener;

	private ArrayList<Button> buttonList;
	private LinearLayout.LayoutParams params;
	private LinearLayout buttons;
	private int numPlayers;


	/**
	 * Interface to get data to be displayed on the board fragment when a
	 * player's button is pressed
	 * 
	 * @author
	 * 
	 */
	public interface OnCommunicateWithBoardFragment {
		// Show the player's cards on the game board at the
		// index specified. The index is the index of the player
		// in the player array
		public int showPlayer(Player p);
	}



	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		View view = inflater.inflate(R.layout.player_fragment, container,
				false);
		buttonList = new ArrayList<Button>();

		buttons = (LinearLayout) view
				.findViewById(R.id.player_frag_linear_layout);
		Log.d("Player Fragment", "Num of players (location: Player Frag): " + numPlayers);

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the interface. If not, it throws an exception
		try {
			boardListener = (OnCommunicateWithBoardFragment) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnCommunicateWithBoardFragment");
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void setTurn(int index) {
		for (int i=0; i<buttonList.size(); i++) {
			Button b = buttonList.get(i);
			if (i == index) {
				b.setBackgroundResource(R.drawable.rectangle_current_player);
			}
			else {
				b.setBackgroundResource(R.drawable.rectangle_transparent_gray);
			}
			b.setTextColor(Color.WHITE);
		}
	}


	public void addButton(final Player p) { //p is final because it is referenced at a later point by the listener
		Button b = new Button(getActivity());
		b.setBackgroundResource(R.drawable.rectangle_transparent_gray);
		b.setTextColor(Color.WHITE);
		buttonList.add(b);

		b.setText(p.getName());
		b.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
		b.setOnTouchListener(new OnTouchListener() {
			int old;
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					highlight(buttonList.indexOf((Button) v));
					old = boardListener.showPlayer(p); //not sure if this will work correctly
					return true;
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					highlight(old);
					boardListener.showPlayer(null);
					return false;
				}
				return false;
			}

		});

		buttons.addView(b);
	}
	
	/**
	 * Highlights the current player's button or the button being pressed by changing the button's
	 * coloring to a white scheme
	 * @param j
	 */
	public void highlight(int j) {
		Button b = buttonList.get(j);
		b.setBackgroundResource(R.drawable.rectangle_transparent_white);
		b.setTextColor(Color.BLACK);

		for (int i = 0; i < buttonList.size(); i++) {
			if (i != j) {

				buttonList.get(i).setBackgroundResource(R.drawable.rectangle_transparent_gray);
				buttonList.get(i).setTextColor(Color.WHITE);	
			}
		}
	}
}
