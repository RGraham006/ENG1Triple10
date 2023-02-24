package com.mygdx.game.Items;

import com.mygdx.game.Core.BlackTexture;

public class Item
{
  public ItemEnum name;
  BlackTexture tex;
  public float doneness;
  public float burntness;

  public Item(ItemEnum item)
  {
    name = item;
    tex = new BlackTexture(GetItemPath(item));
  }

  public static String GetItemPath(ItemEnum name){
    return "Items/"+name.name()+".png";
  }
}
