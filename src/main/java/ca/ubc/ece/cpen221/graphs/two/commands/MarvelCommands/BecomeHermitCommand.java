package ca.ubc.ece.cpen221.graphs.two.commands.MarvelCommands;

import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.InvalidCommandException;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.Marvel.Thanos;

import java.util.Set;

/**
 * A command that represents Thanos becoming a hermit and moving to the far corner
 * of the universe after completing his life's work of balancing the universe
 */
public class BecomeHermitCommand implements Command {

    Thanos t;
    Location hermitLocation;

    /**
     * Creates a new BecomeHermitCommand
     * @param thanos the Thanos object that will become a hermit
     *               is not null and has already executed the SnapCommand
     */
    public BecomeHermitCommand(Thanos thanos) {
        t = thanos;
        hermitLocation = new Location(0, 0);
    }

    @Override
    public void execute(World world) throws InvalidCommandException {
        if (!Util.isLocationEmpty(world, hermitLocation)) {
            Set<Item> item = world.searchSurroundings(hermitLocation, 0);
            for (Item i : item) {
                i.loseEnergy(Integer.MAX_VALUE);
            }
        }
        t.moveTo(hermitLocation);

    }
}
