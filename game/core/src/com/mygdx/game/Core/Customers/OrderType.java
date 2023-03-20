package com.mygdx.game.Core.Customers;

import com.mygdx.game.Items.ItemEnum;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class OrderType
{
  public ItemEnum orderClass;

  public List<ItemEnum> orderables;
  public int stock;

  int defStock;
  int minStock;

  public OrderType(int stock, int minStock, ItemEnum... orders){
    orderables = new LinkedList<>(Arrays.asList(orders));
    this.stock = stock;
    this.minStock = minStock;
    defStock = stock;
  }

  public void TakeStock(int count){
    stock = Math.max(stock-count,minStock);

  }

  public ItemEnum GetOrder(Random rand){
    int orderID = rand.nextInt(orderables.size());
    TakeStock(1);
    return orderables.get(orderID);

  }

  public void Restock(){
    stock = defStock;
  }

}
