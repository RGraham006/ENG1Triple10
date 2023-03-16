package com.mygdx.game.Core.Interactions;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Core.GameObjectManager;
import com.mygdx.game.Core.Scriptable;
import java.util.List;

public class Interaction {

  public enum InteractionType{
      Fetch,
      Give,
  }



  public static Scriptable FindClosetInteractable(Vector2 pos, InteractionType type, float maxRange)
  {
    List<Scriptable> interactables = GameObjectManager.objManager.returnObjectsWithInterface(Interactable.class);

    float distance = maxRange*maxRange;
    float dst2 = 0;
    Scriptable currentClosestScript = null;
    Vector2 temp = Vector2.Zero;
    for (Scriptable script: interactables
    ) {
      temp.set(pos);
      dst2 = (temp.sub(script.gameObject.position).dot(temp));


      if(type == InteractionType.Fetch)
      {
        if(!((Interactable)script).CanRetrieve())
          continue;
      } else if(type == InteractionType.Give){
        if(!((Interactable)script).CanGive())
          continue;
      }
      if(dst2 < distance)
      {
        distance = dst2;
        currentClosestScript = script;

      }

    }


    return currentClosestScript;

  }
}
