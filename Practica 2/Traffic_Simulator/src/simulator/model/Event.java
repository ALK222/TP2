package simulator.model;

import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;

public abstract class Event implements Comparable<Event> {

	protected int _time;
	protected String id;

	Event(int time, String id) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
		this.id = id;
	}

	public int getTime() {
		return _time;
	}

	public String getId(){
		return id;
	}

	@Override
	public int compareTo(Event o) {
		if(this._time == o.getTime()) return 0;
		else if (this._time < o.getTime()) return -1; 
		return 1;
	}

	abstract void execute(RoadMap map) throws RoadException, VehicleException, JunctionException;
}
