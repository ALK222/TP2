package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

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
	
	protected void setContamination(int contamination) throws VehicleException{
		if(contamination < 0 && contamination > 10) {
			throw new VehicleException("Invalid contamination Value");
		}
		else {
			this.total_contamination = contamination;
		}
	}
	

	@Override
	protected void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	protected void moveToNextRoad() {
		//AAAAAAAAAAAAAA
	}


	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
