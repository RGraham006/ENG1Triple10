package piazzapanictests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Chef;
import com.badlogic.gdx.maps.MapLayer;
import com.mygdx.game.Core.BlackSprite;
import com.mygdx.game.Core.GameObject;
import com.mygdx.game.Core.GameObjectManager;
import com.mygdx.game.Core.Interactions.Interactable;
import com.mygdx.game.Core.MasterChef;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;

import com.mygdx.game.RecipeAndComb.RecipeDict;
import com.mygdx.game.Stations.ChopStation;
import com.mygdx.game.Stations.FoodCrate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import com.mygdx.game.Core.GameObjectManager;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the chopping station.
 *
 * @author Jack Vickers
 * @author Azzam Amirul Bahri
 */
@RunWith(GdxTestRunner.class)
public class ChopStationTests extends MasterTestClass {

  /**
   * Tests that an item can be removed from the chopping board when it is not being chopped.
   *
   * @author Jack Vickers
   */
  @Test
  public void testRemoveItemWhileNotChopping() {
    if (GameObjectManager.objManager == null) {
      // creates game object manager which makes sure that the game object manager is not null when it is needed
      new GameObjectManager();
    }
    instantiateWorldAndChoppingStation(); // creates chopping station
    chopStation.GiveItem(new Item(ItemEnum.Lettuce)); // gives lettuce to chopping station
    chopStation.RetrieveItem(); // attempts to retrieve item from chopping station
    assertNull("There should be no item on the chopping station", chopStation.returnItem());
  }

}
