package com.mygdx.game.Stations;


import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;

import java.util.ArrayList;
import java.util.Arrays;

public class HobStation extends Station{

    boolean interacted;
    boolean ready;
    public static ArrayList<ItemEnum> ItemWhiteList;
    public float maxProgress;

    public HobStation(){
        interacted = false;
        ready = false;
        maxProgress = 5;
        if(ItemWhiteList.isEmpty())
            ItemWhiteList = new ArrayList<>(Arrays.asList(ItemEnum.RawPatty, ItemEnum.CookedPatty));
    }


    @Override
    public boolean GiveItem(Item item){
        if(this.item != null)
            return false;
        changeItem(item);
        checkItem();
        return true;
    }


    @Override
    public Item RetrieveItem(){
        if(item!=null){
            returnItem = item;
            deleteItem();
            currentRecipe = null;
            return returnItem;
        }
        return null;
    }


    public boolean CanRetrieve(){
        return true;
    }


    public boolean CanGive(){
        return true;
    }


    public void checkItem(){
        if(ItemWhiteList.contains(item.name))
            currentRecipe = recipes.RecipeMap.get(item.name);
        else
            currentRecipe = null;
    }


    public boolean interact(){
        if (currentRecipe == null)
            return false;
        return interacted = true;
    }


    public void burnItem(){
        changeItem(new Item(ItemEnum.Cinder));
    }


    public void Cook(float dt){
        ready = currentRecipe.RecipeSteps.get(item.step).timeStep(item, dt, interacted, maxProgress);

        if(ready && item.progress == 0){
            item.step++;
            if(item.step == currentRecipe.RecipeSteps.size()){
                changeItem(new Item(currentRecipe.endItem));
                checkItem();
            }
            return;
        }
        if(ready)
            burnItem();
    }
}
