import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.SUIT;
import cs3500.pyramidsolitaire.model.hw02.VALUE;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Before;

/**
 * Tests the PyramidSolitaireTextualView class.
 */
public class ViewTest {
  private final PyramidSolitaireModel<ICard> model = new BasicPyramidSolitaire();
  private final PyramidSolitaireModel<ICard> model2 = new BasicPyramidSolitaire();
  private final PyramidSolitaireModel<ICard> model3 = new BasicPyramidSolitaire();
  private final PyramidSolitaireModel<ICard> model4 = new BasicPyramidSolitaire();
  private final PyramidSolitaireModel<ICard> model5 = new BasicPyramidSolitaire();
  private PyramidSolitaireTextualView view;
  private PyramidSolitaireTextualView view2;
  private PyramidSolitaireTextualView view3;
  private PyramidSolitaireTextualView view4;
  private PyramidSolitaireTextualView view5;


  /**
   * Initializes fields for the test.
   */
  @Before
  public void initialize() {
    model.startGame(model.getDeck(), false, 7, 3);
    view3 = new PyramidSolitaireTextualView(model);
    model2.startGame(model2.getDeck(), false, 7, 3);
    model2.remove(6,4);
    model2.discardDraw(2);
    model2.removeUsingDraw(0, 6,1);
    model2.remove(6,2,6,6);
    model2.remove(6, 3, 6, 5);
    model2.remove(5,3,5,4);
    view = new PyramidSolitaireTextualView(model2);
    this.model3.startGame(this.model3.getDeck(), false, 3, 1);
    this.model5.startGame(this.model5.getDeck(), false, 3, 0);
    view5 = new PyramidSolitaireTextualView(model5);
    view2 = new PyramidSolitaireTextualView(model3);
    view4 = new PyramidSolitaireTextualView(model4);
  }

  @Test
  public void testView7row() {
    assertEquals(view.toString(),
        "            A♥\n"
        + "          2♥  3♥\n"
        + "        4♥  5♥  6♥\n"
        + "      7♥  8♥  9♥  10♥\n"
        + "    J♥  Q♥  K♥  A♣  2♣\n"
        + "  3♣  4♣  5♣  .   .   8♣\n"
        + "9♣  .   .   .   .   .   .\n"
        + "Draw: 4♦, 6♦, 7♦");
  }

  @Test
  public void testView2row() {
    assertEquals(view2.toString(),
        "    A♥\n"
            + "  2♥  3♥\n"
            + "4♥  5♥  6♥\n"
            + "Draw: 7♥");
  }

  @Test
  public void testView3() {
    assertEquals(view3.toString(),
        "            A♥\n"
            + "          2♥  3♥\n"
            + "        4♥  5♥  6♥\n"
            + "      7♥  8♥  9♥  10♥\n"
            + "    J♥  Q♥  K♥  A♣  2♣\n"
            + "  3♣  4♣  5♣  6♣  7♣  8♣\n"
            + "9♣  10♣ J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦");
  }

  @Test
  public void unstartedView() {
    assertEquals("", view4.toString());
  }

  @Test
  public void gameWonView() {
    ICard kOH = new Card(VALUE.KING, SUIT.Hearts);
    ICard aOH = new Card(VALUE.ACE, SUIT.Hearts);
    List<ICard> deck = new ArrayList<ICard>();
    deck = model.getDeck();
    deck.set(0, kOH);
    deck.set(12, aOH);
    model.startGame(deck, false, 1, 0);
    model.remove(0, 0);
    view = new PyramidSolitaireTextualView(model);
    assertEquals(view.toString(), "You win!");
  }

  @Test
  public void gameOverView() {
    assertEquals(view5.toString(), "Game over. Score: 21");
  }
}