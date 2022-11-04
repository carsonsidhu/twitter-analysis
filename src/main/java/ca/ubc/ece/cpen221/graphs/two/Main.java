package ca.ubc.ece.cpen221.graphs.two;

import ca.ubc.ece.cpen221.graphs.two.ai.FoxAI;
import ca.ubc.ece.cpen221.graphs.two.ai.RabbitAI;
import ca.ubc.ece.cpen221.graphs.two.core.WorldImpl;
import ca.ubc.ece.cpen221.graphs.two.core.WorldUI;
import ca.ubc.ece.cpen221.graphs.two.items.Gardener;
import ca.ubc.ece.cpen221.graphs.two.items.Grass;
import ca.ubc.ece.cpen221.graphs.two.items.Marvel.InfinityStone;
import ca.ubc.ece.cpen221.graphs.two.items.Marvel.InvalidStoneTypeException;
import ca.ubc.ece.cpen221.graphs.two.items.Marvel.Thanos;
import ca.ubc.ece.cpen221.graphs.two.items.Zeus;
import ca.ubc.ece.cpen221.graphs.two.items.animals.Fox;
import ca.ubc.ece.cpen221.graphs.two.items.animals.Gnat;
import ca.ubc.ece.cpen221.graphs.two.items.animals.HarambeTheWesternLowlandGorilla;
import ca.ubc.ece.cpen221.graphs.two.items.animals.Rabbit;
import ca.ubc.ece.cpen221.graphs.two.items.vehicles.Delorian;

import javax.swing.SwingUtilities;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The Main class initializes a world with some {@link Grass}, {@link Rabbit}s,
 * {@link Fox}es, {@link Gnat}s, {@link Gardener}, etc.
 * <p>
 * You may modify or add Items/Actors to the World.
 */
public class Main {

    static final int X_DIM = 40;
    static final int Y_DIM = 40;
    static final int SPACES_PER_GRASS = 7;
    static final int INITIAL_GRASS = X_DIM * Y_DIM / SPACES_PER_GRASS;
    static final int INITIAL_GNATS = INITIAL_GRASS / 4;
    static final int INITIAL_RABBITS = INITIAL_GRASS / 4;
    static final int INITIAL_FOXES = INITIAL_GRASS / 32;
    static final int INITIAL_TIGERS = INITIAL_GRASS / 32;
    static final int INITIAL_BEARS = INITIAL_GRASS / 40;
    static final int INITIAL_HYENAS = INITIAL_GRASS / 32;
    static final int INITIAL_CARS = INITIAL_GRASS / 100;
    static final int INITIAL_TRUCKS = INITIAL_GRASS / 150;
    static final int INITIAL_MOTORCYCLES = INITIAL_GRASS / 64;
    static final int INITIAL_MEN = INITIAL_GRASS / 150;
    static final int INITIAL_WOMEN = INITIAL_GRASS / 100;
    static final int INITIAL_HUNTERS = INITIAL_GRASS / 150;
    static final int INITIAL_INFINITY_STONES = 6;
    static final int INITIAL_HARAMBES = 4;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().createAndShowWorld();
            }
        });
    }

    public void createAndShowWorld() {
        World world = new WorldImpl(X_DIM, Y_DIM);
        initialize(world);
        new WorldUI(world).show();
    }

    public void initialize(World world) {
        addGrass(world);
        world.addActor(new Gardener());
        world.addActor(new Zeus());

        addGnats(world);
        addRabbits(world);
        addFoxes(world);
        addDelorian(world);
        addInfinityStones(world);
        addHarambes(world);
        addThanos(world);
    }

    private void addGrass(World world) {
        for (int i = 0; i < INITIAL_GRASS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            world.addItem(new Grass(loc));
        }
    }

    private void addGnats(World world) {
        for (int i = 0; i < INITIAL_GNATS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Gnat gnat = new Gnat(loc);
            world.addItem(gnat);
            world.addActor(gnat);
        }
    }

    private void addFoxes(World world) {
        FoxAI foxAI = new FoxAI();
        for (int i = 0; i < INITIAL_FOXES; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Fox fox = new Fox(foxAI, loc);
            world.addItem(fox);
            world.addActor(fox);
        }
    }

    private void addRabbits(World world) {
        RabbitAI rabbitAI = new RabbitAI();
        for (int i = 0; i < INITIAL_RABBITS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Rabbit rabbit = new Rabbit(rabbitAI, loc);
            world.addItem(rabbit);
            world.addActor(rabbit);
        }
    }

    private void addDelorian(World world){

        Location loc = Util.getRandomEmptyLocation(world);
        Direction dir = Util.getRandomDirection();
        Delorian delorian = new Delorian(loc,dir,world.getHeight()+world.getWidth());
        world.addItem(delorian);
        world.addActor(delorian);
    }

    private void addInfinityStones(World world) {
        Set<String> stones = Set.of("Soul", "Power", "Mind", "Reality", "Time", "Space");
        for(String s : stones) {
            Location loc = Util.getRandomEmptyLocation(world);
            try {
                InfinityStone stone = new InfinityStone(loc, s);
                world.addItem(stone);
            }
            catch (Exception e){
                // TODO handle this exception better
            }
        }
    }

    private void addThanos(World world){
        Location loc = Util.getRandomEmptyLocation(world);
        Thanos t = new Thanos(loc);
        world.addItem(t);
        world.addActor(t);
    }

    private void addHarambes(World world) {
        for (int i = 0; i < INITIAL_HARAMBES; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            HarambeTheWesternLowlandGorilla harambe = new HarambeTheWesternLowlandGorilla(loc);
            world.addItem(harambe);
            world.addActor(harambe);
        }
    }

}