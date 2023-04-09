package com.mygdx.game.Core.GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.mygdx.game.Core.GameObjectManager;
import com.mygdx.game.GameScreen;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import jdk.javadoc.internal.doclets.toolkit.util.DocFinder.Output;

public class SaveState {


  public void SaveState(GameScreen gameState, String path){
    GameState state = new GameState();
    gameState.masterChef.SaveState(state);
    gameState.customerController.SaveState(state);
    gameState.SaveState(state);

    try {
      FileOutputStream fileOut = new FileOutputStream(path);
      ObjectOutputStream stream = new ObjectOutputStream(fileOut);
      stream.writeObject(state);
      stream.close();
      fileOut.close();
      System.out.println("Game state printed to: " + path);

    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }


  }

  public GameState LoadState(String ID){
    GameState state = null;

    FileInputStream fileIn = null;
    try {
      fileIn = new FileInputStream(ID);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      state = (GameState) in.readObject();
      in.close();
      fileIn.close();


    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    return state;


  }
}
