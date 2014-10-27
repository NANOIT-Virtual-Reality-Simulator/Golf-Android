package com.example.golf;

/**
 * Represents a player object. Contains all info for a player (score, card values, name, id).
 * 
 * @author 
 *
 */
public class Player {
	private String name;
	private Card[][] cards;
	private int id;
	
	public Player(String n, Card[][] c, int i) {
		name = n;
		cards = c;
		id = i;
	}
	
	public Card[][] getCards() {
		return cards;
	}
	
	public String getName() {
		return name;
	}
	
	private int scoreFor( Card c )
    {
        if ( c.value == 1 )
        {
            return 0;
        }
        else if ( c.value == 11 || c.value == 12 )
        {
            return 10;
        }
        else if ( c.value == 13 )
        {
            return -3;
        }
        return c.value;
    }
	
	public int getScore() {
	        int score = 0;

	        for ( int k = 0; k < 4; k++ )
	        {
	            Card card1 = cards[0][k];
	            Card card2 = cards[1][k];
	            if ( card1.value != card2.value )
	            {
	                score += card1.faceUp? scoreFor( card1 ) : 0;
	                score += card2.faceUp? scoreFor( card2 ) : 0;
	            }
	            else if ( !card1.faceUp || !card2.faceUp )
	            {
	                score += card1.faceUp? scoreFor( card1 ) : 0;
	                score += card2.faceUp? scoreFor( card2 ) : 0;
	            }
	        }
	        return score;
	}
	
	/**
	 * Determines if all of the player's cards are face up in order
	 * to know if the game is over.
	 * @return
	 */
	public boolean allFaceUp() {
		for (int i=0; i<cards.length; i++) {
			for (int j=0; j<cards[j].length; j++) {
				if (!cards[i][j].faceUp)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Flips all cards for a player. This is used when one player has 
	 * flipped all of his/her cards, thus finishing the game. Therefore
	 * the rest of the players whose cards aren't all flipped yet need
	 * their cards flipped as well.
	 */
	public void flipAllCards() {
		
		for (int i = 0; i < 2; i++) {
			for (int j=0; j < 4; j++) {
				if (!cards[i][j].faceUp)
					cards[i][j].faceUp = true;
			}
		}
		
	}
	
	public int getId() {
		return id;
	}
}
