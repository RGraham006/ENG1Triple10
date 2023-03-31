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

import com.mygdx.game.RecipeAndComb.RecipeDict;
import com.mygdx.game.Stations.AssemblyStation;
import com.mygdx.game.Stations.ChopStation;
import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import piazzapanictests.tests.GdxTestRunner;

class MasterTestClass {

  // Array of chefs
  private static ArrayList<TextureAtlas> chefAtlasArray = new ArrayList<TextureAtlas>();
  Chef[] chef;
  World world;
  ChopStation chopStation;
  AssemblyStation assemblyStation;

  /**
   * Instantiates the world and chefs so that these can be used in the tests.
   *
   * @author Jack Vickers
   */
  void instantiateWorldAndChefs() {
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
   * Needed to instantiate the chefs.
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
   * Needed to instantiate the chefs.
   *
   * @return chefAtlasArray
   * @author Jack Vickers
   */
  private static ArrayList<TextureAtlas> getChefAtlasArray() {
    return chefAtlasArray;
  }

  /**
   * Creates the world and chopping station. Also creates the recipe dictionary and
   * gameobjectmanager.
   *
   * @author Jack Vickers
   */
  void instantiateWorldAndChoppingStation() {
    world = new World(new Vector2(0, 0), true); // creates world
    TiledMap map;
    map = new TmxMapLoader().load("PiazzaPanicMap.tmx"); // loads map
    MapLayer chopping = map.getLayers().get(5); // gets chopping layer
    MapObject object = chopping.getObjects().getByType(RectangleMapObject.class)
        .get(0); // gets chopping object
    Rectangle rect = ((RectangleMapObject) object).getRectangle(); // gets chopping rectangle
    GameObject Chop = new GameObject(null); // creates chopping game object
    Chop.setPosition(rect.getX(),
        rect.getY()); // sets chopping position (this must be done to avoid null pointer exception)
    Chop.setWidthAndHeight(rect.getWidth(),
        rect.getHeight()); // sets chopping width and height (this must be done to avoid null pointer exception)
    chopStation = new ChopStation(); // creates chopping station
    Chop.attachScript(chopStation); // attaches chopping station to chopping game object
    new RecipeDict(); // creates recipe dictionary
    RecipeDict.recipes.implementRecipes(); // implements recipes
  }

  /**
   * Creates the world and assembly station. Also creates the recipe dictionary.
   *
   * @author Jack Vickers
   */
  public void instantiateWorldAndAssemblyStation() {
    world = new World(new Vector2(0, 0), true); // creates world
    TiledMap map;
    map = new TmxMapLoader().load("PiazzaPanicMap.tmx"); // loads map
    MapLayer counter = map.getLayers().get(3); // gets counter layer (layer 3 of the map)
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

}
