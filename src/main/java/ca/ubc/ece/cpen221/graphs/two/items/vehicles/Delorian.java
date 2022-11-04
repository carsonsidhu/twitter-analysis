package ca.ubc.ece.cpen221.graphs.two.items.vehicles;

import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.MoveCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.VehicleCommands.CrashCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.VehicleCommands.LightningStrikeCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.VehicleCommands.TimeTravelCommand;
import ca.ubc.ece.cpen221.graphs.two.items.TimeTraveller;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the great time-travelling Delorian from the Back to the Future Franchise. It is only
 * re-energized when struck by a lightning bolt. It can travel back in time by assuming one of its own past states.
 *
 * AF:
 * As a vehicle it is defined by the velocity, direction, location and energy. As a time traveller it is defined by the
 * list of all past states which it has once had and its time relative to the natural timeline. Together it is a
 * time-travelling vehicle.
 *
 * RI:
 * pastStates must contain only past instances of this Delorian
 * location and direction must be valid for the stage. The energy and velocity must be non-negative.
 * The relativeTime must be non-positive
 */
public class Delorian implements Vehicle, TimeTraveller {

    private static final int INITIAL_ENERGY = 100;
    private static final int MAX_ENERGY = 121;
    private static final int STRENGTH = 1000;
    private static final int INITIAL_VELOCITY = 1;
    private static final ImageIcon delorianImage = Util.loadImage("delorean.gif");
    private static final int TURNING_THRESHOLD = 5;
    private static final int TIMETRAVEL_COST = 10;

    private int range;
    private List<Delorian> pastStates;
    private Location location;
    private Direction direction;
    private int energy;
    private int velocity = INITIAL_VELOCITY;
    private boolean isDead = false;
    private int relativeTime;
    private int accelCounter = 0;

    /**
     * Creates a new instance of a Delorian given a location, direction, and max view distance
     * @param loc the starting location of the Delorian
     * @param dir the starting direction of the Delorian
     * @param maxDistance the view-range of the Delorian. Must be the maximum of the stage.
     */
    public Delorian(Location loc, Direction dir, int maxDistance) {
        range = maxDistance;
        this.location = loc;
        this.direction = dir;
        this.energy = INITIAL_ENERGY;
        pastStates = new ArrayList<>();
    }

    /**
     * Creates a new instance of a Delorian from a previous one
     * @param old a previous instance of a Delorian
     */
    public Delorian(Delorian old){
        pastStates = new ArrayList<>(old.pastStates);
        range = old.range;
        location = new Location(old.getLocation().getX(),old.getLocation().getY());
        direction = old.direction;
        energy = old.energy;
        velocity = old.getVelocity();
        isDead = old.isDead;
        relativeTime = old.relativeTime;
        accelCounter = old.accelCounter;
    }

    @Override
    public void timeTravel(TimeTraveller past, int timeTravelled){
        Delorian pastDelorian;
        if(!(past.getClass().equals(this.getClass()))){
            throw new IllegalArgumentException("Must be a Delorian");
        }
        else{
            pastDelorian = this.getClass().cast(past);
        }
        this.pastStates = new ArrayList<>(pastDelorian.pastStates);
        this.location = new Location(pastDelorian.getLocation());
        this.direction = pastDelorian.direction;
        this.velocity = pastDelorian.velocity;
        this.isDead = false;
        this.relativeTime = timeTravelled;
        accelCounter = pastDelorian.accelCounter;
    }

    @Override
    public List<TimeTraveller> getPastStates(){
        return new ArrayList<>(this.pastStates);
    }

    @Override
    public void moveTo(Location loc) {
        location = loc;
    }

    @Override
    public int getMovingRange() {
        return range;
    }

    @Override
    public ImageIcon getImage() {
        return delorianImage;
    }

    @Override
    public String getName() {
        return "Delorian";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public void addEnergy(int energy) {
        if (this.energy + energy > MAX_ENERGY) {
            this.energy = MAX_ENERGY;
        } else {
            this.energy += energy;
        }
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public void total() {
        this.isDead = true;
    }

    @Override
    public void turn(Direction newDirection) {
        this.direction = newDirection;
    }

    @Override
    public void accelerate(int a) {
            velocity += a;
            if(velocity<1){
                velocity = 1;
            }
            if(velocity>10){
                velocity = 10;
            }
    }

    @Override
    public int getVelocity() {
        return velocity;
    }

    @Override
    public void loseEnergy(int energy) {
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

    @Override
    public int getCoolDownPeriod() {
        return 11-velocity;
    }

    /**
     * This method determines the next move of the Delorian based on its internal factors and immediate neighbors.
     * If it is moving at top-speed 88mph it will time travel by a random number of steps back in time.
     * If it is below the turn_speed_threshold it will either turn or move straight. If it is between these two
     * bounds it will go straight. The Delorian will tend to accelerate by 1 every movement unless it is approching a
     * wall in which case it will slow down and avoid the wall
     * @param world the current world
     * @return a command representing the Delorian's next action
     */
    @Override
    public Command getNextAction(World world) {
        this.pastStates.add(0,new Delorian(this));
        if (energy > 0) {
            if(accelCounter%1==0){
                accelerate(1);
            }
            accelCounter++;
            loseEnergy(1);
            if (velocity >9&&this.energy>=TIMETRAVEL_COST) {
                this.accelerate(-10);
                this.energy-=TIMETRAVEL_COST;
                int randStep = 0;
                do{
                    randStep = (int)(-getPastStates().size()*Math.random()-1);
                }while(randStep>=0||randStep<-this.getPastStates().size());

                return new TimeTravelCommand(randStep,this);

            } else if (!Util.isLocationEmpty(world, new Location(location, direction))) {
                return new CrashCommand(this);
            } else {
                if (velocity<TURNING_THRESHOLD) {
                    boolean westProx = this.location.getX()<=TURNING_THRESHOLD;
                    boolean eastProx = this.location.getX() >= world.getWidth() - TURNING_THRESHOLD;
                    boolean northProx = this.location.getY() <= TURNING_THRESHOLD;
                    boolean southProx = this.location.getY() >= world.getHeight() - TURNING_THRESHOLD;

                    if(eastProx||westProx||northProx||southProx) {
                        if (westProx) {
                            this.turn(Direction.SOUTH);
                        }
                        eastProx = this.location.getX() >= world.getWidth() - TURNING_THRESHOLD;
                        if (eastProx) {
                            this.turn(Direction.NORTH);
                        }
                        northProx = this.location.getY() <= TURNING_THRESHOLD;
                        southProx = this.location.getY() >= world.getHeight() - TURNING_THRESHOLD;
                        if (southProx) {
                            this.turn(Direction.EAST);
                        }
                        if (northProx) {
                            this.turn(Direction.SOUTH);
                        }
                        return new MoveCommand(this,new Location(this.location,this.direction));
                    }
                    else{
                        if ((int) (2 * Math.random()) >= 1) {
                            Direction newDirection;
                            if(this.direction==Direction.NORTH||this.direction==Direction.SOUTH){
                                do {
                                    newDirection = Util.getRandomDirection();
                                }while(newDirection==Direction.NORTH||newDirection==Direction.SOUTH);
                            }
                            else{
                                do {
                                    newDirection = Util.getRandomDirection();
                                }while(newDirection==Direction.EAST||newDirection==Direction.WEST);
                            }
                            this.turn(newDirection);
                        }
                        return new MoveCommand(this, new Location(location, direction));
                    }
                }
                else{
                    boolean westProx = this.location.getX()<=TURNING_THRESHOLD&&direction==Direction.WEST;
                    boolean eastProx = this.location.getX() >= world.getWidth() - TURNING_THRESHOLD&&direction==Direction.EAST;
                    boolean northProx = this.location.getY() <= TURNING_THRESHOLD&&direction==Direction.NORTH;
                    boolean southProx = this.location.getY() >= world.getHeight() - TURNING_THRESHOLD&&direction==Direction.SOUTH;

                    if(eastProx||westProx||northProx||southProx) {
                        accelerate(-2);
                        return new MoveCommand(this,new Location(this.location,this.direction));
                    }
                }
                return new MoveCommand(this, new Location(location, direction));
            }
        }
        else {
            return new LightningStrikeCommand(this);
        }
    }
}
