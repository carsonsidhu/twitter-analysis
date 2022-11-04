package ca.ubc.ece.cpen221.graphs.two.items;

import ca.ubc.ece.cpen221.graphs.two.*;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.WaitCommand;
import ca.ubc.ece.cpen221.graphs.two.items.vehicles.Delorian;

import java.util.ArrayList;
import java.util.List;

/**
 * An omniscient actor who sends bolts of lightning adjacent to any Delorians which are out of energy. Each bolt
 * delivers 1.21 Gigawatts which is enough to power the Delorian
 */
public class Zeus implements Actor {

    /**
     * Gives the cooldown period of Zeus
     * @return cooldown
     */
    @Override
    public int getCoolDownPeriod() {
        return 10;
    }

    /**
     * A method which selects and returns Zeus' next command. If there are any Delorians which are out of energy he
     * will send a bolt of lightning. Otherwise he does nothing
     * @param world the current world
     * @return an executable which is Zeus' action for the next game tick
     */
    @Override
    public Command getNextAction(World world) {
        List<Delorian> deadDelorians = new ArrayList<>();
        for (Item item : world.getItems()) {
            if (item.getClass().equals(Delorian.class) && ((Delorian) item).getEnergy() == 0) {
                deadDelorians.add(Delorian.class.cast(item));
            }
        }
        if (deadDelorians.size() == 0) {
            return new WaitCommand();
        }
        Delorian deadDelorian = deadDelorians.get(0);
        Location loc = null;
        if (Util.isLocationEmpty(world, new Location(deadDelorian.getLocation(), Direction.NORTH))) {
            loc = new Location(deadDelorian.getLocation(), Direction.NORTH);
        } else if (Util.isLocationEmpty(world, new Location(deadDelorian.getLocation(), Direction.SOUTH))) {
            loc = new Location(deadDelorian.getLocation(), Direction.SOUTH);
        } else if (Util.isLocationEmpty(world, new Location(deadDelorian.getLocation(), Direction.WEST))) {
            loc = new Location(deadDelorian.getLocation(), Direction.WEST);
        } else if (Util.isLocationEmpty(world, new Location(deadDelorian.getLocation(), Direction.EAST))) {
            loc = new Location(deadDelorian.getLocation(), Direction.EAST);
        }

        if (loc == null) {
            return new WaitCommand();
        }

        Location finalLoc = loc;
        return world1 -> world1.addItem(new Lightning(finalLoc));
    }
}