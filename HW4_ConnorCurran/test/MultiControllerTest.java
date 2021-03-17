import static org.junit.Assert.assertEquals;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that the controller correctly interacts with a MultiPyramidSolitaire.
 */
public class MultiControllerTest {
  PyramidSolitaireController controller;
  StringReader reader;
  PyramidSolitaireModel<ICard> model;
  StringBuilder stringBuilder;
  PyramidSolitaireView view;

  /**
   * Initializes fields to be used by the test methods.
   */
  @Before
  public void initialize() {
    model = new MultiPyramidSolitaire();
    stringBuilder = new StringBuilder();
    reader = new StringReader("");
    controller = new PyramidSolitaireTextualController(reader, stringBuilder);
    view = new PyramidSolitaireTextualView(model, stringBuilder);
  }

  /**
   * Tests that discardDraw only accepts the draw number if entered incorrectly originally.
   */
  @Test
  public void testValidDiscard() {
    reader = new StringReader("dd kfdjkdjf 1");
    controller = new PyramidSolitaireTextualController(reader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    assertEquals(stringBuilder.toString(), "            A♥  .   .   A♥  .   .   2♥\n"
        + "          2♥  3♥  .   3♥  4♥  .   4♥  5♥\n"
        + "        5♥  6♥  6♥  7♥  7♥  8♥  8♥  9♥  9♥\n"
        + "      10♥ 10♥ J♥  J♥  Q♥  Q♥  K♥  K♥  A♣  A♣\n"
        + "    2♣  2♣  3♣  3♣  4♣  4♣  5♣  5♣  6♣  6♣  7♣\n"
        + "  7♣  8♣  8♣  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣  K♣\n"
        + "K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦  4♦  5♦  5♦  6♦  6♦\n"
        + "Draw: 7♦, 7♦, 8♦\n"
        + "Score: 406\n"
        + "Invalid input. Must be q, Q, or a natural number!\n"
        + "            A♥  .   .   A♥  .   .   2♥\n"
        + "          2♥  3♥  .   3♥  4♥  .   4♥  5♥\n"
        + "        5♥  6♥  6♥  7♥  7♥  8♥  8♥  9♥  9♥\n"
        + "      10♥ 10♥ J♥  J♥  Q♥  Q♥  K♥  K♥  A♣  A♣\n"
        + "    2♣  2♣  3♣  3♣  4♣  4♣  5♣  5♣  6♣  6♣  7♣\n"
        + "  7♣  8♣  8♣  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣  K♣\n"
        + "K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦  4♦  5♦  5♦  6♦  6♦\n"
        + "Draw: 7♦, 8♦, 8♦\n"
        + "Score: 406\n");
  }

  /**
   * Tests that qQ does not quit, that the scanner correctly continues
   * reading after an invalid input, and that rmwd and rm1 correctly remove cards.
   */
  @Test
  public void testValidRemove() {
    reader = new StringReader("rmwd 2 qQ 3 2 rm1 7 5");
    controller = new PyramidSolitaireTextualController(reader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals(stringBuilder.toString(),
        "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  5♥  6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 42\n"
            + "Invalid input. Must be q, Q, or a natural number!\n"
            + "Invalid move. Play again. Card(s) out of bounds or unable to be removed\n"
            + "Invalid move. Play again. Card out of bounds or unable to be removed\n");
  }

  /**
   * Tests that invalid inputs are correctly skipped, and that
   *  rm1, dd, and rm2 correctly remove cards.
   */
  @Test
  public void testValidRemove2() {
    reader = new StringReader("rm1 7 -1 a 5 dd 1 rm2 7 4 7 6");
    controller = new PyramidSolitaireTextualController(reader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    assertEquals(stringBuilder.toString(),
        "            A♥  .   .   A♥  .   .   2♥\n"
            + "          2♥  3♥  .   3♥  4♥  .   4♥  5♥\n"
            + "        5♥  6♥  6♥  7♥  7♥  8♥  8♥  9♥  9♥\n"
            + "      10♥ 10♥ J♥  J♥  Q♥  Q♥  K♥  K♥  A♣  A♣\n"
            + "    2♣  2♣  3♣  3♣  4♣  4♣  5♣  5♣  6♣  6♣  7♣\n"
            + "  7♣  8♣  8♣  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣  K♣\n"
            + "K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦  4♦  5♦  5♦  6♦  6♦\n"
            + "Draw: 7♦, 7♦, 8♦\n"
            + "Score: 406\n"
            + "Invalid input. Must be q, Q, or a natural number!\n"
            + "Invalid input. Must be q, Q, or a natural number!\n"
            + "Invalid move. Play again. Card out of bounds or unable to be removed\n"
            + "            A♥  .   .   A♥  .   .   2♥\n"
            + "          2♥  3♥  .   3♥  4♥  .   4♥  5♥\n"
            + "        5♥  6♥  6♥  7♥  7♥  8♥  8♥  9♥  9♥\n"
            + "      10♥ 10♥ J♥  J♥  Q♥  Q♥  K♥  K♥  A♣  A♣\n"
            + "    2♣  2♣  3♣  3♣  4♣  4♣  5♣  5♣  6♣  6♣  7♣\n"
            + "  7♣  8♣  8♣  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣  K♣\n"
            + "K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦  4♦  5♦  5♦  6♦  6♦\n"
            + "Draw: 7♦, 8♦, 8♦\n"
            + "Score: 406\n"
            + "Invalid move. Play again. Card(s) out of bounds or unable to be removed\n");
  }

  /**
   * Checks that a command is skipped if all of its arguments are
   *  accepted and then the model decides it is invalid, and that it
   *  continues on to the next command.
   */
  @Test
  public void testCommandResets() {
    reader = new StringReader("dd 4 1 rmwd 2 3 2");
    controller = new PyramidSolitaireTextualController(reader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals(stringBuilder.toString(),
        "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  5♥  6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 42\n"
            + "Invalid move. Play again. Card out of bounds or unable to be removed\n"
            + "Invalid move. Play again. Card(s) out of bounds or unable to be removed\n");
  }

  /**
   * Tests that a null model in playGame correctly throws an IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNullModel() {
    controller.playGame(null, model.getDeck(), false, 3, 3);
  }

  /**
   * Tests that a null deck correctly throws an IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNullDeck() {
    controller.playGame(model, null, false, 3, 3);
  }

  /**
   * Tests that an unstartable game in the controller correctly throws an
   *  illegalStateException.
   */
  @Test (expected = IllegalStateException.class)
  public void testUnstartedGame() {
    controller.playGame(model, model.getDeck(), false, -3, 1);
  }

  /**
   * Tests that q correctly quits the game.
   */
  @Test
  public void testQuitq() {
    reader = new StringReader("q rm 2 3 2");
    controller = new PyramidSolitaireTextualController(reader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals(stringBuilder.toString(),
        "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  5♥  6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 42\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  5♥  6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 42\n");
  }

  /**
   * Tests that Q correctly quits the game and is correctly read after another command.
   */
  @Test
  public void testQuitQ() {
    reader = new StringReader("rm 1 Q");
    controller = new PyramidSolitaireTextualController(reader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals(stringBuilder.toString(),
        "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  5♥  6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 42\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  5♥  6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 42\n");
  }

  /**
   * Checks that Q correctly quits the game when in the first position
   *  after the command.
   */
  @Test
  public void testQuitQRow() {
    reader = new StringReader("rm Q 1");
    controller = new PyramidSolitaireTextualController(reader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals(stringBuilder.toString(),
        "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  5♥  6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 42\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  5♥  6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 42\n");
  }

  /**
   * Tests that an invalid move is correctly declared after an invalid rm1.
   */
  @Test
  public void testInvalidRM1() {
    reader = new StringReader("rm1 3 3");
    controller = new PyramidSolitaireTextualController(reader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals(stringBuilder.toString(),
        "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  5♥  6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 42\n"
            + "Invalid move. Play again. Card out of bounds or unable to be removed\n");
  }

  /**
   * Tests that an invalid move is correctly declared after an invalid rm2.
   */
  @Test
  public void testInvalidRM2() {
    reader = new StringReader("rm2 3 13 3 2");
    controller = new PyramidSolitaireTextualController(reader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals(stringBuilder.toString(),
        "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  5♥  6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 42\n"
            + "Invalid move. Play again. Card(s) out of bounds or unable to be removed\n");
  }

  /**
   * Tests that an invalid move is correctly declared after an invalid rmwd.
   */
  @Test
  public void testInvalidRMWD() {
    reader = new StringReader("rmwd 3 3 3");
    controller = new PyramidSolitaireTextualController(reader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 3, 3);
    assertEquals(stringBuilder.toString(),
        "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  5♥  6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 42\n"
            + "    A♥  A♥  2♥\n"
            + "  2♥  3♥  3♥  4♥\n"
            + "4♥  5♥  .   6♥  6♥\n"
            + "Draw: 7♥, 7♥, 8♥\n"
            + "Score: 37\n");
  }
}