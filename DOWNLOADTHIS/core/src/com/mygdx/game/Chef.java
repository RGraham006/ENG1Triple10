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
    private boolean isFrozen;

    public Chef(){
        textureAtlas = MyGdxGame.getTextureAtlas();
        sprite = textureAtlas.createSprite("south1");
        currentSpriteAnimation = 1;
        spriteOrientation = "south";
        posX = Gdx.graphics.getWidth()/2 - sprite.getWidth()/2;
        posY = Gdx.graphics.getHeight()/2 - sprite.getHeight()/2;
        isFrozen = false;
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
                // System.out.println(spriteState);
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
            case "north": posY += 2; break;
            case "south": posY -= 2; break;
            case "east": posX += 2; break;
            case "west": posX -= 2; break;
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
    public float getWidth(){
        return sprite.getWidth();
    }
    public float getHeight(){
        return sprite.getHeight();
    }
    @Override
    public String getMove() {
        if (isFrozen){
            System.out.println("Frozen");
        }
        else{
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
        return "none";
    }
    public boolean isSpace(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            return true;
        }
            return false;
    }
    public boolean isR(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            return true;
        }
            return false;
    }
    public void freeze(){
        isFrozen = true;
    }
    public void unfreeze(){
        isFrozen = false;
    }
}
