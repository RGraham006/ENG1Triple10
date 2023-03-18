package com.mygdx.game.Stations;

import com.mygdx.game.Items.Item;


public class CustomerCounters extends Station{


    public CustomerCounters(){

    }


    @Override
    public boolean GiveItem(Item item){
        changeItem(item);
        return true;
    }


    @Override
    public Item RetrieveItem(){
        returnItem = item;
        deleteItem();
        currentRecipe = null;
        return returnItem;
    }


    @Override
    public boolean CanRetrieve(){
        return item != null;
    }


    @Override
    public boolean CanGive(){
        return item == null;
    }


    @Override
    public boolean CanInteract(){
        return false;
    }


    @Override
    public boolean Interact(){
        return false;
    }


    @Override
    public void Update(float dt) {
    }
}
