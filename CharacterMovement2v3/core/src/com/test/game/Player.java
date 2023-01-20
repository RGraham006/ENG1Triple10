package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Player extends Sprite {
    Sprite sprite;
    //private TextureAtlas textureAtlas;
    private String spriteOrientation, spriteState;
    private int currentSpriteAnimation;
    private int MAX_ANIMATION = 4;
    private float stateTime = 0;
    private float posX, posY;
    private TextureAtlas textureAtlas;

    public Player() {
        textureAtlas = Test.getTextureAtlas();
        sprite = textureAtlas.createSprite("south1");
        currentSpriteAnimation = 1;
        spriteOrientation = "south";
        posX = Gdx.graphics.getWidth()/2 - sprite.getWidth()/2;
        posY = Gdx.graphics.getHeight()/2 - sprite.getHeight()/2;
        sprite.setPosition(posX, posY);
    }

    public void updateSpriteFromInput(String newOrientation) {  //new handleInput
        if(spriteOrientation != newOrientation) {
			currentSpriteAnimation = 1;
			stateTime = 0;
		}
		else {
			if(stateTime > 0.06666) {
				currentSpriteAnimation ++;
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

    public void setTexture(String texture) {
        sprite.setRegion(textureAtlas.findRegion(texture));
    }

    public float getX() {
        return posX;   
    }

    public float getY() {
        return posY;
    }
}
