package ca.ubc.ece.cpen221.graphs.two.commands.VehicleCommands;

import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.LivingItem;
import ca.ubc.ece.cpen221.graphs.two.items.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates an executable command which allows a vehicle to crash or run over something in front of it.
 * <p>
 * AF:
 * Vehicle represents the object which will be crashing or running-over.
 *
 * RI:
 * Vehicle & World must be non-null
 */
public class CrashCommand implements Command {

    private Vehicle vehicle;

    /**
     * Create a new CrashCommand with a given vehicle
     *
     * @param v   the vehicle which is about to crash or run over something.
     */
    public CrashCommand(Vehicle v) {
        this.vehicle = v;
    }

    /**
     * Define the executable command which will crash the vehicle into or have it run over whatever is in front of it
     *
     * @param world the world which contains the Vehicle
     */
    @Override
    public void execute(World world) {
        Iterable<Item> obstacles = world.getItems();
        List<Item> neighbors = new ArrayList<>();
        int strengthSum = 0;
        for (Item obstacle : obstacles) {
            if (obstacle.getLocation().equals(new Location(vehicle.getLocation(), vehicle.getDirection()))) {
                neighbors.add(obstacle);
                strengthSum += obstacle.getStrength();
            }
        }
        if (strengthSum >= vehicle.getStrength()) {
            vehicle.total();
        } else {
            vehicle.accelerate(-8 * (strengthSum / vehicle.getStrength()));
            for (Item neighbor : neighbors) {
                if (neighbor instanceof LivingItem) {
                    neighbor.loseEnergy(Integer.MAX_VALUE);
                } else if (neighbor instanceof Vehicle) {
                    Vehicle otherVehicle;
                    otherVehicle = (Vehicle) neighbor;
                    otherVehicle.total();
                }
            }
        }
    }
}
