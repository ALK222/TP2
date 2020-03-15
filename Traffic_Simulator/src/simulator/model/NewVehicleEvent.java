package simulator.model;

import java.util.ArrayList;
import java.util.List;

import exceptions.RoadException;
import exceptions.VehicleException;

public class NewVehicleEvent extends Event {

	private Vehicle v;

	private String id;

	private int maxSpeed;
	
	private int contClass;

	private List<String> itinerary;
	
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) throws VehicleException {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) throws RoadException, VehicleException {
		List<Junction> vItinerary = new ArrayList<Junction>();
		for(String i : itinerary){
			Junction j = map.getJunction(i);
			vItinerary.add(j);
		}
		v = new Vehicle(id, maxSpeed, contClass, vItinerary);
		v.moveToNextRoad();

	}

}
