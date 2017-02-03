import java.io.IOException;
import java.util.Random;
import java.util.Set;

public class Game {

    private final int START_HAND_SIZE = 5;
    private Computer computerPlayers[];
    private Player humanPlayer;
    private Deck deck;
    private final int compPlayers;

    public Game(Deck deck, int compPlayers, Player humanPlayer ) {
        this.compPlayers = compPlayers;
        this.humanPlayer = humanPlayer;
        this.deck = deck;
        computerPlayers = new Computer[compPlayers];
        for (int i = 0; i < computerPlayers.length; i++) {
            computerPlayers[i] = new Computer();
        }
    }

    public void dealCards() {
        for (int i = 0; i < START_HAND_SIZE; i++) {
            humanPlayer.addToHand(deck.getTopCard());
            for (int j = 0; j < compPlayers; j++) {
                computerPlayers[j].addToHand(deck.getTopCard());
            }
        }
    }

    public void playerTurn() throws IOException {
        System.out.println("Current hand is:");
        humanPlayer.printHand();
        System.out.println();
        if (humanPlayer.getHandSize() == 0) {
            if (deck.hasCards()) {
                System.out.println("Empty! Grabbing from top of deck.");
                humanPlayer.addToHand(deck.getTopCard());
                return;
            } else {
                System.out.println("No more cards to pick up. You are out of the game!");
                return;
            }
        }
        boolean goodInput = false;
        while(!goodInput) {
            System.out.println("Ask or pick up?");
            String line = IO.readLine();
            if (line.equals("pick up")) {
                if (deck.hasCards()) {
                    humanPlayer.addToHand(deck.getTopCard());
                    break;
                } else {
                    System.out.println("No more cards to pick up!");
                    line = "ask";
                }
            }
            if (line.equals("ask") || line.equals("Ask")) {
                System.out.println("Choose a computer player to ask:");
                int chosenPlayer = IO.getNumberFromUser(compPlayers);
                Card card;
                String cardChosen;
                do {
                    System.out.println("What card are you going to ask for?");
                    cardChosen = IO.readLine();
                    card = humanPlayer.selectCard(cardChosen);
                    if (card == null) {
                        System.out.println("You do not have that card. Pick again.");
                    }
                } while (card == null);
                Card computerCard = computerPlayers[chosenPlayer - 1].selectCard(cardChosen);
                if (computerCard == null) {
                    System.out.println("Go Fish!");
                    humanPlayer.addToHand(deck.getTopCard());
                } else {
                    System.out.println("You obtained the computer's card(s)!");
                    //Get all cards that match the value
                    Set<Card> returnedCards = computerPlayers[chosenPlayer - 1].removeCards(cardChosen);
                    for (Card c : returnedCards) {
                        humanPlayer.addToHand(c);
                    }
                }
                goodInput = true;
            } else {
                System.out.println("Could not understand input.");
            }
        }
        humanPlayer.checkAndRemoveFourOfAKind();
    }

    public boolean gameFinished() {
        if (deck.hasCards()) {
            return false;
        }
        if (humanPlayer.getHandSize() > 0) {
            return false;
        }
        for( int i=0; i < compPlayers; i++) {
            if (computerPlayers[i].getHandSize() > 0) {
                return false;
            }
        }
        return true;
    }

    public void printLeaderboard() {
        System.out.println("Leaderboard:");
        System.out.println("You: " + humanPlayer.getPoints());
        for (int i = 0; i < compPlayers; i++) {
            System.out.println("CP" + (i+1) + ": " + computerPlayers[i].getPoints());
        }
    }

    public void computerTurn() {
        for (int i = 0; i < compPlayers; i++) {
            if (computerPlayers[i].getHandSize() == 0) {
                if (deck.hasCards()) {
                    computerPlayers[i].addToHand(deck.getTopCard());
                }
                continue;
            }
            Card card = computerPlayers[i].pickCardToAsk();
            String cardChosen = card.getRank();
            Random random = new Random();
            Card result;
            int playerToAsk = random.nextInt(compPlayers);

            if (i == playerToAsk) {
                //Ask player instead of itself
                System.out.println("CP" + (i+1) + " asks player for " + cardChosen);
                result = humanPlayer.selectCard(cardChosen);

            } else {
                System.out.println("CP" + (i+1) + " asks CP" + (playerToAsk+1) + " for " + cardChosen);
                result = computerPlayers[playerToAsk].selectCard(cardChosen);
            }
            if (result == null) {
                if (deck.hasCards()) {
                    computerPlayers[i].addToHand(deck.getTopCard());
                }
            } else {
                Set<Card> returnedCards;
                if (i == playerToAsk) {
                    returnedCards = humanPlayer.removeCards(cardChosen);
                } else {
                    returnedCards = computerPlayers[playerToAsk].removeCards(cardChosen);
                }
                for (Card c : returnedCards) {
                    computerPlayers[i].addToHand(c);
                }
            }
            computerPlayers[i].checkAndRemoveFourOfAKind();
        }
    }


    public String getWinner() {
        int computer = 0;
        int highestPoints = 0;
        for (int i=0; i < compPlayers; i++) {
            if (computerPlayers[i].getPoints() > highestPoints) {
                computer = i+1;
                highestPoints = computerPlayers[i].getPoints();
            }
        }
        if (humanPlayer.getPoints() > highestPoints) {
            return "you!";
        } else if (humanPlayer.getPoints() == highestPoints) {
            return "a draw!";
        } else {
            return "CP"+computer;
        }
    }

    public Computer[] getComputerPlayers(){
        return computerPlayers;
    }
}
