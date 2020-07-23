package simulator.model;


import org.json.JSONObject;

import exceptions.RoadException;
import exceptions.VehicleException;;

public abstract class SimulatedObject implements Comparable<SimulatedObject>{

	protected String _id;

	SimulatedObject(String id) {
		_id = id;
	}

	public String getId() {
		return _id;
	}

	@Override
	public String toString() {
		return _id;
	}

	@Override
	public int compareTo(SimulatedObject o) {
		if(this._id.equals(o._id)) return 0;
		return -1;
	}

	abstract void advance(int time) throws RoadException, VehicleException;

	abstract public JSONObject report();

}
