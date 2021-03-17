import static org.junit.Assert.assertEquals;
import cs3500.pyramidsolitaire.model.hw02.VALUE;
import org.junit.Test;

/**
 * Test the methods of the VALUE enum.
 */
public class ValueTest {
  /**
   * Gets the integer value of the VALUE.
   */
  @Test
  public void testGetValue() {
    assertEquals(VALUE.ACE.getValue(), 1);
    assertEquals(VALUE.KING.getValue(), 13);
    assertEquals(VALUE.NINE.getValue(), 9);
  }

  /**
   * Turns the VALUE into a one character String.
   */
  @Test
  public void testToString() {
    assertEquals(VALUE.ACE.toString(), "A");
    assertEquals(VALUE.JACK.toString(), "J");
    assertEquals(VALUE.QUEEN.toString(), "Q");
    assertEquals(VALUE.KING.toString(), "K");
    assertEquals(VALUE.FOUR.toString(), "4");
  }
}
