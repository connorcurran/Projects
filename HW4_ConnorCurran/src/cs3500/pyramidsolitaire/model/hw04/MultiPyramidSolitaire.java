package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw02.SUIT;
import cs3500.pyramidsolitaire.model.hw02.VALUE;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a version of PyramidSolitaire wherein multiple pyramids are overlapping on the board.
 */
public class MultiPyramidSolitaire extends BasicPyramidSolitaire {
  /**
   * Builds a board for this PyramidSolitaire game.
   * @param tempDeck the temporary deck to build this board from.
   */
  @Override
  protected void buildBoard(List<ICard> tempDeck) {
    int mergeRow = (int)Math.floor(numRows / 2.0) - 1;
    int delimiters = 0;
    for (int i = 0; i < numRows; i++) {
      delimiters = mergeRow - i;
      this.board.add(new ArrayList<ICard>());
      for (int j = 0; j < this.getRowWidth(i); j++) {
        if (delimiters >= 0) {
          for (int n = 0; n <= i; n ++) {
            this.board.get(i).add(tempDeck.remove(0));
          }
          for (int n = 0; n < delimiters; n++) {
            this.board.get(i).add(null);
          }
          j += delimiters + i;
        }
        else {
          this.board.get(i).add(tempDeck.remove(0));
        }
      }
    }
  }

  /**
   * Is the input deck valid.
   * @param deck the deck to be examined
   * @return whether the input deck is valid
   * @throws IllegalArgumentException if deck is null
   */
  @Override
  protected boolean validDeck(List<ICard> deck) throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null!");
    }
    List<ICard> tempDeck = new ArrayList<>(deck);
    ICard search;
    for (int i = 0; i < 2; i++) {
      for (SUIT s : SUIT.values()) {
        for (VALUE v : VALUE.values()) {
          search = new Card(v, s);
          if (!(tempDeck.contains(search))) {
            return false;
          } else {
            tempDeck.remove(search);
          }
        }
      }
    }
    return deck.size() == 104;
  }

  /**
   * Returns the width of the requested row, measured from the leftmost card to the rightmost card
   * (inclusive) as the game is initially dealt.
   *
   * @param row the desired row (0-indexed)
   * @return the number of spaces needed to deal out that row
   * @throws IllegalArgumentException if the row is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    this.gameStartedException();
    if (row >= this.numRows || row < 0) {
      throw new IllegalArgumentException("Row is invalid!");
    }
    if (numRows % 2 == 0) {
      row ++;
    }
    return numRows + row;
  }

  /**
   * Return a valid and complete deck of cards for a game of Pyramid Solitaire. There is no
   * restriction imposed on the ordering of these cards in the deck. The validity of the deck is
   * determined by the rules of the specific game in the classes implementing this interface.
   *
   * @return the deck of cards as a list
   */
  @Override
  public List<ICard> getDeck() {
    List<ICard> output = new ArrayList<ICard>();
    for (SUIT s : SUIT.values()) {
      for (VALUE v : VALUE.values()) {
        output.add(new Card(v, s));
        output.add(new Card(v, s));
      }
    }
    return output;
  }

  /**
   * Calculates the total spaces available for cards to be dealt.
   *
   * @param numRows the number of rows in the model.
   * @param numDraw the number of draw cards in the model.
   */
  @Override
  protected int totalSpaces(int numRows, int numDraw) {
    int bottomWidth = getRowWidth(numRows - 1);
    int delimiters = (int)Math.floor(numRows / 2.0) - 1;
    int totalDelimiters = delimiters * (delimiters + 1);
    return bottomWidth * (bottomWidth + 1) / 2 - (numRows * (numRows - 1) / 2) + numDraw
        - totalDelimiters;
  }
}
