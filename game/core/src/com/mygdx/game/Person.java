package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Person{

    public void move();

    public void checkCollision();

    public void draw(ShapeRenderer shape);
}
