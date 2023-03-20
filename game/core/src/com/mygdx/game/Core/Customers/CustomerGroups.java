package com.mygdx.game.Core.Customers;

import com.mygdx.game.Core.BlackSprite;
import com.mygdx.game.Core.GameObject;
import com.mygdx.game.Customer;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;
import java.util.LinkedList;
import java.util.List;
import com.badlogic.gdx.math.Vector2;
import java.util.function.Consumer;

public class CustomerGroups {
  public List<Customer> Members = new LinkedList<>();
  public List<Customer> MembersInLine = new LinkedList<>();
  public List<Customer> MembersSeatedOrWalking = new LinkedList<>();
  public List<ItemEnum> Orders = new LinkedList<>();

  public Table table;
  public float Frustration;

  private float RecoveryValue;
  static float FrustrationRecovery = .1f;

  public CustomerGroups(int MemberCount, int CustomerStart, Vector2 Spawn, int frustration, List<ItemEnum> OrderMenu){
    Orders = OrderMenu;
    Frustration = frustration;
    RecoveryValue =  FrustrationRecovery * Frustration;
    for (int i = 0; i < MemberCount; i++) {
      if(OrderMenu.size()<MemberCount)
         i = i;
      Customer custLogic = new Customer(CustomerStart + i,OrderMenu.get(i));
      GameObject customer = new GameObject(new BlackSprite());
      customer.position.set(Spawn);
      customer.attachScript(custLogic);
      customer.isVisible = true;

      Members.add(custLogic);
      MembersInLine.add(custLogic);
    }

  }


  public Customer RemoveFirstCustomer(){
    MembersSeatedOrWalking.add( MembersInLine.remove(0));
    return MembersSeatedOrWalking.get(MembersSeatedOrWalking.size()-1);
  }
  public boolean TryRemoveCustomer(Item item){
    for (int i = 0; i < MembersInLine.size(); i++) {
        if(MembersInLine.get(i).dish == item.name)
        {
          MembersInLine.remove(i);
          Frustration += RecoveryValue;
          return true;
        }
    }

    return false;

  }
  public void updateSpriteFromInput(){
    for (Customer customer : Members) {
     customer.updateSpriteFromInput(customer.getMove());
    }


  }

  public void CheckFrustration(float dt, Consumer<CustomerGroups> CauseLeave){
        Frustration -= dt;
        if(Frustration<=0)
          CauseLeave.accept(this);
  }



}
