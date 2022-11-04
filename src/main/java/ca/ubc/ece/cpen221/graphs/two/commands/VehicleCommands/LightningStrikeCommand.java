package ca.ubc.ece.cpen221.graphs.two.commands.VehicleCommands;

import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.InvalidCommandException;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.Lightning;
import ca.ubc.ece.cpen221.graphs.two.items.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates an executable command which allows a vehicle to be struck by lightning.
 * <p>
 * AF:
 * Vehicle represents the object which will be struck
 *
 * RI:
 * vehicle and worl must be non-null
 */
public class LightningStrikeCommand implements Command {

    private final Vehicle vehicle;

    /**
     * Create a new LightningStrikeCommand with a given vehicle
     *
     * @param vehicle   the vehicle to be struck.
     */
    public LightningStrikeCommand(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Define the executable command which will strike the vehicle with any lightning which is adjacent to it or do
     * nothing if there is no adjacent lightning.
     *
     * @param world the world which contains the Vehicle
     */
    @Override
    public void execute(World world) throws InvalidCommandException {
        Iterable<Item> nodes = world.getItems();
        List<Lightning> neighbors = new ArrayList<>();
        for (Item node : nodes) {
            if (node.getClass().equals(Lightning.class) && (
                    node.getLocation().equals(new Location(vehicle.getLocation(), Direction.NORTH)) ||
                            node.getLocation().equals(new Location(vehicle.getLocation(), Direction.SOUTH)) ||
                            node.getLocation().equals(new Location(vehicle.getLocation(), Direction.EAST)) ||
                            node.getLocation().equals(new Location(vehicle.getLocation(), Direction.WEST)))) {
                neighbors.add(Lightning.class.cast(node));
            }
        }

        if (neighbors.size() != 0) {
            Lightning lightning = neighbors.get(0);
            lightning.loseEnergy(Integer.MAX_VALUE);
            vehicle.addEnergy(lightning.getEnergy());
        }
    }
}
