package ca.ubc.ece.cpen221.graphs.two.items.vehicles;

import ca.ubc.ece.cpen221.graphs.two.Actor;
import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.items.MoveableItem;

/**
 * Represents a Vehicle which is both a moveableItem and Actor.
 */
public interface Vehicle extends MoveableItem, Actor {

    /**
     * Adds energy to the vehicle. One possible application is when refueling. Method should adjust the internal
     * energy between 0 and the Vehicle maximum
     * <p>
     * Precondition: energy to add >0
     * </p>
     */
    public void addEnergy(int energy);

    /**
     * Returns the current energy of the vehicle
     *
     * Postcondition: returns the energy
     */
    public int getEnergy();

    /**
     * Returns the direction in which the front of the vehicle is facing
     *
     * Postcondition: returns the direction
     */
    public Direction getDirection();

    /**
     * This method is invoked to total the vehicle. This kills the vehicle.
     */
    public void total();

    /**
     * This method specifies the new direction for the vehicle to travel in
     * Precondition: must be valid direction
     */
    public void turn(Direction direction);

    /**
     * Changes the velocity of the object by this acceleration. Positive speeds up to vehicle, negative slows it down.
     * Precondition: a valid integer
     */
    public void accelerate(int acceleration);

    /**
     * Returns the velocity of the vehicle
     * Postcondition: the current velocity of the vehicle
     */
    public int getVelocity();
}
