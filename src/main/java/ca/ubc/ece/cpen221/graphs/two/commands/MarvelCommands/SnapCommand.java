package ca.ubc.ece.cpen221.graphs.two.commands.MarvelCommands;

import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.LivingItem;
import ca.ubc.ece.cpen221.graphs.two.items.Marvel.Dust;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A command that eliminates half of the LivingItems in the world and replaces them with
 * Dust objects. Should only be called by Thanos when he has all 6 Infinity Stones
 */
public class SnapCommand implements Command {

    /**
     * Creates a new SnapCommand
     */
    public SnapCommand() {}

    @Override
    public void execute(World world) {
        Iterable<Item> items = world.getItems();

        int deathCount = 0;
        HashSet<Location> snappedLoc = new HashSet<>();
        Stream<Item> itemStream = StreamSupport.stream(items.spliterator(),false);
//        List<Item> livingItemList = itemStream.filter(i -> LivingItem.class.equals(i))
//            .collect(Collectors.toList());
        List<Item> livingItemList = itemStream.filter(i -> i instanceof LivingItem)
            .collect(Collectors.toList());
        //TODO Make it work with only LivingItems

        for(Item i: livingItemList){
            if(deathCount % 2 == 0){
                if(!(i.getName().equals("Thanos"))){

                    i.loseEnergy(Integer.MAX_VALUE);
                    snappedLoc.add(i.getLocation());
                }
            }
            deathCount++;
        }

        for(Location l : snappedLoc){
            Dust d = new Dust(l);
            world.addActor(d);
            world.addItem(d);
        }
        System.out.println("Perfectly balanced, as all things should be.");
    }
}
