import static org.junit.Assert.assertEquals;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.SUIT;
import cs3500.pyramidsolitaire.model.hw02.VALUE;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * A class to test the new functionality of MultiPyramidSolitaire.
 */
public class MultiPyramidSolitaireTest {
  PyramidSolitaireModel<ICard> model;
  PyramidSolitaireModel<ICard> model2;
  PyramidSolitaireModel<ICard> model3;

  /**
   * Initializes preconditions for the test methods.
   */
  @Before
  public void initialize() {
    model = new MultiPyramidSolitaire();
    model.startGame(model.getDeck(), false, 3, 3);
    model2 = new MultiPyramidSolitaire();
    model2.startGame(model2.getDeck(), false, 2, 2);
    model3 = new MultiPyramidSolitaire();
  }

  /**
   * Tests that startGame correctly throws an IllegalArgumentException if
   *  an invalid deck is entered (essentially tests the new implementation of validDeck).
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInValidDeck() {
    ICard c = new Card(VALUE.NINE, SUIT.Hearts);
    List<ICard> deck = model.getDeck();
    deck.add(c);
    deck.remove(0);
    model.startGame(deck, false, 3,3);
  }

  /**
   * Tests that startGame correctly throws an IllegalArgument exception when a board
   *  too large is entered.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testTooLargeBoard() {
    model3.startGame(model3.getDeck(), false, 9, 1);
  }

  /**
   * Tests that getDeck correctly returns a deck sized 104, that contains 2 copies
   *  of a normal deck of cards, and that getDeck() returns the same deck regardless
   *  of the instance of MultiPyramidSolitaire.
   */
  @Test
  public void testGetDeck() {
    List<ICard> deck = model.getDeck();
    assertEquals(deck.size(), 104);
    ICard search;
    boolean result = true;
    for (int i = 0; i < 2; i++) {
      for (SUIT s : SUIT.values()) {
        for (VALUE v : VALUE.values()) {
          search = new Card(v, s);
          if (!(deck.contains(search))) {
            result = false;
          } else {
            deck.remove(search);
          }
        }
      }
    }
    assertEquals(true, result);
    assertEquals(model.getDeck(), model2.getDeck());
  }

  /**
   * Tests that getRowWidth correctly returns the width of different rows on odd and even
   *  numRows models.
   */
  @Test
  public void testGetRowWidth() {
    assertEquals(model.getRowWidth(0), 3);
    assertEquals(model.getRowWidth(1), 4);
    assertEquals(model.getRowWidth(2), 5);
    assertEquals(model2.getRowWidth(0), 3);
    assertEquals(model2.getRowWidth(1), 4);
  }

  /**
   * Tests that a negative row in getRowWidth correctly throws an IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNegativeRowGetWidth() {
    model.getRowWidth(-1);
  }

  /**
   * Tests that an out of bounds row in getRowWidth correctly throws an IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRowGetWidth() {
    model.getRowWidth(3);
  }

  /**
   * Tests that the new BuildBoard (through startGame) correctly adds empty cards and
   *  overall works properly.
   */
  @Test
  public void testBuildBoard() {
    List<ICard> deck = model.getDeck();
    PyramidSolitaireView view = new PyramidSolitaireTextualView(model3);
    model3.startGame(deck, false, 5, 3);
    assertEquals(view.toString(),
        "        A♥  .   A♥  .   2♥\n"
        + "      2♥  3♥  3♥  4♥  4♥  5♥\n"
        + "    5♥  6♥  6♥  7♥  7♥  8♥  8♥\n"
        + "  9♥  9♥  10♥ 10♥ J♥  J♥  Q♥  Q♥\n"
        + "K♥  K♥  A♣  A♣  2♣  2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣, 5♣, 5♣");
    model3.startGame(deck, false, 7, 3);
    assertEquals(view.toString(), "            A♥  .   .   A♥  .   .   2♥\n"
        + "          2♥  3♥  .   3♥  4♥  .   4♥  5♥\n"
        + "        5♥  6♥  6♥  7♥  7♥  8♥  8♥  9♥  9♥\n"
        + "      10♥ 10♥ J♥  J♥  Q♥  Q♥  K♥  K♥  A♣  A♣\n"
        + "    2♣  2♣  3♣  3♣  4♣  4♣  5♣  5♣  6♣  6♣  7♣\n"
        + "  7♣  8♣  8♣  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣  K♣\n"
        + "K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦  4♦  5♦  5♦  6♦  6♦\n"
        + "Draw: 7♦, 7♦, 8♦");
    model3.startGame(deck, false, 1, 3);
    assertEquals(view.toString(), "A♥\n"
        + "Draw: A♥, 2♥, 2♥");
  }
}
