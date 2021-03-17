package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * A Controller for the PyramidSolitaireModel tightly coupled with the PyramidSolitaireTextualView.
 * Starts the game, reads input from a scanner, and executes known commands through
 *  the command design pattern in order to perpetuate effects on the model. Appends output
 *  to the Appendable out in order for the view to display.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  protected final Readable in;
  protected final Appendable out;

  /**
   * Constructs a pyramid solitaire textual controller.
   *
   * @param rd The input readable to be used
   * @param ap The output appendable to used
   * @throws IllegalArgumentException iff one of the arguments are null
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Argument(s) cannot be null!");
    }
    this.in = rd;
    this.out = ap;
  }

  /**
   * Creates a HashMap of known commands and their respective keywords to trigger them.
   * @return the HashMap of commands.
   */
  private Map<String, Supplier<ICommand>> getKnownCommands(PyramidSolitaireModel<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    Map<String, Supplier<ICommand>> knownCommands = new HashMap<String, Supplier<ICommand>>();

    knownCommands.putIfAbsent("rm1", () -> {
      return new RM1Command(model, out);
    });

    knownCommands.putIfAbsent("rm2", () -> {
      return new RM2Command(model, out);
    });

    knownCommands.putIfAbsent("rmwd", () -> {
      return new RMWDCommand(model, out);
    });

    knownCommands.putIfAbsent("dd", () -> {
      return new DDCommand(model, out);
    });

    knownCommands.putIfAbsent("q", () -> {
      return new QuitCommand(model, out);
    });

    knownCommands.putIfAbsent("Q", () -> {
      return new QuitCommand(model, out);
    });

    return knownCommands;
  }

  /**
   * The primary method for beginning and playing a game.
   *
   * @param model   The game of solitaire to be played
   * @param deck    The deck of cards to be used
   * @param shuffle Whether to shuffle the deck or not
   * @param numRows How many rows should be in the pyramid
   * @param numDraw How many draw cards should be visible
   * @throws IllegalArgumentException if the model or deck is null
   * @throws IllegalStateException    if the game cannot be started, or if the controller cannot
   *                                  interact with the player.
   */
  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(model, this.out);
    if (model == null || deck == null) {
      throw new IllegalArgumentException("Deck/Model cannot be null!");
    }
    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    }
    catch (IllegalArgumentException e) {
      throw new IllegalStateException("Game cannot be started!");
    }
    try {
      Map<String, Supplier<ICommand>> knownCommands = getKnownCommands(model);
      Scanner scanner = new Scanner(this.in);
      ICommand command = null;
      this.out.append(view.toString()).append("\n");
      this.out.append("Score: ").append(Integer.toString(model.getScore())).append("\n");
      while (scanner.hasNext()) {
        String input = scanner.next();
        Supplier<ICommand> functionCommand = knownCommands.getOrDefault(input, null);
        if (functionCommand != null) {
          command = functionCommand.get();
          command.run(scanner);
          if (command instanceof QuitCommand) {
            return;
          }
        }
        if (model.isGameOver()) {
          this.out.append(view.toString()).append("\n");
          return;
        }
      }
    }
    catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
