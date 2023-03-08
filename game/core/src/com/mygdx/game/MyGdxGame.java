package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Creates the initial base layer and main objects such as sprite batches and screens Also declares
 * the render, create and dispose functions
 *
 * @author Labib Zabeneh
 * @author Robin Graham
 * @author Riko Puusepp
 * @author Amy Cross
 */
public class MyGdxGame extends Game {

  public SpriteBatch batch;

  /**
   * creates maps and sprites
   */
  @Override
  public void create() {
    batch = new SpriteBatch();
    
    setScreen(new MenuScreen(this));
  }

  /**
   * disposes maps and sprites
   */
  @Override
  public void dispose() {
    super.dispose();
    batch.dispose();
  }

  /**
   * Renders objects
   */
  @Override
  public void render() {
    super.render();
  }

}

