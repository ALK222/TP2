package simulator.model;

import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;

public class NewCityRoad extends NewRoadEvent {

	public NewCityRoad(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) throws RoadException, JunctionException {
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) throws RoadException, VehicleException, JunctionException {
		r = new CityRoad(id, map.getJunction(srcJunc), map.getJunction(destJunc), maxSpeed, co2Limit, lenght, weather);
		map.addRoad(r);
	}

	

}
