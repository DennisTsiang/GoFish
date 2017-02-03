import java.io.IOException;

public class Main {

    private final static int MAX_COMPUTER_PLAYERS = 3;

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Go Fish!");
        System.out.println("Please enter the number of computer players (1-" + MAX_COMPUTER_PLAYERS + "):");
        int compPlayers = IO.getNumberFromUser(MAX_COMPUTER_PLAYERS);
        Deck deck = new Deck();
        deck.riffleShuffle();
        deck.shuffle();
        Game game = new Game(deck, compPlayers, new Player());

        //Deal cards
        game.dealCards();
        while (!game.gameFinished()) {
            //Play game
            game.printLeaderboard();
            game.playerTurn();
            game.computerTurn();
        }
        System.out.println("Game over! The winner is:"+game.getWinner());
    }
}
