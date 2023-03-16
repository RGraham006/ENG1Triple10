package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Core.Scriptable;
import java.util.ArrayList;

/**
 * Creates the chef object which will interact with every object on the map and assemble dishes to
 * be fed to the customer The class also handles all sprite animations and movement.
 *
 * @author Robin Graham
 * @author Amy Cross
 * @author Labib Zabeneh
 * @author Riko Puusepp
 */
public class Chef extends Scriptable implements Person {

  float speed = 5000;

  private String spriteOrientation, spriteState;
  private int currentSpriteAnimation;
  private final int maxAnimation = 4;
  private float stateTime = 0;
  private TextureAtlas chefAtlas;
  public boolean isFrozen;
  private String lastOrientation;
  public Body b2body;

  private Station currentStation;
  Rectangle chefRectangle;
  World world;

  private final int id;

  private Ingredient ingredient;

  private String inventory;

  // timer attributes
  float animationTime;
  float frameTime;
  int currentTimerFrame = 0;
  TextureAtlas timerAtlas;
  Sprite timerSprite;
  public float ppm = 97f;

  /**
   * Initialise the chef object and sets its spawn position.
   *
   * @param world the world in which our objects lie
   * @param id    the individual id of each chef i.e 0,1,2....
   */
  public Chef(World world, int id, TextureAtlas chefAtlas) {
    this.id = id;
    this.world = world;
    this.chefAtlas = chefAtlas; // chef now takes a texture atlas so
    // that the chefs can be created in the test files. Originally,
    // chefs were given a texture atlas from the getChefAtlasArray function in the GameScreen class.
    // Gamescreen could not be directly used in the test files as it caused an error.
  }

  @Override
  public void Start() {
    //Reorganised to fit work flow and requires access to data not yet created
    gameObject.getSprite().setSprite(chefAtlas.createSprite("south1"));
    currentSpriteAnimation = 1;
    spriteOrientation = "south";
    gameObject.position.x = 700 + 32 * id;
    gameObject.position.y = 300;
    isFrozen = false;
    //sprite.setPosition(posX, posY); unnessary now
    //MyGdxGame.buildObject(world, posX, posY, sprite.getWidth(), sprite.getHeight(), "Dynamic");
    this.lastOrientation = "south";

    defineChef();
    ingredient = new Ingredient("none");
    timerAtlas = new TextureAtlas("Timer/timer.txt");
    timerSprite = timerAtlas.createSprite("01");
    currentStation = new Station("none");
  }

  /**
   * Defines all box2d associated variables for the chef and sets its hitbox to be used for
   * collisions.
   */
  public void defineChef() {
    BodyDef bdef = new BodyDef();
    bdef.position.set(gameObject.position.x, gameObject.position.y);
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body = world.createBody(bdef);
    b2body.setUserData("Chef" + id);
    FixtureDef fdefine = new FixtureDef();
    CircleShape shape = new CircleShape();
    shape.setRadius(10);

    fdefine.shape = shape;
    b2body.createFixture(fdefine);
    EdgeShape head = new EdgeShape();
    head.set((new Vector2(-2, 7)), new Vector2(2, 7));
    fdefine.shape = head;
    fdefine.isSensor = true;
    b2body.createFixture(fdefine).setUserData("head");


  }

  /**
   * Updates the chef position and shows the animation depending on its direction and speed.
   */
  @SuppressWarnings("checkstyle:MissingSwitchDefault")
  @Override
  public void updateSpriteFromInput(String newOrientation) {
    if (newOrientation.contains("idle")) {
      spriteState = newOrientation;
    } else {
      if (spriteOrientation != newOrientation) {
        currentSpriteAnimation = 1;
        stateTime = 0;
      } else {
        if (stateTime > 1 / 15.0) { // sprite is updated every 15th of a second
          currentSpriteAnimation++;
          if (currentSpriteAnimation > maxAnimation) { // a chef has 4 different animations
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
    float velx = 0;
    float vely = 0;

    switch (spriteOrientation) {

      //removed multiply by position bc lol whats going on with that
      case "north":
        velx = velx;
        vely = speed;
        break;
      case "south":
        velx = velx;
        vely = -speed;
        break;
      case "east":
        velx = speed;
        vely = vely;
        break;
      case "west":
        velx = -speed;
        vely = vely;
        break;
    }

    b2body.setLinearVelocity(velx, vely); // sets the velocity of the chef
    //cant figure out how to speed the character up it doesn't want to function
    gameObject.position.x = (((b2body.getPosition().x) / 1.6f) - getWidth() / 2);
    gameObject.position.y = ((b2body.getPosition().y) / 1.2f);
  }

  /**
   * Sets the texture of the chef.
   */
  @Override
  public void setTexture(String texture) {
    //System.out.println(texture);
    gameObject.getSprite().sprite.setRegion(chefAtlas.findRegion(texture));
  }

  /**
   * Returns the x position of the chef.
   *
   * @return int posX
   */
  public float getX() {
    return gameObject.position.x;
  }

  /**
   * Returns the y position of the chef.
   *
   * @return int posY
   */
  public float getY() {
    return gameObject.position.y;
  }

  /**
   * Returns the width of the chef.
   *
   * @return int width
   */
  public float getWidth() {
    return gameObject.getSprite().sprite.getWidth();
  }

  /**
   * Returns the height of the chef.
   *
   * @return int height
   */
  public float getHeight() {
    return gameObject.getSprite().sprite.getHeight();
  }

  /**
   * Gets the input from the user and orientates the chef accordingly.
   */
  @Override
  public String getMove() {
    String newOrientation = this.lastOrientation;
    if (isFrozen) {
      System.out.println("Frozen");
      return "idle" + this.lastOrientation;
    } else {
      if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        newOrientation = "west";
      } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        newOrientation = "east";
      } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        newOrientation = "north";
      } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        newOrientation = "south";
      } else {
        return "idle" + lastOrientation;
      }
      this.lastOrientation = newOrientation;
      return newOrientation;
    }
  }

  /**
   * Returns a boolean value if the user is pressing the ctrl key.
   *
   * @return boolean
   */
  public boolean isCtrl() {
    return Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT);
  }

  /**
   * Freezes the chef for a set period of time at its given station.
   *
   * @param seconds time used to freeze chef
   * @param station station chef is currently on
   */
  public void freeze(int seconds, Station station) {
    this.currentStation = station;
    currentStation.setLocked(true);
    isFrozen = true;
    currentTimerFrame = 1;
    frameTime = seconds * 0.1f;
    animationTime = frameTime;
  }

  /**
   * Unfreezes the chef after the timer is finished.
   */
  public void unfreeze() {
    isFrozen = false;
    currentStation.setLocked(false);
    this.currentStation = new Station("none");
    currentTimerFrame = 1;
  }

  /**
   * Stops the chef from moving and sets sprite animation to "idle".
   */
  public void stop() {
    b2body.setLinearVelocity(0, 0);
    spriteState = "idle" + lastOrientation;
    setTexture(spriteState);
  }

  /**
   * Gets the inventory of the chef, so the item they are currently holding.
   *
   * @return Ingredient ingredient
   */
  public Ingredient getInventory() {
    return ingredient;
  }

  /**
   * Sets the ingredient of the inventory when the chef picks up the according ingredient.
   *
   * @param ingredient the ingredient which we are setting the inventory to
   */
  public void setInventory(Ingredient ingredient) {
    this.ingredient = ingredient;
  }

  /**
   * Chooses a random sprite for the chef and makes sure both (or mroe) chef assets are different to
   * each other.
   *
   * @param chefAtlasArray array of chef Atlas's
   * @return Atlas atlas of the chef atlas we are using
   */
  private TextureAtlas getChefAtlas(ArrayList<TextureAtlas> chefAtlasArray) {
    int randomIndex = (int) (Math.random() * chefAtlasArray.size());
    TextureAtlas atlas = chefAtlasArray.get(randomIndex);
    chefAtlasArray.remove(randomIndex);
    return atlas;
  }

  /**
   * Draws the timer onto the screen and runs the animation for the set time Then unfreezes the chef
   * after timer is finished.
   *
   * @param batch that we are drawing to
   */
  public void drawTimer(SpriteBatch batch) {
    System.out.println("draw");
    timerSprite.setPosition(gameObject.position.x, gameObject.position.y + getHeight());
    if (currentTimerFrame <= 7) {
      System.out.println(animationTime);
      if (animationTime <= 0) {
        currentTimerFrame++;
        animationTime = frameTime;
        String state = "0" + currentTimerFrame;
        timerSprite.setRegion(timerAtlas.findRegion(state));
      }
      timerSprite.draw(batch);
      animationTime -= Gdx.graphics.getDeltaTime();
      System.out.println(animationTime);
    } else {
      unfreeze();
    }
  }
}
