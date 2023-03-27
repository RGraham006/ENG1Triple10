package com.mygdx.game.Stations;

import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;

import java.util.ArrayList;
import java.util.Arrays;

public class OvenStation extends Station {

    boolean interacted;
    boolean ready;
    public float maxProgress;
    public float progress;
    public static ArrayList<ItemEnum> ItemWhiteList;


    public OvenStation() {
        ready = false;
        maxProgress = 10;
        if (ItemWhiteList == null) {
            ItemWhiteList = new ArrayList<>(Arrays.asList(ItemEnum.Potato, ItemEnum.CheesePotato, ItemEnum.MeatPotato,
                    ItemEnum.CheesePizza, ItemEnum.MeatPizza, ItemEnum.VegPizza, ItemEnum.CheesePizzaCooked,
                    ItemEnum.MeatPizzaCooked, ItemEnum.VegPizzaCooked, ItemEnum.BakedPotato, ItemEnum.CheeseBake,
                    ItemEnum.MeatBake));
        }
    }


    @Override
    public boolean GiveItem(Item item) {
        changeItem(item);
        checkItem();
        return true;
    }


    @Override
    public Item RetrieveItem() {
        returnItem = item;
        deleteItem();
        currentRecipe = null;
        return returnItem;
    }


    @Override
    public boolean CanRetrieve() {
        return item != null;
    }


    @Override
    public boolean CanGive() {
        return item == null;
    }


    @Override
    public boolean CanInteract(){
        return false;
    }


    @Override
    public boolean Interact() {
        return false;
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
    public void updatePictures() {
        return;
    }

    @Override
    public void Update(float dt) {
        if (currentRecipe != null) {
            Cook(dt);
            ProgressBar();
        }
    }
}