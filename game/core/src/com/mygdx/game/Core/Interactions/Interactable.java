package com.mygdx.game.Core.Interactions;

import com.mygdx.game.Items.Item;
import java.util.Optional;

public interface Interactable {

  public boolean CanRetrieve();
  public boolean CanGive();

  public Optional<Item> RetrieveItem();

  public boolean GiveItem(Item item);
}
