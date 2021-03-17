package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents the value of a Card.
 */
public enum VALUE {
  ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
  JACK(11), QUEEN(12), KING(13);

  private final int value;

  VALUE(int value) {
    this.value = value;
  }

  /**
   * Gets the value of the VALUE.
   * @return the value as an int.
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Converts the Value to a string.
   * @return the string as found on a playing card.
   */
  @Override
  public String toString() {
    if (this == VALUE.KING) {
      return "K";
    }
    if (this == VALUE.QUEEN) {
      return "Q";
    }
    if (this == VALUE.JACK) {
      return "J";
    }
    if (this == VALUE.ACE) {
      return "A";
    }
    return Integer.toString(this.value);
  }
}
