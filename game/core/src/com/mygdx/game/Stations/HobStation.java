package com.mygdx.game.Stations;


import com.mygdx.game.Core.BlackTexture;
import com.mygdx.game.Core.GameObject;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;

import java.util.ArrayList;
import java.util.Arrays;

public class HobStation extends Station{

    boolean interacted;
    boolean ready;
    public static ArrayList<ItemEnum> ItemWhiteList;
    public float progress;
    public float maxProgress;
    public int imageSize = 14;

    public HobStation(){
        interacted = false;
        ready = false;
        maxProgress = 5;
        if(ItemWhiteList == null)
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


    @Override
    public boolean CanRetrieve(){
        return item != null;
    }


    @Override
    public boolean CanGive(){
        return item == null;
    }


    public void checkItem(){
        if(ItemWhiteList.contains(item.name))
            currentRecipe = recipes.RecipeMap.get(item.name);
        else
            currentRecipe = null;
    }


    @Override
    public boolean CanInteract() {
        return currentRecipe != null;
    }

    @Override
    public boolean Interact(){
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


    public void ProgressBar() {
        progress = item.progress / maxProgress;
    }
    
    public void updatePictures(){
        if(item == null) {
            if(heldItem == null)
                return;
            heldItem.Destroy();
            heldItem = null;
            return;
        }
        if(heldItem == null){
            heldItem = new GameObject( new BlackTexture(Item.GetItemPath(item.name)));
            heldItem.image.setSize(imageSize, imageSize);
            heldItem.setPosition(gameObject.position.x + 4, gameObject.position.y + (gameObject.getHeight()/2) + 2);
        }
        else {
            heldItem.image = new BlackTexture(Item.GetItemPath(item.name));
            heldItem.image.setSize(imageSize, imageSize);
        }

    }


    @Override
    public void Update(float dt) {
        if (currentRecipe != null) {
            Cook(dt);
            ProgressBar();
        }

        interacted = false;
    }
}
