package ca.ubc.ece.cpen221.graphs.two.items;

import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;

import javax.swing.ImageIcon;


/**
 * A class representing a bolt of lightning which appears on the stage and remains until an ajacent
 * character takes its energy
 * <p>
 * AF:
 * A lightning bolt which is defined by its location, current energy, and whether it is still alive. If it
 * is not alive, some energy has been taken from it and it can no longer be seen or interacted with
 * <p>
 * RI:
 * Location must be a valid location on the map
 */
public class Lightning implements Item {
    private final static ImageIcon lightningImage = Util.loadImage("lightning.gif");
    private final static int INITIAL_ENERGY = 121;

    private Location location;
    private int energy;
    private boolean isDead;

    /**
     * Creates a new lightning bolt at the specified location on the stage
     *
     * @param location the location where the bolt hits the stage
     */
    public Lightning(Location location) {
        this.energy = INITIAL_ENERGY;
        this.location = location;
        this.isDead = false;
    }

    /**
     * Returns the current energy of the bolt
     *
     * @return energy
     */
    public int getEnergy() {
        return energy;
    }

    @Override
    public ImageIcon getImage() {
        return lightningImage;
    }

    @Override
    public String getName() {
        return "lightning";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }

    @Override
    public void loseEnergy(int energy) {
        isDead = true;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public int getStrength() {
        return 5;
    }
}