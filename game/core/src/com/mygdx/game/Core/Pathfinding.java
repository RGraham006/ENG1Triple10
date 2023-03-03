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



    int  getIndex(int x, int y)
  {
    return x + y * GridX;
  }

  OccupationID getOccupation(int x, int y)
  {
    return Cells[getIndex(x,y)];
  }

  float Euclidian(int ix, int iy, int jx, int jy){
    return (float) Math.sqrt(Math.pow(jx-ix,2) + Math.pow(jy-iy,2));
  }

  float Manhatten(int ix, int iy, int jx, int jy)
  {
    return Math.abs(ix-jx) + Math.abs(iy-jy);
  }

  public Pathfinding(int gridSize, int GridX, int GridY)
  {

    this.GridX = GridX;
    this.GridY = GridY;
    this.gridSize = gridSize;


  }

  public float DistanceTesting(int ix, int iy, int jx, int jy, DistanceTest test){
      if(test == DistanceTest.Euclidean)
        return Euclidian(ix,iy,jx,jy);
      return Manhatten(ix,iy,jx,jy);
  }


  public List<Vector2> FindPath(int x, int y, final int goalX, final int goalY, final DistanceTest distanceTest){
    HashMap<Integer, PathfindingCell> ReachedCells = new HashMap<>();


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

        if(ReachedCells.containsKey(ndex)){

          ncell = ReachedCells.get(ndex);

          if(cell.PathCost + 1 < ncell.PathCost){
            //swap
            ncell.parent = cell;
            ncell.PathCost = cell.PathCost + 1;
          }

        } else {
          ncell = new PathfindingCell(nx,ny, ndex, DistanceTesting(nx,ny,goalX,goalY,distanceTest),cell.PathCost+1);
          ncell.parent = cell;
          frontier.add(ncell);
          ReachedCells.put(ndex,ncell);

        }
      }
      if(cell.x+1 < GridX){
        nx = cell.x + 1;
        ny = cell.y;

        ndex = getIndex(nx,ny);

        if(ReachedCells.containsKey(ndex)){

          ncell = ReachedCells.get(ndex);

          if(cell.PathCost + 1 < ncell.PathCost){
            //swap
            ncell.parent = cell;
            ncell.PathCost = cell.PathCost + 1;
          }


        }else {
          ncell = new PathfindingCell(nx,ny, ndex, DistanceTesting(nx,ny,goalX,goalY,distanceTest),cell.PathCost+1);
          ncell.parent = cell;
          frontier.add(ncell);
          ReachedCells.put(ndex,ncell);
        }
      }

      if(cell.y-1 >= 0){
        nx = cell.x;
        ny = cell.y - 1;

        ndex = getIndex(nx,ny);

        if(ReachedCells.containsKey(ndex)){

          ncell = ReachedCells.get(ndex);

          if(cell.PathCost + 1 < ncell.PathCost){
            //swap
            ncell.parent = cell;
            ncell.PathCost = cell.PathCost + 1;
          }


        }else {
          ncell = new PathfindingCell(nx,ny, ndex, DistanceTesting(nx,ny,goalX,goalY,distanceTest),cell.PathCost+1);
          ncell.parent = cell;
          frontier.add(ncell);
          ReachedCells.put(ndex,ncell);
        }
      }
      if(cell.y+1 < GridY){
        nx = cell.x;
        ny = cell.y + 1;

        ndex = getIndex(nx,ny);
        if(ReachedCells.containsKey(ndex)){

          ncell = ReachedCells.get(ndex);

          if(cell.PathCost + 1 < ncell.PathCost){
            //swap
            ncell.parent = cell;
            ncell.PathCost = cell.PathCost + 1;
          }


        }else {
          ncell = new PathfindingCell(nx,ny, ndex, DistanceTesting(nx,ny,goalX,goalY,distanceTest),cell.PathCost+1);
          ncell.parent = cell;
          frontier.add(ncell);
          ReachedCells.put(ndex,ncell);
        }
      }





    }


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



    }

    List<Vector2> path = new LinkedList<>();
    path.add(new Vector2(cell.x,cell.y));

    while(cell.parent != null)
    {
      cell = cell.parent;
      path.add(new Vector2(cell.x,cell.y));
    }
    Collections.reverse(path);
    return path;




  }

}

