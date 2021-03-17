import static org.junit.Assert.assertEquals;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import org.junit.Test;

/**
 * Tests the functionality of the PyramidSolitaireCreator class.
 */
public class PyramidSolitaireCreatorTest {

  /**
   * Tests that a BasicPyramidSolitaire class is correctly created when type = BASIC.
   */
  @Test
  public void testBasicCreate() {
    assertEquals(BasicPyramidSolitaire.class,
        PyramidSolitaireCreator.create(GameType.BASIC).getClass());
  }

  /**
   * Tests that a MultiPyramidSolitaire class is correctly created when type = MULTIPYRAMID.
   */
  @Test
  public void testMultiCreate() {
    assertEquals(MultiPyramidSolitaire.class,
        PyramidSolitaireCreator.create(GameType.MULTIPYRAMID).getClass());
  }

  /**
   * Tests that a RelaxedPyramidSolitaire class is correctly created when type = RELAXED.
   */
  @Test
  public void testRelaxedCreate() {
    assertEquals(RelaxedPyramidSolitaire.class,
        PyramidSolitaireCreator.create(GameType.RELAXED).getClass());
  }

  /**
   * Tests that an IllegalArgumentException is correctly thrown when a null type is entered.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNullType() {
    PyramidSolitaireCreator.create(null);
  }
}
