package com.example.golf;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The About fragment is started when a user presses the 'About' button from the
 * main activity screen. It displays information about the Golf card game, tells users
 * how to play, and lists information about the creators.
 */
public class AboutFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.about_fragment, 
				container, false);
		
		
		return view;
	}
	

}
