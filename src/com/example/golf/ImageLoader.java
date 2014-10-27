package com.example.golf;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Loads the bitmap images for cards into a 2-d array.
 * This was meant to serve as a caching mechanism for memory management.
 * @author 
 *
 */
public class ImageLoader {
	Bitmap images[][];
	Bitmap blank, faceDown;
	
	public ImageLoader(Resources r, String cardImage) {

		images = new Bitmap[4][13];
		blank = BitmapFactory.decodeResource(r, R.drawable.blank);
		
		if (cardImage.equals("Pokemon")) {
			faceDown = BitmapFactory.decodeResource(r, R.drawable.pokemon);
		}
		else if (cardImage.equals("Scary")) {
			faceDown = BitmapFactory.decodeResource(r, R.drawable.creeper);
		}
		else if (cardImage.equals("Yugioh!")) {
			faceDown = BitmapFactory.decodeResource(r, R.drawable.yugioh);
		}
		else {
			faceDown = BitmapFactory.decodeResource(r, R.drawable.blue_facedown_card);
		}
			
			
		for (int i=1; i<=13; i++) {
			images[0][i-1] = BitmapFactory.decodeResource(r, r.getIdentifier(("spades_" + i), "drawable", "com.example.golf"));
			images[1][i-1] = BitmapFactory.decodeResource(r, r.getIdentifier(("clubs_" + i), "drawable", "com.example.golf"));
			images[2][i-1] = BitmapFactory.decodeResource(r, r.getIdentifier(("hearts_" + i), "drawable", "com.example.golf"));
			images[3][i-1] = BitmapFactory.decodeResource(r, r.getIdentifier(("diamonds_" + i), "drawable", "com.example.golf"));
			
		}
		
	}

	public Bitmap getImage(Card c) {
		if (c == null)
			return blank;
		if (!c.faceUp)
			return faceDown;
		switch (c.suit) {
		case SPADES:
			return images[0][c.value-1];
		case CLUBS:
			return images[1][c.value-1];
		case HEARTS:
			return images[2][c.value-1];
		case DIAMONDS:
			return images[3][c.value-1];
		default:
			return blank;
		}

	}
}

