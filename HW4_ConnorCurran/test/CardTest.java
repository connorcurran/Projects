import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw02.SUIT;
import cs3500.pyramidsolitaire.model.hw02.VALUE;
import org.junit.Test;
import org.junit.Before;

/**
 * Tests methods of the Card class.
 */
public class CardTest {
  private ICard aOH;
  private ICard aOD;
  private ICard kOD;
  private ICard aOH2;

  /**
   * Initializes the fields before running tests.
   */
  @Before
  public void initialize() {
    aOH = new Card(VALUE.ACE, SUIT.Hearts);
    aOD = new Card(VALUE.ACE, SUIT.Diamonds);
    kOD = new Card(VALUE.KING, SUIT.Diamonds);
    aOH2 = new Card(VALUE.ACE, SUIT.Hearts);
  }

  /**
   * Tests the getValue() method.
   */
  @Test
  public void testGetValue() {
    assertEquals(1, aOH.getValue());
    assertEquals(1, aOD.getValue());
    assertEquals(13, kOD.getValue());
  }

  /**
   * Tests the equals() method.
   */
  @Test
  public void testEquals() {
    assertTrue(aOH.equals(aOH2));
    assertTrue(aOH.equals(aOH));
    assertFalse(aOD.equals(kOD));
    assertFalse(aOH.equals(" "));
  }

  /**
   * Tests the hashCode() method.
   */
  @Test
  public void testHashcode() {
    assertEquals(aOH.hashCode(), aOH2.hashCode());
    assertEquals(aOH.hashCode(), aOH.hashCode());
    assertNotEquals(aOH.hashCode(), aOD.hashCode());
    assertNotEquals(kOD.hashCode(), aOD.hashCode());
  }

  /**
   * Tests the toString() method.
   */
  @Test
  public void testToString() {
    assertEquals(aOH.toString(), "A♥");
    assertEquals(aOD.toString(), "A♦");
    assertEquals(kOD.toString(), "K♦");
  }
}
