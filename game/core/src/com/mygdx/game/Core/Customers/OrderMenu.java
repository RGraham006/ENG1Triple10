package com.mygdx.game.Core.Customers;

import com.mygdx.game.Items.ItemEnum;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class OrderMenu
{

  List<OrderType> OrderCatagories = new LinkedList<>();
  Random rand;
  int minStock;

  public OrderMenu(int defaultStock, int rareStock, int minStock){
    rand = new Random();
    this.minStock = minStock;
    OrderType burgers = new OrderType(defaultStock, minStock, ItemEnum.Burger,ItemEnum.CheeseBurger);
    OrderType salads =  new OrderType(defaultStock, minStock, ItemEnum.LettuceOnionSalad,ItemEnum.LettuceTomatoSalad,ItemEnum.TomatoOnionLettuceSalad);
    OrderType potato =  new OrderType(rareStock,    minStock, ItemEnum.BakedPotato, ItemEnum.MeatBake, ItemEnum.CheeseBurger);
    OrderType pizza =   new OrderType(rareStock,    minStock, ItemEnum.CheesePizzaCooked, ItemEnum.MeatPizzaCooked, ItemEnum.VegPizzaCooked);

    OrderCatagories.add(burgers);
    OrderCatagories.add(salads);
   // OrderCatagories.add(potato);
    //OrderCatagories.add(pizza);
  }

  public List<ItemEnum> CreateNewOrder(int count, Randomisation random)
  {
    if(Randomisation.TrueRandom == random)
      return CreateTrueRandomOrder(count);
    else if(Randomisation.Normalised == random)
      return CreateNormalisedOrder(count);

    return new LinkedList<>();

  }

  List<ItemEnum> CreateTrueRandomOrder(int count){
    List<ItemEnum> orders = new LinkedList<>();
    for (int i = 0; i < count; i++) {
      int catagory = rand.nextInt(OrderCatagories.size());
      orders.add(OrderCatagories.get(catagory).GetOrder(rand));
    }
    return orders;
  }

  List<ItemEnum> CreateNormalisedOrder(int count){

    List<ItemEnum> orders = new LinkedList<>();
    for (int j = 0; j < count; j++) {

    int totalStock = 0;

    for (OrderType catagory: OrderCatagories
    ) {
      totalStock += catagory.stock;
    }
    int catagoryID = rand.nextInt(totalStock-1);
    for (OrderType catagory:OrderCatagories
    ) {
      totalStock -=  catagory.stock;

      if(totalStock<=0)
      {
       orders.add(catagory.GetOrder(rand));
       break;
      }
    }
    }

    return  orders;
  }

  public void Restock(){
    for (OrderType type: OrderCatagories
    ) {
      type.Restock();
    }
  }

}
