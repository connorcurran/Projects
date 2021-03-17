import static org.junit.Assert.assertEquals;
import cs3500.pyramidsolitaire.model.hw02.SUIT;
import org.junit.Test;

/**
 * Tests the methods of the SUIT enum.
 */
public class SuitTest {

  /**
   * Tests the getSymbol method of the SUIT enum.
   */
  @Test
  public void testGetSymbol() {
    assertEquals(SUIT.Hearts.getSymbol(), '♥');
    assertEquals(SUIT.Diamonds.getSymbol(), '♦');
    assertEquals(SUIT.Clubs.getSymbol(), '♣');
    assertEquals(SUIT.Spades.getSymbol(), '♠');
  }
}
