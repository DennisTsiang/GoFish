import java.util.Random;

public class Computer extends Player {

    public Card pickCardToAsk() {
        Random random = new Random();
        int selection = random.nextInt(hand.size());
        return hand.get(selection);

    }
}
