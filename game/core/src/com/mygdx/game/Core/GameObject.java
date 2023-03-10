package com.mygdx.game.Core;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.LinkedList;
import java.util.List;

public class GameObject {

  public Renderable image;

  public Vector2 position;
  public Boolean destroyed = false;
  private Integer UID;
  List<Scriptable> Scripts = new LinkedList<>();


  public GameObject(Renderable renderable) {

    image = renderable;
    if (GameObjectManager.objManager != null) {
      GameObjectManager.objManager.SubmitGameObject(this);
    }
    position = new Vector2();

  }

  public void attachScript(Scriptable script) {
    Scripts.add(script);
    script.setGameObject(this);
    script.Start();
  }

  public Integer getUID() {
    return UID;
  }

  public void setUID(int ID) {
    if (UID != null) {
      throw new IllegalArgumentException("You cannot change the UID");
    }

    UID = ID;
  }

  public void doUpdate(float dt) {
    for (Scriptable script : Scripts
    ) {
      script.Update(dt);
    }
  }

  public void doFixedUpdate(float dt) {
    for (Scriptable script : Scripts
    ) {
      script.FixedUpdate(dt);
    }
  }


  public void render(SpriteBatch batch) {

    image.Render(batch, position.x, position.y);

  }


  public BlackSprite getSprite() {
    return ((BlackSprite) image);
  }

  public BlackTexture getBlackTexture() {
    return (BlackTexture) image;
  }

  public void setPosition(float x, float y) {
    position.set(x, y);
  }
}
