package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.mygdx.game.Core.*;
import com.mygdx.game.Core.Interactions.Interactable;
import com.mygdx.game.Core.ValueStructures.CustomerControllerParams;
import com.mygdx.game.Core.ValueStructures.EndOfGameValues;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.RecipeAndComb.CombinationDict;
import com.mygdx.game.RecipeAndComb.RecipeDict;
import com.mygdx.game.Stations.*;

/**
 * This is the main class of the game which runs all the logic and rendering Here all the outside
 * objects are created and drawn as well as interactions registered
 *
 * @author Robin Graham
 * @author Riko Puusepp
 * @author Kelvin Chen
 * @author Amy Cross
 * @author Labib Zabeneh
 */
public class GameScreen implements Screen {

  // game attributes
  private final MyGdxGame game;

  // camera
  private final OrthographicCamera camera;
  private Pathfinding pathfinding;
  private final int TILE_WIDTH = 32;
  private final int TILE_HEIGHT = 32;

  // box2d
  static World world;
  private final Box2DDebugRenderer b2dr;

  private CustomerController customerController;




  // map
  private final TiledMap map;
  private final TiledMapRenderer mapRenderer;

  // character assets
  private static ArrayList<TextureAtlas> customerAtlasArray;
  private final Customer[] customers = new Customer[5];
  private int chefControl;

  MasterChef masterChef;
  Texture dish1, dish2;
  Texture spaceTexture, ctrlTexture, shiftTexture, rTexture, mTexture;

  List<GameObject> Stations = new LinkedList();
  List<GameObject> customerCounters = new LinkedList();
  List<GameObject> assemblyStations = new LinkedList();

  public Texture menu = new Texture("recipeSheet.png");
  private final int x = 3;




  public Texture ingredientsSprites = new Texture("pixel_veggies1.png");
  public TextureRegion tomatoUnchopped = new TextureRegion(ingredientsSprites, 224, 32, 32, 32);
  public TextureRegion tomatoChopped = new TextureRegion(ingredientsSprites, 256, 32, 32, 32);
  public TextureRegion lettuceUnchopped = new TextureRegion(ingredientsSprites, 256, 0, 32, 32);
  public TextureRegion lettuceChopped = new TextureRegion(ingredientsSprites, 288, 0, 32, 32);
  public TextureRegion onionUnchopped = new TextureRegion(ingredientsSprites, 0, 32, 32, 32);
  public TextureRegion onionChopped = new TextureRegion(ingredientsSprites, 32, 32, 32, 32);
  public Texture meatUncooked = new Texture("pattyUncooked.png");
  public Texture meatCooked = new Texture("pattyCooked.png");
  public Texture bunUntoasted = new Texture("bunUntoasted.png");
  public Texture bunToasted = new Texture("bunToasted.png");
  public Texture burger = new Texture("bourger_32x32.png");
  public Texture salad = new Texture("Salad_32x32.png");
  public GameObject exitLogo = new GameObject(new BlackTexture("Exit.png"));

  // game timer and displayTimer
  private float seconds = 0f;
  private int timer = 0;
  private final Label timerLabel;
  private final Label moneyLabel;
  private final BitmapFont timerFont;

  Music gameMusic;

  public showRecipeInstructions recipeScreen = new showRecipeInstructions();

  /**
   * Constructor class which initialises all the variables needed to draw the sprites and also
   * manage the logic of the render as well as setting the camera and map
   *
   * @param game base Object which is used to draw on
   */
  public GameScreen(MyGdxGame game) {
    this.game = game;
    camera = new OrthographicCamera();
    recipeScreen.showRecipeInstruction();

    int viewportWidth = 32 * TILE_WIDTH;
    int viewportHeight = 18 * TILE_HEIGHT;
    camera.setToOrtho(false, viewportWidth, viewportHeight);
    camera.update();

    gameMusic = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
    gameMusic.setLooping(true);

    // tomatoTexture = new Texture("tomato_2.png");


    recipeScreen.createInstructionPage("Empty");

    dish1 = new Texture("speech_dish1.png");
    dish2 = new Texture("speech_dish2.png");
    spaceTexture = new Texture("space.png");
    ctrlTexture = new Texture("ctrl.png");
    shiftTexture = new Texture("shift.png");
    mTexture = new Texture("m_key.png");
    rTexture = new Texture("r_key.png");
    world = new World(new Vector2(0, 0), true);
    b2dr = new Box2DDebugRenderer();
    exitLogo.isVisible = false;
    exitLogo.getBlackTexture().height=30;
    exitLogo.getBlackTexture().width=30;
    exitLogo.position = new Vector2(713, 454);

    // add map
    map = new TmxMapLoader().load("PiazzaPanicMap.tmx");
    mapRenderer = new OrthogonalTiledMapRenderer(map);
    mapRenderer.setView(camera);

    pathfinding = new Pathfinding(TILE_WIDTH/4,viewportWidth,viewportWidth);


    masterChef = new MasterChef(2,world,camera,pathfinding);
    GameObjectManager.objManager.AppendLooseScript(masterChef);

    CustomerControllerParams CCParams = new CustomerControllerParams();
    CCParams.MaxMoney = 1000;
    CCParams.Reputation = 3;
    CCParams.MoneyStart = 20;
    customerController = new CustomerController(new Vector2(200,100), new Vector2(360,180), pathfinding, (EndOfGameValues vals) -> EndGame(vals),CCParams, new Vector2(190,390),new Vector2(190,290),new Vector2(290,290));
    customerController.SetWaveAmount(5);//Demonstration on how to do waves, -1 for endless

    GameObjectManager.objManager.AppendLooseScript(customerController);

    new CombinationDict();
    CombinationDict.combinations.implementItems();
    new RecipeDict();
    RecipeDict.recipes.implementRecipes();

    // generate customer sprites to be used by customer class
    customerAtlasArray = new ArrayList<TextureAtlas>();
    generateCustomerArray();
//
//    for (int i = 0; i < customers.length; i++) {
//
//      GameObject CustomerGameObject = new GameObject(new BlackSprite());
//      customers[i] = new Customer(i + 1);
//      CustomerGameObject.attachScript(customers[i]);
//      CustomerGameObject.image.setSize(18, 40);
//
//    }

    createCollisionListener();
    int[] objectLayers = {3, 4, 6, 9, 11, 13, 16, 18, 20, 22, 24, 25, 26, 27, 28, 29, 30, 31, 32,
        33, 34, 35, 36, 37, 38, 39};

    //Fixed the hideous mechanism for creating collidable objects
    for (int n = 0; n < 17; n++) {
      MapLayer layer = map.getLayers().get(n);
      String name = layer.getName();

      for (MapObject object : layer.getObjects()
          .getByType(RectangleMapObject.class)) {

        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        buildObject(world, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), "Static",
            name);

        switch (name) {
          case "bin":
            CreateBin(rect);
            break;
          case "counter":
            CreateAssembly(rect);
            break;
          case "frying":
            CreateHobs(rect);
            break;
          case "chopping board":
            CreateChopping(rect);
            break;
          case "toaster":
            CreateToaster(rect);
            break;
          case "oven":
            CreateOven(rect);
            break;
          case "customer counter":
            CreateCustomerCounters(rect);
            break;
          case "tomato":
            CreateFoodCrates(rect, ItemEnum.Tomato);
            break;
          case "lettuce":
            CreateFoodCrates(rect, ItemEnum.Lettuce);
            break;
          case "onion":
            CreateFoodCrates(rect, ItemEnum.Onion);
            break;
          case "mince":
            CreateFoodCrates(rect, ItemEnum.Mince);
            break;
          case "buns":
            CreateFoodCrates(rect, ItemEnum.Buns);
            break;
          case "dough":
            CreateFoodCrates(rect, ItemEnum.Dough);
            break;
          case "cheese":
            CreateFoodCrates(rect, ItemEnum.Cheese);
            break;
          case "potato":
            CreateFoodCrates(rect, ItemEnum.Potato);
            break;
        }
      }
    }

    timerLabel = new Label("TIME: " + timer,
        new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    moneyLabel = new Label("Money: ¥" + timer,
        new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    timerFont = new BitmapFont();
  }


  void CreateBin(Rectangle rect) {
    GameObject Bin = new GameObject(null);
    Bin.setPosition(rect.getX(), rect.getY());
    TrashCan TC = new TrashCan();
    Bin.attachScript(TC);
    Stations.add(Bin);
  }

  void CreateHobs(Rectangle rect) {
    GameObject Hob = new GameObject(null);
    Hob.setPosition(rect.getX(), rect.getY());
    HobStation HS = new HobStation();
    Hob.attachScript(HS);
    Stations.add(Hob);
  }

  void CreateToaster(Rectangle rect) {
    GameObject Toast = new GameObject(null);
    Toast.setPosition(rect.getX(), rect.getY());
    ToasterStation TS = new ToasterStation();
    Toast.attachScript(TS);
    Stations.add(Toast);
  }

  void CreateChopping(Rectangle rect) {
    GameObject Chop = new GameObject(null);
    Chop.setPosition(rect.getX(), rect.getY());
    ChopStation CS = new ChopStation();
    Chop.attachScript(CS);
    Stations.add(Chop);
  }

  void CreateOven(Rectangle rect) {
    GameObject Oven = new GameObject(null);
    Oven.setPosition(rect.getX(), rect.getY());
    OvenStation OS = new OvenStation();
    Oven.attachScript(OS);
    Stations.add(Oven);
  }

  void CreateFoodCrates(Rectangle rect, ItemEnum item) {
    GameObject Crate = new GameObject(null);
    Crate.setPosition(rect.getX(), rect.getY());
    FoodCrate FC = new FoodCrate(item);
    Crate.attachScript(FC);
    Stations.add(Crate);
  }

  void CreateAssembly(Rectangle rect) {
    GameObject Ass = new GameObject(null);
    Ass.setPosition(rect.getX(), rect.getY());
    AssemblyStation AS = new AssemblyStation();
    Ass.attachScript(AS);
    assemblyStations.add(Ass);
    Stations.add(Ass);
  }

  void CreateCustomerCounters(Rectangle rect) {
    GameObject Cust = new GameObject(null);
    Cust.setPosition(rect.getX(), rect.getY());
    CustomerCounters CC = new CustomerCounters();
    Cust.attachScript(CC);
    customerCounters.add(Cust);
    Stations.add(Cust);
  }


  /**
   * A function which builds the world box in Box2d which is used for all the hitboxes;
   *
   * @param world  the world it's being built in
   * @param x      the starting x of the world
   * @param y      the starting y of the world
   * @param width  the width of the world
   * @param height the height of the world
   * @param type   the type of the world
   * @param name   the name of the world
   */
  public void buildObject(World world, float x, float y, float width, float height,
      String type, String name) {
    BodyDef bdef = new BodyDef();
    bdef.position.set((x + width / 2), (y + height / 2));
    if (type == "Static") {
      bdef.type = BodyDef.BodyType.StaticBody;
      pathfinding.addStaticObject((int)x,(int)y,(int)width,(int)height);


    } else if (type == "Dynamic") {
      bdef.type = BodyDef.BodyType.DynamicBody;
    }
    Body body = world.createBody(bdef);
    body.getPosition();
    body.setUserData(name);
    PolygonShape shape = new PolygonShape();
    shape.setAsBox((width / 2), (height / 2));
    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    body.createFixture(fdef);
  }

  /**
   * Generates a customer array which can be used to get random customer sprites from the customer
   * class
   */
  public void generateCustomerArray() {
    String filename;
    TextureAtlas customerAtlas;

    //The file path takes it to data for each animation
    //The TextureAtlas creates a texture atlas where the you pass through the string of the number and it returns the image.
    //Taking all pictures in the diretory of the file
    for (int i = 1; i < 9; i++) {
      filename = "Customers/Customer" + i + "/customer" + i + ".txt";
      customerAtlas = new TextureAtlas(filename);
      customerAtlasArray.add(customerAtlas);
    }
  }


  public void EndGame(EndOfGameValues values){

  }


  /**
   * Returns the customer array that's been created
   *
   * @return ArrayList<TextureAtlas> customerAtlasArray;
   */
  public static ArrayList<TextureAtlas> getCustomerAtlasArray() {
    return customerAtlasArray;
  }

  /**
   * Plays the game music
   */
  @Override
  public void show() {
    gameMusic.play();
  }

  /**
   * Displays the timer for the player
   */
  public void displayTimer() {
    seconds += Gdx.graphics.getDeltaTime();
    if (seconds >= 1f) {
      timer++;
      seconds = 0f;
    }
    CharSequence str = "TIME: " + timer;
    timerFont.draw(game.batch, str, 380, 35);
    timerFont.getData().setScale(1.5f, 1.5f);
    timerLabel.setText(str);


    CharSequence str2 = "Money: ¥" + customerController.Money;
    timerFont.draw(game.batch, str2, 500, 35);
    timerFont.getData().setScale(1.5f, 1.5f);

    if(customerController.Reputation != -1) {
      CharSequence str3 = "Reputation: " + customerController.Reputation;
      timerFont.draw(game.batch, str3, 650, 35);
      timerFont.getData().setScale(1.5f, 1.5f);
    }

  }

  /**
   * Calls all logic updates and sprite draws as well as checks if game has been completed
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    //create world
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    //create camera
    camera.update();
    mapRenderer.setView(camera);
    mapRenderer.render();

//    for (int i = 0; i < customers.length; i++) {
  //    customers[i].updateSpriteFromInput(customers[i].getMove());
   // }

    //Removed and simplified logic

    world.step(1 / 60f, 6, 2);

    game.batch.setProjectionMatrix(camera.combined);

    //Begins drawing the game batch
    game.batch.begin();

    // Calls the function to draw all the ingredients
    //drawIngredients();

    // Calls the function to display timer
    displayTimer();

    //Update Scripts
    GameObjectManager.objManager.doUpdate(Gdx.graphics.getDeltaTime());
    //New rendering system
    RenderManager.renderer.onRender(game.batch);

    // Draws the chefs
    for (int i = 0; i < masterChef.returnChefCount(); i++) {
      //chef[i].sprite.setSize(18,40);
      //chef[i].sprite.draw(game.batch);
      if (masterChef.getChef(i).isFrozen) {  // if frozen, need to update timer and sprite
        masterChef.getChef(i).drawTimer(game.batch);
      }
    }

    // Draws the customers and their orders
//    for (int i = 0; i < customers.length; i++) {
//      //customers[i].gameObject.getSprite().setSize(18, 40);
//      customers[i].draw(game.batch);
//      GameObject foodIcon = customers[i].foodIcon;
//      if (customers[i].isWaiting()) {
//        Customer customer = customers[i];
//        foodIcon.position = new Vector2(((customer.getX() + customer.gameObject.getSprite().sprite.getWidth() / 2) - 5), ((customer.getY() + customer.gameObject.getSprite().sprite.getHeight()) - 5));
//        foodIcon.isVisible = true;
//        if(foodIcon.isClicked(camera)){
//          System.out.println(customer.getDish());
//        }
//      }
///* needs to be changed
//      if (customers[i].getDish() == customerCounters[i].getDish()) {
//        customers[i].fed();
//        foodIcon.isVisible = false;
//        foodIcon = null;
//      }
     // */

  //  }

    // Mutes or plays the music
    if (Gdx.input.isKeyJustPressed((Input.Keys.M))) {
      if (gameMusic.isPlaying()) {
        gameMusic.pause();
      } else {
        gameMusic.play();
      }
    }

    // Draws the instuctions and menu
    game.batch.draw(spaceTexture, 160, 400, 130, 80);
    game.batch.draw(ctrlTexture, 280, 410, 90, 60);
    game.batch.draw(shiftTexture, 360, 415, 90, 50);
    game.batch.draw(rTexture, 450, 413, 90, 53);
    game.batch.draw(mTexture, 534, 413, 90, 53);
    game.batch.draw(menu, 10, 405, 130, 70);
    game.batch.end();

    // Runs the logic for the collisions between counters and chefs
    gameLogic();

    // Checks if all the customers have been fed and the game is over
//    int fedCounter = 0;
//    for (int i = 0; i < customers.length; i++) {
//      if (customers[i].getFed()) {
//        fedCounter++;
//      }
//    }
//    if (fedCounter == 5) {
//      game.setScreen(new VictoryScreen(game, this, timer));
//    }
  }

  /**
   * Checks each chef, counter, station, and assembly station then draws the ingredient sprites on
   * them
   */

  /**
   * Gets all the collisions and checks if the input keys are pressed and then performs the actions
   * as specified
   */
  public void gameLogic() {

    // gets all the collisions and iterates through them
    int numContacts = world.getContactCount();
    if (numContacts > 0) {
      for (Contact contact : world.getContactList()) {
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();

        // Checks if the object being interacted with is a chef
        if (objectB.toString().contentEquals("Chef" + chefControl) || objectA.toString()
            .contentEquals("Chef" + chefControl)) {

          boolean isShift = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
          boolean isSpace = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
          boolean isR = Gdx.input.isKeyPressed(Input.Keys.R);
          boolean invFull;
          invFull = masterChef.getCurrentChef().getInventory().getName() != "none";

        }
      }
    }
  }

  /**
   * Finds all the collisions and assigns the names Also has a convenience function to disregard the
   * chef collision
   */
  public void createCollisionListener() {
    world.setContactListener(new ContactListener() {

      /**
       * gets the collision start and finds the names of the things colliding
       *
       * @param contact The object containing collision information
       */
      @Override
      public void beginContact(Contact contact) {

        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        Gdx.app.log("beginContact", "between " + objectA + " and " + objectB);
      }


      /**
       * outputs when two objects have stopped colliding
       *
       * @param contact The object containing decollision information
       */
      @Override
      public void endContact(Contact contact) {

        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        Gdx.app.log("endContact", "between " + objectA + " and " + objectB);
      }

      /**
       * Finds out when the two chefs have collided to ignore this collision
       *
       * @param contact The object containing collision information
       * @param oldManifold Needed by the override
       */
      @Override
      public void preSolve(Contact contact, Manifold oldManifold) {
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        if ((objectA.toString().contentEquals("Chef0")) && (objectB.toString()
            .contentEquals("Chef1"))) {
          System.out.println("CONTACT");
          contact.setEnabled(false);
        }
      }

      @Override
      public void postSolve(Contact contact, ContactImpulse impulse) {
      }

    });
  }

  @Override
  public void resize(int width, int height) {
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
   * Disposes of all sprites from memory to keep it optimised
   */
  @Override
  public void dispose() {
    game.batch.dispose();
  }

}
