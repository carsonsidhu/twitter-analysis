package ca.ubc.ece.cpen221.graphs.two.items.Marvel;

import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.items.Item;

import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of the Infinity Stones. These stones are formed from collapsing
 * singularities at the Big Bang. They grant the possessor great power, but can only be
 * used by a select group of the most powerful beings in the Universe (and RDJ).
 */
public class InfinityStone implements Item {
    // AF:
    // location: represents where in the world the dust is
    // stoneType: represents which one of the 6 infinity stones this InfinityStone object is
    // isFound: represents if Thanos has found this InfiniyStone, i.e. it should be removed from the world

    //RI:
    // location: is not null, and a valid location within the world that the InfinityStone is located in
    // isFound: is false until Thanos puts the stone in his Infinity Gauntlet, and then is true
    // stoneType: is one of the 6 infinity stones, listed as keys in the STONE_IMAGES Map

    private static final int STRENGTH = Integer.MAX_VALUE;
    private static final Map<String, ImageIcon> STONE_IMAGES;

    static {
        STONE_IMAGES = new HashMap<>();
        STONE_IMAGES.put("Soul", Util.loadImage("SoulStone.gif"));
        STONE_IMAGES.put("Reality", Util.loadImage("RealityStone.gif"));
        STONE_IMAGES.put("Mind", Util.loadImage("MindStone.gif"));
        STONE_IMAGES.put("Space", Util.loadImage("SpaceStone.gif"));
        STONE_IMAGES.put("Power", Util.loadImage("PowerStone.gif"));
        STONE_IMAGES.put("Time", Util.loadImage("TimeStone.gif"));
    }

    private Location location;
    private boolean isFound;
    private String stoneType;

    /**
     * Creates a new InfinityStone item given a stone type and location
     *
     * @param location  the location to create the stone at
     *                  must be valid and empty
     * @param stoneType the type of stone to create
     *                  must be one of the 6 infinity stones as defined in the keys
     *                  of STONE_IMAGES
     * @throws InvalidStoneTypeException if the stone type does not match one of the
     *                                   6 infinity stones
     */
    public InfinityStone(Location location, String stoneType) throws InvalidStoneTypeException {
        this.location = location;
        if (!STONE_IMAGES.containsKey(stoneType)) {
            throw new InvalidStoneTypeException("The infinity stones are objects of great power," +
                "formed from collapsing singularities at the Big Bang. Unfortunately, this is not one" +
                "of them.");
        }
        this.stoneType = stoneType;
        isFound = false;

    }


    @Override
    public ImageIcon getImage() {
        return STONE_IMAGES.get(stoneType);
    }

    @Override
    public String getName() {
        return stoneType + " Stone";
    }

    @Override
    public Location getLocation() { return location; }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public void loseEnergy(int energy) {

    }

    @Override
    public boolean isDead() {
        return isFound;
    }

    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }

    /**
     * Places the Infinity Stone into Thanos' Infinity Gauntlet
     */

    public void placeInGauntlet() {
        isFound = true;
    }
}
