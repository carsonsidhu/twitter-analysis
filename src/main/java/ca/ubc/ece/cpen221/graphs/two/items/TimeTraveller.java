package ca.ubc.ece.cpen221.graphs.two.items;

import ca.ubc.ece.cpen221.graphs.two.items.vehicles.Delorian;

import java.util.ArrayList;
import java.util.List;

public interface TimeTraveller {
    public void timeTravel(TimeTraveller past, int timeTravelled);

    public List<TimeTraveller> getPastStates();
}
