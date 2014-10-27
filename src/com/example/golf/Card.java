package com.example.golf;

/**
 * This class represents a Card object. It implements the swappable interface
 * so that a card may be swapped with another.
 * @author 
 *
 */
public class Card implements Swappable {
	int value;
	Suit suit;
	boolean faceUp;
	
	public Card (int i, Suit s) {
		value = i;
		suit = s;
		faceUp = false;
	}
	
	public void swap(Card c) {
		int v = value;
		Suit s = suit;
		boolean f = faceUp;
		value = c.value;
		suit = c.suit;
		faceUp = c.faceUp;
		c.value = v;
		c.suit = s;
		c.faceUp = f;
	}

	@Override
	public Card swapCard(Swappable destination) {
		
		Card destCard = destination.getCard();
		this.swap(destCard);
		
		if (destination.isFromDeck()) {
			return destCard;
		}
		
		return null;
	}

	@Override
	public Card getCard() {
		return this;
	}

	@Override
	public boolean isFromDeck() {
		return false;
	}
	
	
}
