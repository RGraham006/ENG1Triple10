package piazzapanictests.tests;

import static org.junit.Assert.assertEquals;

import com.mygdx.game.Core.DistanceTest;
import com.mygdx.game.Core.Pathfinding;
import com.mygdx.game.GameScreen;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.badlogic.gdx.math.Vector2;



/**
Testing pathfinding
@author Felix Seanor
 **/
@RunWith(GdxTestRunner.class)

public class PathfindingTests {


  Pathfinding pathfinding;

  void SetUpPathfinding(){
    pathfinding = new Pathfinding(GameScreen.TILE_WIDTH/4,GameScreen.viewportWidth,GameScreen.viewportWidth);

  }


  @Test
  public void TestPathfindingNullMove(){
    SetUpPathfinding();
    List<Vector2> path = pathfinding.FindPath(0,0,0,0, DistanceTest.Euclidean);

    assertEquals("The path must be empty for a move of zero",new LinkedList<>(),path);
  }

  @Test
  public void TestPathfinding()
  {
    SetUpPathfinding();
    int stepSize = GameScreen.TILE_WIDTH/4;
    List<Vector2> pathA = pathfinding.FindPath(0,0,stepSize,0, DistanceTest.Euclidean);

    List<Vector2> PathAActual = new LinkedList<>();
    PathAActual.add(new Vector2(stepSize,0));
    assertEquals("The path must be have only one move upwards", PathAActual, pathA);

    List<Vector2> pathB = pathfinding.FindPath(0,0,stepSize*5,stepSize*5, DistanceTest.Euclidean);
    Vector2 End = pathB.get(pathB.size()-1);
    assertEquals("The path must reach the end goal", new Vector2(stepSize*5,stepSize*5), End);



  }

  @Test
  public void TestObsticalAvoidance(){
    SetUpPathfinding();
    int stepSize = GameScreen.TILE_WIDTH/4;

    pathfinding.addStaticObject(0,5*stepSize,stepSize*2,stepSize*2);

    List<Vector2> path = pathfinding.FindPath(0,0,0,10*stepSize, DistanceTest.Manhatten);

    Boolean Fails = path.contains(new Vector2(0,5*stepSize));

    assertEquals("The path must avoid the obstical", false, Fails);
    }
  }


