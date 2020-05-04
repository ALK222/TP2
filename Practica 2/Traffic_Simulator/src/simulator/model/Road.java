package simulator.model;

import java.util.Comparator;
import java.util.List;

import javax.swing.JComponent;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;
import simulator.misc.SortedArrayList;

public abstract class Road extends SimulatedObject {

	// ATRIBUTTES

	protected Junction origin;

	protected Junction destination;

	protected Integer length;

	protected Integer max_speed;

	protected Integer current_speed_limit;

	protected Integer contamination_alarm;

	protected Weather weather_condition;

	protected Integer total_contamination;

	protected List<Vehicle> vehicles;

	Road(String id, Junction srcJunc, Junction destJunc, int length, int contLimit, int maxSpeed, Weather weather)
			throws RoadException, JunctionException {
		super(id);
		this.vehicles = new SortedArrayList<Vehicle>();
		if (maxSpeed < 0)
			throw new RoadException("Invalid max speed");

		if (contLimit < 0)
			throw new RoadException("Invalid contamination limit");

		if (destJunc == null)
			throw new RoadException("No end junction provided");

		if (srcJunc == null)
			throw new RoadException("No initial junction provided");

		if (weather == null)
			throw new RoadException("Illegal weather");
		this.max_speed = maxSpeed;
		this.weather_condition = weather;
		this.contamination_alarm = contLimit;
		this.total_contamination = 0;
		this.length = length;
		this.origin = srcJunc;
		this.destination = destJunc;
		this.destination.addIncommingRoad(this);
		this.origin.addOutGoingRoad(this);
	}

	protected abstract void updateSpeedLimit();

	protected abstract int calculateVehicleSpeed(Vehicle v) throws VehicleException;

	public Integer getLenght() {
		return this.length;
	}

	protected void addContamination(int c) throws RoadException {
		if (c < 0) {
			throw new RoadException("Invalid contamination value");
		} else {
			this.total_contamination += c;
		}
	}

	protected abstract void reduceTotalContamination();

	protected void enter(Vehicle v) throws RoadException {
		if (v.getSpeed() != 0) {
			throw new RoadException("Incoming vehicles must be stopped");
		} else if (v.getLocation() != 0) {
			throw new RoadException("Invalid location for incoming vehicle");
		} else {
			this.vehicles.add(v);
		}
	}

	protected void exit(Vehicle v) {
		this.vehicles.remove(v);
	}

	protected void setWeather(Weather w) throws RoadException {
		if (w == null) {
			throw new RoadException("Invalid weather");
		} else {
			this.weather_condition = w;
		}
	}

	@Override
	void advance(int time) throws RoadException, VehicleException {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for (Vehicle v : vehicles) {
			if (!v.getStatus().equals(VehicleStatus.ARRIVED)) {
				v.setSpeed(this.calculateVehicleSpeed(v));
				v.advance(time);
			}
		}

		// Recuerda shortear esta wea

		Comparator<Vehicle> c = new Comparator<Vehicle>() {

			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (o1.getLocation() == o2.getLocation())
					return 0;
				else if (o1.getLocation() < o2.getLocation())
					return 1;
				return -1;
			}
		};

		this.vehicles.sort(c);
	}

	@Override
	public JSONObject report() {
		JSONObject report = new JSONObject();
		JSONArray ja = new JSONArray();
		report.put("id", (String) this.getId());
		report.put("speedlimit", (int) this.current_speed_limit);
		report.put("weather", (String) this.weather_condition.parse());
		report.put("co2", (int) this.total_contamination);
		for (Vehicle v : vehicles) {
			ja.put((String) v.getId());
		}
		report.put("vehicles", ja);
		return report;
	}

	public Junction getSrc() {
		return this.origin;
	}

	public Junction getDest() {
		return this.destination;
	}

	public Integer getTotalCO2() {
		return this.total_contamination;
	}

	public Integer getCO2Limit() {
		return this.contamination_alarm;
	}

	public String getWeather() {
		return this.weather_condition.parse();
	}

	public Integer getMaxSpeed() {
		return this.max_speed;
	}

	public Integer getCurrSpeed() {
		return this.current_speed_limit;
	}

}
