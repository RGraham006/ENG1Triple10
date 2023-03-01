package com.mygdx.game.RecipeAndComb;

import com.mygdx.game.Items.ItemEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RecipeDict {

    public static RecipeDict recipes;
    public Map<ItemEnum, Recipe> RecipeMap = new HashMap<>();

    public void implementRecipes(){
        ArrayList<Step> steps;

        //Chopping Board Recipes

        //Lettuce -> CutLettuce
        steps = new ArrayList<Step>(Arrays.asList(new TimeStep()));
        RecipeMap.put(ItemEnum.Lettuce, new Recipe(ItemEnum.CutLettuce, steps));

        //Tomato -> CutTomato
        RecipeMap.put(ItemEnum.Tomato, new Recipe(ItemEnum.CutTomato, steps));

        //Onion -> CutOnion
        RecipeMap.put(ItemEnum.Onion, new Recipe(ItemEnum.CutOnion, steps));

        //Mince -> RawPatty
        RecipeMap.put(ItemEnum.Mince, new Recipe(ItemEnum.RawPatty, steps));

        //Hob Recipes

        //RawPatty -> CookedPatty
        steps = new ArrayList<>(Arrays.asList(new TimeStep(), new InteractionStep(), new TimeStep()));
        RecipeMap.put(ItemEnum.RawPatty, new Recipe(ItemEnum.CookedPatty, steps));

        //Buns -> ToastedBuns;
        RecipeMap.put(ItemEnum.Buns, new Recipe(ItemEnum.ToastedBuns, steps));

        //Burning Recipes

        //CookedPatty
        steps = new ArrayList<Step>(Arrays.asList(new TimeStep()));
        RecipeMap.put(ItemEnum.CookedPatty, new Recipe(ItemEnum.Cinder, steps));

        //ToastedBuns
        RecipeMap.put(ItemEnum.ToastedBuns, new Recipe(ItemEnum.Cinder, steps));

    }

    public RecipeDict() {
        if (recipes != null)
            return;
        recipes = this;
    }

}
