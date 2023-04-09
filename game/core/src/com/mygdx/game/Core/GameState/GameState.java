package com.mygdx.game.Core.GameState;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Items.ItemEnum;
import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable
{
  public int Money;
  public int MaxMoney;

  public CustomerGroupState[] CustomerGroupsData;
  public Vector2[] ChefPositions;
  public ItemState[] ChefHoldingStacks;//Must be in format of chefs 1 items (if not holding an item at slot N then null)
  public List<List<ItemState>> FoodOnCounters;
  public Vector2 GroupSize;
  public int Wave;
  public int MaxWave;
  public int MaxFrustration;

  public int Timer;
  public float seconds;
  public int Reputation;
  public int MaxReputation;
}

