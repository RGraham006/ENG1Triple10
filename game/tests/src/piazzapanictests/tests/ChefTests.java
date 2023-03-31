package piazzapanictests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Chef;
import com.mygdx.game.Core.BlackSprite;
import com.mygdx.game.Core.GameObject;
import com.mygdx.game.Core.GameObjectManager;
import com.mygdx.game.Core.Interactions.Interactable;
import com.mygdx.game.Core.MasterChef;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;
import com.mygdx.game.Stations.FoodCrate;
import java.util.ArrayList;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests to do with the Chefs.
 *
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
   *
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
   *
   * @author Jack Vickers
   */
  private void generateChefArray() {
    String filename;
    TextureAtlas chefAtlas;
    for (int i = 1; i < 4; i++) {
      filename = "Chefs/Chef" + i + "/chef" + i + ".txt";
      chefAtlas = new TextureAtlas(Gdx.files.internal(filename));
      chefAtlasArray.add(chefAtlas);
    }
  }

  /**
   * Returns the chef atlas array.
   *
   * @return chefAtlasArray
   * @author Jack Vickers
   */
  private static ArrayList<TextureAtlas> getChefAtlasArray() {
    return chefAtlasArray;
  }

//  /**
//   * Tests that the chef can move up.
//   * @author Jack Vickers
//   */
//  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
//  @Test
//  public void testChefMoveUp() {
//    instantiateWorldAndChefs();
//    float currentY = chef[0].getY();
//    float currentX = chef[0].getX();
//    world.step(1 / 60f, 6, 2);
//    chef[0].updateSpriteFromInput("north");
//    world.step(1 / 60f, 6, 2);
//    chef[0].updateSpriteFromInput("north");
//    assertTrue("The y position of the chef should be greater ", chef[0].getY() > currentY);
//    assertEquals("The x position of the chef should be the same ", chef[0].getX(), currentX, 0.0);
//  }
//
//  /**
//   * Tests that the chef can move down.
//   * @author Jack Vickers
//   */
//  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
//  @Test
//  public void testChefMoveDown() {
//    instantiateWorldAndChefs();
//    float currentY = chef[0].getY();
//    float currentX = chef[0].getX();
//    world.step(1 / 60f, 6, 2);
//    chef[0].updateSpriteFromInput("south");
//    world.step(1 / 60f, 6, 2);
//    chef[0].updateSpriteFromInput("south");
//    assertTrue("The y position of the chef should be less ",chef[0].getY() < currentY);
//    assertEquals("The x position of the chef should be the same ", chef[0].getX(), currentX, 0.0);
//  }
//
//  /**
//   * Tests that the chef can move left.
//   * @author Jack Vickers
//   */
//  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
//  @Test
//  public void testChefMoveLeft() {
//    instantiateWorldAndChefs();
//    float currentX = chef[0].getX();
//    float currentY = chef[0].getY();
//    world.step(1 / 60f, 6, 2);
//    chef[0].updateSpriteFromInput("west");
//    world.step(1 / 60f, 6, 2);
//    chef[0].updateSpriteFromInput("west");
//    assertTrue("The x position of the chef should be less ",chef[0].getX() < currentX);
//    assertEquals("The y position of the chef should be the same ", chef[0].getY(), currentY, 0.0);
//  }
//
//  /**
//   * Tests that the chef can move right.
//   * @author Jack Vickers
//   */
//  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
//  @Test
//  public void testChefMoveRight() {
//    instantiateWorldAndChefs();
//    float currentX = chef[0].getX();
//    float currentY = chef[0].getY();
//    world.step(1 / 60f, 6, 2);
//    chef[0].updateSpriteFromInput("east");
//    world.step(1 / 60f, 6, 2);
//    chef[0].updateSpriteFromInput("east");
//    assertTrue("The x position of the chef should be greater ",chef[0].getX() > currentX);
//    assertEquals("The y position of the chef should be the same ", chef[0].getY(), currentY, 0.0);
//  }

  /**
   * Tests that the chef can drop an item.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testDropItem() {
    instantiateWorldAndChefs();
    chef[0].GiveItem(new Item(ItemEnum.Mince)); // Give the chef an item
    int inventorySize = chef[0].getInventoryCount(); // Get the size of the inventory
    chef[0].DropItem(); // Drop the item
    assertEquals("The chef should have 1 less item in their inventory after dropping it",
        inventorySize - 1, chef[0].getInventoryCount());

  }

  /**
   * Tests that the chef can't drop an item if they don't have one.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testDropEmptyHand() {
    instantiateWorldAndChefs();
    chef[0].DropItem(); // Attempt to drop an item
    assertEquals("The chef should still have 0 items in their hand",
        0, chef[0].getInventoryCount());
  }

  /**
   * Tests that the chef can pick up an item.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testPickupItem() {
    instantiateWorldAndChefs();
    Item itemToGive = new Item(ItemEnum.Mince);
    chef[0].GiveItem(itemToGive);
    assertEquals("The chef should have mince at the top of their inventory stack",
        new Item(ItemEnum.Mince),
        chef[0].getInventory().peek());
  }

  /**
   * Tests that the chef can't pick up an item if their inventory is full.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testPickupFullInventory() {
    instantiateWorldAndChefs();
    Item item1 = new Item(ItemEnum.Mince);
    Item item2 = new Item(ItemEnum.Lettuce);
    chef[0].GiveItem(item1);
    chef[0].GiveItem(item1);
    chef[0].GiveItem(item1);
    chef[0].GiveItem(item2);
    Stack<Item> Items = new Stack<Item>();
    Items.push(item1);
    Items.push(item1);
    Items.push(item1);
    assertTrue("The chef inventory should contain the first 3 items given to it and not the 4th",
        chef[0].getInventory().equals(Items));
  }

  /**
   * Tests that the chef can pick up minced meat from the pantry.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testPickUpPantryMince() {
    instantiateWorldAndChefs();
    Item itemToGive = new FoodCrate(
        ItemEnum.Mince).RetrieveItem(); // Creates a mince food create and gets the mince from it
    chef[0].GiveItem(itemToGive); // Gives the mince to the chef
    assertEquals("The chef should have mince at the top of their inventory stack",
        new Item(ItemEnum.Mince),
        chef[0].getInventory().peek());
  }

  /**
   * Tests that the chef can pick up lettuce from the pantry.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testPickupPantryLettuce() {
    instantiateWorldAndChefs();
    Item itemToGive = new FoodCrate(
        ItemEnum.Lettuce).RetrieveItem(); // Creates a lettuce food crate and gets the lettuce from it
    chef[0].GiveItem(itemToGive); // Gives the lettuce to the chef
    assertEquals("The chef should have lettuce at the top of their inventory stack",
        new Item(ItemEnum.Lettuce),
        chef[0].getInventory().peek());
  }

  /**
   * Tests that the chef can pick up bread from the pantry.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testPickupPantryBread() {
    instantiateWorldAndChefs();
    Item itemToGive = new FoodCrate(
        ItemEnum.Buns).RetrieveItem(); // Creates a buns food crate and gets the buns from it
    chef[0].GiveItem(itemToGive); // Gives the buns to the chef
    assertEquals("The chef should have bread at the top of their inventory stack",
        new Item(ItemEnum.Buns),
        chef[0].getInventory().peek());
  }

  /**
   * Tests that the chef can pick up a tomato from the pantry.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testPickupPantryTomato() {
    instantiateWorldAndChefs();
    Item itemToGive = new FoodCrate(
        ItemEnum.Tomato).RetrieveItem(); // Creates a tomato food crate and gets the tomato from it
    chef[0].GiveItem(itemToGive); // Gives the tomato to the chef
    assertEquals("The chef should have tomato at the top of their inventory stack",
        new Item(ItemEnum.Tomato),
        chef[0].getInventory().peek());
  }

  /**
   * Tests that the chef can pick up cheese from the pantry.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testPickupPantryCheese() {
    instantiateWorldAndChefs();
    Item itemToGive = new FoodCrate(
        ItemEnum.Cheese).RetrieveItem(); // Creates a cheese food crate and gets the cheese from it
    chef[0].GiveItem(itemToGive); // Gives the cheese to the chef
    assertEquals("The chef should have cheese at the top of their inventory stack",
        new Item(ItemEnum.Cheese),
        chef[0].getInventory().peek());
  }

  /**
   * Tests that the chef can pick up an onion from the pantry.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testPickupPantryOnion() {
    instantiateWorldAndChefs();
    Item itemToGive = new FoodCrate(
        ItemEnum.Onion).RetrieveItem(); // Creates an onion food crate and gets the onion from it
    chef[0].GiveItem(itemToGive); // Gives the onion to the chef
    assertEquals("The chef should have onion at the top of their inventory stack",
        new Item(ItemEnum.Onion),
        chef[0].getInventory().peek());
  }

  /**
   * Tests that the chef can pick up a potato from the pantry.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testPickupPantryPotato() {
    instantiateWorldAndChefs();
    Item itemToGive = new FoodCrate(
        ItemEnum.Potato).RetrieveItem(); // Creates a potato food crate and gets the potato from it
    chef[0].GiveItem(itemToGive); // Gives the potato to the chef
    assertEquals("The chef should have potato at the top of their inventory stack",
        new Item(ItemEnum.Potato),
        chef[0].getInventory().peek());
  }

  /**
   * Tests that the chef can pick up dough from the pantry.
   *
   * @author Jack Vickers
   * @date 26/03/2023
   */
  @Test
  public void testPickupPantryDough() {
    instantiateWorldAndChefs();
    Item itemToGive = new FoodCrate(
        ItemEnum.Dough).RetrieveItem(); // Creates a dough food crate and gets the dough from it
    chef[0].GiveItem(itemToGive); // Gives the dough to the chef
    assertEquals("The chef should have dough at the top of their inventory stack",
        new Item(ItemEnum.Dough),
        chef[0].getInventory().peek());
  }

  /**
   * Tests that the chef cannot pick up from an empty tile with no items present.
   *
   * @author Hubert Solecki
   * @date 31/03/2023
   */
  @Test
  public void testPickupEmptyTile() {
    instantiateWorldAndChefs();
    int chefInventoryCountBefore = chef[0].getInventoryCount();
    chef[0].FetchItem();
    int chefInventoryCountAfter = chef[0].getInventoryCount();
    assertEquals("The chef's inventory is still empty after attempting to pick up an item from an empty tile",
            chefInventoryCountBefore, chefInventoryCountAfter);
  }

}
