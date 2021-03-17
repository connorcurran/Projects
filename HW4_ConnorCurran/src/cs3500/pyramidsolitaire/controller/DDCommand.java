package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.Scanner;

/**
 * An ICommand to discard a valid card from the draw pile.
 */
public class DDCommand extends AbstractPyramidSolitaireCommand {

  public DDCommand(PyramidSolitaireModel<?> model, Appendable out) {
    super(model, out);
  }

  /**
   * Removes a valid card from the draw pile.
   * @param scanner The scanner to read input from.
   */
  @Override
  public void run(Scanner scanner) {
    getInputs(scanner, 1);

    try {
      model.discardDraw(inputs.get(0));
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
