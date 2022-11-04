package ca.ubc.ece.cpen221.graphs.two.commands.VehicleCommands;

import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.InvalidCommandException;
import ca.ubc.ece.cpen221.graphs.two.items.TimeTraveller;

/**
 * This class creates an executable command which allows a timeTraveller to travel to the past.
 * <p>
 * AF:
 * TimeTraveller represents the object which will be travelling through time by the number of
 * steps specified in the constructor.
 *
 * RI:
 * steps must be <0 (back in time only)
 * traveller and Vehicle & World must be non-null
 */
public class TimeTravelCommand implements Command {

    private int steps;
    private TimeTraveller traveller;

    /**
     * Create a new TimeTravelCommand with a given timeStep and TimeTraveller
     *
     * @param steps     the number of steps back in time the TimeTraveller will travel. Must be <0.
     * @param traveller the TimeTraveller object
     */
    public TimeTravelCommand(int steps, TimeTraveller traveller) {
        this.steps = steps;
        this.traveller = traveller;
    }

    /**
     * Define the executable command which will transport the TimeTraveller through time
     *
     * @param world the world which contains the TimeTraveller
     * @throws InvalidCommandException when the time steps points to a time before the timeTraveller was created
     */
    @Override
    public void execute(World world) throws InvalidCommandException {
        if(-steps-1>traveller.getPastStates().size()){
            throw new InvalidCommandException("You didn't exist that long ago");
        }
        traveller.timeTravel(traveller.getPastStates().get(-steps - 1), steps);
    }
}
