package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Chef implements Person{
    private int x;
    private int y;
    private int size;
    public Chef(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.size = size;
    }

    @Override
    public void move(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            this.y ++;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            this.y --;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            this.x --;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            this.x ++;
        }
    }

    @Override
    public void checkCollision(){

    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.circle(x, y, size);
    }
}
