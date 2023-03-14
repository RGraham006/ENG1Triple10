package com.mygdx.game.Stations;

import com.mygdx.game.Ingredient;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Assembly station for assembling our ingredients into a final dish
 *
 * @author Robin Graham
 */
public class AssemblyStation extends Station{

  private ArrayList<Item> ingredients;
  private ArrayList<ItemEnum> tempIngredients;
  private ItemEnum temp;
  private boolean assembled;
  private Item dish;


  public AssemblyStation() {
    ingredients = new ArrayList<Item>();
    tempIngredients = new ArrayList<ItemEnum>();
  }


  @Override
  public boolean GiveItem(Item item){
    if(assembled){
      ingredients.add(getDish());
      ingredients.add(item);
    }
    if(ingredients.size() < 4) {
      ingredients.add(item);
      return true;
    }
    return false;
  }


  @Override
  public Item RetrieveItem(){
    if(assembled)
      return getDish();
    if(ingredients.isEmpty())
      return null;
    return ingredients.remove(ingredients.size()-1);
  }


  public boolean CanRetrieve(){
    return true;
  }


  public boolean CanGive(){
    return true;
  }


  public boolean interact(){
    if (ingredients.size()<2)
      return false;
    return combine();
  }

  /**
   * Returns the list of ingredients in our arraylist form
   *
   * @return ArrayList ingredients
   */
  public ArrayList<Item> getIngredients() {
    return this.ingredients;
  }


  /**
   * removes all ingredients from the arraylist which means a successfull dish or simply ingredients
   * taken away
   */
  public void clearIngredients() {
    ingredients = new ArrayList<Item>();
  }

  private void clearTempIngredients(){
    tempIngredients = new ArrayList<ItemEnum>();
  }


  /**
   * Assembles the dish into the final one when we have all the correct ingredients
   */
  public boolean combine() {
    for(int x = 0; x < (ingredients.size()-1); x++){
      tempIngredients.add(ingredients.get(x).name);
    }
    Collections.sort(tempIngredients);
    for(int x = 0; x < tempIngredients.size()-2; x++){
      temp = combinations.CombinationMap.get(tempIngredients.get(x).name()+tempIngredients.get(x+1).name());
      if(temp == null){
        clearTempIngredients();
        return false;
      }
      tempIngredients.set(x+1, temp);
    }
    setDish(tempIngredients.get(tempIngredients.size()-1));
    clearIngredients();
    clearTempIngredients();
    return assembled = true;
  }

  /**
   * Gets the current dish
   *
   */
  public Item getDish() {
    assembled = false;
    return dish;
  }

  /**
   * Creates a new item and stores in dish using enum passed in the parameter
   *
   * @param item the enum passed in
   */
  public void setDish(ItemEnum item) {
    this.dish = new Item(item);
  }
}