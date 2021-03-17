package cs3500.pyramidsolitaire.controller;

import java.util.Scanner;

/**
 * Represents a command to be executed by a PyramidSolitaireController.
 */
public interface ICommand {
  void run(Scanner scanner);
}
