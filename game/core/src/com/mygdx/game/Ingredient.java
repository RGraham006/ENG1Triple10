package com.mygdx.game;

/**
 * Creates an ingredient that can be picked up and transferred between chefs and stations; can also
 * have its state changed by a station;
 *
 * @author Robin Graham
 */
public class Ingredient {

  private final String name;
  private String state;
  private final String station;

  /**
   * Creates the ingredient with a certain name; all ingredients start unprocessed;
   *
   * @param name creates the name of the ingredient
   */
  public Ingredient(String name) {
    this.name = name;
    this.state = "unprocessed";
    this.station = setStation();
  }

  /**
   * Checks the ingredient name and assigns it a station that it can be used at;
   *
   * @return String station;
   */
  private String setStation() {
    if (name == "onion" || name == "lettuce" || name == "tomato") {
      return "chopping";
    } else if (name == "bun") {
      return "toaster";
    } else if (name == "patty") {
      return "frying";
    } else {
      return "none";
    }
  }

  /**
   * Gets the name of the ingredient;
   *
   * @return String name;
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the state of the ingredient
   *
   * @return String state;
   */
  public String getState() {
    return state;
  }

  /**
   * Sets the state of the ingredient from unprocessed to processed;
   *
   * @param state the new state for the ingredient
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Returns the station it can be used at;
   *
   * @return String station;
   */
  public String getStation() {
    return station;
  }
}
