package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents any number of commands to be executed on a PyramidSolitaireModel.
 * Executes these commands through the run method, and appends output to the Appendable out field.
 */
public abstract class AbstractPyramidSolitaireCommand implements ICommand {
  protected final PyramidSolitaireModel<?> model;
  protected final Appendable out;
  protected final PyramidSolitaireView view;
  protected final List<Integer> inputs = new ArrayList<Integer>();

  protected AbstractPyramidSolitaireCommand(PyramidSolitaireModel<?> model, Appendable out) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("Model or out cannot be null!");
    }
    this.model = model;
    this.out = out;
    this.view = new PyramidSolitaireTextualView(model);
  }

  /**
   * Attempts to get the next value from the scanner until a valid input is detected.
   * @param scanner The scanner for input to be read from.
   * @return The int representing the user's input (Their natural number input, or -1 to quit).
   */
  protected int getNextValue(Scanner scanner) {
    if (scanner == null) {
      throw new IllegalArgumentException("Scanner cannot be null!");
    }
    while (true) {
      try {
        int output = scanner.nextInt();
        if (output > 0) {
          return output;
        }
        else {
          append(this.out, "Invalid input. Must be q, Q, or a natural number!");
        }
      }
      catch (InputMismatchException e) {
        if (scanner.next().equalsIgnoreCase("q")) {
          return -1;
        }
        else {
          append(this.out, "Invalid input. Must be q, Q, or a natural number!");
        }
      }
      catch (NoSuchElementException e) {
        throw new IllegalStateException("Nothing to read");
      }
    }
  }

  /**
   * Appends the given value to Appendable out, along with a new line.
   * @param out The appendable to be appended to.
   * @param value The value to be appended.
   * @throws IllegalStateException if the appendable cannot be written to, for whatever reason.
   */
  protected void append(Appendable out, String value) throws IllegalStateException {
    if (out == null || value == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    try {
      out.append(value);
      out.append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to appendable.");
    }
  }

  /**
   * Appends the effect of the command if the model's game is not over.
   * @param out The Appendable to append the effect to.
   */
  protected void appendEffect(Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("Out cannot be null!");
    }
    if (! (model.isGameOver())) {
      append(out, view.toString());
      append(out, "Score: " + model.getScore());
    }
  }

  /**
   * Gets the specified number of inputs necessary to run the command.
   * @param scanner The scanner to read inputs from.
   * @param count The count of inputs to read.
   */
  protected void getInputs(Scanner scanner, int count) {
    if (scanner == null) {
      throw new IllegalArgumentException("Scanner cannot be null!");
    }
    while (this.inputs.size() != count) {
      int input = getNextValue(scanner);
      if (input == -1) {
        new QuitCommand(model, out).run(scanner);
        return;
      }
      else {
        this.inputs.add(input - 1);
      }
    }
  }

  /**
   * Runs the command and perpetuates it's effects.
   * @param scanner The scanner for input to be read from.
   */
  @Override
  public abstract void run(Scanner scanner);
}
