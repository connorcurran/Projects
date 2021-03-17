package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.util.Scanner;

/**
 * An ICommand that quits the game.
 */
public class QuitCommand extends AbstractPyramidSolitaireCommand {
  public QuitCommand(PyramidSolitaireModel<?> model, Appendable out) {
    super(model, out);
  }

  /**
   * Runs the Quit Command, quitting the game and appending relevant information to the scanner.
   * @param scanner The scanner to read input from.
   */
  @Override
  public void run(Scanner scanner) {
    append(this.out, "Game quit!");
    append(this.out, "State of game when quit:");
    append(this.out, new PyramidSolitaireTextualView(this.model).toString());
    append(out, "Score: " + this.model.getScore());
    scanner.reset();
  }
}
