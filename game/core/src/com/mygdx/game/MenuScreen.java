package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Core.GameObjectManager;
import com.mygdx.game.Core.RenderManager;

/**
 * Implements the screen that's displayed for the start of the game
 *
 * @author Kelvin Chen
 * @author Amy Cross
 */
public class MenuScreen implements Screen {

  GameScreen gameScreen;
  GameScreen VictoryScreen;
  TextureAtlas mainMenuAtlas;
  final MyGdxGame root;
  boolean scenarioClicked = false;
  TextField textField;

  private final TextureRegion playbtn;
  private final TextureRegion playbtnDown;
  private final TextureRegion scenariobtn;
  private final TextureRegion scenariobtnDown;
  private final TextureRegion exitbtn;
  private final TextureRegion exitbtnDown;
  private final Stage stage;
  private final Table table;

  /**
   * constructs the screen including the position of the buttons and their hitboxes;
   *
   * @param root The base object
   */
  public MenuScreen(final MyGdxGame root) {

    if (GameObjectManager.objManager == null) {
      new GameObjectManager();
    } else {
      GameObjectManager.objManager.reset();
    }

    if (RenderManager.renderer == null) {
      new RenderManager();
    }

    this.root = root;
    gameScreen = new GameScreen(root);

    mainMenuAtlas = new TextureAtlas(Gdx.files.internal("mainMenu.atlas"));
    playbtn = new TextureRegion(mainMenuAtlas.findRegion("playButton"));
    playbtnDown = new TextureRegion(mainMenuAtlas.findRegion("playButtonDown"));
    scenariobtn = new TextureRegion(mainMenuAtlas.findRegion("scenarioButton"));
    scenariobtnDown = new TextureRegion(mainMenuAtlas.findRegion("scenarioButtonDown"));
    exitbtn = new TextureRegion(mainMenuAtlas.findRegion("exitButton"));
    exitbtnDown = new TextureRegion(mainMenuAtlas.findRegion("exitButtonDown"));

    stage = new Stage();
    Gdx.input.setInputProcessor(stage);

    table = new Table();
    table.setFillParent(true);
    stage.addActor(table);
    Drawable drawablePlaybtnUp = new TextureRegionDrawable(new TextureRegion(playbtn));
    Drawable drawablePlaybtnDown = new TextureRegionDrawable(new TextureRegion(playbtnDown));
    Drawable drawableScenariobtnUp = new TextureRegionDrawable(new TextureRegion(scenariobtn));
    Drawable drawableScenariobtnDown = new TextureRegionDrawable(
        new TextureRegion(scenariobtnDown));
    Drawable drawableExitbtnUp = new TextureRegionDrawable(new TextureRegion(exitbtn));
    Drawable drawableExitbtnDown = new TextureRegionDrawable(new TextureRegion(exitbtnDown));

    Button.ButtonStyle playbtnStyle = new Button.ButtonStyle();
    Button playbtn = new Button();
    playbtn.setStyle(playbtnStyle);
    playbtnStyle.up = drawablePlaybtnUp;
    playbtnStyle.down = drawablePlaybtnDown;
    table.add(playbtn).width(250).height(50).padTop(75);
    table.row();

    Button scenariobtn = new Button();
    Button.ButtonStyle scenariobtnStyle = new Button.ButtonStyle();
    scenariobtn.setStyle(scenariobtnStyle);
    scenariobtnStyle.up = drawableScenariobtnUp;
    scenariobtnStyle.down = drawableScenariobtnDown;
    table.add(scenariobtn).width(250).height(50).pad(25);
    table.row();

    Button.ButtonStyle exitbtnStyle = new Button.ButtonStyle();
    Button exitbtn = new Button();
    exitbtn.setStyle(exitbtnStyle);
    exitbtnStyle.up = drawableExitbtnUp;
    exitbtnStyle.down = drawableExitbtnDown;
    table.add(exitbtn).width(250).height(50);

    table.setBackground(new TextureRegionDrawable(mainMenuAtlas.findRegion("menuPP")));

    ChangeListener playbtnMouseListener = new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        root.setScreen(gameScreen);
        dispose();
      }
    };
    playbtn.addListener(playbtnMouseListener);

    // Creates a new skin using the given JSON file
    // This will be used to create the TextField
    Skin skin = new Skin(Gdx.files.internal("clean-crispy-ui.json"));
    scenariobtn.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        textField = new TextField("", skin); // Creates a new TextField with the given text and skin
        textField.setMessageText("Enter the maximum number of customers for the scenario mode");
        textField.setAlignment(Align.center); // Aligns the text to the center of the TextField
        textField.setPosition(200, 200); // Sets the position of the TextField
        stage.addActor(textField); // Adds the TextField to the stage
        scenarioClicked = true; // Sets the clicked flag for this button to true
      }
    });

    ChangeListener exitbtnMouseListener = new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        Gdx.app.exit();
        dispose();
      }
    };
    exitbtn.addListener(exitbtnMouseListener);
  }


  @Override
  public void show() {

  }

  /**
   * Renders the screen and checks for interactions
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act(Gdx.graphics.getDeltaTime());
    stage.draw();
    if (scenarioClicked) {
      if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        System.out.println(textField.getText()); // Prints the text from the TextField
      }
    }
  }

  /**
   * Sets the camera for the screen
   *
   * @param width  the width of the camera
   * @param height the height of the camera
   */
  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  /**
   * Gets rid of excess sprites
   */
  @Override
  public void dispose() {
    stage.dispose();
  }


}
