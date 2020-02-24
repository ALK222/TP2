package simulator.model;

public class NewCityRoad extends NewRoadEvent {

	NewCityRoad(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) {
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
		this.r = new CityRoad(id, srcJunc, destJunc, maxSpeed, co2Limit, length, weather);
	}

	

}
