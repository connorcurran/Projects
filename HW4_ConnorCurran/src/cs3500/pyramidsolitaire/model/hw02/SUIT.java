package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents a suit of a card.
 */
public enum SUIT {
  Hearts('♥'), Clubs('♣'), Diamonds('♦'), Spades('♠');

  private final char symbol;

  SUIT(char c) {
    this.symbol = c;
  }

  /**
   * Returns the symbol of the card.
   * @return the symbol of the card.
   */
  public char getSymbol() {
    return this.symbol;
  }
}