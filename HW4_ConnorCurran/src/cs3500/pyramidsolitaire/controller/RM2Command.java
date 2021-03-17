package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.Scanner;

/**
 * A Command to remove two cards from a deck in a PyramidSolitaire game.
 */
public class RM2Command extends AbstractPyramidSolitaireCommand {
  public RM2Command(PyramidSolitaireModel<?> model, Appendable out) {
    super(model, out);
  }

  /**
   * Removes two cards from a pyramidSolitaire game.
   * @param scanner The scanner to read input from.
   */
  @Override
  public void run(Scanner scanner) {
    getInputs(scanner, 4);

    try {
      model.remove(inputs.get(0), inputs.get(1), inputs.get(2), inputs.get(3));
      appendEffect(out);
    }
    catch (IllegalArgumentException e) {
      append(out, "Invalid move. Play again. Card(s) out of bounds or unable to be removed");
    }
    catch (IllegalStateException e) {
      append(out, "Game has not been started!");
    }
    catch (IndexOutOfBoundsException e) {
      return;
    }
  }
}
