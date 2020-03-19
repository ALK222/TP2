package simulator.model;

import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;

public class NewInterCityRoad extends NewRoadEvent {

	public NewInterCityRoad(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) throws RoadException, JunctionException {
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) throws RoadException, VehicleException, JunctionException {
		r = new InterCityRoad(id, map.getJunction(srcJunc), map.getJunction(destJunc), lenght, co2Limit, maxSpeed, weather);
		map.addRoad(r);
	}

	

}