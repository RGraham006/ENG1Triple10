package com.mygdx.game.RecipeAndComb;

import com.mygdx.game.Items.Item;

import static java.lang.Math.min;

public class InteractionStep extends Step {

  public boolean timeStep(Item item, float dt, boolean Interacted, float MaxProgress) {
    if (Interacted) {
      item.progress = 0;
      return true;
    }
    item.progress = min(item.progress + dt, MaxProgress);
    return item.progress == MaxProgress;
  }

}
