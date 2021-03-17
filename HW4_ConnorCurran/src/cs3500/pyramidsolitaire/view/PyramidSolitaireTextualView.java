package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;
import java.util.List;

/**
 * Provides a text-based view for the PyramidSolitaireModel.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {
  private final PyramidSolitaireModel<?> model;
  private final Appendable output;

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.output = new StringBuilder();
  }

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable output) {
    this.model = model;
    this.output = output;
  }

  /**
   * Constructs the textual view.
   * @return the completed String representing the model
   */
  @Override
  public String toString() {
    if (this.model.getNumRows() == -1) {
      return "";
    }
    if (this.model.getScore() == 0) {
      return "You win!";
    }
    if (this.model.isGameOver()) {
      return "Game over. Score: " + this.model.getScore();
    }
    String output = "";
    String temp = "";
    for (int i = 0; i < this.model.getNumRows(); i++) {
      for (int j = this.model.getNumRows() - 1; j > i; j--) {
        output += "  ";
      }
      for (int k = 0; k < this.model.getRowWidth(i); k++) {
        if (this.model.getCardAt(i, k) == null) {
          temp = ".";
        }
        else {
          temp = this.model.getCardAt(i, k).toString();
        }
        if (k < this.model.getRowWidth(i) - 1) {
          if (temp.length() == 2) {
            output += temp + "  ";
          }
          else if (temp.length() == 1) {
            output += temp + "   ";
          }
          else {
            output += temp + " ";
          }
        }
        else {
          output += temp;
        }
      }
      output += "\n";
    }
    output += "Draw:";
    List<?> draw = this.model.getDrawCards();
    if (! (draw.isEmpty())) {
      output += " ";
      for (int i = 0; i < this.model.getNumDraw(); i++) {
        output += draw.get(i).toString();
        if (i < this.model.getNumDraw() - 1) {
          output += ", ";
        }
      }
    }
    return output;
  }

  /**
   * Renders a model as text, with each level of the pyramid on a new line.
   *
   * @throws IOException if the rendering fails for some reason
   */
  @Override
  public void render() throws IOException {
    try {
      this.output.append(this.toString());
    }
    catch (IllegalArgumentException e) {
      throw new IOException("Rendering has failed!");
    }
  }
}