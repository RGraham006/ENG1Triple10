package com.mygdx.game.Core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Renderable {

  public int layer;

  public abstract void Render(SpriteBatch batch, float x, float y);
  public abstract void setSize(float x, float y);

  public Renderable(int layer){
    this.layer = layer;
  }

  public Renderable(){
    layer = 0;
  }


  public int compare(Renderable o1, Renderable o2) {
    if (o1.layer < o2.layer)
      return -1;
    if(o1.layer == o2.layer)
      return  0;

    //o1 > o2
    return 1;
  }
}
