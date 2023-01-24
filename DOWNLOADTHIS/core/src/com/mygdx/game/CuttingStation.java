package com.mygdx.game;
import javax.annotation.processing.SupportedSourceVersion;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


public class CuttingStation extends Sprite implements Workstations{
	Rectangle cuttingStationRectangle;
	Rectangle chefRectangle;
	public CuttingStation(){
		// Define station dimensions on map
		cuttingStationRectangle = new Rectangle(83, 290, 80, 80);
	}
	@Override
	public void setTexture(String spriteState) {
		// TODO Auto-generated method stub
		// might not need as can just draw rectangle around object on map
	}

	@Override
	public Boolean acceptIngredients(Chef chef, Ingredient ingredient) {
		// Checks if ingredient selected = tomato, onion or lettuce
		chefRectangle = new Rectangle(chef.getX(),chef.getY(),chef.getHeight(),chef.getWidth());
		if ((ingredient.getName() == "tomato"  || ingredient.getName() == "onion" || ingredient.getName() == "lettuce")
		&& cuttingStationRectangle.overlaps(chefRectangle) 
		&& chef.isSpace()){
			System.out.println("Yes");
			return true;
		}
		return false;
	}

	@Override
	public void holdChef() {
		// TODO Auto-generated method stub
		
	}
    public boolean action(Chef chef){
		// Freeze Shef
		if (cuttingStationRectangle.overlaps(chefRectangle) && chef.isR()){
			// chef.freeze();
			System.out.println("CUTTING");
			return true;
		}
		
		// Start time + animation
		// Unfreeze chef
		// Collect ingredients
		
		return false;

	}

	public float getX(){
		return cuttingStationRectangle.x;
	}
	public float getY(){
		return cuttingStationRectangle.y;
	}
	public float getWidth(){
		return cuttingStationRectangle.width;
	}
	public float getHeight(){
		return cuttingStationRectangle.height;
	}
}
