package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Customer implements Person{

    private int x;
    private int y;
    private int size;
    public Customer(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.size = size;
    }

    @Override
    public void draw(ShapeRenderer shape) {

    }

    @Override
    public void checkCollision() {

    }

    @Override
    public void move() {

    }
}
