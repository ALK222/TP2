package simulator.control;

import java.io.InputStream;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	
	private TrafficSimulator traffic_simulator;
	
	private Factory<Event> events_factory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		this.traffic_simulator = sim;
		this.events_factory = eventsFactory;
	}
	
	public void loadEvents(InputStream in) {
		
	}
}
