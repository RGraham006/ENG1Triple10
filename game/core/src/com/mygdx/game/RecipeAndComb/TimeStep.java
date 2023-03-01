package com.mygdx.game.RecipeAndComb;

import com.mygdx.game.Items.Item;

import static java.lang.Math.min;

public class TimeStep extends Step{

    public boolean timeStep(Item item, float dt, boolean Interacted, float MaxProgress){

        item.progress = min(item.progress + dt, MaxProgress);
        return item.progress == MaxProgress;
    }

}
