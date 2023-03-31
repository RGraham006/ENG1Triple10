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
import com.mygdx.game.Stations.ChopStation;
import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import piazzapanictests.tests.GdxTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the toaster station.
 *
 * @author Hubert Solecki
 */

@RunWith(GdxTestRunner.class)
public class ToasterStationTests extends MasterTestClass {

  @Test
  public void testPass() {
    assertTrue(true);
  }
}
