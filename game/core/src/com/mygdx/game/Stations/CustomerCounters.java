package com.mygdx.game.Stations;

import com.mygdx.game.Items.Item;

import java.util.function.Consumer;
import java.util.function.Function;


public class CustomerCounters extends Station{

    Function<Item, Boolean> script;

    public CustomerCounters(Function<Item, Boolean> script){
        this.script = script;
    }


    @Override
    public boolean GiveItem(Item item){
        changeItem(item);
        GiveFood();
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


    public void GiveFood(){
        boolean answer = script.apply(item);
        if(answer)
            deleteItem();
    }

    @Override
    public void Update(float dt) {
    }
}
