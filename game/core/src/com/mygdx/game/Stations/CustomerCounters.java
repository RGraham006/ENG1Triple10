package com.mygdx.game.Stations;

import com.mygdx.game.Items.Item;


public class CustomerCounters extends Station{

    boolean interacted;

    public CustomerCounters(){
        interacted = false;
    }


    @Override
    public boolean GiveItem(Item item){
        if(this.item != null)
            return false;
        changeItem(item);
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


    public boolean interact(){
        if (currentRecipe == null)
            return false;
        return interacted = true;
    }

}
