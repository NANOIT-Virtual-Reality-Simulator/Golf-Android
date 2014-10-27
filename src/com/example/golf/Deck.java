package com.example.golf;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class implements a Deck object. Like the card class, it also implements the
 * swappable interface because a user may swap between cards, the deck, and the discard pile.
 * @author 
 *
 */
public class Deck implements Swappable {
	private ArrayList<Card> cards;
	private Random rand;
	private int curIndex;

	public Deck() {

		cards = new ArrayList<Card>();

		for (int i=1; i<14; i++) {
			cards.add(new Card(i, Suit.SPADES));
			cards.add(new Card(i, Suit.CLUBS));
			cards.add(new Card(i, Suit.HEARTS));
			cards.add(new Card(i, Suit.DIAMONDS));
		}
		
		rand = new Random();
		curIndex = rand.nextInt(cards.size());
	}
	
	public Card draw() {
		Card c = cards.remove(curIndex);
		curIndex = rand.nextInt(cards.size());
		return c;
	}
	
	public Card preview() {
		return cards.get(curIndex);
	}

	@Override
	public Card swapCard(Swappable destination) {
		Card deckCard = draw();
		deckCard.faceUp = true;
		Card destCard = destination.getCard();
		deckCard.swap(destCard);
		//deckCard is now sent to discard pile
		return deckCard;
	}

	@Override
	public Card getCard() {
		Card c = draw();
		c.faceUp = true;
		return c;
	}

	@Override
	public boolean isFromDeck() {
		return true;
	}
}
