package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A basic version of the PyramidSolitaire Game.
 * Maintains the game state through a series of methods that alter the board.
 * Cards are represented as a ICard object, which has fields of enum VALUE and SUIT.
 * The Pyramid is represented through a list of arrayLists, the board, and through a
 *  list of ICards, the draw.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<ICard> {
  protected final List<ArrayList<ICard>> board;
  protected final List<ICard> draw;
  protected int numRows;
  protected int numDraw;

  /**
   * Builds a base BasicPyramidSolitaire objects, with numRows and numDraw init to -1.
   */
  public BasicPyramidSolitaire() {
    this.numRows = -1;
    this.numDraw = -1;
    this.board = new ArrayList<ArrayList<ICard>>();
    this.draw = new ArrayList<ICard>();
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
      }
    }
    return output;
  }

  /**
   * <p>Deal a new game of Pyramid Solitaire.
   * The cards to be used and their order are specified by the the given deck, unless the {@code
   * shuffle} parameter indicates the order should be ignored.</p>
   *
   * <p>This method first verifies that the deck is valid. It deals cards in rows
   * (left-to-right, top-to-bottom) into the characteristic pyramid shape with the specified number
   * of rows, followed by the specified number of draw cards. When {@code shuffle} is {@code false},
   * the 0th card in {@code deck} is used as the first card dealt.</p>
   *
   * <p>This method should have no other side effects, and should work for any valid arguments.</p>
   *
   * @param deck    the deck to be dealt
   * @param shuffle if {@code false}, use the order as given by {@code deck}, otherwise use a
   *                randomly shuffled order
   * @param numRows number of rows in the pyramid
   * @param numDraw number of draw cards available at a time
   * @throws IllegalArgumentException if the deck is null or invalid, the number of pyramid rows is
   *                                  non-positive, the number of draw cards available at a time is
   *                                  negative, or a full pyramid and draw pile cannot be dealt with
   *                                  the number of given cards in deck
   */
  @Override
  public void startGame(List<ICard> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null!");
    }
    List<ICard> tempDeck = new ArrayList<ICard>(deck);
    if (! (validDeck(tempDeck))) {
      throw new IllegalArgumentException("Deck is invalid!");
    }
    //null deck checked in validDeck
    if (numRows <= 0) {
      throw new IllegalArgumentException("Number of rows must be positive!");
    }
    if (numDraw < 0) {
      throw new IllegalArgumentException("Number of draw cards available cannot be negative!");
    }
    this.numRows = numRows;
    this.numDraw = numDraw;
    if (deck.size() < totalSpaces(numRows, numDraw)) {
      throw new IllegalArgumentException(("Full deck and draw pile cannot be dealt given size "
          + "of deck!"));
    }
    if (shuffle) {
      Collections.shuffle(tempDeck);
    }
    this.board.clear();
    this.buildBoard(tempDeck);
    this.draw.clear();
    this.draw.addAll(tempDeck);
  }

  /**
   * Calculates the total spaces available for cards to be dealt.
   *
   * @param numRows the number of rows in the model.
   * @param numDraw the number of draw cards in the model.
   */
  protected int totalSpaces(int numRows, int numDraw) {
    return numRows * (numRows + 1) / 2 + numDraw;
  }

  /**
   * Builds a board for this PyramidSolitaire game.
   * @param tempDeck the temporary deck to build this board from.
   */
  protected void buildBoard(List<ICard> tempDeck) {
    for (int i = 0; i < numRows; i++) {
      this.board.add(new ArrayList<ICard>());
      for (int j = 0; j <= i; j++) {
        this.board.get(i).add(tempDeck.remove(0));
      }
    }
  }

  /**
   * Is the input deck valid.
   * @param deck the deck to be examined
   * @return whether the input deck is valid
   * @throws IllegalArgumentException if deck is null
   */
  protected boolean validDeck(List<ICard> deck) throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null!");
    }
    ICard search;
    for (SUIT s : SUIT.values()) {
      for (VALUE v : VALUE.values()) {
        search = new Card(v, s);
        if (! (deck.contains(search))) {
          return false;
        }
      }
    }
    return deck.size() == 52;
  }

  /**
   * Remove two exposed cards on the pyramid, using the two specified card positions.
   *
   * @param row1  row of first card position, numbered from 0 from the top of the pyramid
   * @param card1 card of first card position, numbered from 0 from left
   * @param row2  row of second card position
   * @param card2 card of second card position
   * @throws IllegalArgumentException if the attempted remove is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    this.gameStartedException();
    if (exposed(row1, card1) && exposed(row2, card2)) {
      if (getCardAt(row1, card1).getValue() + getCardAt(row2, card2).getValue() == 13) {
        this.board.get(row1).set(card1, null);
        this.board.get(row2).set(card2, null);
      }
      else {
        throw new IllegalArgumentException("Cards cannot be removed!");
      }
    }
    else {
      throw new IllegalArgumentException("Card(s) not exposed!");
    }
  }

  /**
   * Remove a single card on the pyramid, using the specified card position.
   *
   * @param row  row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the attempted remove is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    this.gameStartedException();
    if (exposed(row, card) && this.getCardAt(row, card).getValue() == 13) {
      this.board.get(row).set(card, null);
    }
    else {
      throw new IllegalArgumentException("Card cannot be removed!");
    }
  }

  /**
   * Remove two cards, one from the draw pile and one from the pyramid.
   *
   * @param drawIndex the card from the draw pile, numbered from 0 from left
   * @param row       row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card      card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the attempted remove is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    this.gameStartedException();
    if (drawIndex >= this.draw.size()) {
      throw new IllegalArgumentException("Draw index out of bounds!");
    }
    if (exposed(row, card)) {
      if (getCardAt(row, card).getValue() + this.draw.get(drawIndex).getValue() == 13) {
        discardDraw(drawIndex);
        this.board.get(row).set(card, null);
      }
      else {
        throw new IllegalArgumentException("Cards do not add to 13!");
      }
    }
    else {
      throw new IllegalArgumentException("Cannot remove unexposed card!");
    }
  }

  /**
   * Is the selected card exposed.
   *
   * @param row the 0 indexed row of the card being examined
   * @param card the 0 indexed position in the row of the card being examined
   * @return whether the card is exposed (the two cards directly below it have been removed)
   * @throws IllegalArgumentException if index is out of bounds
   */
  protected boolean exposed(int row, int card) throws IllegalArgumentException {
    if (row >= this.numRows || card >= getRowWidth(row) || row < 0 || card < 0) {
      throw new IllegalArgumentException("Index out of bounds!");
    }
    if (row == this.numRows - 1) {
      return true;
    }
    return (board.get(row + 1).get(card) == null
        && board.get(row + 1).get(card + 1) == null
        && board.get(row).get(card) != null);
  }

  /**
   * Discards an individual card from the draw pile.
   *
   * @param drawIndex the card from the draw pile to be discarded
   * @throws IllegalArgumentException if the index is invalid or no card is present there.
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    this.gameStartedException();
    if (drawIndex >= this.numDraw) {
      throw new IllegalArgumentException("Cannot remove unexposed draw card!");
    }
    if (drawIndex < 0) {
      throw new IllegalArgumentException("Invalid index!");
    }
    this.draw.remove(drawIndex);
  }

  /**
   * Returns the number of rows originally in the pyramid, or -1 if the game hasn't been started.
   *
   * @return the height of the pyramid, or -1
   */
  @Override
  public int getNumRows() {
    return this.numRows;
  }

  /**
   * Returns the maximum number of visible cards in the draw pile, or -1 if the game hasn't been
   * started.
   *
   * @return the number of visible cards in the draw pile, or -1
   */
  @Override
  public int getNumDraw() {
    return this.numDraw;
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
    return row + 1;
  }

  /**
   * Signal if the game is over or not. A game is said to be over if there are no possible removes
   * or discards.
   *
   * @return true if game is over, false otherwise
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public boolean isGameOver() throws IllegalStateException {
    this.gameStartedException();
    if (this.getScore() == 0) {
      return true;
    }
    for (int i = 0; i < this.numRows; i++) {
      for (int j = 0; j < getRowWidth(i); j++) {
        if (removable(i, j)) {
          return false;
        }
      }
    }
    return this.getDrawCards().isEmpty();
  }

  /**
   * Determines whether the specified card is removable.
   * @param row the 0 based row index
   * @param card the 0 based index within the row
   * @return whether the specified card is removable (boolean).
   */
  protected boolean removable(int row, int card) {
    if (! (exposed(row, card))) {
      return false;
    }
    ICard selected = getCardAt(row, card);
    if (selected == null) {
      return false;
    }
    int selectedValue = selected.getValue();
    if (selectedValue == 13) {
      return true;
    }
    for (int i = 0; i < this.numRows; i++) {
      for (int j = 0; j < this.getRowWidth(i); j++) {
        if (getCardAt(i, j) == null) {
          continue;
        }
        if (getCardAt(i, j).getValue() + selectedValue == 13 && exposed(i, j)) {
          return true;
        }
      }
    }
    for (ICard c : this.getDrawCards()) {
      if (selectedValue + c.getValue() == 13) {
        return true;
      }
    }
    return false;
  }

  /**
   * Return the current score, which is the sum of the values of the cards remaining in the
   * pyramid.
   *
   * @return the score
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getScore() throws IllegalStateException {
    this.gameStartedException();
    int sum = 0;
    for (ArrayList<ICard> a : this.board) {
      for (ICard c : a) {
        if (c != null) {
          sum += c.getValue();
        }
      }
    }
    return sum;
  }

  /**
   * Returns the card at the specified coordinates.
   *
   * @param row  row of the desired card (0-indexed from the top)
   * @param card column of the desired card (0-indexed from the left)
   * @return the card at the given position, or <code>null</code> if no card is there
   * @throws IllegalArgumentException if the coordinates are invalid
   * @throws IllegalStateException    if the game hasn't been started yet
   */
  @Override
  public ICard getCardAt(int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    this.gameStartedException();
    if (row >= this.numRows || card >= getRowWidth(row) || row < 0 || card < 0) {
      throw new IllegalArgumentException("Coordinates invalid!");
    }
    return this.board.get(row).get(card);
  }

  /**
   * Returns the currently available draw cards. There should be at most {@link
   * PyramidSolitaireModel#getNumDraw} cards (the number specified when the game started) -- there
   * may be fewer, if cards have been removed.
   *
   * @return the ordered list of available draw cards
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public List<ICard> getDrawCards() throws IllegalStateException {
    this.gameStartedException();
    if (! (draw.isEmpty())) {
      return this.draw.subList(0, Math.min(this.numDraw, this.draw.size()));
    }
    else {
      return new ArrayList<ICard>();
    }
  }

  /**
   * Checks if the game is started, and does the following only.
   * @throws IllegalStateException if the game hasn't been started yet
   */
  protected void gameStartedException() throws IllegalStateException {
    if (this.getNumRows() == -1) {
      throw new IllegalStateException("Game not started!");
    }
  }
}
