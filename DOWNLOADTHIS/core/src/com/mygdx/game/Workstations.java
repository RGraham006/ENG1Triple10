package com.mygdx.game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Workstations {
    public void setTexture(String spriteState);
    public Boolean acceptIngredients(Chef chef, Ingredient ingredient);
    public void holdChef();
    
}
