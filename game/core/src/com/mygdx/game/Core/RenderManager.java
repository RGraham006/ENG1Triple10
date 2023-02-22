package com.mygdx.game.Core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class RenderManager
{

  public static RenderManager renderer;


  public RenderManager(){
    if(renderer != null)
      throw new IllegalArgumentException("This cannot be created more than once");


      renderer = this;

  }


  public void onRender(SpriteBatch batch){
    List<GameObject> LayerOrderedRenderables = new LinkedList<>();

    for (GameObject obj: GameObjectManager.objManager.GameObjects.values()
    ) {
      LayerOrderedRenderables.add(obj);
    }


    Collections.sort(LayerOrderedRenderables,new Comparator<GameObject>() {
      @Override
      public int compare(GameObject o1, GameObject o2) {
        return -o1.image.compare(o1.image,o2.image);
      }
    } );

    for (GameObject obj: LayerOrderedRenderables
    ) {
      obj.render(batch);
    }
  }

}
