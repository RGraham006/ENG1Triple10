package com.mygdx.game.RecipeAndComb;

import com.mygdx.game.Items.ItemEnum;


import java.util.HashMap;
import java.util.Map;

public class CombinationDict {

    public static CombinationDict combinations;
    public Map<String, ItemEnum> CombinationMap = new HashMap<>();

    public void implementItems(){
        //Salads
        CombinationMap.put(ItemEnum.CutLettuce.name()+ItemEnum.CutTomato.name(), ItemEnum.LettuceTomatoSalad);
        CombinationMap.put(ItemEnum.CutLettuce.name()+ItemEnum.CutOnion.name(), ItemEnum.LettuceOnionSalad);
        CombinationMap.put(ItemEnum.CutTomato.name()+ItemEnum.CutOnion.name(), ItemEnum.TomatoOnionSalad);
        CombinationMap.put(ItemEnum.CutLettuce.name()+ItemEnum.TomatoOnionSalad.name(), ItemEnum.TomatoOnionLettuceSalad);
        CombinationMap.put(ItemEnum.CutTomato.name()+ItemEnum.LettuceOnionSalad.name(), ItemEnum.TomatoOnionLettuceSalad);
        CombinationMap.put(ItemEnum.CutOnion.name()+ItemEnum.LettuceTomatoSalad.name(), ItemEnum.TomatoOnionLettuceSalad);

        //Burgers
        CombinationMap.put(ItemEnum.CookedPatty.name()+ItemEnum.ToastedBuns.name(), ItemEnum.Burger);
        CombinationMap.put(ItemEnum.Cheese.name()+ItemEnum.Burger.name(), ItemEnum.CheeseBurger);
    }

    public CombinationDict(){
        if(combinations != null)
            return;
        combinations = this;
    }
}
