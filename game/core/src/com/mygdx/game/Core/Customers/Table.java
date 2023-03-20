package com.mygdx.game.Core.Customers;
import com.badlogic.gdx.math.Vector2;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Table
{

  Vector2 position;

   List<Vector2> seats = new LinkedList<>();

   int currentlySeated = 0;
  int offset = 0;
  int Radius;
  public Table(Vector2 pos, int Radius){
    position = pos;
    this.Radius = Radius;
  }

  public void DesignateSeating(int count, Random rand){
    currentlySeated = 0;

    offset = rand.nextInt(count);

    seats = new LinkedList<>();

    seats.add(new Vector2(1*Radius,0));
    seats.add(new Vector2(-1*Radius,0));
    seats.add(new Vector2(0,1*Radius));
    seats.add(new Vector2(0,-1*Radius));



  }


  public Vector2 GetNextSeat(){
    Vector2 seat =seats.get((offset + currentlySeated)%seats.size());
    currentlySeated++;
    return seat.add(position);
  }

  public boolean isFree(){
    return seats.size()==0;
  }

  public void relinquish(){
    seats = new LinkedList<>();
  }
}