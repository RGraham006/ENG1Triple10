package com.mygdx.game.Stations;

import com.mygdx.game.Core.Interactions.Interactable;
import com.mygdx.game.Core.Scriptable;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;

public class FoodCrate extends Scriptable implements Interactable {

    private ItemEnum ingredient;

    public FoodCrate(ItemEnum ingredient){

        this.ingredient = ingredient;

    }

    @Override
    public boolean GiveItem(Item item){
        return false;
    }

    @Override
    public Item RetrieveItem(){
        return new Item(ingredient);
    }

    @Override
    public boolean CanRetrieve(){
        return true;
    }

    public boolean CanGive(){
        return false;
    }


}
