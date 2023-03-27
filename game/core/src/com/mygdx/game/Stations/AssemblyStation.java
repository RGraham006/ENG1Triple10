package com.mygdx.game.Stations;

import com.mygdx.game.Core.BlackTexture;
import com.mygdx.game.Core.GameObject;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Assembly station for assembling our ingredients into a final dish
 *
 * @author Robin Graham
 */
public class AssemblyStation extends Station{

  private ArrayList<Item> ingredients;
  private ArrayList<ItemEnum> tempIngredients;
  public ArrayList<GameObject> heldItems = new ArrayList<>();
  private ItemEnum temp;
  private boolean assembled;
  private Item dish;
  private Item tempDish;
  public int ingredientSize = 12;


  public AssemblyStation() {
    if(ingredients == null)
      ingredients = new ArrayList<Item>();
    tempIngredients = new ArrayList<ItemEnum>();
    assembled = false;
  }


  @Override
  public boolean GiveItem(Item item){
    if(assembled){
      ingredients.add(getDish());
      ingredients.add(item);
      updatePictures();
      return true;
    }
    ingredients.add(item);
    updatePictures();
    return true;
  }


  @Override
  public Item RetrieveItem(){
    if(assembled) {
      tempDish = getDish();
      heldItem.Destroy();
      heldItem = null;
      return tempDish;
    }
    if(ingredients.isEmpty())
      return null;
    int index = ingredients.size() - 1;
    heldItems.get(index).Destroy();
    heldItems.remove(index);
    return ingredients.remove(index);
  }


  @Override
  public boolean CanRetrieve(){
    return true;
  }


  @Override
  public boolean CanGive(){
    return ingredients.size()<4;
  }


  @Override
  public boolean CanInteract(){
    return !(ingredients.size()<2);
  }


  @Override
  public boolean Interact(){
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
    for(int x = 0; x < (ingredients.size()); x++){
      tempIngredients.add(ingredients.get(x).name);
    }
    Collections.sort(tempIngredients);
    for(int x = 0; x < tempIngredients.size()-1; x++){
      temp = combinations.CombinationMap.get(tempIngredients.get(x).name()+tempIngredients.get(x+1).name());
      if(temp == null){
        clearTempIngredients();
        return false;
      }
      tempIngredients.set(x+1, temp);
      Collections.sort(tempIngredients);
    }
    setDish(tempIngredients.get(tempIngredients.size()-1));
    clearIngredients();
    clearTempIngredients();
    assembled = true;
    updatePictures();
    return assembled;
  }

  /**
   * Gets the current dish
   *
   */
  public Item getDish() {
    assembled = false;
    Item tempDish = dish;
    dish = null;
    return tempDish;
  }

  /**
   * Creates a new item and stores in dish using enum passed in the parameter
   *
   * @param item the enum passed in
   */
  public void setDish(ItemEnum item) {
    this.dish = new Item(item);
  }

  @Override
  public void updatePictures() {
    if(ingredients.isEmpty()) {
      for(int x = 0; x < heldItems.size(); x++){
        heldItems.get(x).Destroy();
      }
      heldItems = new ArrayList<>();
    }

    if(assembled){
      heldItem = new GameObject(new BlackTexture(Item.GetItemPath(dish.name)));
      heldItem.image.setSize(imageSize, imageSize);
      heldItem.setPosition(gameObject.position.x + (gameObject.getWidth()/2) - 9, gameObject.position.y + gameObject.getHeight() - imageSize - 2);
      return;
    }

    int index = ingredients.size();
    heldItem = new GameObject(new BlackTexture(Item.GetItemPath(ingredients.get(index-1).name)));
    heldItem.image.setSize(ingredientSize, ingredientSize);
    if(index == 1)
      heldItem.setPosition(gameObject.position.x + 2, gameObject.position.y + gameObject.getHeight() - ingredientSize - 2);
    else if (index == 2)
      heldItem.setPosition(gameObject.position.x + gameObject.getWidth() - ingredientSize - 2, gameObject.position.y + gameObject.getHeight() - ingredientSize - 2);
    else if (index == 3)
      heldItem.setPosition(gameObject.position.x + 2, gameObject.position.y + gameObject.getHeight() - (2 * ingredientSize) - 4);
    else
      heldItem.setPosition(gameObject.position.x + gameObject.getWidth() - ingredientSize - 2, gameObject.position.y + gameObject.getHeight() - (2 * ingredientSize) - 4);
    heldItems.add(heldItem);

  }


  @Override
  public void Update(float dt){}
}
