package com.example.golf;

/**
 * The swappable interface is used for cards that are able to be swapped.
 * Cards that are swappable are cards from the player's board fragment, the deck,
 * and cards that were sent to the discard pile.
 * @author 
 *
 */
public interface Swappable {
	
	//Returns card that is being discarded
	public Card swapCard(Swappable destination);
	
	public Card getCard();
	
	public boolean isFromDeck();
	

}
