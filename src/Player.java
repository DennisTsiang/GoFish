import java.util.*;

public class Player {

    protected List<Card> hand = new ArrayList<>();
    private int points = 0;

    public void addToHand(Card card) {
        hand.add(card);
    }


    public Card selectCard(String card) {
        if (!Character.isDigit(card.charAt(0))) {
            card = Character.toUpperCase(card.charAt(0)) + card.substring(1);
        }
        Iterator itr = hand.listIterator();
        while (itr.hasNext()) {
            Card current = (Card) itr.next();
            String currentCardValue = current.getRank();
            if(currentCardValue.equals(card)) {
                return current;
            }
        }
        return null;
    }

    public int getHandSize() {
        return hand.size();
    }

    public void printHand() {
        if (hand.isEmpty()) {
            System.out.println("Empty! Grabbing top card from deck.");
            return;
        }
        Iterator itr = hand.listIterator();
        do {
            Card current = (Card) itr.next();
            System.out.println(current);
        } while (itr.hasNext());
    }

    public Set<Card> removeCards(String cardChosen) {
        Card computerCard;
        Set<Card> cardsToGive = new HashSet<>();
        do {
            computerCard = selectCard(cardChosen);
            if (computerCard != null) {
                cardsToGive.add(computerCard);
                hand.remove(computerCard);
            }
        } while (computerCard != null);
        return cardsToGive;
    }

    public void checkAndRemoveFourOfAKind() {
        Map<String, Integer> hashTable = new HashMap<>();
        List<Card> handCopy = new LinkedList<>(hand);
        Iterator itr = handCopy.listIterator();
        Card current;
        while (itr.hasNext()) {
            current = (Card) itr.next();
            if (hashTable.get(current.getRank()) == null) {
                hashTable.put(current.getRank(),1);
            } else {
                int count = hashTable.get(current.getRank());
                count++;
                if (count == 4) {
                    removeCards(current.getRank());
                    points++;
                } else {
                    hashTable.put(current.getRank(), count);
                }

            }

        }

    }

    public int getPoints(){
        return points;
    }
}
