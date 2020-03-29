package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import exceptions.RoadException;
import exceptions.VehicleException;

public class Vehicle extends SimulatedObject{

	// ATRIBUTTES

	private List<Junction> itinerary;

	private int max_speed;

	private int current_speed;

	private VehicleStatus status;

	private Road current_road;

	private int location;

	private int contamination_grade;

	private int total_contamination;

	private int total_distance;

	private int current_junction;

	// CONSTRUCTOR

	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws VehicleException {
		super(id);
		if (maxSpeed < 0) {
			throw new VehicleException("Invalid speed");
		} else {
			this.max_speed = maxSpeed;
		}
		if (contClass < 0 && contClass > 10) {
			throw new VehicleException("Invalid contamination class");
		} else {
			this.contamination_grade = contClass;
		}
		if (itinerary.size() < 2) {
			throw new VehicleException("Invalid itinerary");
		} else {
			this.itinerary = Collections.unmodifiableList(itinerary);
		}
		this.current_speed = 0;
		this.total_contamination = 0;
		this.total_distance = 0;
		this.status = VehicleStatus.PENDING;
		this.current_junction = 0;
	}

	// METHODS

	protected void setSpeed(int speed) throws VehicleException {
		if (speed < 0) {
			throw new VehicleException("Invalid Speed");
		} else if(this.status.equals(VehicleStatus.TRAVELING)) {
			
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
	protected VehicleStatus getStatus() {
		return this.status;
	}
	protected void setContamination(int contamination) throws VehicleException {
		if (contamination < 0 && contamination > 10) {
			throw new VehicleException("Invalid contamination Value");
		} else {
			this.contamination_grade = contamination;
		}
	}

	@Override
	protected void advance(int time) throws RoadException {
		if (status.equals(VehicleStatus.TRAVELING)) {
			int new_location = Math.min(this.location + this.current_speed, this.current_road.getLenght());
			
			int contamination = (new_location - this.location) * this.contamination_grade;
			
			this.total_contamination += contamination;
			
			this.current_road.addContamination(contamination);
			this.total_distance += new_location - this.location;
			this.location = new_location;
			
			if (new_location >= this.current_road.getLenght()) {
				// entrar a junction
				if(this.current_junction+1 == this.itinerary.size()) { 
					this.current_speed=0;
					this.status = VehicleStatus.ARRIVED;}
				else{
					this.status = VehicleStatus.WAITING;
					this.current_speed=0;
					this.current_road.destination.enter(this);
				}
			}
			
		}

	}

	protected void moveToNextRoad() throws RoadException, VehicleException {
		this.location = 0;
		this.current_speed = 0;
		if (!this.status.equals(VehicleStatus.PENDING) && !this.status.equals(VehicleStatus.WAITING)) throw new VehicleException("Illegal status");
		
		if (this.status.equals(VehicleStatus.PENDING)) {
			this.current_road = this.itinerary.get(0).roadTo(this.itinerary.get(0));
			this.status = VehicleStatus.TRAVELING;
			this.current_junction++;
		} else {
			this.current_road.exit(this);
			this.current_road = this.current_road.destination.roadTo(this.itinerary.get(current_junction));
			this.status = VehicleStatus.TRAVELING;
			this.current_junction++;
		}
		this.current_road.enter(this);
	}
	
	@Override
	public JSONObject report() {
		JSONObject information = new JSONObject();
		information.put("id", (String)this._id);
		information.put("speed", (int)this.current_speed);
		information.put("distance", (int)this.total_distance);
		information.put("co2", (int)this.total_contamination);
		information.put("class", (int)this.contamination_grade);
		information.put("status", (String)this.status.parse());
		if (this.status != VehicleStatus.PENDING && this.status != VehicleStatus.ARRIVED) {
			information.put("road", (String)this.current_road.getId());
			information.put("location", (int)this.location);
		}
		return information;
	}

}
