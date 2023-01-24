package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.awt.*;
import java.util.TooManyListenersException;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private static TextureAtlas textureAtlas;
	private Chef chef1;
	Ingredient tomato;
	CuttingStation cuttingstation;
	ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	boolean ingredientSelected;
	boolean ingredientOnWorkstation;
	Texture tomatoTexture;
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	AssetManager assetManager;



	@Override
	public void create () {
		// sort out camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16, 9);
		camera.update();
		tomatoTexture = new Texture("tomato_2.png");
		
		// add map
		map = new TmxMapLoader().load("testMap.tmx");
		float unitScale = 1 / 32f;
		mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);

		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas(Gdx.files.internal("sprites.txt"));
		chef1 = new Chef();
		tomato = new Ingredient(tomatoTexture, 363, 280, "tomato");
		cuttingstation = new CuttingStation();
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
		chef1.updateSpriteFromInput(chef1.getMove());
		
		// assetManager = new AssetManager();
		// assetManager.load("sprites.png", Texture.class);
		// assetManager.finishLoading();

		// myImage = assetManager.get("sprites.png", Texture.class);

		chef1.sprite.setPosition(chef1.getX(), chef1.getY());
		batch.begin();
		
		// batch.draw(tomatoTexture, 50,50);
		chef1.sprite.setSize(48, 80);
		chef1.sprite.draw(batch);
		tomato.draw(batch, 0);
		
		// Pick up ingredient and put it in UI
		if(tomato.pickup(chef1) && !ingredientSelected){
			ingredientSelected = true;
		}
		if (ingredientSelected){
			batch.draw(tomatoTexture, 30, 440);
			String ingredientOnHand = "tomato";
		}
		// Place on cutting station if valid.
		if((cuttingstation.acceptIngredients(chef1, tomato)) && ingredientSelected){
			ingredientSelected = false;
			ingredientOnWorkstation = true;
		}
		if (ingredientOnWorkstation){
			// System.out.println("on deck");
			batch.draw(tomatoTexture, 90, 320);
		}
		// Perform action
		if (cuttingstation.action(chef1) && ingredientOnWorkstation){
			ingredientOnWorkstation = false;
		}
		batch.end();
		// Drawing Hitbox's
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(1, 0, 0, 1);
		shapeRenderer.rect(cuttingstation.getX(), cuttingstation.getY(), cuttingstation.getWidth(), cuttingstation.getHeight());
		shapeRenderer.rect(chef1.getX(),chef1.getY(),chef1.getWidth(), chef1.getHeight());
		// System.out.println(chef1.getHeight());
		shapeRenderer.end();
	}
	
	public static TextureAtlas getTextureAtlas() {
		return textureAtlas;
	}
}
