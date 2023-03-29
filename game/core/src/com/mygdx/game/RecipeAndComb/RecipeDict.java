package com.mygdx.game.RecipeAndComb;

import com.mygdx.game.Items.ItemEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RecipeDict {

  public static RecipeDict recipes;
  public Map<ItemEnum, Recipe> RecipeMap = new HashMap<>();

  public void implementRecipes() {
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

    //Dough -> PizzaBase
    RecipeMap.put(ItemEnum.Dough, new Recipe(ItemEnum.PizzaBase, steps));

    //CutTomato -> TomSauce
    RecipeMap.put(ItemEnum.CutTomato, new Recipe(ItemEnum.TomSauce, steps));

    //Oven Recipes

    //CheesePizza -> CheesePizzaCooked
    RecipeMap.put(ItemEnum.CheesePizza, new Recipe(ItemEnum.CheesePizzaCooked, steps));

    //MeatPizza -> MeatPizzaCooked
    RecipeMap.put(ItemEnum.MeatPizza, new Recipe(ItemEnum.MeatPizzaCooked, steps));

    //VegPizza -> VegPizzaCooked
    RecipeMap.put(ItemEnum.VegPizza, new Recipe(ItemEnum.VegPizzaCooked, steps));

    //Potato -> BakedPotato
    RecipeMap.put(ItemEnum.Potato, new Recipe(ItemEnum.BakedPotato, steps));

    //CheesePotato -> CheeseBake
    RecipeMap.put(ItemEnum.CheesePotato, new Recipe(ItemEnum.CheeseBake, steps));

    //MeatPotato -> MeatBake
    RecipeMap.put(ItemEnum.MeatPotato, new Recipe(ItemEnum.MeatBake, steps));

    //Toaster Recipe

    //Buns -> ToastedBuns;
    RecipeMap.put(ItemEnum.Buns, new Recipe(ItemEnum.ToastedBuns, steps));

    //Burning Recipes

    //CookedPatty
    RecipeMap.put(ItemEnum.CookedPatty, new Recipe(ItemEnum.Cinder, steps));

    //CheesePizzaCooked
    RecipeMap.put(ItemEnum.CheesePizzaCooked, new Recipe(ItemEnum.Cinder, steps));

    //MeatPizzaCooked
    RecipeMap.put(ItemEnum.MeatPizzaCooked, new Recipe(ItemEnum.Cinder, steps));

    //VegPizzaCooked
    RecipeMap.put(ItemEnum.VegPizzaCooked, new Recipe(ItemEnum.Cinder, steps));

    //BakedPotato
    RecipeMap.put(ItemEnum.BakedPotato, new Recipe(ItemEnum.Cinder, steps));

    //CheeseBake
    RecipeMap.put(ItemEnum.CheeseBake, new Recipe(ItemEnum.Cinder, steps));

    //MeatBake
    RecipeMap.put(ItemEnum.MeatBake, new Recipe(ItemEnum.Cinder, steps));

    //Hob Recipes

    //RawPatty -> CookedPatty
    steps = new ArrayList<>(Arrays.asList(new TimeStep(), new InteractionStep(), new TimeStep()));
    RecipeMap.put(ItemEnum.RawPatty, new Recipe(ItemEnum.CookedPatty, steps));


  }

  public RecipeDict() {
      if (recipes != null) {
          return;
      }
    recipes = this;;
  }

}
