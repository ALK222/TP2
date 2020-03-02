package simulator.model;

import java.util.List;

import exceptions.RoadException;
import exceptions.VehicleException;

public class NewVehicleEvent extends Event {

	private Vehicle v;
	
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) throws VehicleException {
		super(time);
		this.v = new Vehicle(id, maxSpeed, contClass, itinerary);
	}

	@Override
	void execute(RoadMap map) throws RoadException {
		v.moveToNextRoad();

	}

}
