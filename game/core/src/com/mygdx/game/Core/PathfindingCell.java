package com.mygdx.game.Core;

import java.nio.file.Path;

public class PathfindingCell implements Comparable<PathfindingCell> {

  public float Heuristic;
  public float PathCost;

  public int x;
  public int y;
  public int Index;

  public PathfindingCell parent;

  /**
   * Creates a new pathfinding cell. This was written by Vickers so may be wrong.
   *
   * @param x The x position of the cell
   * @param y The y position of the cell
   * @param index The index of the cell
   * @param heuristic The heuristic value of the cell
   * @param path The path cost of the cell
   */
  public PathfindingCell(int x, int y, int index, float heuristic, float path) {
    this.x = x;
    this.y = y;

    Heuristic = heuristic;
    PathCost = path;
    Index = index;


  }

  public float score() {
    return Heuristic + PathCost;
  }

  @Override
  public int compareTo(PathfindingCell o) {
    if (score() > o.score()) {
      return 1;
    }
    if (score() < o.score()) {
      return -1;
    }
    return 0;
  }


}
