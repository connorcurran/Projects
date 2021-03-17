package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;

/**
 * A relaxed version of PyramidSolitaire wherein a card only has to be exposed from one direction
 *  i.e. only one of the cards below it is removed in order to be removable.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire {

  /**
   * Determines whether the identified card is exposed according to the relaxed rules.
   * @param row the 0 indexed row of the card being examined
   * @param card the 0 indexed position in the row of the card being examined
   * @return whether the card is exposed.
   * @throws IllegalArgumentException if the entered index is out of bounds.
   */
  @Override
  protected boolean exposed(int row, int card) throws IllegalArgumentException {
    if (row >= this.numRows || card >= getRowWidth(row) || row < 0 || card < 0) {
      throw new IllegalArgumentException("Index out of bounds!");
    }
    if (row == this.numRows - 1) {
      return true;
    }
    return (board.get(row + 1).get(card) == null
        || board.get(row + 1).get(card + 1) == null);
  }
}