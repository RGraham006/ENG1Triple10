package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Core.BlackTexture;
import com.mygdx.game.Core.GameObject;

import java.util.HashMap;

public class showRecipeInstructions {
    HashMap<String, String> recipeMap = new HashMap<>();
    public GameObject imageObject;

    public void showRecipeInstruction(){
        recipeMap.put("Empty", "Recipes/EmptyRecipe.png");
        recipeMap.put("salad", "speech_dish1.png");
        recipeMap.put("burger", "Recipes/BurgerRecipe.png");
    }
    public void createInstructionPage(String dish){
        imageObject = new GameObject(new BlackTexture(recipeMap.get(dish)));
        imageObject.position = new Vector2((32*32/2)-(imageObject.getBlackTexture().width/2),0);
        imageObject.isVisible = false;
    }

    public void changeInstructionPage(String dish){
        imageObject.getBlackTexture().changeTexture(new Texture(recipeMap.get(dish)));
    }

    public void makeVisible(){
        imageObject.isVisible = true;
    }

    public void makeInvisible(){
        imageObject.isVisible = false;
    }

    public void removeInstructionPage(){
        imageObject = null;
    }
}
