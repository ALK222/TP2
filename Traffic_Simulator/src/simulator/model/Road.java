package simulator.model;

import java.util.List;

import org.json.JSONObject;

import exceptions.RoadException;

public abstract class Road extends SimulatedObject{
	
	//ATRIBUTTES
	
	

	protected Junction origin;
	
	protected Junction destination;
	
	protected int length;
	
	protected int max_speed;
	
	protected int current_speed_limit;
	
	protected int contamination_alarm;
	
	protected Weather weather_condition;
	
	protected int total_contamination;
	
	protected List<Vehicle> vehicles;

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws RoadException  {
		super(id);
		if(maxSpeed < 0) {
			throw new RoadException("Invalid max speed");
		}
		else {
			this.max_speed = maxSpeed;
		}
		if(contLimit < 0) {
			throw new RoadException("Invalid contamination limit");
		}
		else {
			this.contamination_alarm = contLimit;
		}
		if(srcJunc == null) {
			throw new RoadException("No initial junction provided");
		}
		else {
			this.origin = srcJunc;
			srcJunc.addExit(this);
		}
		if(destJunc == null) {
			throw new RoadException("No end junction provided");
		}
		else {
			this.destination = destJunc;
			destJunc.addBegining(this);
		}
	}
	
	protected abstract void updateSpeedLimit();
	
	protected abstract void calculateVehicleSpeed(Vehicle v);
	
	protected int getLenght() {
		return this.length;
	}

	
	
	protected void addContamination(int c) throws RoadException {
		if(c < 0) {
			throw new RoadException("Invalid contamination value");
		}
		else {
			this.total_contamination += c;
		}
	}
	
	protected abstract void reduceTotalContamination();
	
	protected void enter(Vehicle v) throws RoadException {
		if(v.getSpeed() != 0) {
			throw new RoadException("Incoming vehicles must be stopped");
		}
		else if(v.getLocation() != 0) {
			throw new RoadException("Invalid location for incoming vehicle");
		}
		else {
			this.vehicles.add(v);
		}
	}
	
	protected void exit(Vehicle v) {
		this.vehicles.remove(v);
	}
	
	
	protected void setWeather(Weather w) throws RoadException {
		if(w == null) {
			throw new RoadException("Invalid weather");
		}
		else {
			this.weather_condition = w;
		}
	}
	
	
	@Override
	void advance(int time) throws RoadException {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for(Vehicle v : vehicles) {
			calculateVehicleSpeed(v);
			v.advance(time);
		}
		//Recuerda shortear esta wea
		
	}

	@Override
	public JSONObject report() {
		JSONObject report = new JSONObject();
		report.append("id", this.getId());
		report.append("speedlimit", this.current_speed_limit);
		report.append("weather", this.weather_condition);
		report.append("co2", this.total_contamination);
		report.append("vehicles", vehicles);
		return report;
	}

}
