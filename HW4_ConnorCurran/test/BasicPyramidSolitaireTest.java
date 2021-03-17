import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.SUIT;
import cs3500.pyramidsolitaire.model.hw02.VALUE;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Before;

/**
 * Contains tests for the methods of BasicPyramidSolitaire.
 */
public class BasicPyramidSolitaireTest {
  private PyramidSolitaireModel<ICard> model;
  private PyramidSolitaireModel<ICard> model2;

  /**
   * Initializes the fields necessary for testing.
   */
  @Before
  public void initialize() {
    model = new BasicPyramidSolitaire();
    model2 = new BasicPyramidSolitaire();
    this.model.startGame(this.model.getDeck(), false, 3, 3);
    this.model2.startGame(this.model.getDeck(), false, 7, 3);
  }

  /**
   * Tests that the empty constructor correctly initializes numRows and numDraw to -1.
   */
  @Test
  public void testEmptyConstructor() {
    PyramidSolitaireModel<ICard> newModel = new BasicPyramidSolitaire();
    assertEquals(newModel.getNumRows(), -1);
    assertEquals(newModel.getNumDraw(), -1);
  }

  /**
   * Checks that an invalid deck:.
   * @throws IllegalArgumentException when invalid.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeck() {
    List<ICard> deck = new ArrayList<ICard>();
    deck = model.getDeck();
    deck.remove(0);
    model.startGame(deck, false, 3, 3);
  }

  /**
   * Checks that a null deck:.
   * @throws IllegalArgumentException when null
   */
  @Test (expected = IllegalArgumentException.class)
  public void testStartGameNullDeck() {
    model.startGame(null, false, 3, 3);
  }

  /**
   * Tests that startGame correctly creates the draw deck when numRows and numDraw valid.
   */
  @Test
  public void testStartGameValidDeck() {
    model.startGame(model.getDeck(), false, 3, 3);
    List<ICard> deck = new ArrayList<ICard>();
    deck.add(new Card(VALUE.SEVEN, SUIT.Hearts));
    deck.add(new Card(VALUE.EIGHT, SUIT.Hearts));
    deck.add(new Card(VALUE.NINE, SUIT.Hearts));
    assertEquals(model.getDrawCards().subList(0, 3), deck);
  }

  /**
   * Tests that nonPositive numRows:.
   * @throws IllegalArgumentException when nonPositive
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNonPositiveNumRows() {
    model2.startGame(model2.getDeck(), false, -1, 3);
  }

  /**
   * Tests that a negative numDraw:.
   * @throws IllegalArgumentException when negative
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNegativeNumDraw() {
    model2.startGame(model2.getDeck(), false, 3, -1);
  }

  /**
   * Tests that an IllegalArgumentException is thrown when a full pyramid and draw pile cannot be
   * dealt.
   * @throws IllegalArgumentException when full pyramid and draw pile cannot be dealt.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testCannotDeal() {
    model.startGame(model.getDeck(), false, 9, 8);
  }

  /**
   * Tests that the deck is correctly shuffled when shuffle is true.
   */
  @Test
  public void testShuffle() {
    model.startGame(model.getDeck(), false, 3, 3);
    model2.startGame(model.getDeck(), false, 3, 3);
    assertEquals(model2.getDrawCards(), model.getDrawCards());
    model.startGame(model.getDeck(), true, 3, 3);
    assertNotEquals(model.getDrawCards(), model2.getDrawCards());
  }

  /**
   * Tests that getDeck correctly returns a valid deck.
   */
  @Test
  public void getDeckValid() {
    List<ICard> deck = model.getDeck();
    ICard search;
    boolean output = true;
    for (SUIT s : SUIT.values()) {
      for (VALUE v : VALUE.values()) {
        search = new Card(v, s);
        if (! (deck.contains(search))) {
          output = false;
        }
      }
    }
    assertTrue(output);
  }

  /**
   * Tests that an unexposed card cannot be removed.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRemoveUnexposed() {
    model2.remove(5, 5, 5, 2);
  }

  /**
   * Tests that cards not adding to 13 cannot be removed.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRemoveNot13() {
    model2.remove(6, 0, 6, 1);
  }

  /**
   * Tests that an unstarted game cannot have remove called.
   */
  @Test (expected = IllegalStateException.class)
  public void testRemoveUnstarted() {
    PyramidSolitaireModel<ICard> model3 = new BasicPyramidSolitaire();
    model3.remove(0, 0);
  }

  /**
   * Tests that an out of bound remove selection:.
   * @throws IllegalArgumentException when removing.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRemoveOutOfBoundsAbove() {
    model2.remove(-1, -1, 3, 3);
  }

  /**
   * Tests that an out of bound remove selection:.
   * @throws IllegalArgumentException when removing below.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRemoveOutOfBoundsBelow() {
    model2.remove(7, 7, 3, 3);
  }

  /**
   * Tests that all remove functions correctly remove cards.
   */
  @Test
  public void testCorrectRemove() {
    model.removeUsingDraw(1, 2, 1);
    assertNull(model.getCardAt(2, 1));
    assertEquals(model.getDrawCards().get(1), new Card(VALUE.NINE, SUIT.Hearts));
    model2.remove(6,4);
    assertNull(model2.getCardAt(6, 4));
    model2.remove(6, 3,6, 5);
    assertNull(model2.getCardAt(6, 3));
    assertNull(model2.getCardAt(6, 5));
  }

  /**
   * Tests that remove cannot remove a card that is not a king.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNot13OneCard() {
    model.remove(2,2);
  }

  /**
   * Tests that remove cannot work if game not started.
   */
  @Test (expected = IllegalStateException.class)
  public void testUnstartedOneCard() {
    PyramidSolitaireModel<ICard> model3 = new BasicPyramidSolitaire();
    model3.remove(0,0);
  }

  /**
   * Tests that an upper out of bounds call:.
   * @throws IllegalArgumentException when called.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testUpperOutOfBoundsOneCard() {
    model.remove(-1,-1);
  }

  /**
   * Tests that a lower out of bounds call:.
   * @throws IllegalArgumentException when called.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLowerOutOfBoundsOneCard() {
    model.remove(3,3);
  }

  /** Tests that a right out of bounds call:.
   * @throws IllegalArgumentException when called.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRightOfBoundsOneCard() {
    model.remove(0,1);
  }

  /** Tests that a right out of bounds call:.
   * @throws IllegalArgumentException when called.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLeftOfBoundsOneCard() {
    model.remove(0, -1);
  }

  /**
   * Tests that an unexposed card cannot be removed.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testUnexposedRemoveOneCard() {
    model2.remove(4, 2);
  }

  /**
   * Tests that an unexposed card cannot be removed using draw.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testUnexposedRemoveDraw() {
    model.discardDraw(0);
    model.discardDraw(0);
    model.discardDraw(0);
    model.removeUsingDraw(2, 1, 1);
  }

  /**
   * Tests that an unstarted game cannot use removeUsingDraw.
   */
  @Test (expected = IllegalStateException.class)
  public void testUnstartedRemoveDraw() {
    PyramidSolitaireModel<ICard> unstarted = new BasicPyramidSolitaire();
    unstarted.removeUsingDraw(0, 0, 0);
  }

  /**
   * Tests that cards not adding to 13 cannot be removed using draw.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNot13RemoveDraw() {
    model.removeUsingDraw(0, 2, 1);
  }

  /**
   * Tests that card out of bound above cannot be removed.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsAboveRemoveDraw() {
    model.removeUsingDraw(0, -1, -1);
  }

  /**
   * Tests that card out of bound below cannot be removed.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsBelowRemoveDraw() {
    model.removeUsingDraw(0, 3, 3);
  }

  /**
   * Tests that card out of draw visibility cannot be removed.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsDrawRemoveDraw() {
    model.removeUsingDraw(3, 2, 0);
  }

  /**
   * Tests that discard draw:.
   * @throws IllegalStateException if game not started.
   */
  @Test (expected = IllegalStateException.class)
  public void testUnstartedDiscard() {
    PyramidSolitaireModel<ICard> unstarted = new BasicPyramidSolitaire();
    unstarted.discardDraw(0);
  }

  /**
   * Tests that discard draw:.
   * @throws IllegalArgumentException when drawIndex is >= numDraw
   */
  @Test (expected = IllegalArgumentException.class)
  public void testUnexposedDrawDiscard() {
    model.discardDraw(3);
  }

  /**
   * Tests that discard draw:.
   * @throws IllegalArgumentException when drawIndex is < 0.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNegativeDrawIndexDiscard() {
    model.discardDraw(-1);
  }

  /**
   * Tests that discardDraw correctly removes a draw card and cycles a new one in.
   */
  @Test
  public void testDiscardDrawCorrect() {
    assertEquals(model.getDrawCards().get(0).getValue(), 7);
    model.discardDraw(0);
    assertEquals(model.getDrawCards().get(0).getValue(), 8);
    assertEquals(model.getDrawCards().get(1).getValue(), 9);
    model.discardDraw(1);
    assertEquals(model.getDrawCards().get(1).getValue(), 10);
    assertEquals(model.getDrawCards().get(2).getValue(), 11);
    model.discardDraw(2);
    assertEquals(model.getDrawCards().get(2).getValue(), 12);
  }

  /**
   * Tests that getNumRows correctly returns the height of the pyramid.
   */
  @Test
  public void testNumRows() {
    assertEquals(model.getNumRows(), 3);
    assertEquals(model2.getNumRows(), 7);
    PyramidSolitaireModel<ICard> unstarted = new BasicPyramidSolitaire();
    assertEquals(unstarted.getNumRows(), -1);
  }

  /**
   * Tests that getNumDraw correctly returns the exposed draw cards,
   * or -1 if the game is unstarted.
   */
  @Test
  public void testNumDraw() {
    assertEquals(model.getNumDraw(), 3);
    assertEquals(model2.getNumDraw(), 3);
    PyramidSolitaireModel<ICard> unstarted = new BasicPyramidSolitaire();
    assertEquals(unstarted.getNumDraw(), -1);
    model.startGame(model.getDeck(), false, 3, 4);
    assertEquals(model.getNumDraw(), 4);
  }

  /**
   * Tests that getRowWidth:.
   * @throws IllegalStateException when game unstarted.
   */
  @Test (expected = IllegalStateException.class)
  public void testUnstartedWidth() {
    PyramidSolitaireModel<ICard> un = new BasicPyramidSolitaire();
    un.getRowWidth(0);
  }

  /**
   * Tests that getRowWidth throws IllegalArgumentException if row < 0.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNegativeRowWidth() {
    model.getRowWidth(-1);
  }

  /**
   * Tests that getRowWidth throws IllegalArgumentException if row index >= numRows.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsWidth() {
    model.getRowWidth(3);
  }

  /**
   * Tests that getRowWidth functions correctly on valid inputs.
   */
  @Test
  public void testGetRowWidth() {
    assertEquals(model.getRowWidth(0), 1);
    assertEquals(model.getRowWidth(1), 2);
    assertEquals(model.getRowWidth(2), 3);
    assertEquals(model2.getRowWidth(3), 4);
    assertEquals(model2.getRowWidth(4), 5);
    assertEquals(model2.getRowWidth(5), 6);
  }

  /**
   * Tests that isGameOver:.
   * @throws IllegalStateException when game is not started.
   */
  @Test (expected = IllegalStateException.class)
  public void testUnstartedGameOver() {
    PyramidSolitaireModel<ICard> un = new BasicPyramidSolitaire();
    un.isGameOver();
  }

  /**
   * Tests that isGameOver has proper functionality.
   */
  @Test
  public void testIsGameOver() {
    assertFalse(model.isGameOver());
    assertFalse(model2.isGameOver());
    for (int i = 0; i < 46; i++) {
      model.discardDraw(0);
    }
    assertTrue(model.isGameOver());
    ICard kOH = new Card(VALUE.KING, SUIT.Hearts);
    ICard aOH = new Card(VALUE.ACE, SUIT.Hearts);
    List<ICard> deck = new ArrayList<ICard>();
    deck = model.getDeck();
    deck.set(0, kOH);
    deck.set(12, aOH);
    model.startGame(deck, false, 1, 0);
    model.remove(0, 0);
    assertTrue(model.isGameOver());
  }

  /**
   * Tests that getCardAt:.
   * @throws IllegalStateException when game is not started.
   */
  @Test (expected = IllegalStateException.class)
  public void testGetCardUnstarted() {
    PyramidSolitaireModel<ICard> un = new BasicPyramidSolitaire();
    un.getCardAt(0, 0);
  }

  /**
   * Tests that getCardAt:.
   * @throws IllegalArgumentException if row < 0
   */
  @Test (expected = IllegalArgumentException.class)
  public void testGetCardNegRow() {
    model.getCardAt(-1, 0);
  }

  /**
   * Tests that getCardAt:.
   * @throws IllegalArgumentException if card < 0
   */
  @Test (expected = IllegalArgumentException.class)
  public void testGetCardNegCard() {
    model.getCardAt(0, -1);
  }

  /**
   * Tests that getCardAt:.
   * @throws IllegalArgumentException if row > numRows
   */
  @Test (expected = IllegalArgumentException.class)
  public void testGetCardOOBRow() {
    model.getCardAt(3, 0);
  }

  /**
   * Tests that getCardAt:.
   * @throws IllegalArgumentException if card > rowWidth
   */
  @Test (expected = IllegalArgumentException.class)
  public void testGetCardOOBCard() {
    model.getCardAt(0, 1);
  }

  /**
   * Tests that getCardAt correctly finds the specified card.
   */
  @Test
  public void testGetCardAt() {
    ICard aOH = new Card(VALUE.ACE, SUIT.Hearts);
    ICard sOH = new Card(VALUE.SIX, SUIT.Hearts);
    ICard tOD = new Card(VALUE.NINE, SUIT.Clubs);
    assertEquals(model.getCardAt(0, 0), aOH);
    assertEquals(model.getCardAt(2, 2), sOH);
    assertEquals(model2.getCardAt(6, 0), tOD);
  }

  /**
   * Tests that getDrawCards properly:.
   * @throws IllegalStateException when game not started.
   */
  @Test (expected = IllegalStateException.class)
  public void testUnstartedGetDraw() {
    PyramidSolitaireModel<ICard> un = new BasicPyramidSolitaire();
    un.getDrawCards();
  }

  /**
   * Tests that getDrawCards works properly.
   */
  @Test
  public void testGetDrawCards() {
    List<ICard> deck = new ArrayList<ICard>();
    ICard tOD = new Card(VALUE.THREE, SUIT.Diamonds);
    ICard fOD = new Card(VALUE.FOUR, SUIT.Diamonds);
    ICard fiOD = new Card(VALUE.FIVE, SUIT.Diamonds);
    ICard sOH = new Card(VALUE.SEVEN, SUIT.Hearts);
    ICard eOH = new Card(VALUE.EIGHT, SUIT.Hearts);
    ICard nOH = new Card(VALUE.NINE, SUIT.Hearts);
    deck.add(tOD);
    deck.add(fOD);
    deck.add(fiOD);
    assertEquals(model2.getDrawCards(), deck);
    deck.clear();
    deck.add(sOH);
    deck.add(eOH);
    deck.add(nOH);
    assertEquals(model.getDrawCards(), deck);
    deck.clear();
    model.startGame(model.getDeck(), false, 2, 0);
    assertEquals(model.getDrawCards(), deck);
  }
}