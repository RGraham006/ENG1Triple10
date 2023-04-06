package com.mygdx.game.Core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Core.Customers.CustomerGroups;
import com.mygdx.game.Core.Customers.OrderMenu;
import com.mygdx.game.Core.Customers.Randomisation;
import com.mygdx.game.Core.Customers.Table;
import com.mygdx.game.Core.ValueStructures.CustomerControllerParams;
import com.mygdx.game.Core.ValueStructures.EndOfGameValues;
import com.mygdx.game.Customer;
import com.mygdx.game.Items.Item;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;
import java.util.function.Consumer;

/*
  This script controls the customers and handles their logic through a veriaty of secondary scripts.
  Also handles when the current game should end.

  Last modified: 27/03/2023
 */

public class CustomerController extends Scriptable
{
  CustomerGroups currentWaiting = null;
  List<CustomerGroups> SittingCustomers = new LinkedList<>();
  List<CustomerGroups> WalkingBackCustomers = new LinkedList<>();
  Pathfinding pathfinding;
  List<Table> tables;
  Consumer<EndOfGameValues> CallEndGame;

  public int Reputation;
  public int Money;
  int MaxMoney;
  int MaxReputation;
  int MoneyPerCustomer;

  int Waves = -1;
  OrderMenu menu;
  int currentCustomer = 0;
  int currentWave = 0;
  private float EatingTime = 7;

  Random rand = new Random(System.currentTimeMillis());

  private Vector2 groupSize = new Vector2(2,4);
  float NextToLeave = EatingTime;

  int MaxCustomers;
  int CustomersRemaining;

  Consumer<CustomerGroups> FrustrationCallBack;

  Vector2 DoorTarget;
  Vector2 OrderAreaTarget;


  private ArrayList<TextureAtlas> CustomerAtlas = new ArrayList<>();
  private int CustomerFrustrationStart = 80;

  /**
   * Creates the customer controller
   * @param DoorPosition Customer spawn and exit.
   * @param OrderArea First position in order line
   * @param path    Pathfinding Module.
   * @param CallUpGameFinish Game Finish Function.
   * @param params Parameter class
   * @param TablePositions Where the tables are, TEMPORARY
   * @author Felix Seanor
   */
  public CustomerController(Vector2 DoorPosition, Vector2 OrderArea, Pathfinding path,
      Consumer<EndOfGameValues> CallUpGameFinish, CustomerControllerParams params, Vector2... TablePositions){
    tables = new LinkedList<>();
    FrustrationCallBack = (CustomerGroups a)   -> FrustrationLeave(a);
    MoneyPerCustomer = params.MoneyPerCustomer;
    CallEndGame = CallUpGameFinish;
    Money = params.MoneyStart;
    MaxMoney = params.MaxMoney;
    Reputation = params.Reputation;
    MaxReputation = params.Reputation;

    groupSize.y = Math.min(params.MaxCustomersPerWave,groupSize.y);
    groupSize.x = Math.max(params.MinCustomersPerWave, groupSize.x);

    CalculateWavesFromNoCustomers(params.NoCustomers);


    generateCustomerArray();

    for (Vector2 pos: TablePositions
    ) {
      tables.add(new Table(pos,30));
    }

    pathfinding = path;
    OrderAreaTarget = OrderArea;
    DoorTarget = DoorPosition;


    menu = new OrderMenu(10,7,3);
  }

  public void CalculateWavesFromNoCustomers(int NoCustomers){

    MaxCustomers = NoCustomers;
    CustomersRemaining = NoCustomers;

    if(NoCustomers==-1)
    {

      SetWaveAmount(-1);
      return;
    }
    float averageCustomersPerWave = (groupSize.x + groupSize.y)/2;

    int waves = (int)Math.ceil(NoCustomers/averageCustomersPerWave);

    SetWaveAmount(waves);


  }
  /***
   * Set the maximum number of waves to do, exclusively. Resets currentWave to 0
   * @param amount
   * @author Felix Seanor
   */
  public void SetWaveAmount(int amount){
    Waves = amount;
    currentWave = 0;
  }

  /**
   * Generates a customer array which can be used to get random customer sprites from the customer
   * class
   */
  public void generateCustomerArray() {
    String filename;
    TextureAtlas customerAtlas;

    //The file path takes it to data for each animation
    //The TextureAtlas creates a texture atlas where the you pass through the string of the number and it returns the image.
    //Taking all pictures in the diretory of the file
    for (int i = 1; i < 9; i++) {
      filename = "Customers/Customer" + i + "/customer" + i + ".txt";
      customerAtlas = new TextureAtlas(filename);
      CustomerAtlas.add(customerAtlas);
    }
  }
  /**
   * Updates all customers in groups to update their animation.
   * @param customers
   * @author Felix Seanor
   */
  public void UpdateCustomerMovements(List<CustomerGroups> customers){
    for (int i = 0; i < customers.size(); i++) {
      customers.get(i).updateSpriteFromInput();
    }
  }

  public CustomerGroups getCurrentWaitingCustomerGroup()
  {return currentWaiting;}
  /**
   *  Modifiesr the reputation, if reputation + DR <= 0 END GAME.
   * @param DR delta reputation
   * @author Felix Seanor
   */
  public void ModifyReputation(int DR){

    Reputation += DR;
    Reputation = Math.min(Reputation,MaxReputation);

    if(Reputation<=0)
      EndGame();
  }

  /**
   * Do check and modify money.
   * @param DM Delta Money
   * @return decrease in money is allowed.
   * @author Felix Seanor
   */
  public boolean ChangeMoney(float DM){
    if(DM>=0)
    {
      Money += DM;
      Money = Math.min(MaxMoney,Money);
      return true;
    }

    if(Money-DM>=0){
      Money  += DM;
      return true;
    }

    return false;


  }


  @Override
  public void Update(float dt) {
    super.Update(dt);

    if(currentWaiting!=null)
    currentWaiting.updateSpriteFromInput();
    UpdateCustomerMovements(SittingCustomers);
    UpdateCustomerMovements(WalkingBackCustomers);


    FrustrationCheck(dt);

    RemoveCustomerTest();
      SeeIfCustomersShouldLeave(dt);
    CanAcceptNewCustomer();



  }

  /**
   * Gets the next avalible Table.
   * @return next table. NULL if no free
   * @author Felix Seanor
   */
  public Table GetTable(){
    for (Table option:tables
    ) {
        if(option.isFree())
          return option;
    }

    return null;
  }

  /**
   * Change frustration of the currently waiting customer group
   * @param dt delta time
   * @author Felix Seanor
   */
  private void FrustrationCheck(float dt){
    if(currentWaiting == null)
      return;
    currentWaiting.CheckFrustration(dt,FrustrationCallBack);
  }

  /**
   * See if a currently seated customer should leave to make space for a new customer to enter.
   * @param dt
   * @author Felix Seanor
   */
  public void SeeIfCustomersShouldLeave(float dt){
    if(SittingCustomers.size()>0)
      NextToLeave -= dt;

    if(NextToLeave <= 0)
      RemoveCurrentlySeatedCustomers();

    TryDeleteCustomers();

  }

  /**
   * Removes the first currently seated customer and makes them walk outside and despawn.
   */
  void RemoveCurrentlySeatedCustomers(){
      CustomerGroups groups = SittingCustomers.get(0);
      groups.table.relinquish();
      WalkingBackCustomers.add(groups);
      SittingCustomers.remove(0);

      SetCustomerGroupTarget(groups,DoorTarget);

      NextToLeave = EatingTime;

  }

  /**
   * Trys to remove customers when they reach the exit.
   */
  void TryDeleteCustomers(){
    List<Integer> removals = new LinkedList<>();
    int r =0;
    for (CustomerGroups group: WalkingBackCustomers
    ) {

      for (int i = group.Members.size()-1; i >= 0 ; i--) {

          if(group.Members.get(i).gameObject.position.dst(DoorTarget.x,DoorTarget.y)<1) {
            group.Members.get(i).gameObject.Destroy();
            group.Members.remove(i);
          }

      }

      if(group.Members.size()==0)
        removals.add(r);

      r++;

    }
    for (Integer i: removals
    ) {
      WalkingBackCustomers.remove(i);
    }
  }

  int WavesLeft(){
    return  Waves-currentWave;
  }

  /**
   * Creates a new customer group of a random size, and gives them a list of foods to order.
   * @author Felix Seanor
   */


  public int calculateCustomerAmount(){

    if(WavesLeft() == 0)
      return CustomersRemaining;

    int rnd = rand.nextInt((int)groupSize.y-(int)groupSize.x)+ (int)groupSize.x ;

    int minimumCustomerDraw = WavesLeft() * (int)groupSize.x;
    int MaxDraw = (CustomersRemaining - rnd)  - WavesLeft() * (int)groupSize.y;

    minimumCustomerDraw = CustomersRemaining - minimumCustomerDraw;


    return Math.min(minimumCustomerDraw,rnd) + Math.max(0,MaxDraw);

  }
  void CreateNewCustomer(){
    Table table = GetTable();


      int customerAmount = calculateCustomerAmount();
    CustomersRemaining -= customerAmount;


    currentWaiting = new CustomerGroups(customerAmount,currentCustomer, DoorTarget, CustomerFrustrationStart, menu.CreateNewOrder(customerAmount, Randomisation.Normalised),CustomerAtlas);
    currentCustomer += customerAmount;

    currentWaiting.table= table;
    table.DesignateSeating(customerAmount,rand);

    SetWaitingForOrderTarget();
  }

  /**
   * Makes the currently waiting customers to queue up dynamically.
   * @author Felix Seanor
   */
  void SetWaitingForOrderTarget(){
    for (int i = 0; i < currentWaiting.MembersInLine.size(); i++) {
        SetCustomerTarget(currentWaiting.MembersInLine.get(i),new Vector2(0,40*i).add(OrderAreaTarget));
    }
  }

  /**
   * Checks if a new customer can be accepted if so, add a new one in. End the game if the set number of waves has elapsed.
   * Set Waves to -1 for "endless"
   * @author Felix Seanor
   */

  public void CanAcceptNewCustomer(){
    if(DoSatisfactionCheck())
    {
      SittingCustomers.add(currentWaiting);
      currentWaiting = null;
    }

    if(currentWaiting == null && SittingCustomers.size() < tables.size())
    {
      if(Waves != currentWave++) //if not the max number of waves increment
      CreateNewCustomer();
      else
        EndGame();


    }



  }

  /**
   * If the current customer is too frustrated then make all customers in that group leave
   * decrement Frustration
   * @param group group to leave
   * @author Felix Seanor
   */
  public void FrustrationLeave(CustomerGroups group){
    SetCustomerGroupTarget(group,DoorTarget);
    group.table.relinquish();
    currentWaiting = null;
    WalkingBackCustomers.add(group);
    ModifyReputation(-1);
  }

  /**
   * Sets the pathfinding target of an entire group, making them walk to the location.
   * @param group
   * @param target
   * @author Felix Seanor
   */
  public void SetCustomerGroupTarget(CustomerGroups group, Vector2 target){
    for (Customer customer: group.Members
    ) {
        SetCustomerTarget(customer,target);
    }
  }

  /**
   * Sets an individual customers pathfinding target. Begins pathfinding
   * @param customer
   * @param target
   * @author Felix Seanor
   */

  public void SetCustomerTarget(Customer customer, Vector2 target){
    customer.GivePath(pathfinding.FindPath((int) customer.gameObject.position.x,
        (int) customer.gameObject.position.y, (int) target.x, (int) target.y,DistanceTest.Manhatten));

  }

  /**
   * Test remove customers.
   */
  void RemoveCustomerTest(){
    if(Gdx.input.isKeyJustPressed(
        Keys.S )&& currentWaiting != null){

      Customer customer = currentWaiting.RemoveFirstCustomer();
      SetCustomerTarget(customer,currentWaiting.table.GetNextSeat());
      ChangeMoney(MoneyPerCustomer);

      SetWaitingForOrderTarget();
    }

  }

  /**
   * End the game sequence. Call upper end game sequence.
   * @author Felix Seanor
   */
  private void EndGame(){
    //calculate win or loss

    //Calculate points
    EndOfGameValues values = new EndOfGameValues();
    values.score = Money;
    values.Won  = Reputation>0;
    CallEndGame.accept(values);
  }

  /**
   * Interface with the customer from the chefs via customer counters.
   * Checks to see if the given food is an ordered food
   * Makes customer sit down if so
   * @param item
   * @return True if accepted, otherwise false
   * @author Felix Seanor
   */
  public boolean tryGiveFood(Item item){
    int success = currentWaiting.SeeIfDishIsCorrect(item);





    if(success != -1) {
      currentWaiting.MembersSeatedOrWalking.add(currentWaiting.MembersInLine.remove(success));
      currentWaiting.updateFrustrationOnSucessfulService();
      SetCustomerTarget(currentWaiting.MembersSeatedOrWalking.get(currentWaiting.MembersSeatedOrWalking.size()-1),currentWaiting.table.GetNextSeat());
      SetWaitingForOrderTarget();
      ChangeMoney(MoneyPerCustomer);
    }
    return success !=-1;
  }

  /**
   * Checks to see if the current customer group can be expelled from the currenly waiting slot
   * @return True if so, otherwise false
   * @author Felix Seanor
   */
  boolean DoSatisfactionCheck(){
    return currentWaiting != null && currentWaiting.MembersInLine.size()==0 ;
  }



}
