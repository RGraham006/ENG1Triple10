package com.mygdx.game.Stations;

import com.mygdx.game.Items.Item;

public class ToasterStation extends Station{


  @Override
  public boolean CanRetrieve() {
    return false;
  }

  @Override
  public boolean CanGive() {
    return false;
  }

  @Override
  public boolean GiveItem(Item item) {
    return false;
  }

  @Override
  public Item RetrieveItem() {
    return null;
  }
}
