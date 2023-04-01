package com.mygdx.game.Stations;

import com.mygdx.game.Core.BlackTexture;
import com.mygdx.game.Core.GameObject;
import com.mygdx.game.Core.Interactions.Interactable;
import com.mygdx.game.Core.Scriptable;
import com.mygdx.game.Items.Item;
import com.mygdx.game.RecipeAndComb.CombinationDict;
import com.mygdx.game.RecipeAndComb.Recipe;
import com.mygdx.game.RecipeAndComb.RecipeDict;


/**
 * Defines a station object with its given name and attributes we also have the ability to "lock" a
 * station so it can only interact with one item at a time
 *
 * @author Robin Graham
 */
public abstract class Station extends Scriptable implements Interactable {

  public Item item;
  public Item returnItem;
  public RecipeDict recipes;
  public CombinationDict combinations;
  private boolean locked;
  public Recipe currentRecipe;
  GameObject heldItem;
  public int imageSize = 18;
  GameObject bubble;

  public Station() {
    item = null;
    locked = false;
    recipes = RecipeDict.recipes;
    combinations = CombinationDict.combinations;
    currentRecipe = null;
  }

  public void init() {
    bubble = new GameObject(new BlackTexture("Timer/01.png"));
    bubble.setPosition(gameObject.position.x + (gameObject.getWidth()/2) - (bubble.getWidth()/2), gameObject.position.y + (gameObject.getHeight()) + 2);
    bubble.isVisible = false;
  }

  /**
   * Sets item object
   *
   * @param item sets item object to item
   * @return boolean
   */
  public abstract boolean GiveItem(Item item);

  /**
   * Returns the item to be used in other classes
   *
   * @return Item item
   */
  public abstract Item RetrieveItem();

  public abstract void updatePictures();

  /**
   * Sets the station to a "locked" state
   *
   * @param locked assignes variable to either true or false
   */
  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  /**
   * return the boolean value of locked for the station
   *
   * @return boolean locked
   */
  public boolean getLocked() {
    return locked;
  }


  public void changeItem(Item item){
    this.item = item;
    updatePictures();
  }

  public void deleteItem(){
    item = null;
    updatePictures();
  }



}

