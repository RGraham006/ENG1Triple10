package com.mygdx.game.Stations;

import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;

import java.util.ArrayList;
import java.util.Arrays;

public class ToasterStation extends Station{

    boolean interacted;
    boolean ready;
    public float maxProgress;
    public float progress;
    public static ArrayList<ItemEnum> ItemWhiteList;


    public ToasterStation() {
        ready = false;
        maxProgress = 8;
        if (ItemWhiteList == null) {
            ItemWhiteList = new ArrayList<>(Arrays.asList(ItemEnum.Buns));
        }
    }


    @Override
    public boolean GiveItem(Item item) {
        if (this.item != null)
            return false;
        changeItem(item);
        checkItem();
        return true;
    }


    @Override
    public Item RetrieveItem() {
        if (item != null) {
            returnItem = item;
            deleteItem();
            currentRecipe = null;
            return returnItem;
        }
        return null;
    }


    public boolean CanRetrieve() {
        return true;
    }


    public boolean CanGive() {
        return item == null;
    }


    public void checkItem() {
        if (ItemWhiteList.contains(item.name))
            currentRecipe = recipes.RecipeMap.get(item.name);
        else
            currentRecipe = null;
    }


    public void Cook(float dt) {
        ready = currentRecipe.RecipeSteps.get(item.step).timeStep(item, dt, interacted, maxProgress);

        if (ready) {
            changeItem(new Item(currentRecipe.endItem));
            checkItem();
        }
    }


    public void ProgressBar() {
        progress = item.progress / maxProgress;
    }


    @Override
    public void Update(float dt) {
        if (currentRecipe != null) {
            Cook(dt);
            ProgressBar();
        }

    }
}
