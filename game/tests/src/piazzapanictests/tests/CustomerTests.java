package piazzapanictests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Core.CustomerController;
import com.mygdx.game.Core.Customers.CustomerGroups;
import com.mygdx.game.Core.GameObjectManager;
import com.mygdx.game.Core.Pathfinding;
import com.mygdx.game.Core.ValueStructures.CustomerControllerParams;
import com.mygdx.game.Core.ValueStructures.EndOfGameValues;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.ItemEnum;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)

public class CustomerTests {

  GameObjectManager manager;
  CustomerController cust;
  Pathfinding pathfinding;
  EndOfGameValues vals;
  CustomerControllerParams params = new CustomerControllerParams();
  void InstantiateCustomerScripts(){

    GameObjectManager.objManager = null;

    pathfinding = new Pathfinding(GameScreen.TILE_WIDTH/4,GameScreen.viewportWidth,GameScreen.viewportWidth);

    manager = new GameObjectManager();
  params = new CustomerControllerParams();
  params.MoneyStart=10;
  params.Reputation=3;
  params.MaxMoney=100;
  params.NoCustomers=5;
  params.MaxCustomersPerWave=4;
  params.MinCustomersPerWave=2;

  cust = new CustomerController(new Vector2(0,0), new Vector2(32,0),pathfinding, (EndOfGameValues a)->EndGame(a), params,new Vector2(190,390),new Vector2(190,290),new Vector2(290,290));


  }

@Test
  public void TestEndGame(){
      InstantiateCustomerScripts();

      cust.SetWaveAmount(0);
      cust.ModifyReputation(-10);

  assertNotNull("The game must do an end state call",vals);

  vals = null;

  cust.ModifyReputation(20);

  cust.CanAcceptNewCustomer();


  assertNotNull("The game must do an end state call",vals);


  }

  @Test
  public void TestCustomerGroups(){
    InstantiateCustomerScripts();


    cust.CanAcceptNewCustomer();

    CustomerGroups group = cust.getCurrentWaitingCustomerGroup();

    assertNotEquals("Group must have members", 0,group.Members.size());

    ItemEnum dish = group.Members.get(0).dish;
    assertNotNull("Members must have a dish", dish);

    boolean attempt  = group.SeeIfDishIsCorrect(dish) != -1;

    assertTrue("Must be able to remove a customer by their food",attempt);

    cust.tryGiveFood(new Item(dish));


    assertEquals("A member must be trying to sit down now", group.MembersSeatedOrWalking.size(),1);
    assertEquals("That member must have the correct dish to be able to sit down", dish,group.MembersSeatedOrWalking.get(0).dish);


  }





  void EndGame(EndOfGameValues val){
  vals =val;
  }

}
