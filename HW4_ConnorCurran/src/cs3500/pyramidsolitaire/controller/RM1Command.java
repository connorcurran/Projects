package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.Scanner;

/**
 * An ICommand to remove one card from the pyramid.
 */
public class RM1Command extends AbstractPyramidSolitaireCommand {
  public RM1Command(PyramidSolitaireModel<?> model, Appendable out) {
    super(model, out);
  }

  /**
   * Runs the command, removing a valid card from the pyramid.
   * @param scanner The scanner to read input from.
   */
  @Override
  public void run(Scanner scanner) {
    getInputs(scanner, 2);
    try {
      model.remove(inputs.get(0), inputs.get(1));
      appendEffect(out);
    }
    catch (IllegalArgumentException e) {
      append(out, "Invalid move. Play again. Card out of bounds or unable to be removed");
    }
    catch (IllegalStateException e) {
      append(out, "Game has not been started!");
    }
    catch (IndexOutOfBoundsException e) {
      return;
    }
  }
}
