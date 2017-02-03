import java.util.Scanner;

public class Card {

    private Suit suit;
    private String value;

    public Card(String value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public String getRank(){
        Scanner sc = new Scanner(value);
        return sc.next();
    }

    @Override
    public String toString() {
        String cardSuit = "";

        switch( suit) {
            case CLUBS:
                cardSuit = "Clubs";
                break;
            case SPADES:
                cardSuit = "Spades";
                break;
            case HEARTS:
                cardSuit = "Hearts";
                break;
            case DIAMONDS:
                cardSuit = "Diamonds";
                break;

        }

        return value+" of "+cardSuit;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Card)) {
            return false;
        }
        if (this.toString().equals(obj.toString())) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
