package com.mygdx.game.Core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class BlackSprite extends Renderable {

  public Sprite sprite;


  @Override
  public void Render(SpriteBatch batch, float x, float y) {
    sprite.setPosition(x, y);
    sprite.draw(batch);


  }


  @Override
  public void setSize(int x,int y) {
    sprite.setSize(x, y);
  }

  public void setSprite(Sprite sprite) {
    if (this.sprite == null) {
      this.sprite = sprite;
      return;
    }
    this.sprite.set(sprite);
  }


  public int GetWidth()
  {
    return (int)(sprite.getWidth());
  }
  public int GetHeight(){
    return  (int)(sprite.getHeight());
  }


}
