package com.mygdx.game.RecipeAndComb;

import com.mygdx.game.Items.ItemEnum;

import java.util.ArrayList;

public class Recipe {

    public ArrayList<Step> RecipeSteps = new ArrayList<>();
    public ItemEnum endItem;

    public Recipe(ItemEnum endItem, ArrayList<Step> stepList){
        this.endItem = endItem;
        RecipeSteps.addAll(stepList);
    }


}
