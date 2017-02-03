import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Deck {

    private List<Card> cards = new LinkedList<>();

    public Deck() {
        fillCards();
    }

    public void fillCards() {
        //Fill deck with all cards excluding Jokers
        cards.clear();
        for(Suit suit : Suit.values()) {
            for( int i=1; i < 10; i++) {
                cards.add( new Card(Integer.toString(i+1), suit) );
            }
            cards.add( new Card("Jack",suit) );
            cards.add( new Card("Queen", suit) );
            cards.add( new Card("King", suit) );
            cards.add( new Card("Ace", suit) );
        }

    }

    public Card getTopCard() {
        return cards.remove(0);

    }

    public void shuffle() {
        Random random = new Random();
        for(int i=0; i < cards.size(); i++) {
            //Get number between 0 and 51
            int index = random.nextInt(cards.size());
            //Remove current card
            Card temp = cards.get(i);
            //Add the other card in the same place
            cards.set(i, cards.get(index));
            cards.set(index, temp);
        }
    }

    public void riffleShuffle() {
        int initialDeckSize = cards.size();
        List<Card> halfDeck = new LinkedList<>();
        for( int i=0; i < initialDeckSize/2; i++ ) {
            //Grab top card
            halfDeck.add(cards.remove(0));
        }
        List<Card> shuffledDeck = new LinkedList<>();
        for( int i=0; i < initialDeckSize/2; i++ ) {
            shuffledDeck.add(halfDeck.remove(0));
            shuffledDeck.add(cards.remove(0));
        }
        cards = shuffledDeck;
    }

    public void printDeck() {
        for(int i=0; i < cards.size(); i++) {
            System.out.println(cards.get(i));
        }
    }

    public boolean hasCards() {
        return !cards.isEmpty();
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Deck)) {
            return false;
        } else if (this.cards.size() != ((Deck) that).cards.size()) {
            return false;
        }

        for(int i=0; i < cards.size(); i++) {
            if (!this.cards.get(i).equals(((Deck) that).cards.get(i))) {
                System.out.println(cards.get(i).toString() + " is not equal to " + ((Deck) that).cards.get(i).toString());
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return cards != null ? cards.hashCode() : 0;
    }
}
