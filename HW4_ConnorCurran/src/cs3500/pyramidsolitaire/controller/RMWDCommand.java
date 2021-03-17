package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.Scanner;

/**
 * An ICommand to remove one card and a draw card.
 */
public class RMWDCommand extends AbstractPyramidSolitaireCommand {
  public RMWDCommand(PyramidSolitaireModel<?> model, Appendable out) {
    super(model, out);
  }

  /**
   * Runs the command, using a draw card to remove a card from the pyramid.
   * @param scanner The scanner to read input from.
   */
  @Override
  public void run(Scanner scanner) {
    getInputs(scanner, 3);

    try {
      model.removeUsingDraw(inputs.get(0), inputs.get(1), inputs.get(2));
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
