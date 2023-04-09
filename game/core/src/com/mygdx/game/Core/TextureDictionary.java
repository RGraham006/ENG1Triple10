package com.mygdx.game.Core;

import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

public class TextureDictionary {

  public static TextureDictionary textures;

  private HashMap<String,Texture> HeldTextures = new HashMap<>();


  public TextureDictionary(){
    if(textures != null)
      return;

    textures = this;
  }

  public Texture Get(String path){

    if(HeldTextures.containsKey(path))
      return HeldTextures.get(path);

    Texture tex = new Texture(path);

    HeldTextures.put(path,tex);
    return tex;
  }

}
