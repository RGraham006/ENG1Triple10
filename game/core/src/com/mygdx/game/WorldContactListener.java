package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Enables ability to interact with two object (fixtures) on a map
 * using box2d. Also displays the log to the user so we know what we're 
 * interacting with
 *
 * 
 * @author Riko Puusepp
 * @author Robin Graham
 */
public class WorldContactListener implements ContactListener{
    Object objectA, objectB;
    /**
     * Beginning of the contact between two objects by getting their user data
     * 
     * @param contact contact listener object we pass into the class
     */
    @Override
    public void beginContact(Contact contact) {
            Gdx.app.log("Begin Contact", "");
            Object objectA = contact.getFixtureA().getBody().getUserData();
            Object objectB = contact.getFixtureB().getBody().getUserData();
            
            // System.out.println(objectA);
            // System.out.println(objectB);
    }
    /**
     * Log onto screen when object has stopped colliding
     * @param contact contact listener object after collision
     */
    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("End Contact", "");
        
    } 
    /**
     * How we interact with the objects while colliding
     * @param contact object
     * @param oldManifold object
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub
        
    }
    /**
     * Similar to pre solve but what we do after collision 
     * (not used in our program)
     * @param contact object
     * @param impulse object
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub
        
    }
    /**
     * Returns our objectA object, which will either be a chef or 
     * an ingredient or counter or walls
     * @return Object objectA
     */
    public Object getObjectA(){
        return objectA;
    }
    /**
     * Returns our objectB object, which will always be a chef
     * @return object objectB
     */
    public Object getObjectB(){
        return objectB;
    }
    
}
