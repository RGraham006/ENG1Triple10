package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.security.Key;

public class Chef extends Sprite implements Person {
    Sprite sprite;
    private String spriteOrientation, spriteState;
    private int currentSpriteAnimation;
    private int MAX_ANIMATION = 4;
    private float stateTime = 0;
    private float posX, posY;
    private TextureAtlas textureAtlas;

    public Chef(){
        textureAtlas = MyGdxGame.getTextureAtlas();
        sprite = textureAtlas.createSprite("south1");
        currentSpriteAnimation = 1;
        spriteOrientation = "south";
        posX = Gdx.graphics.getWidth()/2 - sprite.getWidth()/2;
        posY = Gdx.graphics.getHeight()/2 - sprite.getHeight()/2;
        sprite.setPosition(posX, posY);
    }

    @Override
    public void updateSpriteFromInput(String newOrientation) {
        if(spriteOrientation != newOrientation) {
            currentSpriteAnimation = 1;
            stateTime = 0;
        }
        else {
            if(stateTime > 0.06666) {
                currentSpriteAnimation ++;
                System.out.println(spriteState);
                if(currentSpriteAnimation > MAX_ANIMATION) {
                    currentSpriteAnimation = 1;
                }
                stateTime = 0;
            }
            else {
                stateTime += 0.01;
            }
        }
        spriteState = newOrientation + Integer.toString(currentSpriteAnimation);
        setTexture(spriteState);
        spriteOrientation = newOrientation;

        switch(spriteOrientation) {
            case "north": posY += 1.5; break;
            case "south": posY -= 1.5; break;
            case "east": posX += 1.5; break;
            case "west": posX -= 1.5; break;
        }
    }
    @Override
    public void setTexture(String texture) {
        sprite.setRegion(textureAtlas.findRegion(texture));
    }

    public float getX() {
        return posX;
    }

    public float getY(){
        return posY;
    }
    @Override
    public String getMove() {
        String newOrientation = "none";
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            newOrientation = "west";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            newOrientation = "east";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            newOrientation = "north";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            newOrientation = "south";
        }
        return newOrientation;
    }
}
