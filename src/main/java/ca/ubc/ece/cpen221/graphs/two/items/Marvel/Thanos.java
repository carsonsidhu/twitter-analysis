package ca.ubc.ece.cpen221.graphs.two.items.Marvel;

import ca.ubc.ece.cpen221.graphs.two.Food;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.MarvelCommands.BecomeHermitCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.MarvelCommands.MoveTowardStoneCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.MarvelCommands.PutStoneInGauntletCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.MarvelCommands.SnapCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.WaitCommand;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.LivingItem;

import javax.swing.ImageIcon;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/**
 * An implementation of the character Thanos on his quest to balance the universe. He travels across the world
 * collecting the 6 infinity stones, sparing nothing that is in his path (and has less strength than him). As
 * he collects the stones and places them in the infinity gauntlet his power and abilities will increase.
 * Once he has all 6 stones he will complete the snap, wiping out half of the items in the world, and
 * achieving balance. His mission complete, he will now teleport to the far edge of the galaxy and live
 * a hermetic life (and do nothing till the end of time).
 */
public class Thanos implements LivingItem {

    //AF:
    // numStones: represents how many InfinityStones Thanos has in his gauntlet
    // strength: represents Thanos' strength, and determines what he can destroy
    // coolDown: represents how long Thanos must wait between actions
    // moveDistance: represents how far Thanos can move in one action
    // energy: represents how much energy Thanos has. If this goes to 0 or below he dies
    // dramaticPause: represents the time that Thanos waits before making the snap after getting all 6
    // InfinityStones because he's ~dramatic~
    // loc: represents where in the world Thanos is
    // isDead; represents if Thanos is dead
    // snapped: represents if Thanos has completed the snap yet
    // hermit: represents if Thanos has completed the snap and then moved to the far corner of the universe
    // to be a hermit
    // controlMinds: represents if Thanos has the ability to control minds, which is granted by the Mind Stone

    //RI:
    // numStones: starts at 0 and is never greater than 6
    // strength: starts at START_STRENGTH, then goes to Integer.MAX_VALUE when he obtains the Power Stone
    // coolDown: starts at START_COOL_DOWN, then goes to 1 when he obtains the Time Stone
    // moveDistance: starts at START_MOVE_DISTANCE, then goes to 5 when he obtains the Space Stone
    // energy: starts at START_ENERGY, then goes to Integer.MAX_VALUE when he obtains the Soul Stone
    // dramatic pause: starts at START_PAUSE. Once he has all 6 InfinityStones this number counts down by 1
    // each action, until it reaches 0, at which point he snaps
    // loc: is not null, and a valid location within the world that the Thanos is located in
    // isDead: starts as false, and becomes true if Thanos' energy is 0 or below
    // snapped: starts as false, and becomes true once he obtains the 6 InfinityStones and balances the universe
    // (executes the SnapCommand)
    // hermit: starts as false and becomes true once he has moved to the far corner of the universe to be a hermit
    // after snapping (executes the BecomeHermitCommand)
    // controlMinds: starts as false, and becomes true once he obtains the Mind Stone



    private static final int START_STRENGTH = 2000;
    private static final int START_COOL_DOWN = 5;
    private static final int START_MOVE_DISTANCE = 1;
    private static final int START_NUM_STONES = 0;
    private static final int START_ENERGY = 1000;
    private static final int START_PAUSE = 10;
    private static final ImageIcon THANOS_IMAGE = Util.loadImage("Thanos.gif");

    private int numStones;
    private int strength;
    private int coolDown;
    private int moveDistance;
    private int energy;
    private int dramaticPause;
    private Location loc;
    private boolean isDead;
    private boolean snapped;
    private boolean hermit;
    private boolean controlMinds;

    /**
     * Creates a new Thanos object with his default stats at the given location
     * @param loc the location that Thanos will be created at
     *            must be valid and not null
     */
    public Thanos(Location loc) {
        this.loc = loc;
        numStones = START_NUM_STONES;
        strength = START_STRENGTH;
        coolDown = START_COOL_DOWN;
        moveDistance = START_MOVE_DISTANCE;
        energy = START_ENERGY;
        isDead = false;
        snapped = false;
        hermit = false;
        dramaticPause = START_PAUSE;
        controlMinds = false;
    }


    @Override
    public Command getNextAction(World world) {
        if(hermit){return new WaitCommand();}
        if(snapped){
            hermit = true;
            return new BecomeHermitCommand(this);
        }
        if(numStones == 6){
            if(dramaticPause == 0) {

                snapped = true;
                return new SnapCommand();
            }
            if(dramaticPause == 10){
                System.out.println("I am inevitable.");
            }
            dramaticPause--;
            return new WaitCommand();
        }

       InfinityStone closestStone = (InfinityStone) closestStone(world);
        if(closestStone != null){
            if(loc.getDistance(closestStone.getLocation())== 1){
                return new PutStoneInGauntletCommand(closestStone,this);
            }

            return new MoveTowardStoneCommand(closestStone, this);
        }

        return new WaitCommand();

    }

    /**
     * Adds a stone to Thanos' Infinity gauntlet
     * Depending on which stone he obtains, Thanos' powers are increased in different ways
     * Space Stone: increases move distance
     * Time Stone: reduces cool down
     * Power Stone: increases strength
     * Soul Stone: increases energy
     * Mind Stone: gives Thanos the ability to control minds (if he's trying to move to a space
     * that is occupied by a MoveableItem, he will move the item to an empty adjacent location if possible)
     * Reality Stone: makes him cooler i.e. nothing (I didn't want to try and "alter reality" and break the
     * world implementation or UI)
     * @param s the stone to add to the gauntlet
     */
    public void addStoneToGauntlet(InfinityStone s){
        numStones++;

        String stoneName = s.getName();

        switch(stoneName) {
            case "Space Stone":
                moveDistance = 5;
                break;
            case "Time Stone":
                coolDown = 1;
                break;
            case "Power Stone":
                strength = Integer.MAX_VALUE;
                break;
            case "Soul Stone":
                energy = Integer.MAX_VALUE;
                break;
            case "Mind Stone":
                controlMinds = true;
        }
        //TODO add behaviour for each stone
    }

    //Resources
    // https://www.geeksforgeeks.org/convert-an-iterable-to-stream-in-java/
    // https://www.baeldung.com/java-stream-filter-lambda

    /**
     * Finds the closest stone to Thanos' current location
     * @return the closest stone
     */
    private Item closestStone(World w){
        Iterable<Item> items = w.getItems();
        Stream<Item> itemStream = StreamSupport.stream(items.spliterator(),false);
        Optional<Item> item = itemStream.filter(i -> i.getName().endsWith("Stone"))
            .reduce((a, b) -> loc.getDistance(a.getLocation()) > loc.getDistance(b.getLocation()) ? b:a);

        if(item.isEmpty()){
            return null;
        }

        return item.get();
    }

    public boolean canControlMinds(){return controlMinds;}

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public LivingItem breed() {
        return null;
    }

    @Override
    public void eat(Food food) {}

    @Override
    public int getCoolDownPeriod() {
        return coolDown;
    }


    @Override
    public void moveTo(Location targetLocation) {
        this.loc = targetLocation;
    }

    @Override
    public int getMovingRange() {
        return moveDistance;
    }

    @Override
    public ImageIcon getImage() {
        return THANOS_IMAGE;
    }

    @Override
    public String getName() {
        return "Thanos";
    }

    @Override
    public Location getLocation() {
        return loc;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public void loseEnergy(int energy) {

        if(this.energy - energy <=0){
            isDead = true;
        }
        this.energy -= energy;

    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }
}
