package com.mygdx.game;

import java.util.ArrayList;
/**
 * Assembly station for assembling our ingredients into a final
 * dish
 * 
 * @author Robin Graham
 */
public class AssemblyStation{

    private String name;
    private ArrayList<Ingredient> ingredients;

    private boolean assembled;

    private String dish;
    /**
     * Assigns name to station and creates an arraylist which our ingredients
     * will be placed onto
     * @param name name (number) of station e.g. assemblyStation1
     */
    public AssemblyStation(String name){
        this.name = name;
        this.ingredients = new ArrayList<Ingredient>();
    }
    /**
     * Adds ingredient to the ingredients arraylist
     * @param ingredient new ingredient to be added to arraylist
     */
    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }
    /**
     * Returns the list of ingredients in our arraylist form
     * @return ArrayList ingredients
     */
    public ArrayList<Ingredient> getIngredients(){
        return this.ingredients;
    }
    /**
     * Returns the name of the sation
     * @return String name
     */
    public String getName(){
        return this.name;
    }
    /**
     * removes all ingredients from the arraylist which means a 
     * successfull dish or simply ingredients taken away
     */
    public void clearIngredients(){
        this.ingredients = new ArrayList<Ingredient>();
    }
    /**
     * Checks if the ingredient we put on the station is valid
     * For example, if we have lettuce we can ONLY make a salad
     * from there
     * @param name name of ingredient passed in
     * @return Boolean value determining if ingredient placed on is valid
     */
    public boolean validIngredient(String name){
        ArrayList<String> currentIngredients = new ArrayList<String>();
        for (int i = 0; i < ingredients.size(); i++) {
            currentIngredients.add(ingredients.get(i).getName());
        }
        if(currentIngredients.contains("lettuce")){
            if(name == "patty" || name == "bun"){
                return false;
            }
        }
        if (currentIngredients.contains("bun") || currentIngredients.contains("patty")){
            if(name == "lettuce"){
                return false;
            }
        }
        return true;
    }
    /**
     * Assembles the dish into the final one when we have all the correct ingredients
     */
    public void assembleDish(){
        ArrayList<String> currentIngredients = new ArrayList<String>();
        for (int i = 0; i < this.ingredients.size(); i++) {
            currentIngredients.add(this.ingredients.get(i).getName());
        }
        if(currentIngredients.contains("lettuce") && currentIngredients.contains("onion") && currentIngredients.contains("tomato")){
            this.dish = "salad";
            assembled = true;
            this.clearIngredients();
        }
        if(currentIngredients.contains("patty") && currentIngredients.contains("bun") && currentIngredients.contains("tomato") && currentIngredients.contains("onion")){
            this.dish = "burger";
            assembled = true;
            this.clearIngredients();
        }
    }
    /**
     * Gets our current dish name
     * @return String dish is dish is returned, none otherwise
     */
    public String getDish(){
        if(assembled){
            return dish;
        }
        else{return "none";}
    }
    /**
     * Set our dish to the dish passed in the parameter
     * @param dish the dish name passed in
     */
    public void setDish(String dish) {
        this.dish = dish;
    }
}
