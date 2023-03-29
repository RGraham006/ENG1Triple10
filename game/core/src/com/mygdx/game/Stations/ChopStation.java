package com.mygdx.game.Stations;

import com.mygdx.game.Core.BlackTexture;
import com.mygdx.game.Core.GameObject;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;
import com.mygdx.game.RecipeAndComb.CombinationDict;
import com.mygdx.game.RecipeAndComb.RecipeDict;
import java.util.ArrayList;
import java.util.Arrays;

public class ChopStation extends Station {


  boolean interacted;
  boolean ready;
  public static ArrayList<ItemEnum> ItemWhiteList;
  public float progress;
  public float maxProgress;
  public int imageSize = 18;

  public ChopStation() {
    interacted = false;
    ready = false;
    maxProgress = 5;
    if (ItemWhiteList == null) {
      ItemWhiteList = new ArrayList<>(Arrays.asList(ItemEnum.Lettuce, ItemEnum.Tomato,
          ItemEnum.Onion, ItemEnum.Mince, ItemEnum.CutTomato, ItemEnum.Dough));
    }
  }


  @Override
  public boolean GiveItem(Item item) {
    if (this.item == null) {
      changeItem(item);
      checkItem();
      return true;
    }
    return false;
  }


  @Override
  public Item RetrieveItem() {
    if (item != null) {
      returnItem = item;
      deleteItem();
      currentRecipe = null;
      return returnItem;
    }
    return null;
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
    return currentRecipe != null;
  }

  public void checkItem() {
    if (ItemWhiteList.contains(item.name)) {
      currentRecipe = RecipeDict.recipes.RecipeMap.get(item.name);
    } else {
      currentRecipe = null;
    }
  }

  /**
   * Returns the item in the station.
   *
   * @return The item in the station.
   * @author Jack Vickers
   */
  public Item returnItem() {
    return item;
  }


  @Override
  public boolean Interact() {
    return interacted = true;
  }

  public void Cut(float dt) {
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
    if (item == null) {
      if (heldItem == null) {
        return;
      }
      heldItem.Destroy();
      heldItem = null;
      return;
    }
    if (heldItem == null) {
      heldItem = new GameObject(new BlackTexture(Item.GetItemPath(item.name)));
      heldItem.image.setSize(imageSize, imageSize);
      heldItem.setPosition(gameObject.position.x + (gameObject.PhysicalWidth / 2) - 12,
          gameObject.position.y + (gameObject.getHeight() / 2) + 5);
    } else {
      heldItem.image = new BlackTexture(Item.GetItemPath(item.name));
      heldItem.image.setSize(imageSize, imageSize);
    }
  }

  @Override
  public void Update(float dt) {
    if (currentRecipe != null) {
      Cut(dt);
      ProgressBar();
    }

  }
}
