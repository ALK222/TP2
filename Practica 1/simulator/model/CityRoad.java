package simulator.model;

import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather)
			throws RoadException, JunctionException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	protected void updateSpeedLimit() {
		
		this.current_speed_limit = this.max_speed;

	}

	@Override
	protected int calculateVehicleSpeed(Vehicle v) throws VehicleException {

		return (int)Math.ceil(((11.0-v.getContamination())/11.0)*this.max_speed);

	}

	@Override
	protected void reduceTotalContamination() {
		int x = 0;
		if(this.weather_condition.equals( Weather.WINDY) || this.weather_condition.equals(Weather.STORM)) x = 10;
		else x=2;
		

		this.total_contamination -= (int) x;

		if(	this.total_contamination < 0) 	this.total_contamination= 0;

	}

}
