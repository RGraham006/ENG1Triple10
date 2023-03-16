package piazzapanictests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Chef;
import com.mygdx.game.Core.BlackSprite;
import com.mygdx.game.Core.GameObject;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests to do with the Chefs.
 * @author Jack Vickers
 * @author Hubert Solecki
 * @author Azzam Amirul Bahri
 */
@RunWith(GdxTestRunner.class)
public class ChefTests {
  // Array of chefs
  private static ArrayList<TextureAtlas> chefAtlasArray = new ArrayList<TextureAtlas>();
  public Chef[] chef;
  public World world;

  /**
   * Instantiates the world and chefs so that these can be used in the tests.
   * @author Jack Vickers
   */
  private void instantiateWorldAndChefs() {
    world = new World(new Vector2(0, 0), true);
    generateChefArray();
    chef = new Chef[2];
    int chefControl = 0;
    for (int i = 0; i < chef.length; i++) {
      GameObject chefsGameObject = new GameObject(
          new BlackSprite()); // passing in null since chef will define it later
      chef[i] = new Chef(world, i, getChefAtlasArray().get(chefControl));
      chefsGameObject.attachScript(chef[i]);
      chefsGameObject.image.setSize(18, 40); // set size of sprite
      chef[i].updateSpriteFromInput("idlesouth");
    }
  }

  /**
   * Generates a chef array which can be used to get random chef sprites from the chef class.
   * @author Jack Vickers
   */
  private void generateChefArray() {
    String filename;
    TextureAtlas chefAtlas;
    for (int i = 1; i < 4; i++) {
      filename = "Chefs/Chef" + i + "/chef" + i + ".txt";
      chefAtlas = new TextureAtlas(filename);
      chefAtlasArray.add(chefAtlas);
    }
  }

  /**
   * Returns the chef atlas array.
   * @author Jack Vickers
   * @return chefAtlasArray
   */
  private static ArrayList<TextureAtlas> getChefAtlasArray() {
    return chefAtlasArray;
  }

  /**
   * Tests that the chef can move up.
   * @author Jack Vickers
   */
  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
  @Test
  public void testChefMoveUp() {
    instantiateWorldAndChefs();
    float currentY = chef[0].getY();
    float currentX = chef[0].getX();
    world.step(1 / 60f, 6, 2);
    chef[0].updateSpriteFromInput("north");
    world.step(1 / 60f, 6, 2);
    chef[0].updateSpriteFromInput("north");
    assertTrue("The y position of the chef should be greater ", chef[0].getY() > currentY);
    assertEquals("The x position of the chef should be the same ", chef[0].getX(), currentX, 0.0);
  }

  /**
   * Tests that the chef can move down.
   * @author Jack Vickers
   */
  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
  @Test
  public void testChefMoveDown() {
    instantiateWorldAndChefs();
    float currentY = chef[0].getY();
    float currentX = chef[0].getX();
    world.step(1 / 60f, 6, 2);
    chef[0].updateSpriteFromInput("south");
    world.step(1 / 60f, 6, 2);
    chef[0].updateSpriteFromInput("south");
    assertTrue("The y position of the chef should be less ",chef[0].getY() < currentY);
    assertEquals("The x position of the chef should be the same ", chef[0].getX(), currentX, 0.0);
  }

  /**
   * Tests that the chef can move left.
   * @author Jack Vickers
   */
  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
  @Test
  public void testChefMoveLeft() {
    instantiateWorldAndChefs();
    float currentX = chef[0].getX();
    float currentY = chef[0].getY();
    world.step(1 / 60f, 6, 2);
    chef[0].updateSpriteFromInput("west");
    world.step(1 / 60f, 6, 2);
    chef[0].updateSpriteFromInput("west");
    assertTrue("The x position of the chef should be less ",chef[0].getX() < currentX);
    assertEquals("The y position of the chef should be the same ", chef[0].getY(), currentY, 0.0);
  }

  /**
   * Tests that the chef can move right.
   * @author Jack Vickers
   */
  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
  @Test
  public void testChefMoveRight() {
    instantiateWorldAndChefs();
    float currentX = chef[0].getX();
    float currentY = chef[0].getY();
    world.step(1 / 60f, 6, 2);
    chef[0].updateSpriteFromInput("east");
    world.step(1 / 60f, 6, 2);
    chef[0].updateSpriteFromInput("east");
    assertTrue("The x position of the chef should be greater ",chef[0].getX() > currentX);
    assertEquals("The y position of the chef should be the same ", chef[0].getY(), currentY, 0.0);
  }

}
