package com.mygdx.game.Stations;

import com.mygdx.game.Core.BlackTexture;
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
        bubble.isVisible = true;
        changeItem(item);
        checkItem();
        return true;
    }


    @Override
    public Item RetrieveItem() {
        returnItem = item;
        deleteItem();
        currentRecipe = null;
        bubble.isVisible = false;
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
    public boolean CanInteract() {
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
            return;
        }
        progressBar();
    }


    public void progressBar(){
        bubble.image = new BlackTexture("Timer/0"+getProgress()+".png");
    }


    public int getProgress() {
        progress = item.progress / maxProgress;
        return (int) (progress/0.125) + 1;
    }

    @Override
    public void updatePictures() {
        return;
    }

    @Override
    public void Update(float dt) {
        if (currentRecipe != null) {
            Cook(dt);
        }

    }
}
