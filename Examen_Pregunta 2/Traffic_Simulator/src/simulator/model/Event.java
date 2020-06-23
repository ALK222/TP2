package simulator.model;

import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	public int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {
		if(this._time == o.getTime()) return 0;
		else if (this._time < o.getTime()) return -1; 
		return 1;
	}

	public abstract String toString();

	abstract void execute(RoadMap map) throws RoadException, VehicleException, JunctionException;
}
