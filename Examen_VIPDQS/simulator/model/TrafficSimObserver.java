package simulator.model;

import java.util.List;

import exceptions.EventException;
import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;

public interface TrafficSimObserver {
    abstract void onAdvanceStart(RoadMap map, List<Event> events, int time);
    
    abstract void onAdvanceEnd(RoadMap map, List<Event> events, int time);

    abstract void onEventAdded(RoadMap map, List<Event> events, Event e, int time);

    abstract void onReset(RoadMap map, List<Event> events, int time);
    
    abstract void onRegister(RoadMap map, List<Event> events, int time);
    
    abstract void onError(String err) throws VehicleException, EventException, JunctionException, RoadException;
}


