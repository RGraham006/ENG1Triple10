package com.mygdx.game.Core.GameState;

import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;

public class ItemState implements java.io.Serializable{
  public ItemEnum item;
  public int step;
  public float progress;

  public ItemState(Item obj){
    if(obj ==null)
    {
      item = null;
      step = 0;
      progress = 0;

      return;
    }
    item = obj.name;
    step = obj.step;
    progress = obj.progress;

  }

}
