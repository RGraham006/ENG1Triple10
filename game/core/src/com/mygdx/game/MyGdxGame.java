package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	Texture img;
	ShapeRenderer shape;
	private OrthographicCamera  camera;
	Chef chef;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		chef = new Chef(50, 50, 50);
		//img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render () {

		shape.begin(ShapeRenderer.ShapeType.Filled);
		ScreenUtils.clear(1, 0, 0, 1);
		chef.move();
		chef.draw(shape);
		shape.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
