package com.mygdx.game;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.mygdx.game.Core.Scriptable;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Assigns all attributes and animation and interactions that the customer will go through in the
 * game including which dish they will pick.
 *
 * @author Robin Graham
 */
public class Customer extends Scriptable implements Person {

  private final float waitHeight;
  private final float waitWidth;
  private int currentSpriteAnimation;
  private final int MAX_ANIMATION = 4;
  private TextureAtlas customerAtlas;
  private float stateTime = 0;

  private int size;
  private String spriteOrientation, spriteState;
  private final String lastOrientation;
  private final int customerNumber;
  private boolean idle;   // customer will be invisible during idle because out of map
  private boolean waitingAtCounter;   // customer will be waiting at the counter for their dish
  private boolean eaten;
  private int spawnWait;
  private final String dish;

  Random rand = new Random();

  /**
   * Initialises the customer and certain variables that we are going to use to interact with the
   * game. We also set the spawn randomiser and the interval between when each customer arrives
   *
   * @param customerNumber the ID of each individual customer which will be interacted with
   */
  public Customer(int customerNumber) {
    currentSpriteAnimation = 1;
    spriteOrientation = "north";
    // sprite.setPosition(posX, posY);
    this.idle = true;
    this.waitingAtCounter = false;
    this.customerNumber = customerNumber;
    this.lastOrientation = "north";
    this.eaten = false;
    this.waitWidth = 235;
    this.waitHeight = 340 - customerNumber * 32;
    if (customerNumber == 1) {
      spawnWait = 1;
    } else {
      spawnWait = rand.nextInt(6000) + 3000;
    }
    this.dish = pickDish();
    System.out.println("customer " + customerNumber + ": " + dish);
  }

  @Override
  public void Start() {
    customerAtlas = getCustomerAtlas(GameScreen.getCustomerAtlasArray());
    gameObject.getSprite().setSprite(customerAtlas.createSprite("north1"));
    gameObject.position.x = 148;
    gameObject.position.y = 66;

  }

  /**
   * Updates the sprite to follow the correct animation
   */
  @Override
  public void updateSpriteFromInput(String newOrientation) {
    if (newOrientation.contains("idle")) {
      spriteState = newOrientation;
    } else {
      if (spriteOrientation != newOrientation) {
        currentSpriteAnimation = 1;
        stateTime = 0;
      } else {
        if (stateTime > 0.06666) {
          currentSpriteAnimation++;
          if (currentSpriteAnimation > MAX_ANIMATION) {
            currentSpriteAnimation = 1;
          }
          stateTime = 0;
        } else {
          stateTime += 0.01;
        }
      }
      spriteState = newOrientation + currentSpriteAnimation;
    }
    setTexture(spriteState);
    spriteOrientation = newOrientation;

    switch (spriteOrientation) {
      case "north":
        gameObject.position.y += 2;
        break;
      case "south":
        gameObject.position.y -= 2;
        break;
      case "east":
        gameObject.position.x += 2;
        break;
      case "west":
        gameObject.position.x -= 2;
        break;
    }
  }

  /**
   * Sets the customer teexure for each customer
   */
  @Override
  public void setTexture(String texture) {
    gameObject.getSprite().sprite.setRegion(customerAtlas.findRegion(texture));
  }

  /**
   * Gets the move of the customer and direction and sets the animations accordingly
   *
   * @return currentDirection direction of the customer
   */
  @Override
  public String getMove() {
    String currentDirection = courseSet();
    if (currentDirection == "waiting") {
      return "idleeast";
    } else if (currentDirection == "complete") {
      System.out.println("customer served and completed animation");
    }
    return currentDirection;
  }

  /**
   * Spawns the customer and sets it course
   *
   * @return if customer waiting or moving
   */
  public String courseSet() {
    if (this.spawnWait > 0) {
      if (this.spawnWait == 1) {   // final waiting frame, sprite becomes visible
        idle = false;
      }
      this.spawnWait--;
      return "waiting";
    }

    String direction = "waiting";
    // customer is walking to counter
    if (!idle && !eaten) {
      if (gameObject.position.y < waitHeight) {
        direction = "north";
      } else if (gameObject.position.x < waitWidth) {
        direction = "east";
      } else {
        waitingAtCounter = true;
      }
    }
    // customer is walking away from counter
    if (!idle && eaten) {
      if (gameObject.position.x > 148) {
        direction = "west";
      } else if (gameObject.position.y > 66) {
        direction = "south";
      } else {
        idle = true;
      }
    }

    return direction;
  }

  /**
   * Returns the x of the customer
   *
   * @return int posX the x position of the customer
   */
  public float getX() {
    return gameObject.position.x;
  }

  /**
   * Returns the y of the customer.
   *
   * @return int posY the y position of the customer
   */
  public float getY() {
    return gameObject.position.y;
  }

  /**
   * Returns if the customer has been fed and then sets its appropriate flag.
   */
  public void fed() {
    idle = false;
    waitingAtCounter = false;
    eaten = true;
  }

  /**
   * Picks the dish for the customer at random.
   *
   * @return String of the name of the dish that the customer is assigned
   */
  private String pickDish() {
    Random random = new Random();
    boolean choice = random.nextBoolean();
    if (choice) {
      return "burger";
    } else {
      return "salad";
    }
  }

  /**
   * Assigns the sprite for the customer at random.
   *
   * @param customerAtlasArray array of customer sprites
   * @return Atlas atlas of the customer object
   */
  private TextureAtlas getCustomerAtlas(ArrayList<TextureAtlas> customerAtlasArray) {
    /*
          Currently can remove texture from array after each .get() to avoid repeats.
          If customers > number of sprites, then there will not be enough sprites available.
    */
    int randomIndex = (int) (Math.random() * customerAtlasArray.size());
    TextureAtlas atlas = customerAtlasArray.get(randomIndex);
    customerAtlasArray.remove(randomIndex);
    return atlas;
  }

  /**
   * Checks if the customer is waiting or not.
   *
   * @return Boolean value for result
   */
  public boolean isWaiting() {
    return waitingAtCounter;
  }

  /**
   * Gets the dish that the customer has.
   *
   * @return Dish dish which is the object the customer has
   */
  public String getDish() {
    return dish;
  }

  /**
   * Draws the customer batch onto the screen given its x and y and direction variables.
   *
   * @param batch the batch in which we draw onto
   */


  /**
   * Checks if the customer has successfully been fed.
   *
   * @return Boolean value of if the customer has been fed
   */
  public boolean getFed() {
    return eaten;
  }
}