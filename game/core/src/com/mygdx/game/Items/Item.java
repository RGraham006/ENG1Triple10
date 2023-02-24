package com.mygdx.game.Items;

import com.mygdx.game.Core.BlackTexture;

public class Item
{
  public ItemEnum name;
  public BlackTexture tex;
  public float doneness;
  public float burntness;

  int width = 20;
  int height= 20;

  public Item(ItemEnum item)
  {
    name = item;
    tex = new BlackTexture(GetItemPath(item));

    tex.setSize(width,height);
  }

  public static String GetItemPath(ItemEnum name){
    return "Items/"+name.name()+".png";
  }
}
