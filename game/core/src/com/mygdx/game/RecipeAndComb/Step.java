package com.mygdx.game.RecipeAndComb;

import com.mygdx.game.Items.Item;

public abstract class Step {

    public abstract boolean timeStep(Item item, float dt, boolean Interacted, float MaxProgress);

}
