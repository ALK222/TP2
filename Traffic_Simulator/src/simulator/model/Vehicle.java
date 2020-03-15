package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import exceptions.RoadException;
import exceptions.VehicleException;

public class Vehicle extends SimulatedObject{
	
	//ATRIBUTTES
	
	private List<Junction> itinerary;
	
	private int max_speed;
	
	private int current_speed;
	
	private VehicleStatus status;
	
	private Road current_road;
	
	private int location;
	
	private int contamination_grade;
	
	private int total_contamination;
	
	private int total_distance;
	
	//CONSTRUCTOR
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws VehicleException {
		super(id);
		if(maxSpeed < 0) {
			throw new VehicleException("Invalid speed");
		}
		else {
			this.max_speed = maxSpeed;
		}
		if(contClass < 0 && contClass > 10) {
			throw new VehicleException("Invalid contamination class");
		}
		else {
			this.contamination_grade = contClass;
		}
		if(itinerary.size() < 2) {
			throw new VehicleException("Invalid itinerary");
		}
		else {
			this.itinerary = Collections.unmodifiableList(itinerary);				
		}
		this.current_speed = 0;
		this.total_contamination = 0;
		this.total_distance = 0;
	}

	//METHODS
	
	protected void setSpeed(int speed) throws VehicleException {
		if(speed < 0) {
			throw new VehicleException("Invalid Speed");
		}
		else {
			this.current_speed = Math.min(speed, this.max_speed);
		}
	}
	
	protected int getSpeed() {
		return this.current_speed;
	}
	
	protected int getLocation() {
		return this.location;
	}
	protected int getContamination() {
		return this.contamination_grade;
	}
	
	protected Road getCurrentRoad() {
		return this.current_road;
	}
	protected void setContamination(int contamination) throws VehicleException{
		if(contamination < 0 && contamination > 10) {
			throw new VehicleException("Invalid contamination Value");
		}
		else {
			this.total_contamination = contamination;
		}
	}
	

	@Override
	protected void advance(int time) throws RoadException {
		if(status == VehicleStatus.TRAVELING) {
			int new_location = Math.min(this.location + this.current_speed, this.current_road.getLenght());
			int contamination = (new_location - this.location) * this.contamination_grade;
			this.total_contamination += contamination;
			this.current_road.addContamination(contamination);
			if(new_location >= this.current_road.getLenght()) {
				//entrar a junction
				
			}
			this.location = new_location;
		}
		
	}

	protected void moveToNextRoad() throws RoadException {
		this.current_road.exit(this);
		this.current_road.destination.enter(this);
	}


	@Override
	public JSONObject report() {
		JSONObject information = new JSONObject();
		information.append("id", this._id);
		information.append("speed", this.current_speed);
		information.append("distance", this.total_distance);
		information.append("Co2", this.total_contamination);
		information.append("class", this.contamination_grade);
		information.append("status", this.status);
		if(this.status != VehicleStatus.PENDING && this.status != VehicleStatus.ARRIVED) {
			information.append("road", this.current_road.getId());
			information.append("location", this.location);
		}
		return information;
	}

	
}
