package com.mygdx.game.Stations;

import com.mygdx.game.Core.Interactions.Interactable;
import com.mygdx.game.Items.Item;

public class TrashCan implements Interactable {

    @Override
    public boolean GiveItem(Item item){
        return true;
    }

    @Override
    public Item RetrieveItem(){
        return null;
    }

    @Override
    public boolean CanRetrieve(){
        return false;
    }

    public boolean CanGive(){
        return true;
    }


}
