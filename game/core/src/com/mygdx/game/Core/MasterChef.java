package com.mygdx.game.Core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Chef;
import com.mygdx.game.Core.Interactions.Interactable;
import com.mygdx.game.Core.Interactions.Interaction;
import com.mygdx.game.Core.Interactions.Interaction.InteractionType;
import com.mygdx.game.Items.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MasterChef extends Scriptable
{

  public int currentControlledChef = 0;
  private static ArrayList<TextureAtlas> chefAtlasArray;

  private Camera camera;
  Chef[] chefs;

  private Pathfinding pathfind;


  public int returnChefCount(){
    return chefs.length;
  }

  public Chef getChef(int i) {
    return chefs[i];
  }

  public Chef getCurrentChef()
  {

    return  chefs[currentControlledChef];
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


  public MasterChef(int count, World world, Camera camera, Pathfinding pathfinding){

    chefAtlasArray = new ArrayList<TextureAtlas>();
    this.pathfind = pathfinding;
    generateChefArray();
    chefs = new Chef[count];

    this.camera = camera;


    for (int i = 0; i < chefs.length; i++) {
      GameObject chefsGameObject = new GameObject(
          new BlackSprite());//passing in null since chef will define it later
      chefs[i] = new Chef(world, i,chefAtlasArray);
      chefsGameObject.attachScript(chefs[i]);
      chefsGameObject.image.setSize(18, 40);

      chefs[i].updateSpriteFromInput("idlesouth");
    }

  }


  void SelectChef(int i)
  {
    currentControlledChef = i;

  }


  public void GiveItem()
  {

    if(chefs[currentControlledChef].CanFetchItem())
      return;

    Scriptable script = Interaction.FindClosetInteractable(chefs[currentControlledChef].gameObject.position,InteractionType.Give);

    Optional<Item> itemToGive = chefs[currentControlledChef].FetchItem();


    if(!itemToGive.isPresent())
      return;

    ((Interactable)script).GiveItem(itemToGive.get());
  }

  public void FetchItem(){

    if(chefs[currentControlledChef].CanGiveItem())
      return;

    Scriptable script = Interaction.FindClosetInteractable(chefs[currentControlledChef].gameObject.position,InteractionType.Fetch);


    Item itemToGive = ((Interactable)script).RetrieveItem();

    if(itemToGive == null)
      return;


    chefs[currentControlledChef].GiveItem(itemToGive);


  }

  void selectChef() {
    for (int i = 0; i < chefs.length; i++) {
      if (Gdx.input.isKeyPressed(Input.Keys.NUM_1
          + i)) // increments to next number for each chef 1,2,3 ect (dont go above 9)
      {
        SelectChef(i);

        for (Chef c:chefs
        ) {
          c.stop();
        }
        // Runs chefs logic updates

      }
    }
  }

  @Override
  public void Update(float dt){
    selectChef();

    chefs[currentControlledChef].updateSpriteFromInput(chefs[currentControlledChef].getMove());

    if(Gdx.input.isKeyJustPressed(Inputs.GIVE_ITEM))
      GiveItem();
    if(Gdx.input.isKeyJustPressed(Inputs.FETCH_ITEM))
      FetchItem();

    if( Gdx.input.isButtonJustPressed(0)) {
      Vector3 touchpos = new Vector3();
      touchpos.set(Gdx.input.getX(), Gdx.input.getY(),0);
      touchpos = camera.unproject(touchpos);

   //   List<Vector2> path = pathfind.FindPath((int)getCurrentChef().gameObject.position.x,(int)getCurrentChef().gameObject.position.y,(int)touchpos.x,(int)touchpos.y,DistanceTest.Euclidean);
      //System.out.println(path);
     // getCurrentChef().GivePath(path);
    }

    if(Gdx.input.isKeyJustPressed(Keys.B))
      getCurrentChef().MoveAlongPath();

    }

}
