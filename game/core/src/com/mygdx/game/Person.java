package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/**
 * Interface for person which is used to create chefs and
 * customers
 * @author Robin Graham
 */
public interface Person {
    public void updateSpriteFromInput(String newOrientation);
    public void setTexture(String spriteState);
    public String getMove();
}
