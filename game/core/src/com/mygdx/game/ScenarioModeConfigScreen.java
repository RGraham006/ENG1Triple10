package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

/**
 * Implements the screen that the allows the user to configure the number of customers for the
 * scenario mode.
 * @author Jack Vickers
 * @date 04/04/2023
 */
public class ScenarioModeConfigScreen implements Screen {

  final MyGdxGame game;
  GameScreen gameScreen;
  Texture scenarioConfigTexture;
  Stage stage;
  Table table;
  TextField textField;
  TextureAtlas scenarioConfigAtlas;
  private final TextureRegion playbtn;
  private final TextureRegion playbtnDown;

  /**
   * Constructor for the scenario mode config screen
   *
   * @param game The MyGdxGame object
   * @author Jack Vickers
   * @date 04/04/2023
   */

  public ScenarioModeConfigScreen(MyGdxGame game) {
    float scale = 1.0f;
    this.game = game;

    // Load the scenario mode config screen assets
    scenarioConfigAtlas = new TextureAtlas(Gdx.files.internal("mainMenu.atlas"));
    scenarioConfigTexture = new Texture(Gdx.files.internal("mainMenu.png"));
    Image image = new Image(scenarioConfigTexture);

    // Create the stage and add the background image
    stage = new Stage();
//    stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    if (stage.getViewport().getScreenWidth() > 720) {
      scale = 2.00f;
    }
    image.setSize(stage.getWidth(), stage.getHeight());
    image.setPosition(0, 0);
    stage.addActor(image);
    Gdx.input.setInputProcessor(stage); // Make the stage consume events
    table = new Table(); // Create a table that fills the screen. Everything will go inside this table.
    stage.addActor(table);
    table.setFillParent(true);

    // Creates a skin for the text field using the clean-crispy-ui.json file
    Skin skin = new Skin(Gdx.files.internal("clean-crispy-ui.json"));

    // Creates the title and instructions for the scenario mode config screen
    Label title = new Label("Scenario Mode Options", new LabelStyle(new BitmapFont(), Color.WHITE));
    title.setFontScale(1.50f*scale);
    table.add(title).padBottom(30*scale).padTop(50*scale); // Adds the title to the table
    table.row(); // Adds a new row to the table
    Label instructions = new Label(
        "Enter the maximum number of customers for the scenario mode (default is 5):",
        new LabelStyle(new BitmapFont(), Color.WHITE));
    instructions.setFontScale(1.10f*scale);
    instructions.setAlignment(Align.left);
    table.add(instructions).padBottom(20*scale);
    table.row();

    // Sets the background using a section of the background image used on the main menu screen
    table.setBackground(new TextureRegionDrawable(scenarioConfigAtlas.findRegion("menuPP")));
    textField = new TextField("", skin);
    textField.getStyle().font.getData().setScale(1.50f*scale);
    textField.setMessageText("5");
    textField.setAlignment(Align.center);
    stage.addActor(textField); // Adds the text field to the stage
    table.add(textField).width(250*scale).height(50*scale); // Adds the text field to the table
    table.row();

    playbtn = new TextureRegion(scenarioConfigAtlas.findRegion("playButton"));
    playbtnDown = new TextureRegion(scenarioConfigAtlas.findRegion("playButtonDown"));
    Drawable drawablePlaybtnUp = new TextureRegionDrawable(new TextureRegion(playbtn));
    Drawable drawablePlaybtnDown = new TextureRegionDrawable(new TextureRegion(playbtnDown));

    Button.ButtonStyle playbtnStyle = new Button.ButtonStyle();
    Button playbtn = new Button();
    playbtn.setStyle(playbtnStyle);
    playbtnStyle.up = drawablePlaybtnUp;
    playbtnStyle.down = drawablePlaybtnDown;
    table.add(playbtn).width(250*scale).height(50*scale).padTop(75*scale);
    table.row();

    playbtn.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        int numCustomers = 5;
        if (textField.getText().matches("[0-9]+")) {
          numCustomers = Integer.parseInt(textField.getText());
        }
        gameScreen = new GameScreen(game, numCustomers);
        game.setScreen(gameScreen);
      }
    });
  }

  /**
   * Called when this screen becomes the current screen.
   */
  @Override
  public void show() {

  }

  /**
   * Called when the screen should render itself.
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act(Gdx.graphics.getDeltaTime());
    stage.draw();
    game.batch.begin();
    game.batch.end();
  }

  /**
   * Resize the screen.
   *
   * @param width  the new width
   * @param height the new height
   */
  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
  }

  /**
   * @see ApplicationListener#pause()
   */
  @Override
  public void pause() {

  }

  /**
   * @see ApplicationListener#resume()
   */
  @Override
  public void resume() {

  }

  /**
   * Called when this screen is no longer the current screen for a {@link Game}.
   */
  @Override
  public void hide() {

  }

  /**
   * Called when this screen should release all resources.
   */
  @Override
  public void dispose() {

  }
}
