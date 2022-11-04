package ca.ubc.ece.cpen221.graphs.two.items.Marvel;

import ca.ubc.ece.cpen221.graphs.two.Actor;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.MarvelCommands.DustMoveCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.WaitCommand;
import ca.ubc.ece.cpen221.graphs.two.items.MoveableItem;

import javax.swing.ImageIcon;
import java.util.Random;

/**
 * An object that represents the remains (dust) of the LivingItems that fell victim to
 * Thanos' snap. Moves randomly to adjacent locations and settles (disappears) after a random number of
 * time steps, up to a threshold
 */
public class Dust implements MoveableItem, Actor {


    //AF:
    // location: represents where in the world the Dust is
    // dissipated: represents if the dust has settled (i.e. is dead and should be removed from world)
    // dissipationTime: is the maximum amount of time that the Dust will take to settle, represented by a
    // max number of moves the Dust can make. Dust can settle after making 1 move. When it actually
    // settles is determined randomly

    //RI:
    // location: is not null, and a valid location within the world that the Dust is located in
    // dissipated: is true when first created and must be false after dissipationTime = 0, but can also be
    // false beforehand due to random dissipation
    // dissipationTime: must start as START_DISSIPATION_TIME, then decrease each action taken, until it reaches 0
    // must not be less than 0 or greater than START_DISSIPATION_TIME

    private static final int MOVE_DISTANCE = 1;
    private static final ImageIcon DUST_IMAGE = Util.loadImage("dust.gif");
    private static final int COOL_DOWN_TIME = 10;
    private static final int START_DISSIPATION_TIME = 4;

    private Location location;
    private boolean dissipated;
    private int dissipationTime;

    /**
     * Creates a new Dust object at the specified location
     * @param l
     */
    public Dust(Location l){
        this.location = l;
        dissipated = false;
        dissipationTime = START_DISSIPATION_TIME;

    }


    @Override
    public void moveTo(Location targetLocation) { location = targetLocation; }


    @Override
    public int getMovingRange() {
        return MOVE_DISTANCE;
    }

    @Override
    public ImageIcon getImage() { return DUST_IMAGE; }

    @Override
    public String getName() {
        return "dust";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getStrength() {
        return 0;
    }

    @Override
    public void loseEnergy(int energy) { dissipated = true; }

    @Override
    public boolean isDead() {
        return dissipated;
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
    public int getCoolDownPeriod() {
        return COOL_DOWN_TIME;
    }

    @Override
    public Command getNextAction(World world) {
        if(dissipationTime < START_DISSIPATION_TIME){
            Random rand = new Random();
            int randNum = rand.nextInt();
            if(randNum % 2 == 0 || dissipationTime == 0) {
                dissipated = true;
                return new WaitCommand();
            }
        }

        dissipationTime--;
        return new DustMoveCommand(this);


    }
}
