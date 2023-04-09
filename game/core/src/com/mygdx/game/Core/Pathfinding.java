package com.mygdx.game.Core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;


public class Pathfinding {

  int GridX;
  int GridY;
  int gridSize;

  OccupationID[] Cells;


  /**
   * Transforms 2D space into linear
   * @param x
   * @param y
   * @return
   * @author Felix Seanor
   */
  private int getIndex(int x, int y) {
    return x + y * GridX;
  }


  /**
   * Gets the current occupation the cell.
   * @param x
   * @param y
   * @return
   * @author Felix Seanor
   */
  private OccupationID getOccupation(int x, int y) {
    return Cells[getIndex(x, y)];
  }

  /**
   *
   * @param ix
   * @param iy
   * @param jx
   * @param jy
   * @return sqrt(I^2 - J^2)
   */
  private float Euclidian(int ix, int iy, int jx, int jy) {
    return (float) Math.sqrt(Math.pow(jx - ix, 2) + Math.pow(jy - iy, 2));
  }

  /**
   * Returns manhatten distance
   * @param ix
   * @param iy
   * @param jx
   * @param jy
   * @return |I-J|
   * @author Felix Seanor
   *
   */
  private float Manhatten(int ix, int iy, int jx, int jy) {
    return Math.abs(ix - jx) + Math.abs(iy - jy);
  }

  /**
   * Creates a pathfinding module with given parameters
   * @param gridSize size of each grid cell. Should be <= Pixel size
   * @param GridX X dimensions size
   * @param GridY Y dimensions size
   * @author Felix Seanor
   */
  public Pathfinding(int gridSize, int GridX, int GridY) {

    this.GridX = GridX / gridSize;
    this.GridY = GridY / gridSize;
    this.gridSize = gridSize;

    Cells = new OccupationID[GridX * GridY];

    for (int i = 0; i < Cells.length; i++) {
      Cells[i] = OccupationID.Empty;
    }

  }

  /**
   * returns the distance from i to j given a valid Distance equation.
   * @param ix
   * @param iy
   * @param jx
   * @param jy
   * @param test
   * @return distance
   * @author Felix Seanor
   */
  private float DistanceTesting(int ix, int iy, int jx, int jy, DistanceTest test) {
    if (test == DistanceTest.Euclidean) {
      return Euclidian(ix, iy, jx, jy);
    }
    return Manhatten(ix, iy, jx, jy);
  }

  public void addStaticObject(int x, int y, int width, int height) {
    int _x = x;
    int _y = y;

    x /= gridSize;
    y /= gridSize;

    _x -= x * gridSize;
    _y -= y * gridSize;

    width = (int) Math.ceil((double) (width + _x) / gridSize);
    height = (int) Math.ceil((double) (height + _y) / gridSize);

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Cells[getIndex(x + i, y + j)] = OccupationID.Filled;
      }
    }

  }

  /**
   * Checks if a move is legal.
   *
   * @param x x coordinate
   * @param y y coordinate
   * @param index index of the cell
   * @return true if legal, false if not
   * @author Felix Seanor
   */
  private boolean LegalMove(int x, int y, int index) {
    if (!(x >= 0 && x < GridX)) {
      return false;
    }
    if (!(y >= 0 && y < GridY)) {
      return false;
    }

    return Cells[index] == OccupationID.Empty;


  }

  public List<Vector2> FindPath(int x, int y, int goalX, int goalY,
      final DistanceTest distanceTest) {
    HashMap<Integer, PathfindingCell> ReachedCells = new HashMap<>();

    if(x == goalX && y == goalY)
      return new LinkedList<>();

    int _x = x;
    int _y = y;
    x = x / gridSize;
    y = y / gridSize;

    int _goalX = goalX;
    int _goalY = goalY;

    goalX = goalX / gridSize;
    goalY = goalY / gridSize;

    PriorityQueue<PathfindingCell> frontier = new PriorityQueue<>();

//    System.out.println("First: " + getIndex(x, y));
    frontier.add(
        new PathfindingCell(x, y, getIndex(x, y), DistanceTesting(x, y, goalX, goalY, distanceTest),
            0));

    PathfindingCell cell = null;
    int nx;
    int ny;
    int ndex;
    float nDist = 0;
    PathfindingCell ncell;
    boolean Found = false;
    while (frontier.size() > 0) {
      cell = frontier.remove();

      if (cell.x == goalX && cell.y == goalY) {
        Found = true;
        break;
      }

      if (cell.x - 1 >= 0) { // left
        nx = cell.x - 1;
        ny = cell.y;

        ndex = getIndex(nx, ny);

        if (LegalMove(nx, ny, ndex)) {
          if (ReachedCells.containsKey(ndex)) {
            ncell = ReachedCells.get(ndex);
            if (cell.PathCost + 1 < ncell.PathCost) {
              //swap
              ncell.parent = cell;
              ncell.PathCost = cell.PathCost + 1;
            }
          } else {
            ncell = new PathfindingCell(nx, ny, ndex,
                DistanceTesting(nx, ny, goalX, goalY, distanceTest), cell.PathCost + 1);
            ncell.parent = cell;
            frontier.add(ncell);
            ReachedCells.put(ndex, ncell);
          }
        }
      }
      if (cell.x + 1 < GridX) { // right
        nx = cell.x + 1;
        ny = cell.y;

        ndex = getIndex(nx, ny);
        if (LegalMove(nx, ny, ndex)) {

          if (ReachedCells.containsKey(ndex)) {

            ncell = ReachedCells.get(ndex);

            if (cell.PathCost + 1 < ncell.PathCost) {
              //swap
              ncell.parent = cell;
              ncell.PathCost = cell.PathCost + 1;
            }


          } else {
            ncell = new PathfindingCell(nx, ny, ndex,
                DistanceTesting(nx, ny, goalX, goalY, distanceTest), cell.PathCost + 1);
            ncell.parent = cell;
            frontier.add(ncell);
            ReachedCells.put(ndex, ncell);
          }
        }
      }

      if (cell.y - 1 >= 0) { // down
        nx = cell.x;
        ny = cell.y - 1;

        ndex = getIndex(nx, ny);
        if (LegalMove(nx, ny, ndex)) {

          if (ReachedCells.containsKey(ndex)) {

            ncell = ReachedCells.get(ndex);

            if (cell.PathCost + 1 < ncell.PathCost) {
              //swap
              ncell.parent = cell;
              ncell.PathCost = cell.PathCost + 1;
            }


          } else {
            ncell = new PathfindingCell(nx, ny, ndex,
                DistanceTesting(nx, ny, goalX, goalY, distanceTest), cell.PathCost + 1);
            ncell.parent = cell;
            frontier.add(ncell);
            ReachedCells.put(ndex, ncell);
          }
        }
      }
      if (cell.y + 1 < GridY) { // up
        nx = cell.x;
        ny = cell.y + 1;

        ndex = getIndex(nx, ny);

        if (LegalMove(nx, ny, ndex)) {

          if (ReachedCells.containsKey(ndex)) {

            ncell = ReachedCells.get(ndex);

            if (cell.PathCost + 1 < ncell.PathCost) {
              //swap
              ncell.parent = cell;
              ncell.PathCost = cell.PathCost + 1;
            }


          } else {
            ncell = new PathfindingCell(nx, ny, ndex,
                DistanceTesting(nx, ny, goalX, goalY, distanceTest), cell.PathCost + 1);
            ncell.parent = cell;
            frontier.add(ncell);
            ReachedCells.put(ndex, ncell);
          }
        }
      }


    }
    List<Vector2> path = new LinkedList<>();

    if (!Found) {
      float MaxDistance = DistanceTesting(x, y, goalX, goalY, distanceTest);
      ;
      for (PathfindingCell tcell : ReachedCells.values()) {
        float distance = DistanceTesting(tcell.x, tcell.y, goalX, goalY, distanceTest);

        if (distance < MaxDistance) {
          cell = tcell;
          MaxDistance = distance;
        }

      }

      path.add(new Vector2(cell.x * gridSize, cell.y * gridSize));

    } else {
      path.add(new Vector2(_goalX, _goalY));

    }

    while (cell.parent != null) {
      cell = cell.parent;
      if (cell.parent != null || !Found) {
//        System.out.println(
//            cell + " : " + getIndex(cell.x, cell.y) + Cells[getIndex(cell.x, cell.y)] + " : "
//                + LegalMove(cell.x, cell.y, getIndex(cell.x, cell.y)));
        path.add(new Vector2(cell.x * gridSize, cell.y * gridSize));

      }
    }
if(path.size()>1)
    path.remove(path.size() - 1);
    //path.add(new Vector2(_x, _y));

    Collections.reverse(path);
    return path;


  }

}

