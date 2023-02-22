package com.mygdx.game;

/**
 * Creates the counters which the customers check for their dishes
 *
 * @author Robin Graham
 */
public class CustomerCounters {

  private final String name;
  private String dish;

  /**
   * Contructor class which makes the name of the station and sets it as empty
   *
   * @param name the name of the station
   */
  public CustomerCounters(String name) {
    this.name = name;
    dish = "none";
  }

  /**
   * Gets the dish when the chef puts it down
   *
   * @param name sets the name of the dish
   */
  public void setDish(String name) {
    this.dish = name;
  }

  /**
   * Returns the name of the dish
   *
   * @return String dish;
   */
  public String getDish() {
    return this.dish;
  }

  /**
   * Returns the name of the counter
   *
   * @return String name;
   */
  public String getName() {
    return name;
  }

}
