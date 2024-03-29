package com.mygdx.game;

import java.util.ArrayList;

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

/**
 * This is the main class of the game which runs all the logic and rendering
 * Here all the outside objects are created and drawn as well as interactions registered
 *
 * @author Robin Graham
 * @author Riko Puusepp
 * @author Kelvin Chen
 * @author Amy Cross
 * @author Labib Zabeneh
 */
public class GameScreen implements Screen {
    
    // game attributes
    private MyGdxGame game;
    
    // camera
    private OrthographicCamera camera;
    private int TILE_WIDTH = 32;
    private int TILE_HEIGHT = 32;

    // box2d
    static World world;
    private Box2DDebugRenderer b2dr;

    // map 
    private TiledMap map;
    private TiledMapRenderer mapRenderer;

    // character assets
    private static ArrayList<TextureAtlas> chefAtlasArray;
    private Chef[] chef;
    private static ArrayList<TextureAtlas> customerAtlasArray;
    private Customer[] customers = new Customer[5];
	private int chefControl;


    Texture dish1, dish2;
    Texture spaceTexture, ctrlTexture, shiftTexture, rTexture, mTexture;

	public Station chopping;
	public Station toaster;
	public Station frying;
	public Station[] counters;
	public AssemblyStation[] assemblyStations;

	public CustomerCounters[] customerCounters;
	public Texture menu = new Texture("recipeSheet.png");
	private int x = 3;


	public Texture ingredientsSprites = new Texture("pixel_veggies1.png");
	public TextureRegion tomatoUnchopped = new TextureRegion(ingredientsSprites, 224,32, 32,32);
	public TextureRegion tomatoChopped = new TextureRegion(ingredientsSprites, 256,32, 32,32);
	public TextureRegion lettuceUnchopped = new TextureRegion(ingredientsSprites, 256,0, 32,32);
	public TextureRegion lettuceChopped = new TextureRegion(ingredientsSprites, 288,0, 32,32);
	public TextureRegion onionUnchopped = new TextureRegion(ingredientsSprites, 0,32, 32,32);
	public TextureRegion onionChopped = new TextureRegion(ingredientsSprites, 32,32, 32,32);
	public Texture meatUncooked = new Texture("pattyUncooked.png");
	public Texture meatCooked = new Texture("pattyCooked.png");
	public Texture bunUntoasted = new Texture("bunUntoasted.png");
	public Texture bunToasted = new Texture("bunToasted.png");
	public Texture burger = new Texture("bourger_32x32.png");
	public Texture salad = new Texture("Salad_32x32.png");

	// game timer and displayTimer
	private float seconds = 0f;
	private int timer = 0;
	private Label timerLabel;
	private BitmapFont timerFont;

	Music gameMusic;

    /**
	 * Constructor class which initialises all the variables needed to draw the sprites
	 * and also manage the logic of the render as well as setting the camera and map
	 *
     * @param game base Object which is used to draw on
     */
    public GameScreen(MyGdxGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        int viewportWidth = 32 * TILE_WIDTH;
        int viewportHeight = 18 * TILE_HEIGHT;
        camera.setToOrtho(false, viewportWidth, viewportHeight);
		camera.update();

		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
		gameMusic.setLooping(true);

		// tomatoTexture = new Texture("tomato_2.png");
		
		dish1 = new Texture("speech_dish1.png");
		dish2 = new Texture("speech_dish2.png");
		spaceTexture = new Texture("space.png");
		ctrlTexture = new Texture("ctrl.png");
		shiftTexture = new Texture("shift.png");
		mTexture = new Texture("m_key.png");
		rTexture = new Texture("r_key.png");
		world = new World(new Vector2(0,0), true);
		b2dr = new Box2DDebugRenderer();

		// add map
		map = new TmxMapLoader().load("PiazzaPanicMap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		mapRenderer.setView(camera);
		chefAtlasArray = new ArrayList<TextureAtlas>();
		generateChefArray();
		chef = new Chef[2];
		for (int i = 0; i < chef.length; i++) {
			chef[i] = new Chef(world, i);
		}
		chefControl = 0;
		chopping = new Station("chopping");
		toaster = new Station("toaster");
		frying = new Station("frying");
		counters = new Station[5];
		for (int i = 0; i < counters.length; i++) {
			counters[i] = new Station("counters" + Integer.toString(i));
		}
		assemblyStations = new AssemblyStation[5];
		for (int i = 0; i < assemblyStations.length; i++) {
			assemblyStations[i] = new AssemblyStation("assembly" + Integer.toString(i));
		}
		customerCounters = new CustomerCounters[5];
		for (int i = 0; i < customerCounters.length; i++) {
			customerCounters[i] = new CustomerCounters("customerCounter" + Integer.toString(i));
		}

		// generate customer sprites to be used by customer class
		customerAtlasArray = new ArrayList<TextureAtlas>();
		generateCustomerArray();

		for (int i = 0; i < customers.length; i++) {
			customers[i] = new Customer(i+1);
		}

		createCollisionListener();
		int[] objectLayers = {3, 4, 6, 9, 11, 13, 16, 18, 20, 22, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37 ,38 ,39};

		for (int index: objectLayers) {
			for(MapObject object : map.getLayers().get(index).getObjects().getByType(RectangleMapObject.class)){
				String name = "null";
				if (index == 3){
					name = "bin";
				}
				if (index == 4){
					name = "wall";
				}
				else if (index == 6){
					name = "counter";
				}
				else if (index == 9){
					name = "frying";
				}
				else if (index == 11){
					name = "chopping";
				}
				else if (index == 13){
					name = "tomato";
				}
				else if (index == 16){
					name = "lettuce";	
				}
				else if (index == 18){
					name = "onion";
				}
				else if (index == 20){
					name = "patty";
				}
				else if (index == 22){
					name = "bun";
				}
				else if (index == 24){
					name = "toaster";
				}
				else if (index == 25){
					name = "assembly1";
				}
				else if (index == 26){
					name = "assembly2";
				}
				else if (index == 27){
					name = "assembly3";
				}
				else if (index == 28){
					name = "assembly4";
				}
				else if (index == 29){
					name = "assembly5";
				}
				else if (index == 30){
					name = "counter1";
				}
				else if (index == 31) {
					name = "counter2";
				}
				else if (index == 32) {
					name = "counter3";
				}
				else if (index == 33) {
					name = "counter4";
				}
				else if (index == 34) {
					name = "counter5";
				}
				else if (index == 35) {
					name = "customerCounter1";
				}
				else if (index == 36) {
					name = "customerCounter2";
				}
				else if (index == 37) {
					name = "customerCounter3";
				}
				else if (index == 38) {
					name = "customerCounter4";
				}
				else if (index == 39) {
					name = "customerCounter5";
				}


				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				buildObject(world, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), "Static", name);
			}
		}
		timerLabel = new Label("TIME: " + Integer.toString(timer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timerFont = new BitmapFont();
  }

	/**
	 * A function which builds the world box in Box2d which is used for all the hitboxes;
	 *
	 * @param world the world it's being built in
	 * @param x the starting x of the world
	 * @param y the starting y of the world
	 * @param width the width of the world
	 * @param height the height of the world
	 * @param type the type of the world
	 * @param name the name of the world
	 */
  public static void buildObject(World world, float x, float y, float width, float height, String type, String name) {
    BodyDef bdef = new BodyDef();
    bdef.position.set((x + width / 2), (y + height / 2));
    if(type == "Static") {
        bdef.type = BodyDef.BodyType.StaticBody;
    } 
    else if (type == "Dynamic") {
        bdef.type = BodyDef.BodyType.DynamicBody;
    }
    Body body = world.createBody(bdef);
    body.setUserData(name);
    PolygonShape shape = new PolygonShape();
    shape.setAsBox((width / 2), (height / 2));
    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    body.createFixture(fdef);
    }

	/**
	 * Generates a customer array which can be used to get random customer sprites from the customer class
	 */
    public void generateCustomerArray() {
		String filename;
		TextureAtlas customerAtlas;
		for (int i = 1; i < 9; i++) {
			filename = "Customers/Customer"+ i + "/customer" + i + ".txt";
			customerAtlas = new TextureAtlas(filename);
			customerAtlasArray.add(customerAtlas);	
		}
	}


	/**
	 * Generates a chef array which can be used to get random chef sprites from the chef class
	 */
	public void generateChefArray() {
		String filename;
		TextureAtlas chefAtlas;
		for (int i = 1; i < 4; i++) {
			filename = "Chefs/Chef" + i + "/chef" + i + ".txt";
			chefAtlas = new TextureAtlas(filename);
			chefAtlasArray.add(chefAtlas);
		}
	}

	/**
	 * Returns the chef array that's been created
	 *
	 * @return ArrayList<TextureAtlas> chefAtlasArray;
	 */
    public static ArrayList<TextureAtlas> getChefAtlasArray() {
		return chefAtlasArray;
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
		CharSequence str = "TIME: " + Integer.toString(timer);
		timerFont.draw(game.batch, str, 380,35);
		timerFont.getData().setScale(1.5f, 1.5f);
		timerLabel.setText(str);
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

		//Check which chef is being control
		if(chef[1].isCtrl() || chef[0].isCtrl()){
			chef[0].stop();
			chef[1].stop();
			if(chefControl == 0){
				chefControl = 1;
			}
			else{chefControl = 0;}
		}

		// Initialise uncontrolled chefs
		if(x > 0){
			switch (x){
				case 3:
				case 1:
					chef[1].updateSpriteFromInput("idlesouth");
					break;
				case 2:
					chef[1].updateSpriteFromInput("south");
					break;
			}
			x --;
		}

		// Runs chefs logic updates
		chef[chefControl].updateSpriteFromInput(chef[chefControl].getMove());
		for (int i = 0; i < customers.length; i++) {
			customers[i].updateSpriteFromInput(customers[i].getMove());
		}
		for (int i = 0; i < chef.length; i++) {
			chef[i].sprite.setPosition(chef[i].getX(), chef[i].getY());
		}

		// Runs customers logic updates
		for (int i = 0; i < customers.length; i++) {
			customers[i].sprite.setPosition(customers[i].getX(), customers[i].getY());
		}

		world.step(1/60f, 6, 2);

		// Checks if the assembly stations have a completed dish on them
		for (int i = 0; i < assemblyStations.length; i++) {
			assemblyStations[i].assembleDish();
		}

		//Begins drawing the game batch
		game.batch.begin();

		// Calls the function to draw all the ingredients
		drawIngredients();

		// Calls the function to display timer
		displayTimer();

		// Draws the chefs
		for (int i = 0; i < chef.length; i++) {
			chef[i].sprite.setSize(18, 40);
			chef[i].sprite.draw(game.batch);
			if (chef[i].isFrozen) {	// if frozen, need to update timer and sprite
				chef[i].drawTimer(game.batch);
			}
		}

		// Draws the customers and their orders
		for (int i = 0; i < customers.length; i++) {
			customers[i].sprite.setSize(18, 40);
			customers[i].draw(game.batch);
			
			if (customers[i].isWaiting()) {
				Customer customer = customers[i];
				if (customer.getDish() == "salad") {
					game.batch.draw(dish1, ((customer.getX() + customer.sprite.getWidth()/2) - 5), ((customer.getY() + customer.sprite.getHeight()) - 5));
				}
				else if (customer.getDish() == "burger") {
					game.batch.draw(dish2, ((customer.getX() + customer.sprite.getWidth()/2) - 5), ((customer.getY() + customer.sprite.getHeight()) - 5));
				}
			}

			if (customers[i].getDish() == customerCounters[i].getDish()){
				customers[i].fed();
			}
		}

		// Mutes or plays the music
		if(Gdx.input.isKeyJustPressed((Input.Keys.M))){
			if(gameMusic.isPlaying()){
				gameMusic.pause();
			}else{gameMusic.play();}
		}

		// Draws the instuctions and menu
		game.batch.draw(spaceTexture, 160, 400, 130, 80);
		game.batch.draw(ctrlTexture, 280, 410, 90, 60);
		game.batch.draw(shiftTexture, 360,415, 90, 50);
		game.batch.draw(rTexture, 450, 413, 90, 53);
		game.batch.draw(mTexture, 534, 413, 90, 53);
		game.batch.draw(menu, 10, 405, 130, 70);

		game.batch.end();

		// Runs the logic for the collisions between counters and chefs
		gameLogic();

		// Checks if all the customers have been fed and the game is over
		int fedCounter = 0;
		for (int i = 0; i < customers.length; i++) {
			if(customers[i].getFed()){
				fedCounter ++;
			}
		}
		if(fedCounter == 5){
			game.setScreen(new VictoryScreen(game, timer));
		}
    }

	/**
	 * Checks each chef, counter, station, and assembly station then draws the ingredient sprites on them
	 */
	public void drawIngredients(){

		// Checks the chopping board for ingredients
		if (chopping.getIngredient().getName() != "none"){
			switch(chopping.getIngredient().getName()){
				case "tomato":
					if(chopping.getIngredient().getState() == "unprocessed"){
						System.out.println("here");
						game.batch.draw(tomatoUnchopped, 460, 350, 15, 15);
					}
					else{
						game.batch.draw(tomatoChopped, 460, 350, 15, 15);
					}break;
				case "onion":
					if(chopping.getIngredient().getState() == "unprocessed"){
						System.out.println("here");
						game.batch.draw(onionUnchopped, 460, 350, 15, 15);
					}
					else{
						game.batch.draw(onionChopped, 460, 350, 15, 15);
					}break;
				case "lettuce":
					if(chopping.getIngredient().getState() == "unprocessed"){
						System.out.println("here");
						game.batch.draw(lettuceUnchopped, 460, 350, 15, 15);
					}
					else{
						game.batch.draw(lettuceChopped, 460, 350, 15, 15);
					}break;
			}
		}

		// Checks the frying pan for ingredients
		if(frying.getIngredient().getName() != "none"){
			switch (frying.getIngredient().getState()){
				case "unprocessed":
					game.batch.draw(meatUncooked, 500, 295, 15, 15);
					break;
				case "processed":
					game.batch.draw(meatCooked, 500, 295, 15, 15);
					break;
			}
		}

		// checks the toaster for ingredients
		if(toaster.getIngredient().getName() != "none"){
			switch (toaster.getIngredient().getState()){
				case "unprocessed":
					game.batch.draw(bunUntoasted, 555, 255, 15, 15);
					break;
				case "processed":
					game.batch.draw(bunToasted, 555, 255, 15, 15);
					break;
			}
		}

		// Checks the counters for ingredients
		for (int i = 0; i < counters.length; i++) {
			switch (counters[i].getIngredient().getName()){
				case "tomato":
					if(counters[i].getIngredient().getState() == "unprocessed") {
						game.batch.draw(tomatoUnchopped, 300 + i * 20 , 160, 15 , 15);
					}else{
						game.batch.draw(tomatoChopped, 300 + i * 20 , 160, 15, 15);
					}break;
				case "lettuce":
					if(counters[i].getIngredient().getState() == "unprocessed") {
						game.batch.draw(lettuceUnchopped, 300 + i * 20 , 160, 15 , 15);
					}else{
						game.batch.draw(lettuceChopped, 300 + i * 20 , 160, 15, 15);
					}break;
				case "onion":
					if(counters[i].getIngredient().getState() == "unprocessed") {
						game.batch.draw(onionUnchopped, 300 + i * 20 , 160, 15 , 15);
					}else{
						game.batch.draw(onionChopped, 300 + i * 20 , 160, 15, 15);
					}break;
				case "patty":
					if(counters[i].getIngredient().getState() == "unprocessed") {
						game.batch.draw(meatUncooked, 300 + i * 20 , 160, 15 , 15);
					}else{
						game.batch.draw(meatCooked, 300 + i * 20 , 160, 15, 15);
					}break;
				case "bun":
					if(counters[i].getIngredient().getState() == "unprocessed") {
						game.batch.draw(bunUntoasted, 300 + i * 20 , 160, 15 , 15);
					}else{
						game.batch.draw(bunToasted, 300 + i * 20 , 160, 15, 15);
					}break;
				case "salad":
					game.batch.draw(salad, 300 + i * 20, 160, 15, 15);
					break;
				case "burger":
					game.batch.draw(burger, 300 + i * 20, 160, 15, 15);
			}
		}

		// Checks the chefs inventory for ingredients
		for (int i = 0; i < chef.length; i++) {
			switch(chef[i].getInventory().getName()){
				case "tomato":
					if(chef[i].getInventory().getState() == "unprocessed") {
						game.batch.draw(tomatoUnchopped, 10 + i * 600 , 18, 20 , 20);
					}else{
						game.batch.draw(tomatoChopped, 10 + i * 600, 18, 20, 20);
					}break;
				case "lettuce":
					if(chef[i].getInventory().getState() == "unprocessed") {
						game.batch.draw(lettuceUnchopped, 10 + i * 600 , 18, 20 , 20);
					}else{
						game.batch.draw(lettuceChopped, 10 + i * 600, 18, 20, 20);
					}break;
				case "onion":
					if(chef[i].getInventory().getState() == "unprocessed") {
						game.batch.draw(onionUnchopped, 10 + i * 600 , 18, 20 , 20);
					}else{
						game.batch.draw(onionChopped, 10 + i * 600, 18, 20, 20);
					}break;
				case "patty":
					if(chef[i].getInventory().getState() == "unprocessed") {
						game.batch.draw(meatUncooked, 10 + i * 600 , 18, 20 , 20);
					}else{
						game.batch.draw(meatCooked, 10 + i * 600, 18, 20, 20);
					}break;
				case "bun":
					if(chef[i].getInventory().getState() == "unprocessed") {
						game.batch.draw(bunUntoasted, 10 + i * 600 , 18, 20 , 20);
					}else{
						game.batch.draw(bunToasted, 10 + i * 600, 18, 20, 20);
					}break;
				case "burger":
					game.batch.draw(burger, 10 + i * 600, 18, 20, 20);
					break;
				case "salad":
					game.batch.draw(salad, 10 + i * 600, 18, 20, 20);
			}
		}

		//Checks the assembly stations for ingredients
		for (int i = 0; i < assemblyStations.length; i++) {
			if (assemblyStations[i].getDish() == "none") {
				for (int j = 0; j < assemblyStations[i].getIngredients().size(); j++) {
					int x = 420 + i * 20;
					int y = 160;
					switch(j){
						case 0:
							x = 420 + i * 20;
							y = 160;
							break;
						case 1:
							x = 420 + i * 20;
							y = 160 + 10;
							break;
						case 2:
							x = 430 + i * 20;
							y = 160;
							break;
						case 3:
							x = 430 + i * 20;
							y = 160 + 10;
							break;
					}
					switch (assemblyStations[i].getIngredients().get(j).getName()) {
						case "tomato":
							game.batch.draw(tomatoChopped, x, y, 10, 10);
							break;
						case "bun":
							game.batch.draw(bunToasted, x, y, 10, 10);
							break;
						case "patty":
							game.batch.draw(meatCooked, x, y, 10, 10);
							break;
						case "onion":
							game.batch.draw(onionChopped, x, y, 10, 10);
							break;
						case "lettuce":
							game.batch.draw(lettuceChopped, x, y, 10, 10);
							break;
					}
				}
			}else if (assemblyStations[i].getDish() == "salad") {
				game.batch.draw(salad, 420 + i * 20, 160, 20, 20);
			} else if (assemblyStations[i].getDish() == "burger") {
				game.batch.draw(burger, 420 + i * 20, 160, 20, 20);
			}
		}
	}

	/**
	 * Gets all the collisions and checks if the input keys are pressed and then performs the actions as specified
	 */
	public void gameLogic(){

		// gets all the collisions and iterates through them
		int numContacts = world.getContactCount();
		if (numContacts > 0){
			for (Contact contact : world.getContactList()){
				Object objectA = contact.getFixtureA().getBody().getUserData();
				Object objectB = contact.getFixtureB().getBody().getUserData();

				// Checks if the object being interacted with is a chef
				if(objectB.toString().contentEquals("Chef" + chefControl)  || objectA.toString().contentEquals("Chef" + chefControl)) {

					boolean isShift = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
					boolean isSpace = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
					boolean isR = Gdx.input.isKeyPressed(Input.Keys.R);
					boolean invFull;
					if (chef[chefControl].getInventory().getName() == "none") {
						invFull = false;
					} else {
						invFull = true;
					}

					// The following statements are for picking items up from the stations
					if (objectA == "tomato" && !invFull && isSpace) {
						chef[chefControl].setInventory(new Ingredient("tomato"));
					}
					if (objectA == "lettuce" && !invFull && isSpace) {
						chef[chefControl].setInventory(new Ingredient("lettuce"));
					}
					if (objectA == "onion" && !invFull && isSpace) {
						chef[chefControl].setInventory(new Ingredient("onion"));
					}
					if (objectA == "patty" && !invFull && isSpace) {
						chef[chefControl].setInventory(new Ingredient("patty"));
					}
					if (objectA == "bun" && !invFull && isSpace) {
						chef[chefControl].setInventory(new Ingredient("bun"));
					}

					// Runs all the checks for the chopping board
					if(objectA == "chopping"){
						if(chef[chefControl].getInventory().getStation() == "chopping" && chopping.getIngredient().getName() == "none" && isSpace){
							Ingredient tempIngredient = new Ingredient(chef[chefControl].getInventory().getName());
							tempIngredient.setState(chef[chefControl].getInventory().getState());
							chopping.setIngredient(tempIngredient);
							chef[chefControl].setInventory(new Ingredient("none"));
						}
						if(isShift && chopping.getIngredient().getName() != "none"){
							chef[chefControl].freeze(3, chopping);
							chopping.getIngredient().setState("processed");
						}
						if(chopping.getIngredient().getState() == "processed" && chopping.getIngredient().getName() != "none" && !invFull && isSpace && !chopping.getLocked()){
							Ingredient tempIngredient = new Ingredient(chopping.getIngredient().getName());
							tempIngredient.setState(chopping.getIngredient().getState());
							chef[chefControl].setInventory(tempIngredient);
							chopping.setIngredient(new Ingredient("none"));
						}
					}

					// Runs all the checks for the frying pan
					if(objectA == "frying"){
						if(chef[chefControl].getInventory().getStation() == "frying" && frying.getIngredient().getName() == "none" && isSpace){
							Ingredient tempIngredient = new Ingredient(chef[chefControl].getInventory().getName());
							tempIngredient.setState(chef[chefControl].getInventory().getState());
							frying.setIngredient(tempIngredient);
							chef[chefControl].setInventory(new Ingredient("none"));
						}
						if(isShift && frying.getIngredient().getName() != "none"){
							chef[chefControl].freeze(3, frying);
							frying.getIngredient().setState("processed");
						}
						if(frying.getIngredient().getState() == "processed" && frying.getIngredient().getName() != "none" && !invFull && isSpace && !frying.getLocked()){
							Ingredient tempIngredient = new Ingredient(frying.getIngredient().getName());
							tempIngredient.setState(frying.getIngredient().getState());
							chef[chefControl].setInventory(tempIngredient);
							frying.setIngredient(new Ingredient("none"));
						}
					}

					// Runs all the checks on the toaster
					if(objectA == "toaster"){
						if(chef[chefControl].getInventory().getStation() == "toaster" && toaster.getIngredient().getName() == "none" && isSpace){
							Ingredient tempIngredient = new Ingredient(chef[chefControl].getInventory().getName());
							tempIngredient.setState(chef[chefControl].getInventory().getState());
							toaster.setIngredient(tempIngredient);
							chef[chefControl].setInventory(new Ingredient("none"));
						}
						if(isShift && toaster.getIngredient().getName() != "none"){
							chef[chefControl].freeze(3, toaster);
							toaster.getIngredient().setState("processed");
						}
						if(toaster.getIngredient().getState() == "processed" && toaster.getIngredient().getName() != "none" && !invFull && isSpace && !toaster.getLocked()){
							Ingredient tempIngredient = new Ingredient(toaster.getIngredient().getName());
							tempIngredient.setState(toaster.getIngredient().getState());
							chef[chefControl].setInventory(tempIngredient);
							toaster.setIngredient(new Ingredient("none"));
						}
					}

					// Gets rid of inventory if next to bin
					if(objectA == "bin" && isSpace){
						chef[chefControl].setInventory(new Ingredient("none"));
					}

					// Checks all the counters for interactions
					for (int i = 1; i < 6; i++) {
						if(objectA.toString().contentEquals("counter" + i)) {
							if (isSpace && counters[i - 1].getIngredient().getName() == "none") {
								Ingredient tempIngredient = new Ingredient(chef[chefControl].getInventory().getName());
								tempIngredient.setState(chef[chefControl].getInventory().getState());
								counters[i - 1].setIngredient(tempIngredient);
								chef[chefControl].setInventory(new Ingredient("none"));
							}
							else if (isSpace && chef[chefControl].getInventory().getName() == "none") {
								Ingredient tempIngredient = new Ingredient(counters[i - 1].getIngredient().getName());
								tempIngredient.setState(counters[i - 1].getIngredient().getState());
								chef[chefControl].setInventory(tempIngredient);
								counters[i - 1].setIngredient(new Ingredient("none"));
							}
						}
					}

					// Checks all the assembly stations for interactions
					for (int i = 0; i < assemblyStations.length; i++) {
						if(objectA.toString().contentEquals("assembly" + Integer.toString(i+1))) {
							if (isSpace && assemblyStations[i].validIngredient(chef[chefControl].getInventory().getName()) && chef[chefControl].getInventory().getState() == "processed") {
								Ingredient tempIngredient = new Ingredient(chef[chefControl].getInventory().getName());
								tempIngredient.setState(chef[chefControl].getInventory().getState());
								chef[chefControl].setInventory(new Ingredient("none"));
								assemblyStations[i].addIngredient(tempIngredient);
							}
							if(assemblyStations[i].getDish() != "none" && isSpace){
								chef[chefControl].setInventory(new Ingredient(assemblyStations[i].getDish()));
								assemblyStations[i].setDish("none");
							}
							if (isR) {
								assemblyStations[i].clearIngredients();
								assemblyStations[i].setDish("none");
							}
						}
					}

					// Checks all the customer counters for interactions
					for (int i = 0; i < customerCounters.length; i++) {
						if(objectA.toString().contentEquals("customerCounter" + Integer.toString(i+1))){
							if(isSpace && customers[i].getDish() == chef[chefControl].getInventory().getName()){
								Ingredient tempDish = new Ingredient(chef[chefControl].getInventory().getName());
								customerCounters[i].setDish(tempDish.getName());
								chef[chefControl].setInventory(new Ingredient("none"));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Finds all the collisions and assigns the names
	 * Also has a convenience function to disregard the chef collision
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
				if((objectA.toString().contentEquals("Chef0")) && (objectB.toString().contentEquals("Chef1"))){
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
