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

@RunWith(GdxTestRunner.class)
public class StationsMasterTestClass {
    // Array of chefs
    private static ArrayList<TextureAtlas> chefAtlasArray = new ArrayList<TextureAtlas>();
    public Chef[] chef;
    public World world;
    public List<GameObject> Stations;
    public ChopStation chopStation;
    boolean instantiated = false;

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

    /**
     * Creates the world and chopping station. Also creates the recipe dictionary and
     * gameobjectmanager.
     * @author Jack Vickers
     */
    private void instantiateWorldAndChoppingStation() {
//    instantiated = true;
        world = new World(new Vector2(0, 0), true); // creates world
        TiledMap map;
        map = new TmxMapLoader().load("PiazzaPanicMap.tmx"); // loads map
        MapLayer chopping = map.getLayers().get(5); // gets chopping layer
        MapObject object = chopping.getObjects().getByType(RectangleMapObject.class)
                .get(0); // gets chopping object
        Rectangle rect = ((RectangleMapObject) object).getRectangle(); // gets chopping rectangle

//    buildObject(world, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), "Static",
//        name);
//    Stations = new LinkedList();
        GameObject Chop = new GameObject(null); // creates chopping game object
        Chop.setPosition(rect.getX(),
                rect.getY()); // sets chopping position (this must be done to avoid null pointer exception)
        Chop.setWidthAndHeight(rect.getWidth(),
                rect.getHeight()); // sets chopping width and height (this must be done to avoid null pointer exception)
        chopStation = new ChopStation(); // creates chopping station
        Chop.attachScript(chopStation); // attaches chopping station to chopping game object
//    Stations.add(Chop);
        new RecipeDict(); // creates recipe dictionary
        RecipeDict.recipes.implementRecipes(); // implements recipes
    }
}
