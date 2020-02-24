package simulator.model;

import exceptions.RoadException;

public abstract class NewRoadEvent extends Event {
	protected Road r;

	NewRoadEvent(int time, String id, String srcJunc, String destJunc, int lenght, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		// TODO Auto-generated constructor stub
	}
	
	void execute(RoadMap map) throws RoadException {
		map.addRoad(r);
	}
	


}
