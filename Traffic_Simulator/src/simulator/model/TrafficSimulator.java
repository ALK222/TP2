package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;
import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	// ATRIBUTTES

	private RoadMap mapa_carreteras;

	private List<Event> event_list;

	private int time;

	// COSNTRUCTOR

	public TrafficSimulator() {
		this.mapa_carreteras = new RoadMap();
		this.event_list = new SortedArrayList<Event>();
		this.time = 0;
	}

	// METHODS

	public void addEvent(Event e) {
		this.event_list.add(e);
	}

	public void advance() throws RoadException, VehicleException, JunctionException {

		// Iterator<Event> it = this.event_list.iterator();
		// Event aux = it.next();
		// while (it.hasNext() && aux.getTime() == this.time) {
		// 	aux.execute(mapa_carreteras);
		// 	it.remove();
		// 	aux = it.next();
		// }
		List<Event> aux1 = new ArrayList<>();
		++time;
		for (Event e : event_list) {
			if (e.getTime() == this.time) {
				aux1.add(e);
				e.execute(mapa_carreteras);
			}
		}

		event_list.removeAll(aux1);

		for (Junction j : mapa_carreteras.getJunctions()) {
			j.advance(time);
		}

		for (Road road : mapa_carreteras.getRoads()) {
			road.advance(time);
		}

		//++time;
	}

	public void reset() {
		mapa_carreteras.reset();
		event_list.clear();
		time = 0;
	}

	public JSONObject report() {
		JSONObject r = new JSONObject();
		r.put("time", (int)time);
		r.put("state", mapa_carreteras.report());
		return r;
	}
}
