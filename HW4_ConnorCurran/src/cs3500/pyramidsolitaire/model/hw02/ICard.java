package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents a card in the Pyramid Solitaire Model.
 */
public interface ICard {
  /**
   * Gets the value of the card.
   * @return the value as an int.
   */
  int getValue();

  /**
   * Get the suit of the card.
   * @return the suit as a char symbol.
   */
  char getSuit();
}
