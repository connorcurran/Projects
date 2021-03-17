import static org.junit.Assert.assertNotEquals;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import org.junit.Before;
import org.junit.Test;

/**
 * A class for testing the new functionality of RelaxedPyramidSolitaire.
 */
public class RelaxedPyramidSolitaireTest {
  PyramidSolitaireModel<ICard> model = new RelaxedPyramidSolitaire();

  /**
   * Initializes the preconditions for the tests.
   */
  @Before
  public void initialize() {
    model.startGame(model.getDeck(), false, 7, 3);
  }

  /**
   * Tests that a card exposed on only one side can be removed in this version.
   */
  @Test
  public void testExposed() {
    PyramidSolitaireModel<ICard> model2 = new RelaxedPyramidSolitaire();
    model2.startGame(model.getDeck(), false, 7, 3);
    model.remove(6, 4);
    model.discardDraw(0);
    model.removeUsingDraw(2, 5, 4);
    assertNotEquals(model2, model);
  }

  /**
   * Tests that an out of bounds (high) row throws IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsHighRow() {
    model.remove(7, 1);
  }

  /**
   * Tests that a negative row throws IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNegRow() {
    model.remove(-1, 1);
  }

  /**
   * Tests that an out of bounds (high) card throws IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testOOBCard() {
    model.remove(0, 1);
  }

  /**
   * Tests that a negative card throws IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNegCard() {
    model.remove(0, -1);
  }

}
