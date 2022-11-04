package ca.ubc.ece.cpen221.graphs.two.items.animals;

import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.Food;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.MoveCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.WaitCommand;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.LivingItem;

import javax.swing.ImageIcon;
import java.util.Set;

/**
 * An implementation of a creature who was simply too good for this world. He who was the best of us, and taken
 * far too soon: Harambe. May this implementation act as a memorial for the late great silverback. Should
 * Harambe have been with us today, we would not want us to treat him different than any other gorilla, for
 * all deserve to be equal. We have restored Harambe to what we believe to be his natural state, living a
 * blissful gorilla life of eating and breeding with his kind at all opportunities. We hope the life he lives
 * here allows him to indulge in the pleasures he so deserved in his all too short time on Earth. We love you
 * Harambe, and may your memory live on for generations to come.
 */
public class HarambeTheWesternLowlandGorilla implements LivingItem {

    //AF:
    // strength: represents Harambe's strength, and determines what he can be harmed by
    // energy: represents how much energy Harambe has. If this goes to 0 or below he dies
    // loc: represents where in the world Harambe is

    //RI:
    // strength: starts at START_STRENGTH
    // energy: starts at START_ENERGY, and decreases by 1 with each action
    // loc: is not null, and a valid location within the world that Harambe is located in


    private static final ImageIcon harambeImage = Util.loadImage("harambejpeg.jpg");

    private static final int MEAT_CALORIES = 2000;
    private static final int STRENGTH = 1000;
    private static final int INITIAL_ENERGY = 100;
    private static final int MAX_ENERGY = 200;
    private static final int MIN_BREEDING_ENERGY = 40;
    private static final int MOVING_RANGE = 7;

    private Location location;
    private int energy;

    /**
     * Create a new Harambe at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation the location where Harambe will be created
     */
    public HarambeTheWesternLowlandGorilla(Location initialLocation) {
        this.location = initialLocation;
        this.energy = INITIAL_ENERGY;
    }

    @Override
    public ImageIcon getImage() {
        return harambeImage;
    }

    @Override
    public String getName() {
        return "Harambe";
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
        return MEAT_CALORIES;
    }

    @Override
    public void loseEnergy(int energyLoss) {
        this.energy -= energyLoss;
    }

    @Override
    public boolean isDead() {
        return energy <= 0;
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }

    @Override
    public int getCoolDownPeriod() {
        return Util.RAND.nextInt(5) + 1;
    }

    @Override
    public Command getNextAction(World world) {
        Direction dir = Util.getRandomDirection();
        Location targetLocation = new Location(this.getLocation(), dir);
        if (Util.isValidLocation(world, targetLocation) &&
            Util.isLocationEmpty(world, targetLocation)) {
            return new MoveCommand(this, targetLocation);
        } else if (Util.isValidLocation(world, targetLocation)) {
            Set<Item> items = world.searchSurroundings(targetLocation, MOVING_RANGE);
            for (Item i : items) {
                if (i instanceof Food) {
                    eat(i);
                }
                if (i instanceof HarambeTheWesternLowlandGorilla) {
                    breed();
                }
            }
        }
        this.energy--;
        return new WaitCommand();
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    public int getMinimumBreedingEnergy() {
        return MIN_BREEDING_ENERGY;
    }

    @Override
    public LivingItem breed() {
        if (getEnergy() >= getMinimumBreedingEnergy()) {
            HarambeTheWesternLowlandGorilla child = new HarambeTheWesternLowlandGorilla(location);
            child.energy = energy / 4;
            this.energy = energy / 2;
            return child;
        } else {
            return null;
        }
    }

    @Override
    public void eat(Food food) {
        energy = Math.min(MAX_ENERGY, energy + food.getPlantCalories());
    }

    @Override
    public int getMovingRange() {
        return MOVING_RANGE;
    }

}

