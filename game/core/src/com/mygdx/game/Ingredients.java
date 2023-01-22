package com.mygdx.game;

public interface Ingredients {
    public boolean pickup(Chef chef);
    public void putDown(Chef chef, Workstations workstation);
}
