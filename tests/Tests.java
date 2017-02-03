import org.junit.Test;
import static org.junit.Assert.*;

public class Tests {

    @Test
    public void riffleShuffleTest() {
        Deck unshuffledDeck = new Deck();
        Deck shuffledDeck = new Deck();
        //Eight riffle shuffles should restore it back to the original order
        for(int i=0; i < 8; i++) {
            shuffledDeck.riffleShuffle();
        }
        try {
            assertTrue(unshuffledDeck.equals(shuffledDeck));
        } catch (AssertionError ae) {
            shuffledDeck.printDeck();
            System.out.println("*********************************************************");
            unshuffledDeck.printDeck();
            assert (false);
        }
    }

    @Test
    public void computerWithEmptyHandWillDrawFromDeck() {
        Deck deck = new Deck();
        Game game = new Game(deck, 1, new Player());
        game.computerTurn();
        Computer computers[] = game.getComputerPlayers();
        assertEquals(computers[0].getHandSize(), 1);
    }

    @Test
    public void whenAllCardsHaveGoneGameIsFinished() {
        Deck deck = new Deck();
        for( int i=0; i < 52; i++) {
            //remove all cards from deck
            deck.getTopCard();
        }
        Game game = new Game(deck, 1, new Player());
        assertTrue(game.gameFinished());

    }

    @Test
    public void whenHandNotEmptyGameNotFinished() {
        Deck deck = new Deck();
        Game game = new Game(deck, 1, new Player());
        game.dealCards();
        for( int i=0; i < 42; i++) {
            deck.getTopCard();
        }
        assertFalse(game.gameFinished());
    }

    @Test
    public void callingSelectCardOnEmptyHandReturnsNullAndCausesPlayerToDrawCardFromDeck() {
        Player humanPlayer = new Player();
        Deck deck = new Deck();
        Game game = new Game(deck, 1, humanPlayer);
        Computer computers[] = game.getComputerPlayers();
        //Computer draws card
        game.computerTurn();
        assertEquals(computers[0].getHandSize(), 1);
        //Computer asks card
        game.computerTurn();
        assertEquals(computers[0].getHandSize(), 2);

    }
}
