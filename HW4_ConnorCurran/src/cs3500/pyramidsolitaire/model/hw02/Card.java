package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * Represents a Card in a solitaire game.
 */
public class Card implements ICard {
  private final VALUE value;
  private final SUIT suit;

  public Card(VALUE value, SUIT suit) {
    this.value = value;
    this.suit = suit;
  }

  /**
   * Gets the value.
   * @return the value of the card.
   */
  @Override
  public int getValue() {
    return value.getValue();
  }

  /**
   * Gets the suit of the card.
   * @return the suit in symbol form of the card.
   */
  @Override
  public char getSuit() {
    return this.suit.getSymbol();
  }

  /**
   * Does the input object equal this object.
   * @param o the input object
   * @return whether the input object equals this.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (! (o instanceof Card)) {
      return false;
    }

    Card that = (Card) o;

    return this.value == that.value
        && this.suit == that.suit;
  }

  /**
   * Produces a unique hashCode.
   * @return the unique hashCode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.suit, this.value);
  }

  /**
   * Converts the Card to a string.
   * @return the string identifier.
   */
  @Override
  public String toString() {
    return this.value.toString() + this.suit.getSymbol();
  }
}
