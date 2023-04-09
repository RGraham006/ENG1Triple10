package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Core.BlackTexture;
import com.mygdx.game.Core.ValueStructures.EndOfGameValues;

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
  private final BitmapFont timerFont;
  private final Label timerLabel;
  private final Label VictoryOrLoss;
  private final Table table;


  /**
   * Assigns all the necessary variables needed for the victory screen and other objects such as the
   * image used
   *
   * @param root the base object to interact with
   * @param time the integer time value set for timer
   */
  public VictoryScreen(final MyGdxGame root, GameScreen gscreen, int time, EndOfGameValues values) {
    this.root = root;


    //this might cause issues if so, change back to new GameScreen
    gameScreen = gscreen;
    victoryScreen = new Texture(Gdx.files.internal("endScreenBG.png"));
    Image image = new Image(victoryScreen);
    stage = new Stage();
    image.setSize(stage.getWidth(), stage.getHeight());
    image.setPosition(0, 0);
    stage.addActor(image);
    this.timer = time;
    Gdx.input.setInputProcessor(stage);
    timerFont = new BitmapFont();
    timerLabel = new Label("TIME: " + timer,
        new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    String VL = (values.Won)? "won!" : "lost.";
    VictoryOrLoss = new Label("You " + VL,
        new Label.LabelStyle(new BitmapFont(), Color.WHITE));


    BlackTexture uptex = new BlackTexture("ExitUp.png");
    BlackTexture downtex = new BlackTexture("ExitDown.png");



    table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    Drawable drawableScenariobtnUp = new TextureRegionDrawable(uptex.textureRegion);
    Drawable drawableScenariobtnDown = new TextureRegionDrawable(downtex.textureRegion);


    Button scenariobtn = new Button();
    Button.ButtonStyle scenariobtnStyle = new Button.ButtonStyle();
    scenariobtn.setStyle(scenariobtnStyle);
    scenariobtnStyle.up = drawableScenariobtnUp;
    scenariobtnStyle.down = drawableScenariobtnDown;

    table.add(scenariobtn).width(250).height(50).pad(200,25,25,25);
    table.row();



    ChangeListener playbtnMouseListener = new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        gameScreen.gameMusic.stop();
        root.setScreen(new MenuScreen(root));
        dispose();
      }
    };

    scenariobtn.addListener(playbtnMouseListener);




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
    CharSequence str = "Final Time: " + timer;
    timerFont.draw(root.batch, str, 400, 300);
    timerFont.getData().setScale(3f, 3f);
    timerLabel.setText(str);
  }


  public void displayVictoryStatus() {
    timerFont.draw(root.batch, VictoryOrLoss.getText(), 400, 400);
    timerFont.getData().setScale(3f, 3f);
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
    displayVictoryStatus();
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
