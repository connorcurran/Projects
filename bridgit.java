import java.util.ArrayList;
import java.util.Arrays;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

/*
 * For extra credit, we added a turn counter that is displayed below the board
 */

// to represent a cell
class Cell {
  int x;
  int y;
  Color color;
  ArrayList<Cell> neighbors;
 
  Cell(Color color) {
    this.color = color;
    this.neighbors = new ArrayList<Cell>();
  }
  
  /*
   * fields:
   * this.x ... int
   * this.y ... int
   * this.color ... Color
   * this.neighbors ... ArrayList<Cell>
   * methods:
   * this.cellImage() ... WorldImage
   * this.sameColor(Cell) ... boolean
   * this.setColor(Color) ... void
   * this.updateNeighbor(Cell) ... void
   */
 
  // creates a square image of this cell
  public WorldImage cellImage() {
    return new RectangleImage(20, 20, OutlineMode.SOLID, this.color);
  }
 
  // determines if this cell is the same color as the given cell
  public boolean sameColor(Cell c) {
    return this.color == c.color;
  }
 
  //EFFECT: changes the color of this cell to the given color
  public void setColor(Color c) {
    this.color = c;
  }
 
  //EFFECT: updates neighbors of this cell with the given cell
  public void updateNeighbor(Cell c) {
    this.neighbors.add(c);
    c.neighbors.add(this);
  }
}

// to represent a BridgIt game
class BridgItWorld extends World {
  ArrayList<Cell> board = new ArrayList<Cell>();
  final int boardSize;
  final Color player1Color;
  final Color player2Color;
  Color currentColor;
  int turns;
 
  BridgItWorld(int boardSize, Color p1, Color p2) {
    if (boardSize >= 3 && boardSize % 2 == 1) {
      this.boardSize = boardSize;
    }
    else {
      throw new IllegalArgumentException("Board size must be at least 3 and odd");
    }
    this.player1Color = p1;
    this.player2Color = p2;
    this.initBoard();
    this.currentColor = p1;
  }
  
  /*
   * fields:
   * this.board ... ArrayList<Cell>
   * this.boardSize ... int
   * this.player1Color ... Color
   * this.player2Color ... Color
   * this.currentColor ... Color
   * methods:
   * this.initBoard() ... void
   * this.lastScene(String) ... WorldScene
   * this.makeScene() ... WorldScene
   * this.onMouseClicked(Posn) ... void
   * this.onTick() ... void
   * this.won(boolean) ... boolean
   */
 
  //EFFECT: Creates the board
  public void initBoard() {
    for (int i = 0; i < Math.pow(this.boardSize, 2); i++) {
      Cell temp = new Cell(Color.white);
      temp.y = i / this.boardSize;
      temp.x = i % this.boardSize;
     
      if (temp.y % 2 == 0 && temp.x % 2 == 1) {
        temp.color = this.player2Color;
      }
     
      if (temp.y % 2 == 1 && temp.x % 2 == 0) {
        temp.color = this.player1Color;
      }
     
      if (i == 0) {
        this.board.add(i, temp);
        continue;
      }
      else if (temp.y == 0) {
        temp.updateNeighbor(this.board.get(i - 1));
      }
      else if (temp.x == 0) {
        temp.updateNeighbor(this.board.get(i - this.boardSize));
      }
      else {
        temp.updateNeighbor(this.board.get(i - 1));
        temp.updateNeighbor(this.board.get(i - this.boardSize));
      }
     
      this.board.add(i, temp);
    }
    this.turns = 1;
  }
 
  // produces a WorldScene from the board
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(this.boardSize * 20, this.boardSize * 20 + 100);
   
    for (Cell c : this.board) {
      scene.placeImageXY(c.cellImage(), c.x * 20 + 10, c.y * 20 + 10);
    }
    WorldImage turns = new TextImage("Current Turn:" + this.turns, Color.black);
    scene.placeImageXY(turns, 60, this.boardSize * 20 + 20);
    return scene;
  }
  
 
  // produces the final scene displaying which player won
  public WorldScene lastScene(String msg) {
    WorldScene result = this.getEmptyScene();
    result.placeImageXY(new TextImage(msg, Color.black), 100, 50);
    return result;
  }
 
  // determines if the game has been won
  public boolean won(boolean p1) {
    int start;
    int increment;
    int cap;
    boolean end;
    Color current;
    if (p1) {
      start = this.boardSize;
      increment = this.boardSize  * 2;
      current = this.player1Color;
      cap = this.board.size();
    }
    else {
      start = 1;
      increment = 2;
      current = this.player2Color;
      cap = this.boardSize;
    }
   
    for (int i = start; i < cap; i += increment) {
      ArrayList<Cell> already = new ArrayList<Cell>();
      ArrayList<Cell> work = new ArrayList<Cell>();
     
      work.add(this.board.get(i));
     
      while (work.size() != 0) {
        Cell next = work.remove(0);
        if (p1) {
          end = next.x % this.boardSize == this.boardSize - 1;
        }
        else {
          end = next.y == this.boardSize - 1;
        }
        if (end && next.color == current) {
          return true;
        }
        else if (!already.contains(next)) {
          for (Cell c : next.neighbors) {
            if (c.color == current) {
              work.add(c);
            }
          }
          already.add(next);
        }
      }
    }
    return false;
  }
 
  // checks if either player has been won on each tick
  public void onTick() {
    if (this.won(true)) {
      this.endOfWorld("Player 1 won!");
    }
    else if (this.won(false)) {
      this.endOfWorld("Player 2 won!");
    }
  }
 
  // handles user clicks, updating squares to correct player's color
  public void onMouseClicked(Posn pos) {
    int col = pos.x / 20;
    int row = pos.y / 20;
    int i = row * this.boardSize + col;
    if (i >= this.board.size()
        || col == 0
        || row == 0
        || col >= this.boardSize - 1
        || row >= this.boardSize - 1) {
      return;
    }
    Cell selected = this.board.get(i);
    if (selected.color == Color.white) {
      selected.setColor(this.currentColor);
      if (this.currentColor == this.player1Color) {
        this.currentColor = this.player2Color;
      }
      else {
        this.currentColor = this.player1Color;
        turns++;
      }
    }
  }
}

// to represent examples of BridgIt
class ExamplesBridgIt {
  
  Cell cellP;
  Cell cellM;
  Cell cellM2;
  Cell cellW;
  BridgItWorld b;
  BridgItWorld bSmall;
  BridgItWorld bBig;
  BridgItWorld bWonHoriz;
  BridgItWorld bWonVert;
  BridgItWorld bWonHorizComplex;
  BridgItWorld bWonVertComplex;
  Exception e;
  
  // The handin server didnt like when I had the numbers on the same line as the braces
  // so i had to split them up
  
  //for the complex win paths
  ArrayList<Integer> pinkWin = new ArrayList<Integer>(Arrays.asList(
      12, 20, 24, 30, 46, 52, 58, 60, 62, 72, 74, 84)); 
  ArrayList<Integer> magentaWin = new ArrayList<Integer>(Arrays.asList(
      16, 38, 46, 48, 56, 78, 90, 92, 100));
 
  // initial conditions for tests
  void initConditions() {
    this.cellP = new Cell(Color.pink);
    this.cellM = new Cell(Color.magenta);
    this.cellM2 = new Cell(Color.magenta);
    this.cellW = new Cell(Color.white);
    
    this.b = new BridgItWorld(5, Color.pink, Color.magenta);
    this.bSmall = new BridgItWorld(3, Color.pink, Color.magenta);
    this.bBig = new BridgItWorld(11, Color.pink, Color.magenta);
    
    this.bWonHoriz = new BridgItWorld(5, Color.pink, Color.magenta);
    this.bWonHoriz.board.get(6).color = Color.pink;
    this.bWonHoriz.board.get(8).color = Color.pink;
    
    this.bWonVert = new BridgItWorld(5, Color.pink, Color.magenta);
    this.bWonVert.board.get(6).color = Color.magenta;
    this.bWonVert.board.get(16).color = Color.magenta;
    
    this.bWonHorizComplex = new BridgItWorld(11, Color.pink, Color.magenta);
    for (int i = 0; i < pinkWin.size(); i ++) {
      this.bWonHorizComplex.board.get(pinkWin.get(i)).color = Color.pink;
    }
    
    this.bWonVertComplex = new BridgItWorld(11, Color.pink, Color.magenta);
    for (int i = 0; i < magentaWin.size(); i ++) {
      this.bWonVertComplex.board.get(magentaWin.get(i)).color = Color.magenta;
    }
    
    
    this.e = new IllegalArgumentException("Board size must be at least 3 and odd");
    
  }
  
  // to test the BridgItWorld constructor
  void testConstructor(Tester t) {
    // tests for exceptions when the size of the board is not odd or it is less than 3
    t.checkConstructorException(this.e, "BridgItWorld", 10, Color.pink, Color.magenta);
    t.checkConstructorException(this.e, "BridgItWorld", 2, Color.pink, Color.magenta);
  }
  
  //to test cellImage
  void testCellImage(Tester t) {
    initConditions();
    
    // tests for a magenta cell
    t.checkExpect(this.cellM.cellImage(),
        new RectangleImage(20, 20, OutlineMode.SOLID, Color.magenta));
    
    // tests for a pink cell
    t.checkExpect(this.cellP.cellImage(),
        new RectangleImage(20, 20, OutlineMode.SOLID, Color.pink));
    
    // tests for a white cell
    t.checkExpect(this.cellW.cellImage(),
        new RectangleImage(20, 20, OutlineMode.SOLID, Color.white));
  }
  
  // to test updateNeighbor
  void testUpdateNeigbor(Tester t) {
    initConditions();
    
    // tests that each cell contains the other in its neighbors
    this.cellP.updateNeighbor(this.cellW);
    t.checkExpect(this.cellP.neighbors.contains(this.cellW), true);
    t.checkExpect(this.cellW.neighbors.contains(this.cellP), true);
    
    // tests that cellW and cellM are neighbors of each other, and that cellP is not
    // neighbors with cellM
    this.cellW.updateNeighbor(this.cellM);
    t.checkExpect(this.cellW.neighbors.contains(this.cellM), true);
    t.checkExpect(this.cellM.neighbors.contains(this.cellW), true);
    t.checkExpect(this.cellP.neighbors.contains(this.cellM), false);
  }
  
  // to test setColor
  void testSetColor(Tester t) {
    initConditions();
    
    // tests setting a white cell to magenta
    this.cellW.setColor(Color.magenta);
    t.checkExpect(this.cellW.color, Color.magenta);
    
    // tests setting a white cell to pink
    this.cellW.setColor(Color.pink);
    t.checkExpect(this.cellW.color, Color.pink);
    
    // tests setting a magenta cell to pink
    this.cellM.setColor(Color.pink);
    t.checkExpect(this.cellM.color, Color.pink);
    
    // tests setting a pink cell to white
    this.cellP.setColor(Color.white);
    t.checkExpect(this.cellP.color, Color.white);
  }
  
  // to test sameColor
  void testSameColor(Tester t) {
    initConditions();
    // tests that a cell is the same color as itself
    t.checkExpect(this.cellP.sameColor(this.cellP), true);
    
    //tests that a magenta cell is not the same color as a white cell
    t.checkExpect(this.cellM.sameColor(this.cellW), false);
    
    // tests that a magenta cell is the same color as a different magenta cell
    t.checkExpect(this.cellM.sameColor(this.cellM2), true);
    
    // tests that a pink cell is not the same color as a magenta cell
    t.checkExpect(this.cellP.sameColor(this.cellM), false);
  }
  
  // to test initBoard
  void testInitBoard(Tester t) {
    initConditions();
    
    // checks board of correct size is created
    t.checkExpect(this.b.boardSize, 5);
    t.checkExpect(this.b.board.size(), 25);
    t.checkExpect(this.bSmall.boardSize, 3);
    t.checkExpect(this.bSmall.board.size(), 9);
    t.checkExpect(this.bBig.boardSize, 11);
    t.checkExpect(this.bBig.board.size(), 121);
    
    ArrayList<Color> colorTest = new ArrayList<Color>(
        Arrays.asList(Color.magenta, Color.white, Color.pink));
    
    // checks a checkerboard is created for a 5x5 board
    for (int i = 0; i < this.b.board.size(); i++) {
      if (i / this.b.boardSize % 2 == 0) {
        t.checkExpect(this.b.board.get(i).color, colorTest.get(1 - (i % 2)));
      }
      else {
        t.checkExpect(this.b.board.get(i).color, colorTest.get(i % 2 + 1));
      }
    }
    
    // checks a checkerboard is created for a 3x3 board
    for (int i = 0; i < this.bSmall.board.size(); i++) {
      if (i / this.bSmall.boardSize % 2 == 0) {
        t.checkExpect(this.bSmall.board.get(i).color, colorTest.get(1 - (i % 2)));
      } 
      else {
        t.checkExpect(this.bSmall.board.get(i).color, colorTest.get(i % 2 + 1));
      }
    }
    
    // checks a checkerboard is created for a 11x11 board
    for (int i = 0; i < this.bBig.board.size(); i++) {
      if (i / this.bBig.boardSize % 2 == 0) {
        t.checkExpect(this.bBig.board.get(i).color, colorTest.get(1 - (i % 2)));
      }
      else {
        t.checkExpect(this.bBig.board.get(i).color, colorTest.get(i % 2 + 1));
      }
    } 
  }
  
  // to test lastScene
  void testLastScene(Tester t) {
    initConditions();
    WorldScene testLast;
    
    // tests player1 winning
    testLast = this.b.getEmptyScene();
    testLast.placeImageXY(new TextImage("Player 1 won!", Color.black), 100, 50);
    t.checkExpect(this.b.lastScene("Player 1 won!"), testLast);
    
    // tests player2 winning
    testLast = this.b.getEmptyScene();
    testLast.placeImageXY(new TextImage("Player 2 won!", Color.black), 100, 50);
    t.checkExpect(this.b.lastScene("Player 2 won!"), testLast);
  }
  
  // to test makeScene
  void testMakeScene(Tester t) {
    initConditions();
    WorldScene testScene;
    WorldImage turns;
    
    // tests 5x5 board
    testScene = new WorldScene(this.b.boardSize * 20, this.b.boardSize * 20 + 100);
    for (Cell c : this.b.board) {
      testScene.placeImageXY(c.cellImage(), c.x * 20 + 10, c.y * 20 + 10);
    }
    turns = new TextImage("Current Turn:" + this.b.turns, Color.black);
    testScene.placeImageXY(turns, 60, this.b.boardSize * 20 + 20);
    t.checkExpect(this.b.makeScene(), testScene);
    
    // tests 3x3 board
    testScene = new WorldScene(this.bSmall.boardSize * 20, this.bSmall.boardSize * 20 + 100);
    for (Cell c : this.bSmall.board) {
      testScene.placeImageXY(c.cellImage(), c.x * 20 + 10, c.y * 20 + 10);
    }
    turns = new TextImage("Current Turn:" + this.bSmall.turns, Color.black);
    testScene.placeImageXY(turns, 60, this.bSmall.boardSize * 20 + 20);
    t.checkExpect(this.bSmall.makeScene(), testScene);
    
    // tests 11x11 board
    testScene = new WorldScene(this.bBig.boardSize * 20, this.bBig.boardSize * 20 + 100);
    for (Cell c : this.bBig.board) {
      testScene.placeImageXY(c.cellImage(), c.x * 20 + 10, c.y * 20 + 10);
    }
    turns = new TextImage("Current Turn:" + this.bBig.turns, Color.black);
    testScene.placeImageXY(turns, 60, this.bBig.boardSize * 20 + 20);
    t.checkExpect(this.bBig.makeScene(), testScene);
  }
  
  void testOnMouseClicked(Tester t) {
    initConditions();
    
    // tests edge square does not change and does not change to next player's turn
    this.b.onMouseClicked(new Posn(0, 0));
    t.checkExpect(this.b.board.get(0).color, Color.white);
    t.checkExpect(this.b.currentColor, Color.pink);
    
    // tests that a white square changes to pink on pink's turn, updates to magenta's turn
    t.checkExpect(this.b.currentColor, Color.pink);
    t.checkExpect(this.b.board.get(6).color, Color.white);
    this.b.onMouseClicked(new Posn(25, 25));
    t.checkExpect(this.b.currentColor, Color.magenta);
    t.checkExpect(this.b.board.get(6).color, Color.pink);
    
    // test that a white square changes to magenta on magenta's turn, updates to pink's turn
    t.checkExpect(this.b.currentColor, Color.magenta);
    t.checkExpect(this.b.board.get(8).color, Color.white);
    this.b.onMouseClicked(new Posn(65, 25));
    t.checkExpect(this.b.currentColor, Color.pink);
    t.checkExpect(this.b.board.get(8).color, Color.magenta);
    
    // checks that clicking on a pink square does not change its color or the turn
    t.checkExpect(this.b.board.get(7).color, Color.pink);
    t.checkExpect(this.b.currentColor, Color.pink);
    this.b.onMouseClicked(new Posn(45, 25));
    t.checkExpect(this.b.board.get(7).color, Color.pink);
    t.checkExpect(this.b.currentColor, Color.pink);
    
    // checks that clicking on a magenta square does not change its color or the turn
    t.checkExpect(this.b.board.get(13).color, Color.magenta);
    t.checkExpect(this.b.currentColor, Color.pink);
    this.b.onMouseClicked(new Posn(65, 45));
    t.checkExpect(this.b.board.get(13).color, Color.magenta);
    t.checkExpect(this.b.currentColor, Color.pink);
  }
  
  void testWon(Tester t) {
    initConditions();
    
    // tests a case where no one has won
    t.checkExpect(this.b.won(true), false);
    t.checkExpect(this.b.won(false), false);
    
    // tests a case where player1 has won
    t.checkExpect(this.bWonHoriz.won(true), true);
    t.checkExpect(this.bWonHoriz.won(false), false);
    
    // tests a case where player2 has won
    t.checkExpect(this.bWonVert.won(true), false);
    t.checkExpect(this.bWonVert.won(false), true);
    
    // tests a complex path where player1 has won
    t.checkExpect(this.bWonHorizComplex.won(true), true);
    t.checkExpect(this.bWonHorizComplex.won(false), false);
    
    // tests a complex path where player2 has won
    t.checkExpect(this.bWonVertComplex.won(true), false);
    t.checkExpect(this.bWonVertComplex.won(false), true);
  }
  
  // to test the BridgIt game
  void testGame(Tester t) {
    BridgItWorld f = new BridgItWorld(11, Color.PINK, Color.MAGENTA);
    f.bigBang(f.boardSize * 20, f.boardSize * 20 + 40, .1);
  }
}