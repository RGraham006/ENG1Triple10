package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Person {
    public void updateSpriteFromInput(String newOrientation);
    public void setTexture(String spriteState);
    public String getMove();
}
