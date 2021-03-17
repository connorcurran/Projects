import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import tester.Tester;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

/*
 * Note:
 *  This is a complete version of the game. Everything needed for rendering
 *   is present, along with all functions for gameplay.
 */

// Represents a single square of the game area
class Cell {
  // In logical coordinates, with the origin at the top-left corner of the screen
  int x;
  int y;
  Color color;
  boolean flooded;
  // the four adjacent cells to this one
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;
 
  Cell(Color color) {
    this.color = color;
  }
 
  /*
   * fields:
   * this.x ... int
   * this.y ... int
   * this.color ... Color
   * this.flooded ... boolean
   * this.left ... Cell
   * this.top ... Cell
   * this.right ... Cell
   * this.bottom ... Cell
   * methods:
   * this.cellImage() ... WorldImage
   * this.updateNeighbors(Cell, Cell) ... void
   * this.setColor(Color) ... void
   * this.updateFlood(ArrayList<Cell>) ... void
   * this.sameColor(Cell) ... void
   * methods of fields:
   * this.left.cellImage() ... WorldImage
   * this.left.updateNeighbors(Cell, Cell) ... void
   * this.left.setColor(Color) ... void
   * this.left.updateFlood(ArrayList<Cell>) ... void
   * this.left.sameColor(Cell) ... void
   * this.top.cellImage() ... WorldImage
   * this.top.updateNeighbors(Cell, Cell) ... void
   * this.top.setColor(Color) ... void
   * this.top.updateFlood(ArrayList<Cell>) ... void
   * this.top.sameColor(Cell) ... void
   * this.right.cellImage() ... WorldImage
   * this.right.updateNeighbors(Cell, Cell) ... void
   * this.right.setColor(Color) ... void
   * this.right.updateFlood(ArrayList<Cell>) ... void
   * this.right.sameColor(Cell) ... void
   * this.bottom.cellImage() ... WorldImage
   * this.bottom.updateNeighbors(Cell, Cell) ... void
   * this.bottom.setColor(Color) ... void
   * this.bottom.updateFlood(ArrayList<Cell>) ... void
   * this.bottom.sameColor(Cell) ... void
   */
 
  // creates a square image of this cell
  public WorldImage cellImage() {
    return new RectangleImage(20, 20, OutlineMode.SOLID, this.color);
  }
 
  // updates neighbors of this cell with the given cells
  public void updateNeighbors(Cell left, Cell top) {
    this.top = top;
    if (top != null) {
      top.bottom = this;
    }
    this.left = left;
    if (left != null) {
      left.right = this;
    }
  }
 
  //EFFECT: changes the color of this cell to the given color
  public void setColor(Color c) {
    this.color = c;
  }
 
  //EFFECT: changes color of cells and changes their flooded state
  public void updateFlood(ArrayList<Cell> soFar) {
    ArrayList<Cell> neighbors =
        new ArrayList<Cell>(Arrays.asList(this.left, this.top, this.right, this.bottom));
    neighbors.removeIf(n -> n == null);
    neighbors.removeIf(n -> soFar.contains(n));
    for (Cell n : neighbors) {
      if (n.flooded && n.sameColor(this)) {
        soFar.add(n);
        n.updateFlood(soFar);
      }
      else if (n.sameColor(this)) {
        n.flooded = true;
        soFar.add(n);
        n.updateFlood(soFar);
      }
      else if (n.flooded) {
        n.color = this.color;
      }
    }
  }
 
  // determines if this cell is the same color as the given cell
  public boolean sameColor(Cell c) {
    return this.color == c.color;
  }
}

// to represent a timer
class UpdateTimer extends TimerTask {
  int timer = 0;
  FloodItWorld w;
 
  UpdateTimer(FloodItWorld w) {
    this.w = w;
  }
 
  /*
   * fields:
   * this.timer ... int
   * this.w ... FloodItWorld
   * methods:
   * this.run() ... void
   * methods for fields:
   * this.w.initBoard() ... void
   * this.w.lastScene(String) ... WorldScene
   * this.w.gameWon() ... boolean
   * this.w.onTick() ... void
   * this.w.onKeyEvent(String) ... void
   * this.w.onMouseClicked(Posn) ... void
   * this.w.makeScene() ... void
   * this.w.updateTimer(int) ...void
   */
 
  //EFFECT: increments the timer
  public void run() {
    timer += 1;
    w.timer = timer;
  }
}

// to represent a FloodIt game
class FloodItWorld extends World {
  // All the cells of the game
  ArrayList<Cell> board = new ArrayList<Cell>();
  int boardSize;
  int colors;
  int maxTurns;
  int turns;
  Timer t;
  int timer;
  ArrayList<Color> allColors = new ArrayList<Color>(Arrays.asList(Color.blue, Color.yellow,
      Color.red, Color.green, Color.orange, Color.pink, Color.white, Color.magenta));
 
  FloodItWorld(int size, int colors) {
    this.boardSize = size;
    this.colors = colors;
    this.maxTurns = this.boardSize * this.colors / 3;
    this.initBoard();
  }
 
  /*
   * fields:
   * this.board ... ArrayList<Cell>
   * this.boardSize ... int
   * this.colors ... int
   * this.maxTurns ... int
   * this.turns ... int
   * this.t ... Timer
   * this.allColors ... ArrayList<Color>
   * methods:
   * this.initBoard() ... void
   * this.lastScene(String) ... WorldScene
   * this.gameWon() ... boolean
   * this.onTick() ... void
   * this.onKeyEvent(String) ... void
   * this.onMouseClicked(Posn) ... void
   * this.makeScene() ... WorldScene
   * this.updateTimer(int) ... void
   * methods of fields:
   * this.t.run() ... void
   */
 
  //EFFECT: creates the board and begins the timer
  public void initBoard() {
    t = new Timer();
    TimerTask updateTimer = new UpdateTimer(this);
    t.schedule(updateTimer, 0, 1000);
    timer = 0;
    this.turns = 0;
   
    for (int i = 0; i < Math.pow(this.boardSize, 2); i++) {
      Color c = this.allColors.get((int) (Math.random() * this.colors));
      Cell temp = new Cell(c);
      temp.y = i / this.boardSize;
      temp.x = i % this.boardSize;
     
      if (i == 0) {
        temp.updateNeighbors(null, null);
        temp.flooded = true;
      }
      else if (temp.y == 0) {
        temp.updateNeighbors(this.board.get(i - 1), null);
      }
      else if (temp.x == 0) {
        temp.updateNeighbors(null, this.board.get(i - this.boardSize));
      }
      else {
        temp.updateNeighbors(this.board.get(i - 1), this.board.get(i - this.boardSize));
      }
     
      this.board.add(i, temp);
    }
  }
 
  // produces the final scene displaying turns used and time elapsed
  public WorldScene lastScene(String msg) {
    WorldScene result = this.getEmptyScene();
    result.placeImageXY(new TextImage(msg + " In " + this.timer + " seconds,",
        Color.black), 100, 50);
    result.placeImageXY(new TextImage("Using " + this.turns + " turns", Color.black), 100, 75);
    return result;
  }
 
  // determines if the game has been won
  public boolean gameWon() {
    Cell first = this.board.get(0);
    for (Cell c : this.board) {
      if (!(first.sameColor(c))) {
        return false;
      }
    }
    return true;
  }
 
  // handles flooding the board and checks win condition
  public void onTick() {
    this.board.get(0).updateFlood(new ArrayList<Cell>());
    if (this.gameWon()) {
      this.endOfWorld("You won!");
    }
    else if (this.turns > this.maxTurns) {
      this.endOfWorld("You lost!");
    }
  }
 
  // checks for "r" to reset the game
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      t.cancel();
      this.board.clear();
      this.initBoard();
    }
  }
 
  // handles user click, counting turns and updating current color
  public void onMouseClicked(Posn pos) {
    int col = pos.x / 20;
    int row = pos.y / 20;
    int i = row * this.boardSize + col;
    if (i >= this.board.size()) {
      return;
    }
    Cell selected = this.board.get(i);
    Cell first = this.board.get(0);
    if (!(selected.sameColor(first))) {
      first.setColor(selected.color);
      turns += 1;
    }
  }

  // produces a WorldScene from the board, score, and time
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(this.boardSize * 20, this.boardSize * 20 + 100);
   
    for (Cell c : this.board) {
      scene.placeImageXY(c.cellImage(), c.x * 20 + 10, c.y * 20 + 10);
    }
    WorldImage scoreImg = new TextImage(this.turns + " / " + this.maxTurns, Color.black);
    scene.placeImageXY(scoreImg, 30, this.boardSize * 20 + 40);
    WorldImage timerImg = new TextImage(this.timer + " seconds", Color.black);
    scene.placeImageXY(timerImg, 50, this.boardSize * 20 + 75);
   
    return scene;
  }
}

// to represent examples of FloodIt
class ExamplesFloodIt {
 
  Cell cellR;
  Cell cellB;
  Cell cellG;
  Cell cellB2;
  FloodItWorld f;
  FloodItWorld fSmall;
 
  // initial conditions for tests
  void initConditions() {
    this.cellR = new Cell(Color.RED);
    this.cellB = new Cell(Color.BLUE);
    this.cellB2 = new Cell(Color.BLUE);
    this.cellG = new Cell(Color.GREEN);
    this.f = new FloodItWorld(5, 6);
    this.fSmall = new FloodItWorld(2,6);
   
  }
 
  // to test cellImage
  void testCellImage(Tester t) {
    initConditions();
    t.checkExpect(this.cellR.cellImage(),
        new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED));
    t.checkExpect(this.cellB.cellImage(),
        new RectangleImage(20, 20, OutlineMode.SOLID, Color.BLUE));
  }
 
  // to test updateNeighbors
  void testUpdateNeighbors(Tester t) {
    initConditions();
    this.cellB.updateNeighbors(this.cellR, this.cellG);
    t.checkExpect(this.cellB.left, this.cellR);
    t.checkExpect(this.cellB.top, this.cellG);
  }
 
  // to test setColor
  void testSetColor(Tester t) {
    initConditions();
    this.cellB.setColor(Color.RED);
    t.checkExpect(this.cellB.color, Color.RED);
    this.cellR.setColor(Color.BLUE);
    t.checkExpect(this.cellR.color, Color.BLUE);
    this.cellG.setColor(Color.GREEN);
    t.checkExpect(this.cellG.color, Color.GREEN);
  }
  
  // to test updateFlood
  void testUpdateFlood(Tester t) {
    initConditions();
    this.cellB.right = this.cellB2;
    this.cellB.bottom = this.cellR;
    this.cellB.updateFlood(new ArrayList<Cell>());
    t.checkExpect(this.cellR.flooded, false);
    t.checkExpect(this.cellB2.flooded, true);
  }
 
  // to test sameColor
  void testSameColor(Tester t) {
    initConditions();
    t.checkExpect(this.cellB.sameColor(this.cellB), true);
    t.checkExpect(this.cellR.sameColor(this.cellB), false);
    t.checkExpect(this.cellG.sameColor(this.cellB), false);
    t.checkExpect(this.cellB.sameColor(this.cellB2), true);
  }
 
  // to test initBoard
  void testInitBoard(Tester t) {
    initConditions();
    f.initBoard();
    t.checkExpect(this.f.board.size() > 0, true);
    t.checkExpect(this.f.timer, 1);
    t.checkExpect(this.f.turns, 0);
  }
 
  // to test lastScene
  void testLastScene(Tester t) {
    initConditions();
    f.initBoard();
    WorldScene endWin = f.getEmptyScene();
    endWin.placeImageXY(new TextImage("You won" + " In " + 1 + " seconds,",
        Color.black), 100, 50);
    endWin.placeImageXY(new TextImage("Using " + 0 + " turns", Color.black), 100, 75);
    t.checkExpect(this.f.lastScene("You won"), endWin);
   
    f.turns = 20;
    WorldScene endLoss = f.getEmptyScene();
    endLoss.placeImageXY(new TextImage("You lost" + " In " + 1 + " seconds,",
        Color.black), 100, 50);
    endLoss.placeImageXY(new TextImage("Using " + 20 + " turns", Color.black), 100, 75);
   
    t.checkExpect(this.f.lastScene("You lost"), endLoss);
  }
 
  // to test gameWon
  void testGameWon(Tester t) {
    initConditions();
    f.initBoard();
    t.checkExpect(this.f.gameWon(), false);
    f.board = new ArrayList<Cell>(Arrays.asList(this.cellB, this.cellB2, this.cellB, this.cellB2));
    t.checkExpect(this.f.gameWon(), true);
  }
 
  // to test makeScene
  void testMakeScene(Tester t) {
    initConditions();
    fSmall.board = new ArrayList<Cell>(Arrays.asList(this.cellB, this.cellR,
        this.cellG, this.cellB2));
    WorldScene boardTest = new WorldScene(this.fSmall.boardSize * 20,
        this.fSmall.boardSize * 20 + 100);
    for (Cell c : this.fSmall.board) {
      boardTest.placeImageXY(c.cellImage(), c.x * 20 + 10, c.y * 20 + 10);
    }
    WorldImage scoreImg =
        new TextImage(this.fSmall.turns + " / " + this.fSmall.maxTurns, Color.black);
    boardTest.placeImageXY(scoreImg, 30, this.fSmall.boardSize * 20 + 40);
    WorldImage timerImg = new TextImage(this.fSmall.timer + " seconds", Color.black);
    boardTest.placeImageXY(timerImg, 50, this.fSmall.boardSize * 20 + 75);
    
    t.checkExpect(this.fSmall.makeScene(), boardTest);
    
    fSmall.board = 
        new ArrayList<Cell>(Arrays.asList(this.cellR, this.cellG, this.cellB, this.cellB2));
    boardTest = new WorldScene(this.fSmall.boardSize * 20, this.fSmall.boardSize * 20 + 100);
    for (Cell c : this.fSmall.board) {
      boardTest.placeImageXY(c.cellImage(), c.x * 20 + 10, c.y * 20 + 10);
    }
    
    scoreImg = new TextImage(this.fSmall.turns + " / " + this.fSmall.maxTurns, Color.black);
    boardTest.placeImageXY(scoreImg, 30, this.fSmall.boardSize * 20 + 40);
    timerImg = new TextImage(this.fSmall.timer + " seconds", Color.black);
    boardTest.placeImageXY(timerImg, 50, this.fSmall.boardSize * 20 + 75);
    
    t.checkExpect(this.fSmall.makeScene(), boardTest);
  }
 
  // to test the FloodItGame
  void testGame(Tester t) {
    FloodItWorld f = new FloodItWorld(10, 6);
    f.bigBang(f.boardSize * 20, f.boardSize * 20 + 100, .08);
  }
}