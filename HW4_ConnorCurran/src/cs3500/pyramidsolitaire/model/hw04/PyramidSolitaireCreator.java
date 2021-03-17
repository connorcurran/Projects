package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Holds a method that creates an instance of PyramidSolitaire based on the value
 *  of an enum GameType.
 */
public class PyramidSolitaireCreator {

  /**
   * Represents types of PyramidSolitaire games that can be played.
   */
  public enum GameType {
    BASIC, RELAXED, MULTIPYRAMID;
  }

  /**
   * Constructs an instance of PyramidSolitaireModel based on the value of GameType type.
   * @param type the GameType examined to determine which version to create.
   * @return the new instance.
   */
  public static PyramidSolitaireModel<ICard> create(GameType type) {
    if (type == null) {
      throw new IllegalArgumentException("Type cannot be null");
    }
    if (type.equals(GameType.BASIC)) {
      return new BasicPyramidSolitaire();
    }
    else if (type.equals(GameType.RELAXED)) {
      return new RelaxedPyramidSolitaire();
    }
    else if (type.equals(GameType.MULTIPYRAMID))  {
      return new MultiPyramidSolitaire();
    }
    else {
      throw new IllegalArgumentException("Type must be one of BASIC, RELAXED, or MULTIPYRAMID!");
    }
  }
}
