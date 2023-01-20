package com.test.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Test extends ApplicationAdapter {
	private SpriteBatch batch;
	private static TextureAtlas textureAtlas;
	private Player player;

	private OrthographicCamera camera;

	private TiledMap map;
	private TiledMapRenderer mapRenderer;



	@Override
	public void create () {
		// sort out camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16, 9);
		camera.update();

		// add map
		map = new TmxMapLoader().load("testMap.tmx");
		float unitScale = 1 / 32f;
		mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);

		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas(Gdx.files.internal("sprites.txt"));
		player = new Player();
	}

	@Override
	public void dispose () {
		textureAtlas.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		mapRenderer.setView(camera);
		mapRenderer.render();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.updateSpriteFromInput("west");
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.updateSpriteFromInput("east");
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.updateSpriteFromInput("north");
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player.updateSpriteFromInput("south");
		}

		player.sprite.setPosition(player.getX(), player.getY());
		batch.begin();
		player.sprite.setSize(48, 80);
		player.sprite.draw(batch);
		batch.end();
	}

	public static TextureAtlas getTextureAtlas() {
		return textureAtlas;
	}
}
