package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Ingredient extends Actor implements Ingredients{
    Sprite sprite;
    Rectangle chefRectangle;
    Rectangle ingredientRectangle;
    private boolean ingredientSelected;
    private float posX, posY;
    private String ingredientName;
    private Texture texture;

    public Ingredient(Texture texture, float posX, float posY, String ingredientName){
        this.texture = texture;
        this.posX = posX;
        this.posY = posY;
        this.ingredientName = ingredientName;
        this.ingredientSelected = false;
        
        setBounds(posX, posY, texture.getWidth(), texture.getHeight());
        setTouchable(Touchable.enabled);

    }



    @Override
    public void draw(Batch batch, float parentAlpha){
        //draw texture
        batch.draw(texture, posX, posY);
        
    }
    @Override
    public boolean pickup(Chef chef) {
        chefRectangle = new Rectangle(chef.getX(),chef.getY(),chef.getHeight(),chef.getWidth());
        ingredientRectangle = new Rectangle(posX, posY, texture.getWidth(), texture.getHeight());
        // System.out.println(Intersector.overlaps(chefRectangle, ingredientRectangle));
        if (chef.isSpace() && Intersector.overlaps(chefRectangle, ingredientRectangle)){
            System.out.println("Space");
            return true;
        }
        return false;
    }
    
    @Override
    public void putDown(Chef chef, Workstations workstation) {
        
    }


    public String getName(){
        return ingredientName;
    }
   

}
    

