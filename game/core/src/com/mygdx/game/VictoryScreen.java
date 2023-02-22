package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * This class creates and displays the victory screen
 *
 * @author Robin Graham
 */
public class VictoryScreen implements Screen {

  final MyGdxGame root;
  GameScreen gameScreen;
  Texture victoryScreen;
  Stage stage;
  int timer;
  private BitmapFont timerFont;
  private Label timerLabel;

  /**
   * Assigns all the necessary variables needed for the victory screen and other objects such as the
   * image used
   *
   * @param root the base object to interact with
   * @param time the integer time value set for timer
   */
  public VictoryScreen(final MyGdxGame root, int time) {
    this.root = root;
    gameScreen = new GameScreen(root);
    victoryScreen = new Texture(Gdx.files.internal("endScreenBG.png"));
    Image image = new Image(victoryScreen);
    stage = new Stage();
    image.setSize(stage.getWidth(), stage.getHeight());
    image.setPosition(0, 0);
    stage.addActor(image);
    this.timer = time;
    Gdx.input.setInputProcessor(stage);
    timerFont = new BitmapFont();
    timerLabel = new Label("TIME: " + Integer.toString(timer),
        new Label.LabelStyle(new BitmapFont(), Color.WHITE));
  }

  /**
   *
   */
  @Override
  public void show() {

  }

  /**
   * Displays the timer onto the screen with the set time defined
   */
  public void displayTimer() {
    CharSequence str = "Final Time: " + Integer.toString(timer);
    timerFont.draw(root.batch, str, 200, 250);
    timerFont.getData().setScale(3f, 3f);
    timerLabel.setText(str);
  }

  /**
   * Renders the stage and assets
   *
   * @param delta used for working with time
   */
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act(Gdx.graphics.getDeltaTime());
    stage.draw();
    root.batch.begin();
    displayTimer();
    root.batch.end();
  }

  /**
   * Resize the window
   *
   * @param width  of the window
   * @param height of the window
   */
  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
  }

  /**
   *
   */
  @Override
  public void pause() {

  }

  /**
   *
   */
  @Override
  public void resume() {

  }

  /**
   *
   */
  @Override
  public void hide() {

  }

  /**
   *
   */
  @Override
  public void dispose() {
    stage.dispose();
  }
}
