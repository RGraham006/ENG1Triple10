package piazzapanictests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;;
import com.badlogic.gdx.maps.MapLayer;
import com.mygdx.game.Core.GameObject;
import com.mygdx.game.Core.GameObjectManager;
import com.mygdx.game.Core.Interactions.Interactable;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;

import com.mygdx.game.RecipeAndComb.CombinationDict;
import com.mygdx.game.RecipeAndComb.RecipeDict;
import com.mygdx.game.Stations.AssemblyStation;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import com.mygdx.game.Core.GameObjectManager;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the assembly station.
 *
 * @author Jack Vickers
 */
@RunWith(GdxTestRunner.class)
public class AssemblyStationTests {

  public World world;
  public AssemblyStation assemblyStation;

  /**
   * Creates the world and assembly station. Also creates the recipe dictionary and
   * gameobjectmanager.
   *
   * @author Jack Vickers
   */
  public void instantiateWorldAndAssemblyStation() {
    world = new World(new Vector2(0, 0), true); // creates world
    TiledMap map;
    map = new TmxMapLoader().load("PiazzaPanicMap.tmx"); // loads map
    MapLayer counter = map.getLayers().get(3); // gets counter layer
    MapObject object = counter.getObjects().getByType(RectangleMapObject.class)
        .get(0); // gets counter object
    Rectangle rect = ((RectangleMapObject) object).getRectangle(); // gets chopping rectangle
    GameObject Assemble = new GameObject(null);
    Assemble.setPosition(rect.getX(), rect.getY());
    Assemble.setWidthAndHeight(rect.getWidth(), rect.getHeight());
    assemblyStation = new AssemblyStation();
    Assemble.attachScript(assemblyStation);
    new RecipeDict(); // creates recipe dictionary
    RecipeDict.recipes.implementRecipes(); // implements recipes
  }

  /**
   * Tests that an item can be placed on an assembly station.
   *
   * @author Jack Vickers
   */
  @Test
  public void testPlaceItem() {
    if (GameObjectManager.objManager == null) {
      // creates game object manager which makes sure that the game object manager is not null when it is needed
      new GameObjectManager();
    }
    instantiateWorldAndAssemblyStation(); // creates world and assembly station
    Item item = new Item(ItemEnum.Buns);
    assemblyStation.GiveItem(item); // gives a bun to the assembly station
    assertEquals(assemblyStation.getIngredients().get(0),
        item); // checks that the bun is in the inventory
  }

  /**
   * Tests that an item can be successfully removed from the station.
   */
  @Test
  public void testPickupItem() {
    if (GameObjectManager.objManager == null) {
      // creates game object manager which makes sure that the game object manager is not null when it is needed
      new GameObjectManager();
    }
    instantiateWorldAndAssemblyStation(); // creates world and assembly station

    Item item = new Item(ItemEnum.Buns);
    assemblyStation.GiveItem(item); // gives a bun to the assembly station
    assertEquals(assemblyStation.getIngredients().get(0),
        item); // checks that the bun is on the assembly station
    Item retrievedItem = assemblyStation.RetrieveItem();

    assertEquals("The item retrieved from the station should match the last item put on it", item,
        retrievedItem);

    assertTrue("The station should be empty after retrieving this item",
        assemblyStation.getIngredients().isEmpty());

    assertEquals("There should be no gameOjects on the station",
        assemblyStation.getHeldItems().size(), 0);

    assertNull("There should be no game objects on the station",
        assemblyStation.getHeldItem());
  }

  /**
   * Tests that nothing happens when an item is attempted to be removed from an empty assembly
   * station.
   */
  @Test
  public void testPickupWhenEmpty() {
    if (GameObjectManager.objManager == null) {
      // creates game object manager which makes sure that the game object manager is not null when it is needed
      new GameObjectManager();
    }
    instantiateWorldAndAssemblyStation(); // creates world and assembly station

    assertNull("There should be no game objects on the station to start with",
        assemblyStation.getHeldItem());

    assertNull(
        "Attempting to retrieve an item when there isn't one on the station should return null",
        assemblyStation.RetrieveItem());

    assertTrue("The station should still be empty",
        assemblyStation.getIngredients().isEmpty());

    assertEquals("There should be no gameOjects on the station",
        assemblyStation.getHeldItems().size(), 0);

    assertNull("There should be no game objects on the station",
        assemblyStation.getHeldItem());

  }

  /**
   * Tests that items can be placed on an assembly station after other items have been combined on
   * this station. Also tests that they can all be successfully removed from the station.
   *
   * @author Jack Vickers
   */
  @Test
  public void testPlaceItemAfterCombining() {
    if (GameObjectManager.objManager == null) {
      // creates game object manager which makes sure that the game object
      // manager is not null when it is needed
      new GameObjectManager();
    }
    new CombinationDict();
    CombinationDict.combinations.implementItems(); // creates combination dictionary
    instantiateWorldAndAssemblyStation(); // creates world and assembly station

    Item item1 = new Item(ItemEnum.CutLettuce);
    Item item2 = new Item(ItemEnum.CutTomato);
    Item item3 = new Item(ItemEnum.CutOnion);
    assemblyStation.GiveItem(item1);
    assemblyStation.GiveItem(item2);
    assemblyStation.combine(); // combines the cut lettuce and tomato into a lettuce tomato salad
    assemblyStation.GiveItem(
        item3); // gives a cut onion to the assembly station which can be combined with the current
    assemblyStation.GiveItem(item3);
    assemblyStation.GiveItem(item3);
    assertEquals("The first item on the station should be the combined food",
        assemblyStation.getIngredients().get(0),
        new Item(ItemEnum.LettuceTomatoSalad)); // checks that the lettuce is in the inventory
    assertEquals(assemblyStation.getIngredients().get(1),
        item3); // checks that the onion is the 2nd item in the inventory
    assertEquals(assemblyStation.getIngredients().get(2),
        item3); // checks that the onion is in the inventory
    assertEquals(assemblyStation.getIngredients().get(3),
        item3); // checks that the onion is in the inventory

    for (int i = 0; i < 4; i++) { // retrieves all items from the assembly station
      assemblyStation.RetrieveItem();
    }
    assertEquals("There should be no gameOjects on the station",
        assemblyStation.getHeldItems().size(), 0);

    assertNull("There should be no game objects on the station",
        assemblyStation.getHeldItem());
  }

  /**
   * Tests that complete meals can be placed on an assembly station and then successfully removed
   * from it.
   *
   * @author Jack Vickers
   */
  @Test
  public void placeFinishedMealsThenRemoveThem() {
    if (GameObjectManager.objManager == null) {
      // creates game object manager which makes sure that the game object
      // manager is not null when it is needed
      new GameObjectManager();
      instantiateWorldAndAssemblyStation(); // creates world and assembly station

      Item item = new Item(ItemEnum.TomatoOnionLettuceSalad);
      assemblyStation.GiveItem(item);
      assemblyStation.GiveItem(item);
      assemblyStation.GiveItem(item);
      assemblyStation.GiveItem(item);
      assertEquals("There should be 4 items on the station",
          assemblyStation.getHeldItems().size(), 4);
      for (int i = 0; i < 4; i++) { // retrieves all items from the assembly station
        assemblyStation.RetrieveItem();
      }
      assertEquals("There should be no ingredients on the station",
          assemblyStation.getIngredients().size(), 0);
      assertEquals("There should be no gameOjects on the station",
          assemblyStation.getHeldItems().size(), 0);
      assertNull("There should be no game objects on the station",
          assemblyStation.getHeldItem());

    }
  }

  /**
   * Tests that an assembly station can hold a maximum of 4 items.
   *
   * @author Jack Vickers
   */
  @Test
  public void testItemLimit() {
    if (GameObjectManager.objManager == null) {
      // creates game object manager which makes sure that the game object manager is not null when it is needed
      new GameObjectManager();
    }
    instantiateWorldAndAssemblyStation(); // creates world and assembly station
    Item item = new Item(ItemEnum.Buns);
    for (int i = 0; i < 4; i++) {
      assemblyStation.GiveItem(item); // gives 4 buns to the assembly station
    }
    assertEquals(assemblyStation.getIngredients().size(),
        4); // checks that the assembly station has 12 buns
    assemblyStation.GiveItem(item); // attempts to give a 5th bun to the assembly station
    assertEquals("There should be no more than 4 items allowed on an assembly station",
        assemblyStation.getIngredients().size(),
        4); // checks that the assembly station still has 4 buns
  }

  /**
   * Tests that items from different recipes cannot be combined on an assembly station.
   *
   * @author Jack Vickers
   */
  @Test
  public void testInvalidCombination() {
    if (GameObjectManager.objManager == null) {
      // creates game object manager which makes sure that the game object
      // manager is not null when it is needed
      new GameObjectManager();
    }
    new CombinationDict();
    CombinationDict.combinations.implementItems(); // creates combination dictionary
    instantiateWorldAndAssemblyStation(); // creates world and assembly station

    Item item1 = new Item(ItemEnum.CutLettuce);
    Item item2 = new Item(ItemEnum.CookedPatty);
    assemblyStation.GiveItem(item1);
    assemblyStation.GiveItem(item2);
    assemblyStation.combine(); // attempts to combine the cut lettuce
    // and cooked patty which is not a valid combination
    assertEquals("The items should still be separate on the assembly station",
        assemblyStation.getIngredients().get(0), item1);
    assertEquals("The items should still be separate on the assembly station",
        assemblyStation.getIngredients().get(1), item2);
  }

  /**
   * Tests that all valid combinations of items can be combined on an assembly station.
   *
   * @author Jack Vickers
   */
  @Test
  public void testValidCombinations() {
    String item1;
    String item2;
    int indexOfSpace;
    if (GameObjectManager.objManager == null) {
      // creates game object manager which makes sure that the game object
      // manager is not null when it is needed
      new GameObjectManager();
    }
    new CombinationDict();
    CombinationDict.combinations.implementItems(); // creates combination dictionary
    instantiateWorldAndAssemblyStation(); // creates world and assembly station

    for (String combination : CombinationDict.combinations.CombinationMap.keySet()) {
      indexOfSpace = combination.indexOf(
          " "); // gets the index of the space between the two words in the key of the combination map
      item1 = combination.substring(0, indexOfSpace); // gets the first item in the combination
      item2 = combination.substring(indexOfSpace + 1); // gets the second item in the combination
      Item item1Object = new Item(ItemEnum.valueOf(item1));
      Item item2Object = new Item(ItemEnum.valueOf(item2));
      assemblyStation.GiveItem(item1Object);
      assemblyStation.GiveItem(item2Object);
      assemblyStation.combine(); // combines the two items

      // checks that the first item on the station is the combined food
      assertTrue("The first item on the station should be the combined food",
          assemblyStation.getIngredients().get(0)
              .equals(new Item(CombinationDict.combinations.CombinationMap.get(combination))));

      assertTrue("There should only be one item on the station",
          assemblyStation.getIngredients().size() == 1);

      assemblyStation.RetrieveItem(); // retrieves the combined food
      assertTrue("There should be no items on the station",
          assemblyStation.getIngredients().isEmpty());

      assertEquals("There should be no gameOjects on the station",
          assemblyStation.getHeldItems().size(), 0);

      assertNull("There should be no game objects on the station",
          assemblyStation.getHeldItem());
    }
  }

}
