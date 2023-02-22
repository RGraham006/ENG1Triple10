package com.mygdx.game;

/**
 * Defines a station object with its given name and attributes we also have the ability to "lock" a
 * station so it can only interact with one item at a time
 *
 * @author Robin Graham
 */
public class Station {

  private String name;
  private Ingredient ingredient;

  private boolean locked;

  /**
   * Assignes name to station
   *
   * @param name creates the name of the station
   */
  public Station(String name) {
    this.name = name;
    ingredient = new Ingredient("none");
    locked = false;
  }

  /**
   * Sets ingredient object
   *
   * @param ingredient sets ingredient object to ingredient
   */
  public void setIngredient(Ingredient ingredient) {
    this.ingredient = ingredient;
  }

  /**
   * Returns the ingredient to be used in other classes
   *
   * @return Ingredient ingredient
   */
  public Ingredient getIngredient() {
    return ingredient;
  }

  /**
   * Sets the station to a "lacked" state
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
}
