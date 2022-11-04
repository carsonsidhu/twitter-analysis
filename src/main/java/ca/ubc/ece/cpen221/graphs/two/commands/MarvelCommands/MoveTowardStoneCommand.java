package ca.ubc.ece.cpen221.graphs.two.commands.MarvelCommands;

import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.InvalidCommandException;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.Marvel.InfinityStone;
import ca.ubc.ece.cpen221.graphs.two.items.Marvel.Thanos;
import ca.ubc.ece.cpen221.graphs.two.items.MoveableItem;

import java.util.Set;


/**
 * A command that moves Thanos towards a given Infinity Stone. When executed it will try to
 * move Thanos his current move distance (or less if he is closer than his current move distance to the stone)
 * in a direction towards the stone. If the location chosen is occupied by a MoveableItem and Thanos has the
 * Mind Stone he will move the item to an adjacent empty location. If he does not have the Mind Stone
 * or the item is not a MoveableItem,Thanos will destryoy the item
 * if Thanos' strength is greater than the occupying Item's strength.
 * Otherwise he will pick a new random direction and attempt to move there. If there are no valid move locations,
 * he will stay in the same location
 */
public class MoveTowardStoneCommand implements Command {

    InfinityStone s;
    Thanos t;

    /**
     * Creates a new MoveTowardStoneCommand
     * @param s the InfinityStone t will move towards
     *          is not null and in same world as Thanos
     * @param t the Thanos object that will be moving towards s
     *          is not null
     */
    public MoveTowardStoneCommand(InfinityStone s, Thanos t){
        this.s = s;
        this.t = t;
    }
    @Override
    public void execute(World world) throws InvalidCommandException {
        Location thanosLoc = t.getLocation();
        Location stoneLoc = s.getLocation();

        Direction moveDirection = Util.getDirectionTowards(thanosLoc,stoneLoc);

        int moveDistance = t.getMovingRange();
        if(moveDistance >= thanosLoc.getDistance(stoneLoc)){
            moveDistance = thanosLoc.getDistance(stoneLoc) - 1;
        }
        Location moveLocation = thanosLoc;
        boolean validLoc = false;
        int locsTried = 0;
        while(!validLoc && locsTried < 10) {
            locsTried++;
            switch (moveDirection) {
                case NORTH:
                    moveLocation = new Location(thanosLoc.getX(), thanosLoc.getY() - moveDistance);
                    break;
                case EAST:
                    moveLocation = new Location(thanosLoc.getX() + moveDistance, thanosLoc.getY());
                    break;
                case SOUTH:
                    moveLocation = new Location(thanosLoc.getX(), thanosLoc.getY() + moveDistance);
                    break;
                case WEST:
                    moveLocation = new Location(thanosLoc.getX() - moveDistance, thanosLoc.getY());
                    break;
                default:
                    moveLocation = thanosLoc;
            }
            if(Util.isLocationEmpty(world, moveLocation) && Util.isValidLocation(world, moveLocation)){
                validLoc = true;
            }
            else {
                if (!Util.isValidLocation(world, moveLocation)) {
                    moveDirection = Util.getRandomDirection();
                } else {
                    Set<Item> item = world.searchSurroundings(moveLocation, 0);
                    for (Item i : item) {
                        boolean itemMoved = false;
                        if(t.canControlMinds() && i instanceof MoveableItem){
                            Location itemLocation = Util.getRandomEmptyAdjacentLocation(world,moveLocation);
                            if(itemLocation != null){
                                ((MoveableItem) i).moveTo(itemLocation);
                                itemMoved = true;
                            }
                        }
                        if (i.getStrength() < t.getStrength() && !itemMoved) {
                            i.loseEnergy(Integer.MAX_VALUE);
                            validLoc = true;
                        } else {
                            moveDirection = Util.getRandomDirection();
                        }
                    }
                }
            }
        }
        if(locsTried == 10){
            moveLocation = thanosLoc;
        }
        t.moveTo(moveLocation);
    }
}
