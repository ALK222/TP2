package simulator.model;

import exceptions.RoadException;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws RoadException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void updateSpeedLimit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void reduceTotalContamination() {
		int x = 0;
		if(this.weather_condition == Weather.SUNNY) x = 2;
		else if(this.weather_condition == Weather.CLOUDY) x = 3;
		else if(this.weather_condition == Weather.RAINY) x = 10;
		else if(this.weather_condition == Weather.WINDY) x = 15;
		else if(this.weather_condition == Weather.STORM) x = 20;
		
		this.total_contamination = (int) (((100.0 - x)/100.0) * this.total_contamination);
	}

}
