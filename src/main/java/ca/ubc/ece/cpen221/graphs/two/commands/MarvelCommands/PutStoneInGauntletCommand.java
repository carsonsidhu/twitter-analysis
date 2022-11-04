package ca.ubc.ece.cpen221.graphs.two.commands.MarvelCommands;

import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.InvalidCommandException;
import ca.ubc.ece.cpen221.graphs.two.items.Marvel.InfinityStone;
import ca.ubc.ece.cpen221.graphs.two.items.Marvel.Thanos;

/**
 * A command that represents Thanos putting an Infinity Stone into his Infinity Gauntlet
 */
public class PutStoneInGauntletCommand implements Command {

    InfinityStone stone;
    Thanos t;

    /**
     * Creates a new PutStoneInGauntletCommand
     * @param stone the stone to put in Thanos' gauntlet
     *              is a stone in the world and not null
     * @param t the Thanos that will put the stone in his Infinity Gauntlet
     *          is not null and must be distance 1 away from stone
     */
    public PutStoneInGauntletCommand(InfinityStone stone, Thanos t){

        this.stone = stone;
        this.t = t;

    }

    @Override
    public void execute(World world) throws InvalidCommandException {
        t.addStoneToGauntlet(stone);
        stone.placeInGauntlet();
    }
}
