package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.InputStreamReader;

/**
 * The main class that handles command line arguments and running
 *  a version of the PyramidSolitaire game based on those inputs.
 */
public final class PyramidSolitaire {

  /**
   * Runs the PyramidSolitaire game based on command line inputs.
   * @param args an array of the command line inputs.
   */
  public static void main(String[] args) {
    PyramidSolitaireModel<ICard> model = null;
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new InputStreamReader(System.in), System.out);
    if (args.length != 0) {
      model = PyramidSolitaireCreator.create(GameType.valueOf(args[0].toUpperCase()));
    }
    if (args.length == 3 && Integer.parseInt(args[1]) > 0 && Integer.parseInt(args[2]) > 0) {
      controller.playGame(model, model.getDeck(), true, Integer.parseInt(args[1]),
          Integer.parseInt(args[2]));
    }
    else if (model != null) {
      controller.playGame(model, model.getDeck(), true, 7, 3);
    }
  }
}