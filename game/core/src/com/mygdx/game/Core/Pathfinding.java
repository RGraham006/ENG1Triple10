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


public class Pathfinding
{

  int GridX;
  int GridY;
  int gridSize;

  OccupationID[] Cells;



  private   int  getIndex(int x, int y)
  {
    return x + y * GridX;
  }

  private  OccupationID getOccupation(int x, int y)
  {
    return Cells[getIndex(x,y)];
  }

  private float Euclidian(int ix, int iy, int jx, int jy){
    return (float) Math.sqrt(Math.pow(jx-ix,2) + Math.pow(jy-iy,2));
  }

  private float Manhatten(int ix, int iy, int jx, int jy)
  {
    return Math.abs(ix-jx) + Math.abs(iy-jy);
  }

  public Pathfinding(int gridSize, int GridX, int GridY)
  {

    this.GridX = GridX/gridSize;
    this.GridY = GridY/gridSize;
    this.gridSize = gridSize;

    Cells = new OccupationID[GridX*GridY];

    for (int i = 0; i < Cells.length; i++) {
      Cells[i] = OccupationID.Empty;
    }

  }

  private float DistanceTesting(int ix, int iy, int jx, int jy, DistanceTest test){
      if(test == DistanceTest.Euclidean)
        return Euclidian(ix,iy,jx,jy);
      return Manhatten(ix,iy,jx,jy);
  }

  public void addStaticObject(int x, int y, int width, int height)
  {
    int _x = x;
    int _y = y;


    x /= gridSize;
    y /= gridSize;

    _x -= x * gridSize;
    _y -= y * gridSize;

    width  = (int)Math.ceil((double)(width + _x)/gridSize);
    height = (int)Math.ceil((double)(height + _y)/gridSize);

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Cells[getIndex(x+i,y+j)] = OccupationID.Filled;
      }
    }

  }


  private  boolean LegalMove(int x, int y, int index)
  {
    if(!(x>=0 && x< GridX))
      return false;
    if(!(y>=0 && y< GridY))
      return false;

      return Cells[index] == OccupationID.Empty;



  }


  public List<Vector2> FindPath(int x, int y, int goalX, int goalY, final DistanceTest distanceTest){
    HashMap<Integer, PathfindingCell> ReachedCells = new HashMap<>();

    x = x/gridSize;
    y = y/gridSize;

    int _goalX = goalX;
    int _goalY = goalY;

    goalX = goalX/gridSize;
    goalY = goalY/gridSize;

    PriorityQueue<PathfindingCell> frontier = new PriorityQueue<>();

    frontier.add(new PathfindingCell(x,y, getIndex(x,y), DistanceTesting(x,y,goalX,goalY,distanceTest),0));

    PathfindingCell cell = null;
    int nx;
    int ny;
    int ndex;
    float nDist = 0;
    PathfindingCell ncell;
    boolean Found = false;
    while(frontier.size()>0)
    {
      cell = frontier.remove();

      if(cell.x == goalX && cell.y == goalY)
      {
        Found = true;
        break;
      }

      if(cell.x-1 >= 0)
      {
        nx = cell.x -1;
        ny = cell.y;

        ndex = getIndex(nx,ny);

        if(LegalMove(nx,ny,ndex)) {
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
      if(cell.x+1 < GridX){
        nx = cell.x + 1;
        ny = cell.y;

        ndex = getIndex(nx,ny);
        if(LegalMove(nx,ny,ndex)) {

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

      if(cell.y-1 >= 0){
        nx = cell.x;
        ny = cell.y - 1;

        ndex = getIndex(nx,ny);
        if(LegalMove(nx,ny,ndex)) {

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
      if(cell.y+1 < GridY) {
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


    if(!Found)
    {
      float MaxDistance = DistanceTesting(x, y, goalX,goalY,distanceTest);;
      for (PathfindingCell tcell: ReachedCells.values())
      {
      float distance =   DistanceTesting(tcell.x,tcell.y, goalX,goalY,distanceTest);


        if(distance<MaxDistance)
        {
          cell = tcell;
          MaxDistance = distance;
        }

      }


      path.add(new Vector2(cell.x*gridSize,cell.y*gridSize));

    } else {
      path.add(new Vector2(_goalX,_goalY));

    }


    while(cell.parent != null)
    {
      cell = cell.parent;
      if(cell.parent != null || !Found)
      path.add(new Vector2(cell.x*gridSize,cell.y*gridSize));
    }



    Collections.reverse(path);
    return path;




  }

}

