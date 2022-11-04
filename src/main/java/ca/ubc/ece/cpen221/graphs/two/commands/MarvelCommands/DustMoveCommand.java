package ca.ubc.ece.cpen221.graphs.two.commands.MarvelCommands;

import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.InvalidCommandException;
import ca.ubc.ece.cpen221.graphs.two.items.Marvel.Dust;

/**
 * Moves a given Dust Item to a random adjacent empty location. If no valid
 * locations, does nothing
 */
public class DustMoveCommand implements Command {

    Dust d;

    /**
     * Creates a new DustMoveCommand
     * @param d the dust item to move
     *          must not be null
     */
    public DustMoveCommand(Dust d){
        this.d = d;
    }


    @Override
    public void execute(World world) throws InvalidCommandException {
        Location newLoc = Util.getRandomEmptyAdjacentLocation(world,d.getLocation());
        if(newLoc != null){
            d.moveTo(newLoc);
        }

    }
}
