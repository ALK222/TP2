package simulator.model;

import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws RoadException, JunctionException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	protected void updateSpeedLimit() {
		//Si contaminaci�n excede limite -> l�mite 50%
		if(this.total_contamination > this.contamination_alarm) this.current_speed_limit= (int)(this.max_speed*0.5);
		else this.current_speed_limit= this.max_speed;
	}

	@Override
	protected void calculateVehicleSpeed(Vehicle v) throws VehicleException {
		if(this.weather_condition.equals(Weather.STORM))v.setSpeed((int)(current_speed_limit*0.8));
		else	v.setSpeed(current_speed_limit);

	}

	@Override
	protected void reduceTotalContamination() {
		int x = 0;
		if(this.weather_condition.equals( Weather.SUNNY)) x = 2;
		else if(this.weather_condition.equals(Weather.CLOUDY)) x = 3;
		else if(this.weather_condition.equals(Weather.RAINY)) x = 10;
		else if(this.weather_condition.equals(Weather.WINDY)) x = 15;
		else if(this.weather_condition.equals(Weather.STORM)) x = 20;
		
		this.total_contamination = (int) (((100.0 - x)/100.0) * this.total_contamination);
		if(	this.total_contamination < 0) 	this.total_contamination= 0;
	}

}
