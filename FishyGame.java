import tester.Tester;
import javalib.funworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.Random;

//to represent a Fishy game
class FishyGame extends World {
  int width = 500;
  int height = 500;
  Fish fish;
  ILoFish otherFish;
  Random rand;
  
  FishyGame(Fish fish, ILoFish otherFish) {
    super();
    this.fish = fish;
    this.otherFish = otherFish;
    this.rand = new Random();
  }
  
  FishyGame(Fish fish, ILoFish otherFish, Random rand) {
    super();
    this.fish = fish;
    this.otherFish = otherFish;
    this.rand = rand;
  }
  
  /*
   * Fields: 
   * this.width ... int
   * this.height ... int
   * this.fish ... fish
   * this.otherFish ... ILoFish
   * this.rand ... Random
   * Methods:
   * this.lastScene(String) ... WorldScene
   * this.onKeyEvent(String) ... WorldScene
   * this.onTick ... WorldScene
   * this.background ... WorldImage
   * this.makeScene ... WorldScene
   * Methods for Fields:
   * this.fish.moveFish(String) ... Fish
   * this.fish.randomMove() ... Fish
   * this.fish.fishImage() ... WorldImage
   * this.fish.collide(Fish) ... boolean
   * this.fish.newFish(double, double) ... Fish
   * this.fish.randColor() ... Color
   * this.otherFish.randomMove() ... ILoFish
   * this.otherFish.consFishImage() ... WorldImage
   * this.otherFish.offScreen() ... ILoFish
   * this.otherFish.eat(Fish) ... ILoFish
   * this.otherFish.gameOver(Fish) ... boolean
   */
  
  //Creates the game over screen
  public WorldScene lastScene(String msg) {
    return getEmptyScene()
        .placeImageXY(new TextImage(msg, 25, Color.black),250, 250);
  }
  
  //Moves the player on key press; x ends the game
  public World onKeyEvent(String ke) {
    if (ke.equals("x")) {
      return this.endOfWorld("Goodbye");
    }
    else {
      return new FishyGame(this.fish.moveFish(ke), this.otherFish);
    }
  }
  
  //updates all other fish and detects collision and eating; also checks win condition
  public World onTick() {
     if(this.otherFish.gameOver(this.fish)) {
       return this.endOfWorld("You Win! You are the biggest fish!");
     }
     else {
       return new FishyGame(this.fish, this.otherFish.randomMove().offScreen().eat(this.fish));
     }
  }
  
  //creates the background image
  public WorldImage background = new RectangleImage(this.width,
      this.height, OutlineMode.SOLID, Color.cyan);
  
  //creates the scene with all fish on it
  public WorldScene makeScene() {
    return this
            .getEmptyScene()
            .placeImageXY(background, this.width / 2, this.height / 2)
            .placeImageXY(this.otherFish.consFishImage(), 250, 250)
            .placeImageXY(this.fish.fishImage(), this.fish.x, this.fish.y); 
  }
}

//to represent a fish in the Fishy game
class Fish {
  int x;
  int y;
  int dx;
  int dy;
  int radius;
  Color color;
  Random rand;
  
  Fish(int x, int y, int dx, int dy, int radius, Color color, Random rand) {
    this.x = x;
    this.y = y;
    this.dx = dx;
    this.dy = dy;
    this.radius = radius;
    this.color = color;
    this.rand = rand;
  }
  
  /*
   * Fields:
   * this.x ... int
   * this.y ... int
   * this.dx ... int
   * this.dy ... int
   * this.radius ... int
   * this.color ... color
   * this.rand ... rand
   * Methods:
   * this.moveFish(String) ... Fish
   * this.randomMove() ... Fish
   * this.fishImage() ... WorldImage
   * this.collide(Fish) ... boolean
   * this.newFish(double, double) ... Fish
   * this.randColor() ... Color
   * Methods for Fields:
   */
  
  //moves the fish according to the key that is pressed; loops if the fish is off screen
  public Fish moveFish(String ke) {
    if (ke.equals("right")) {
      if(this.x + 5 > 500) {
        return new Fish(this.x - 500, this.y, this.dx, this.dy, this.radius, this.color, this.rand);
      }
      else {
        return new Fish(this.x + 5, this.y, this.dx, this.dy, this.radius, this.color, this.rand);
      }
    }
    else if (ke.equals("left")) {
      if (this.x - 5 < 0) {
        return new Fish(this.x + 500, this.y, this.dx, this.dy, this.radius, this.color, this.rand);
      }
      else {
        return new Fish(this.x - 5, this.y, this.dx, this.dy, this.radius, this.color, this.rand);
      }
    }
    else if (ke.equals("up")) {
      if (this.y - 5 < 0) {
        return new Fish(this.x, this.y + 500, this.dx, this.dy, this.radius, this.color, this.rand);
      }
      else {
        return new Fish(this.x, this.y - 5, this.dx, this.dy, this.radius, this.color, this.rand);
      }
    }
    else if (ke.equals("down")) {
      if (this.y + 5 > 500) {
        return new Fish(this.x, this.y - 500, this.dx, this.dy, this.radius, this.color, this.rand);
      }
      else {
        return new Fish(this.x, this.y + 5, this.dx, this.dy, this.radius, this.color, this.rand);
      }
    }
    return this;
  }
 
  //randomly wiggles the other fish in direction perpendicular to their motion
  public Fish randomMove() {
    if(this.dx == 0) {
      Fish tempFish = new Fish(this.x + rand.nextInt(Math.abs(dy) - dy / 2), this.y + dy, this.dx, this.dy, this.radius, this.color, this.rand);
      if(tempFish.x >= 490 - this.radius * 2) {
        tempFish.x = 490 - this.radius * 2;
      }
      if (tempFish.x <= 10 + this.radius * 2) {
        tempFish.x = 10 + this.radius * 2;
      }
      return tempFish;
    }
    if(this.dy == 0) {
      Fish tempFish = new Fish(this.x + dx, this.y + rand.nextInt(Math.abs(dx) - dx / 2), this.dx, this.dy, this.radius, this.color, this.rand);
      if(tempFish.y > 490 - this.radius * 2) {
        tempFish.y = 490 - this.radius * 2;
      }
      if(tempFish.y <= 10 + this.radius * 2) {
        tempFish.y = 10 + this.radius * 2;
      }
      return tempFish;
    }
    return this;
  }
  
  //creates a circle image based on the size and color of this fish
  public WorldImage fishImage() {
    return new CircleImage(this.radius, "solid", this.color);
  }
  
  //determines if this fish has collided with that fish
  public boolean collide(Fish that) {
    /*
     * Fields of parameters
     * that.x ... int 
     * that.y ... int
     * that.dx ... int
     * that.dy ... int
     * that.radius ... int
     * that.color ... Color
     * that.rand ... Random
     * Methods of parameters:
     * that.moveFish(String) ... Fish
     * that.randomMove() ... Fish
     * that.fishImage ... WorldImage
     * that.collide(Fish) ... boolean
     * that.newFish(double, double) ... Fish
     * that.randColor() ... Color
     */
    
    if (Math.sqrt(Math.pow(this.x - that.x, 2.0) + Math.pow(this.y - that.y, 2.0)) <= this.radius + that.radius) {
      System.out.println(Math.sqrt(Math.pow(this.x - that.x, 2.0) + Math.pow(this.y - that.y, 2.0)));
      System.out.println(this.radius + that.radius);
      System.out.println(this.x);
      System.out.println(that.x);
      return true;
    }
    else return false;
  }
  
  //returns a fish travelling top to bottom, bottom to top, left to right, or right to left
  public Fish newFish(double left, double top) {
    int radius = rand.nextInt(40) + 5;
    if (top == 0 && left == 1) {
      return new Fish(10 + radius * 2, rand.nextInt(500 - 2 * radius) + radius, rand.nextInt(3) + 1, 0, radius , this.randColor(), this.rand);
    }
    else if (top == 0 && left == 1) {
      return new Fish(490 - radius * 2, rand.nextInt(500 - 2 * radius) + radius, rand.nextInt(3)- 4, 0, radius , this.randColor(), this.rand);
    }
    else if (top == 1 && left == 0) {
      return new Fish(rand.nextInt(500 - radius * 2) + radius, 10 + radius * 2, 0, rand.nextInt(3) + 1, radius, this.randColor(), this.rand);
    }
    else {
      return new Fish(rand.nextInt(500 - radius * 2) + radius, 490 - radius * 2, 0, rand.nextInt(3) - 4, radius, this.randColor(), this.rand);
    }
  }
  
  //returns a random color
  public Color randColor() {
    int r = this.rand.nextInt(4);
    
    switch(r) {
    case 0:
      return Color.red;
    case 1:
      return Color.yellow;
    case 2:
      return Color.magenta;
    default:
      return Color.gray;
    }
    
  }
}

//to represent a list of fish
interface ILoFish {
  
  //randomly moves a fish
  public ILoFish randomMove();
  
  //creates an image containing all fish in the list
  public WorldImage consFishImage();
  
  //determines if any fish have traveled off the screen; replaces them with new fish
  public ILoFish offScreen();
  
  //determines if that fish should eat any fish in the list or vice versa
  public ILoFish eat(Fish that);
  /*
   * Fields of parameters
   * that.x ... int 
   * that.y ... int
   * that.dx ... int
   * that.dy ... int
   * that.radius ... int
   * that.color ... Color
   * that.rand ... Random
   * Methods of parameters:
   * that.moveFish(String) ... Fish
   * that.randomMove() ... Fish
   * that.fishImage ... WorldImage
   * that.collide(Fish) ... boolean
   * that.newFish(double, double) ... Fish
   * that.randColor() ... Color
   */
  
  //determines if that fish is the biggest fish in the pond
  public boolean gameOver(Fish that);
  /*
   * Fields of parameters
   * that.x ... int 
   * that.y ... int
   * that.dx ... int
   * that.dy ... int
   * that.radius ... int
   * that.color ... Color
   * that.rand ... Random
   * Methods of parameters:
   * that.moveFish(String) ... Fish
   * that.randomMove() ... Fish
   * that.fishImage ... WorldImage
   * that.collide(Fish) ... boolean
   * that.newFish(double, double) ... Fish
   * that.randColor() ... Color
   */
}

//to represent an empty list of fish
class MtLoFish implements ILoFish{
  MtLoFish() {}
  /*
   * Fields:
   * Methods:
   * this.randomMove() ... ILoFish
   * this.WorldImage() ... WorldImage
   * this.offScreen() ... ILoFish
   * this.eat(Fish) ... ILoFish
   * this.gameOver(Fish) ... boolean
   * Methods for Fields:
   */
  
  //randomly moves a fish
  public ILoFish randomMove() {
    return new MtLoFish();
  }
  
  //creates an image containing all fish in the list
  public WorldImage consFishImage() {
    return new RectangleImage(500, 500, "outline", Color.black);
  }
  
  //determines if any fish have traveled off the screen; replaces them with new fish
  public ILoFish offScreen() {
    return new MtLoFish();
  }
  
  //determines if that fish should eat any fish in the list or vice versa
  public ILoFish eat(Fish that) {
    return new MtLoFish();
  }
  
  //determines if that fish is the biggest fish in the pond
  public boolean gameOver(Fish that) {
    return true;
  }
}

//to represent a list containing fish
class ConsLoFish implements ILoFish{
  Fish first;
  ILoFish rest;
  
  ConsLoFish(Fish first, ILoFish rest) {
    this.first = first;
    this.rest = rest;
  }
  /*
   * Fields:
   * this.first ... Fish
   * this.rest ... ILoFish
   * Methods:
   * this.randomMove() ... ILoFish
   * this.WorldImage() ... WorldImage
   * this.offScreen() ... ILoFish
   * this.eat(Fish) ... ILoFish
   * this.gameOver(Fish) ... boolean
   * Methods for Fields:
   * this.first.moveFish(String) ... Fish
   * this.first.randomMove() ... Fish
   * this.first.fishImage() ... WorldImage
   * this.first.collide(Fish) ... boolean
   * this.first.newFish(double, double) ... Fish
   * this.first.randColor() ... Color
   * this.rest.randomMove() ... ILoFish
   * this.rest.WorldImage() ... WorldImage
   * this.rest.offScreen() ... ILoFish
   * this.rest.eat(Fish) ... ILoFish
   * this.rest.gameOver(Fish) ... boolean
   */
  
  //randomly moves a fish
  public ILoFish randomMove() {
    return new ConsLoFish(this.first.randomMove(), this.rest.randomMove());
  }
  
  //creates an image containing all fish in the list
  public WorldImage consFishImage() {
    return new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, this.rest.consFishImage(), this.first.x, this.first.y,
        this.first.fishImage());
  }
  
  //determines if any fish have traveled off the screen; replaces them with new fish
  public ILoFish offScreen() {
    if (this.first.x >= 490 - this.first.radius * 2 || this.first.x <= 10 + this.first.radius * 2) {
      if (this.first.rand.nextInt(2) == 1) {
        return new ConsLoFish(this.first.newFish(1, 0), this.rest.offScreen());
      }
      else {
        return new ConsLoFish(this.first.newFish(0, 0), this.rest.offScreen());
      }
    }
    if(this.first.y >= 490 - this.first.radius * 2 || this.first.y <= 10 + this.first.radius * 2) {
      if (this.first.rand.nextInt(2) == 1) {
        return new ConsLoFish(this.first.newFish(0, 1),this.rest.offScreen());
      }
      else {
        return new ConsLoFish(this.first.newFish(1, 1),this.rest.offScreen());
      }
    }
    return new ConsLoFish(this.first, this.rest.offScreen());
  }
  
  //determines if that fish should eat any fish in the list or vice versa
  public ILoFish eat(Fish that) {
    if (this.first.collide(that)) {
      if (this.first.radius > that.radius) {
        that.x = (int)(Math.random() * 480) + 10;
        that.y = (int)(Math.random() * 480) + 10;
        that.radius = 15;
        this.first.radius = (int)Math.sqrt(Math.pow(this.first.radius, 2) + Math.pow(that.radius, 2));
        return new ConsLoFish(this.first, this.rest.eat(that));
      }
      else {
        System.out.println(that.radius);
        that.radius = (int)Math.sqrt(Math.pow(this.first.radius, 2) + Math.pow(that.radius, 2));
        System.out.println(that.radius);
        return new ConsLoFish(this.first.newFish(this.first.rand.nextInt(2), this.first.rand.nextInt(2)), this.rest.eat(that));
      }
    }
    else return new ConsLoFish(this.first, this.rest.eat(that));
  }
  
  //determines if that fish is the biggest fish in the pond
  public boolean gameOver(Fish that) {
    if(this.first.radius < that.radius) {
      return this.rest.gameOver(that) && true;
    }
    else return false;
  }
}

//A class to represent examples of a Fishy game
class ExamplesFish {
  ILoFish mt = new MtLoFish();
  Random rand = new Random(3);
 
  Fish f1 = new Fish(250, 250, 0, 0, 15, Color.orange, rand);
  Fish b1 = new Fish(100, 100, 3, 0, 25, Color.red, rand);
  Fish b2 = new Fish(400, 400, 0, 3, 10, Color.black, rand);
  Fish b3 = new Fish(250, 250, 0, 3, 10, Color.gray, rand);
  Fish b4 = new Fish(100, 400, 3, 0, 20, Color.magenta, rand);
  
  ILoFish test1 = new ConsLoFish(b1, mt);
  
  ILoFish test2 = new ConsLoFish(b3, mt);
  
  ILoFish bgFish = new ConsLoFish(b1,
      new ConsLoFish(b2,
          new ConsLoFish(b3,
              new ConsLoFish(b4, mt))));
  
  FishyGame f = new FishyGame(f1, bgFish);
  
  //tests the moveFish method
  boolean testMoveFish(Tester t) {
    return t.checkExpect(this.f1.moveFish("left").x, 245)
        && t.checkExpect(this.f1.moveFish("right").x, 255)
        && t.checkExpect(this.f1.moveFish("up").y, 245)
        && t.checkExpect(this.f1.moveFish("down").y, 255);
  }

  //tests the fishImage method
  boolean testFishImage(Tester t) {
    return t.checkExpect(this.f1.fishImage(), new CircleImage(15, "SOLID", Color.orange))
        && t.checkExpect(this.b1.fishImage(), new CircleImage(25, "SOLID", Color.red));
  }
  
  //tests the collide method
  boolean testCollide(Tester t) {
    return t.checkExpect(this.f1.collide(this.b3), true)
        && t.checkExpect(this.f1.collide(this.b2), false);
  }
  
  //tests the newFish method
  boolean testNewFish(Tester t) {
    return t.checkExpect(this.f1.newFish(0, 1), new Fish(45, 48, 0, 1, 19, Color.gray, new Random()))
        && t.checkExpect(this.f1.newFish(1, 1), new Fish(265, 464, 0, -4, 13, Color.magenta, new Random()))
        && t.checkExpect(this.f1.newFish(1, 0), new Fish(58, 381, 1, 0, 24, Color.yellow, new Random()))
        && t.checkExpect(this.f1.newFish(0, 0), new Fish(408, 406, 0, -4, 42, Color.yellow, new Random()));
  }
  
  //tests the consFishImage method
  boolean testConsFishImage(Tester t) {
    return t.checkExpect(this.test1.consFishImage(),
        new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, this.mt.consFishImage(), this.b1.x, this.b1.y, this.b1.fishImage()))
        && t.checkExpect(this.mt.consFishImage(), new RectangleImage(500, 500, "Outline", Color.black));
  }
  
  //tests the eat method
  boolean testEat(Tester t) {
    return t.checkExpect(this.test1.eat(this.b3), test1)
        && t.checkFail(this.test2.eat(this.b3), this.test2);
  }
  
  //tests the big bang method
  boolean testFishyGame(Tester t) { 
    // run the game
    return f.bigBang(500, 500, .075);
  }
}
